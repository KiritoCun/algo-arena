package vn.udn.dut.algoarena.customer.controller.test;

import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import org.json.JSONObject;

public class PistonApiCaller {
    public static void main(String[] args) {
        String submitedCode = """
            class Solution {
                public int[] twoSum(int[] nums, int target) {
                    Map<Integer, Integer> map = new HashMap<>();
                    for (int i = 0; i < nums.length; i++) {
                        int complement = target - nums[i];
                        if (map.containsKey(complement)) {
                            return new int[]{map.get(complement), i};
                        }
                        map.put(nums[i], i);
                    }
                    throw new IllegalArgumentException("No two sum solution");
                }
            }
            """;

        // Extract the method and add static modifier
        String extractedCode = submitedCode
                .replaceFirst("^class Solution \\{", "") // Remove "class Solution {"
                .replaceFirst("}$", "")                  // Remove last "}"
                .trim()
                .replace("public", "public static");     // Add static modifier to methods

        // Create the code to be executed
        String code = """
            import java.util.HashMap;
            import java.util.Map;

            public class Main {
                public static void main(String[] args) {
                    int[] nums = {2, 7, 11, 15};
                    int target = 9;
                    int[] result = twoSum(nums, target);
                    String actualResult = "[" + result[0] + "," + result[1] + "]";
                    String expectResult = "[0,1]";
                    boolean isPassed = expectResult.equals(actualResult);
                    System.out.print(isPassed);
                }

                ${SOLUTION_METHOD}
            }
            """;

        code = code.replace("${SOLUTION_METHOD}", extractedCode);

        // Define the language and version
        String language = "java";
        String version = "15.0.2";

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

                    // Lấy giá trị của key "run"
                    JSONObject runObject = jsonObject.getJSONObject("run");

                    // Lấy giá trị của key "stdout" bên trong "run"
                    String stdoutValue = runObject.getString("stdout");

                    // In kết quả
                    System.out.println("Value of 'stdout': " + stdoutValue);
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
    }
}
