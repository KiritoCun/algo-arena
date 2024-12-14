package vn.udn.dut.algoarena.customer.controller.test;

import org.json.JSONArray;
import org.json.JSONObject;

import javax.tools.JavaCompiler;
import javax.tools.ToolProvider;
import java.io.File;
import java.io.FileWriter;
import java.lang.reflect.Method;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

class TestCase {
    int[] nums;
    int target;
    int[] expected;

    TestCase(int[] nums, int target, int[] expected) {
        this.nums = nums;
        this.target = target;
        this.expected = expected;
    }

    // Static method to create test case from JSONObject
    public static TestCase fromJson(JSONObject json) {
        int[] nums = json.getJSONArray("nums").toList().stream().mapToInt(i -> (int) i).toArray();
        int target = json.getInt("target");
        int[] expected = json.getJSONArray("expected").toList().stream().mapToInt(i -> (int) i).toArray();
        return new TestCase(nums, target, expected);
    }
}

public class CodeEvaluator {

    public static void main(String[] args) {
        // User code as input (solution code)
        String userCode = """
            import java.util.*;
            public class UserSolution {
                public int[] twoSum(int[] nums, int target) {
                    Map<Integer, Integer> map = new HashMap<>();
                    for (int i = 0; i < nums.length; i++) {
                        int complement = target - nums[i];
                        if (map.containsKey(complement)) {
                            return new int[] { map.get(complement), i };
                        }
                        map.put(nums[i], i);
                    }
                    // Intentional bug: modified the exception
                    throw new IllegalArgumentException("Solution not found");
                }
            }
        """;

        // Test case input as string (JSON formatted)
        String testCaseString = """
            [
                {"nums": [2, 7, 11, 15], "target": 9, "expected": [0, 1]},
                {"nums": [3, 2, 4], "target": 6, "expected": [1, 2]},
                {"nums": [3, 3], "target": 6, "expected": [0, 1]}
            ]
        """;

        try {
            // Parse test case string
            JSONArray testCaseJsonArray = new JSONArray(testCaseString);
            List<TestCase> testCases = Arrays.asList(
                    testCaseJsonArray.toList().stream()
                            .map(obj -> new JSONObject((java.util.Map<?, ?>) obj)) // Convert each HashMap to JSONObject
                            .map(TestCase::fromJson)
                            .toArray(TestCase[]::new)
            );

            // Run evaluation
            List<String> result = evaluate(userCode, testCases);
            result.forEach(System.out::println);  // Print all results
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static List<String> evaluate(String userCode, List<TestCase> testCases) throws Exception {
        // Write user code to file
        String className = "UserSolution";
        String fileName = className + ".java";

        // Create directory for compiled files
        File dir = new File("out");
        if (!dir.exists()) {
            dir.mkdir();
        }

        // Write the user code to the file
        try (FileWriter fileWriter = new FileWriter(fileName)) {
            fileWriter.write(userCode);
        }

        // Compile the user code
        JavaCompiler compiler = ToolProvider.getSystemJavaCompiler();
        int compileResult = compiler.run(null, null, null, "-d", "out", fileName);
        if (compileResult != 0) {
            throw new Exception("Compilation failed");
        }

        // Load the compiled class from the "out" directory
        ClassLoader classLoader = new URLClassLoader(new URL[]{dir.toURI().toURL()});
        Class<?> clazz = Class.forName(className, true, classLoader);

        // Store results
        List<String> results = new ArrayList<>();

        // Execute user code for each test case
        for (TestCase testCase : testCases) {
            Object instance = clazz.getDeclaredConstructor().newInstance();
            Method method = clazz.getMethod("twoSum", int[].class, int.class);

            try {
                int[] actual = (int[]) method.invoke(instance, testCase.nums, testCase.target);
                if (Arrays.equals(actual, testCase.expected)) {
                    results.add("Test case passed for input: " + Arrays.toString(testCase.nums) + ", target: " + testCase.target);
                } else {
                    results.add("Test case failed for input: " + Arrays.toString(testCase.nums) + ", target: " + testCase.target);
                }
            } catch (Exception e) {
                results.add("Test case failed for input: " + Arrays.toString(testCase.nums) + ", target: " + testCase.target + " with exception: " + e.getMessage());
            }
        }

        return results;
    }
}
