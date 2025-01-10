package vn.udn.dut.algoarena.publicapi.service;

import org.json.JSONObject;
import org.springframework.stereotype.Service;
import lombok.RequiredArgsConstructor;
import vn.udn.dut.algoarena.port.domain.vo.TestcaseVo;
import vn.udn.dut.algoarena.publicapi.helper.JsonToLanguageDeclaration;
import vn.udn.dut.algoarena.publicapi.helper.MethodExtractorHelper;

import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.*;

@RequiredArgsConstructor
@Service
public class HomepageSearchService2 {

    public static List<String> submitSolution(String submittedCode, String language, String version, List<TestcaseVo> testcaseVoList) {
        List<String> testcaseResult = new ArrayList<>();

        // List to hold dynamic declarations and method signatures for all testcases
        List<String> testcaseDeclarations = new ArrayList<>();
        List<String> methodSignatures = new ArrayList<>();

        // Remove class and modify the method for Java
        if ("java".equals(language)) {
            submittedCode = submittedCode
                    .replaceFirst("^class Solution \\{", "") // Remove "class Solution {"
                    .replaceFirst("}$", "")                  // Remove last "}"
                    .trim()
                    .replace("public", "public static");     // Add static modifier to methods
        } else if ("c#".equals(language)) {
            submittedCode = submittedCode
                    .replaceFirst("^public class Solution \\{", "") // Remove "class Solution {"
                    .replaceFirst("}$", "")                  // Remove last "}"
                    .trim()
                    .replace("public", "public static");     // Add static modifier to methods;
        } else if ("php".equals(language)) {
            submittedCode = submittedCode
                    .replaceFirst("^class Solution \\{", "") // Remove "class Solution {"
                    .replaceFirst("}$", "")                  // Remove last "}"
                    .trim();
        } else if ("python".equals(language)) {
            submittedCode = submittedCode
                    .replaceFirst("^class Solution:", "") // Remove "class Solution:"
                    .replace("self, ", "")
                    .trim();
        }

        // Extract method signatures and declarations dynamically
        for (int i = 0; i < testcaseVoList.size(); i++) {
            String methodSignature = MethodExtractorHelper.extractMethodSignature(submittedCode, String.valueOf(i + 1), language);
            String testcaseJson = testcaseVoList.get(i).getTestcaseJson();
            String testcaseDeclaration = JsonToLanguageDeclaration.convertJsonToDeclaration(testcaseJson, String.valueOf(i + 1), language);

            methodSignatures.add(methodSignature);
            testcaseDeclarations.add(testcaseDeclaration);
        }

        // Switch based on the language
        String code = switch (language.toLowerCase()) {
            case "java" -> getJavaCode(testcaseDeclarations, methodSignatures, submittedCode);
            case "javascript" -> getJavaScriptCode(testcaseDeclarations, methodSignatures, submittedCode);
            case "php" -> getPhpCode(testcaseDeclarations, methodSignatures, submittedCode);
            case "c#" -> getCSharpCode(testcaseDeclarations, methodSignatures, submittedCode);
            case "python" -> getPythonCode(testcaseDeclarations, methodSignatures, submittedCode);
            case "go" -> getGoCode(testcaseDeclarations, methodSignatures, submittedCode);
            // Add other languages here as needed
            default -> throw new IllegalArgumentException("Unsupported language: " + language);
        };

        // Send code to the execution API
        try {
            // Create JSON payload using org.json library
            JSONObject jsonPayload = new JSONObject();
            jsonPayload.put("language", language);
            jsonPayload.put("version", version);

            // Add the code as a file content
            Map<String, String> file = new HashMap<>();
            file.put("content", code);
            jsonPayload.put("files", new org.json.JSONArray().put(new JSONObject(file)));

            // Set up the HTTP connection
            URL url = new URL("https://emkc.org/api/v2/piston/execute");
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("POST");
            connection.setRequestProperty("Content-Type", "application/json");
            connection.setDoOutput(true);

            // Write the JSON payload to the request body
            try (OutputStream os = connection.getOutputStream()) {
                byte[] input = jsonPayload.toString().getBytes(StandardCharsets.UTF_8);
                os.write(input, 0, input.length);
            }

            // Read the response
            int status = connection.getResponseCode();
            if (status == 200) {
                try (Scanner scanner = new Scanner(connection.getInputStream(), StandardCharsets.UTF_8)) {
                    String response = scanner.useDelimiter("\\A").next();
                    System.out.println("Response from Piston API: " + response);

                    // Parse JSON
                    JSONObject jsonObject = new JSONObject(response);

                    // Get the "stdout" value from the "run" object
                    JSONObject runObject = jsonObject.getJSONObject("run");
                    String stdoutValue = runObject.getString("stdout");

                    // Print the result
                    System.out.println("Value of 'stdout': " + stdoutValue);

                    if (!"".equals(stdoutValue) &&  stdoutValue.length() < 50) {
                        if ("python".equals(language)) {
                            testcaseResult = Arrays.stream((stdoutValue.trim().toLowerCase().replaceAll(" ", "").replaceAll("\n", "")).split(",")).toList();
                        } else {
                            testcaseResult = Arrays.stream((stdoutValue.trim().replaceAll("\n", "")).split(",")).toList();//Replace \n for javascript
                        }
                    } else {
                        String stderr = runObject.getString("stderr");
                        if (stderr.isBlank() || stderr.isEmpty()) {
                            stderr = stdoutValue;
                        }
                        List<String> errList = new ArrayList<>();
                        errList.add(stderr);
                        testcaseResult = errList;
                    }

                }
            } else {
                try (Scanner scanner = new Scanner(connection.getErrorStream(), StandardCharsets.UTF_8)) {
                    String errorResponse = scanner.useDelimiter("\\A").next();
                    System.out.println("Failed to execute code. HTTP status: " + status);
                    System.out.println("Error response: " + errorResponse);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return testcaseResult;
    }

    private static String getJavaCode(List<String> testcaseDeclarations, List<String> methodSignatures, String submittedCode) {
        StringBuilder testcaseDeclarationsCode = new StringBuilder();
        StringBuilder methodSignaturesCode = new StringBuilder();
        StringBuilder resultComparisonsCode = new StringBuilder();

        // Build the dynamic code for testcases and method signatures
        for (int i = 0; i < testcaseDeclarations.size(); i++) {
            testcaseDeclarationsCode.append(testcaseDeclarations.get(i)).append("\n");
            methodSignaturesCode.append(methodSignatures.get(i)).append("\n");

            // Dynamically create comparison code for each testcase
            resultComparisonsCode.append("System.out.print(compare(result" + (i + 1) + ", expect" + (i + 1) + "));\n");
            resultComparisonsCode.append("System.out.print(\",\");\n");
        }

        return """
                import java.util.*;

                public class Main {
                    public static void main(String[] args) {
                        try {
                            ${TESTCASE_DECLARATIONS}
                            ${METHOD_SIGNATURES}
                            // You can add result comparisons for each testcase dynamically
                            ${RESULT_COMPARISONS}
                        } catch (Exception e) {
                            System.out.print(e.getMessage());
                        }
                    }

                    ${SOLUTION_METHOD}

                    private static boolean compare(Object a, Object b) {
                        if (a instanceof Object[] && b instanceof Object[]) {
                            return Arrays.deepEquals((Object[]) a, (Object[]) b);
                        } else if (a instanceof boolean[] && b instanceof boolean[]) {
                            return Arrays.equals((boolean[]) a, (boolean[]) b);
                        } else if (a instanceof int[] && b instanceof int[]) {
                            return Arrays.equals((int[]) a, (int[]) b);
                        } else if (a instanceof double[] && b instanceof double[]) {
                            return Arrays.equals((double[]) a, (double[]) b);
                        } else {
                            return Objects.equals(a, b);
                        }
                    }
                }
                """
                .replace("${TESTCASE_DECLARATIONS}", testcaseDeclarationsCode.toString())
                .replace("${METHOD_SIGNATURES}", methodSignaturesCode.toString())
                .replace("${RESULT_COMPARISONS}", resultComparisonsCode.toString())
                .replace("${SOLUTION_METHOD}", submittedCode);
    }

    private static String getJavaScriptCode(List<String> testcaseDeclarations, List<String> methodSignatures, String submittedCode) {
        StringBuilder testcaseDeclarationsCode = new StringBuilder();
        StringBuilder methodSignaturesCode = new StringBuilder();
        StringBuilder resultComparisonsCode = new StringBuilder();

        // Xây dựng động code cho testcase và phương thức
        for (int i = 0; i < testcaseDeclarations.size(); i++) {
            testcaseDeclarationsCode.append(testcaseDeclarations.get(i)).append("\n");
            methodSignaturesCode.append(methodSignatures.get(i)).append("\n");

            // Tạo mã so sánh cho mỗi testcase
            resultComparisonsCode.append("console.log(compare(result" + (i + 1) + ", expect" + (i + 1) + "));\n");
            resultComparisonsCode.append("console.log(\",\");\n");
        }

        return """
            ${SOLUTION_METHOD}
            
            try {
                ${TESTCASE_DECLARATIONS}
                ${METHOD_SIGNATURES}
                
                // In kết quả so sánh cho từng testcase
                ${RESULT_COMPARISONS}
            } catch (e) {
                console.error(e);
            }

            function compare(a, b) {
                if (Array.isArray(a) && Array.isArray(b)) {
                    return JSON.stringify(a) === JSON.stringify(b);
                } else {
                    return a === b;
                }
            }
            """
                .replace("${TESTCASE_DECLARATIONS}", testcaseDeclarationsCode.toString())
                .replace("${METHOD_SIGNATURES}", methodSignaturesCode.toString())
                .replace("${SOLUTION_METHOD}", submittedCode)
                .replace("${RESULT_COMPARISONS}", resultComparisonsCode.toString());
    }

    private static String getPhpCode(List<String> testcaseDeclarations, List<String> methodSignatures, String submittedCode) {
        StringBuilder testcaseDeclarationsCode = new StringBuilder();
        StringBuilder methodSignaturesCode = new StringBuilder();
        StringBuilder resultComparisonsCode = new StringBuilder();

        // Xây dựng động code cho testcase và phương thức
        for (int i = 0; i < testcaseDeclarations.size(); i++) {
            testcaseDeclarationsCode.append(testcaseDeclarations.get(i)).append("\n");
            methodSignaturesCode.append(methodSignatures.get(i)).append("\n");

            // Tạo mã so sánh cho mỗi testcase
            resultComparisonsCode.append("echo compare($result" + (i + 1) + ", $expect" + (i + 1) + ") ? \"true\" : \"false\";\n");
            resultComparisonsCode.append("echo \",\";\n");
        }

        return """
            <?php

            function test() {
                try {
                    ${TESTCASE_DECLARATIONS}
                    ${METHOD_SIGNATURES}
                    
                    // In kết quả so sánh cho từng testcase
                    ${RESULT_COMPARISONS}
                } catch (Exception $e) {
                    echo $e->getMessage();
                }
            }

            ${SOLUTION_METHOD}

            function compare($a, $b) {
                if (is_array($a) && is_array($b)) {
                    return json_encode($a) === json_encode($b);
                } else {
                    return $a === $b;
                }
            }

            test();
            """
                .replace("${TESTCASE_DECLARATIONS}", testcaseDeclarationsCode.toString())
                .replace("${METHOD_SIGNATURES}", methodSignaturesCode.toString())
                .replace("${SOLUTION_METHOD}", submittedCode)
                .replace("${RESULT_COMPARISONS}", resultComparisonsCode.toString());
    }

    private static String getCSharpCode(List<String> testcaseDeclarations, List<String> methodSignatures, String submittedCode) {
        StringBuilder testcaseDeclarationsCode = new StringBuilder();
        StringBuilder methodSignaturesCode = new StringBuilder();
        StringBuilder resultComparisonsCode = new StringBuilder();

        // Xây dựng động code cho testcase và phương thức
        for (int i = 0; i < testcaseDeclarations.size(); i++) {
            testcaseDeclarationsCode.append(testcaseDeclarations.get(i)).append("\n");
            methodSignaturesCode.append(methodSignatures.get(i)).append("\n");

            // Tạo mã so sánh cho mỗi testcase
            resultComparisonsCode.append("Console.Write(Compare(result" + (i + 1) + ", expect" + (i + 1) + ") ? \"true\" : \"false\");\n");
            resultComparisonsCode.append("Console.Write(\",\");\n");
        }

        return """
            using System;
            using System.Linq;
            using System.Text;
            using System.Collections;
            using System.Collections.Generic;

            class Program {
                static void Main() {
                    try {
                        ${TESTCASE_DECLARATIONS}
                        ${METHOD_SIGNATURES}
                        
                        // In kết quả so sánh cho tất cả các testcase
                        ${RESULT_COMPARISONS}
                    } catch (Exception e) {
                        Console.Write(e.Message);
                    }
                }

                public static bool Compare(object a, object b)
                {
                    if (a is Array arrayA && b is Array arrayB)
                    {
                        return CompareArrays(arrayA, arrayB);
                    }
                    else
                    {
                        return Equals(a, b);
                    }
                }

                private static bool CompareArrays(Array arrayA, Array arrayB)
                {
                    if (arrayA.Length != arrayB.Length)
                        return false;

                    var enumeratorA = arrayA.Cast<object>().GetEnumerator();
                    var enumeratorB = arrayB.Cast<object>().GetEnumerator();

                    while (enumeratorA.MoveNext() && enumeratorB.MoveNext())
                    {
                        if (!Compare(enumeratorA.Current, enumeratorB.Current))
                            return false;
                    }

                    return true;
                }

                ${SOLUTION_METHOD}
            }
            """
                .replace("${TESTCASE_DECLARATIONS}", testcaseDeclarationsCode.toString())
                .replace("${METHOD_SIGNATURES}", methodSignaturesCode.toString())
                .replace("${SOLUTION_METHOD}", submittedCode)
                .replace("${RESULT_COMPARISONS}", resultComparisonsCode.toString());
    }

    private static String getPythonCode(List<String> testcaseDeclarations, List<String> methodSignatures, String submittedCode) {
        StringBuilder testcaseDeclarationsCode = new StringBuilder();
        StringBuilder methodSignaturesCode = new StringBuilder();
        StringBuilder resultComparisonsCode = new StringBuilder();

        // Xây dựng động code cho testcase và phương thức
        for (int i = 0; i < testcaseDeclarations.size(); i++) {
            testcaseDeclarationsCode.append("        ").append(testcaseDeclarations.get(i)).append("\n");
            methodSignaturesCode.append("        ").append(methodSignatures.get(i)).append("\n");

            // Tạo mã so sánh cho mỗi testcase
            resultComparisonsCode.append("        ").append("print(compare(result" + (i + 1) + ", expect" + (i + 1) + "))\n");
            resultComparisonsCode.append("        ").append("print(\",\", end=\" \")\n");
        }

        return """
            from typing import List

            def compare(a, b):
                if isinstance(a, list) and isinstance(b, list):
                    return a == b
                return a == b

            ${SOLUTION_METHOD}

            def main():
                try:
            ${TESTCASE_DECLARATIONS}
            ${METHOD_SIGNATURES}

                    # In kết quả so sánh cho tất cả các testcase
            ${RESULT_COMPARISONS}

                except Exception as e:
                    print(str(e))

            if __name__ == "__main__":
                main()
            """
                .replace("${TESTCASE_DECLARATIONS}", testcaseDeclarationsCode.toString())
                .replace("${METHOD_SIGNATURES}", methodSignaturesCode.toString())
                .replace("${SOLUTION_METHOD}", submittedCode)
                .replace("${RESULT_COMPARISONS}", resultComparisonsCode.toString());
    }

    private static String getGoCode(List<String> testcaseDeclarations, List<String> methodSignatures, String submittedCode) {
        StringBuilder testcaseDeclarationsCode = new StringBuilder();
        StringBuilder methodSignaturesCode = new StringBuilder();
        StringBuilder resultComparisonsCode = new StringBuilder();

        // Xây dựng động code cho testcase và phương thức
        for (int i = 0; i < testcaseDeclarations.size(); i++) {
            testcaseDeclarationsCode.append(testcaseDeclarations.get(i)).append("\n");
            methodSignaturesCode.append(methodSignatures.get(i)).append("\n");

            // Tạo mã so sánh cho mỗi testcase
            resultComparisonsCode.append("fmt.Println(compare(result" + (i + 1) + ", expect" + (i + 1) + "))\n");
            resultComparisonsCode.append("fmt.Println(\",\")\n");
        }

        return """
            package main

            import (
                "fmt"
                "reflect"
            )

            func compare(a, b interface{}) bool {
                // Handle slices
                if reflect.TypeOf(a) != reflect.TypeOf(b) {
                    return false
                }

                switch aTyped := a.(type) {
                case []int:
                    bTyped, ok := b.([]int)
                    if !ok {
                        return false
                    }
                    return reflect.DeepEqual(aTyped, bTyped)
                case []bool:
                    bTyped, ok := b.([]bool)
                    if !ok {
                        return false
                    }
                    return reflect.DeepEqual(aTyped, bTyped)
                case []float64:
                    bTyped, ok := b.([]float64)
                    if !ok {
                        return false
                    }
                    return reflect.DeepEqual(aTyped, bTyped)
                case []interface{}:
                    bTyped, ok := b.([]interface{})
                    if !ok {
                        return false
                    }
                    return reflect.DeepEqual(aTyped, bTyped)
                default:
                    // Fallback for other types
                    return reflect.DeepEqual(a, b)
                }
            }

            func main() {
                defer func() {
                    if r := recover(); r != nil {
                        fmt.Println(r)
                    }
                }()

                ${TESTCASE_DECLARATIONS}
                ${METHOD_SIGNATURES}

                // In kết quả so sánh cho tất cả các testcase
                ${RESULT_COMPARISONS}
            }

            ${SOLUTION_METHOD}
            """
                .replace("${TESTCASE_DECLARATIONS}", testcaseDeclarationsCode.toString())
                .replace("${METHOD_SIGNATURES}", methodSignaturesCode.toString())
                .replace("${SOLUTION_METHOD}", submittedCode)
                .replace("${RESULT_COMPARISONS}", resultComparisonsCode.toString());
    }
}
