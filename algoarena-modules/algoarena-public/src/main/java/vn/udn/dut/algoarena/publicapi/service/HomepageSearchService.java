package vn.udn.dut.algoarena.publicapi.service;

import org.json.JSONObject;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import vn.udn.dut.algoarena.port.domain.vo.TestcaseVo;
import vn.udn.dut.algoarena.publicapi.helper.JsonToJavaDeclaration;
import vn.udn.dut.algoarena.publicapi.helper.MethodExtractorHelper;

import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

/**
 *
 * @author HoaLD
 */
@RequiredArgsConstructor
@Service
public class HomepageSearchService {

    public static boolean submitSolution(String submittedCode, String language, String version, TestcaseVo testcaseVo) {
        boolean passed = false;
        String methodSignature = MethodExtractorHelper.extractMethodSignature(submittedCode);

        // Extract the method and add static modifier
        String extractedCode = submittedCode
                .replaceFirst("^class Solution \\{", "") // Remove "class Solution {"
                .replaceFirst("}$", "")                  // Remove last "}"
                .trim()
                .replace("public", "public static");     // Add static modifier to methods

        String testcase = testcaseVo.getTestcaseJson();
        //String testcase = "{ \"s\": \"([)]\",\"expect\": false }";
        String testcaseDeclaration = JsonToJavaDeclaration.convertJsonToJavaDeclaration(testcase);
        String code = """
            import java.util.*;

            public class Main {
                public static void main(String[] args) {
                    try {
                        ${TESTCASE_DECLARATION}
                        ${METHOD_SIGNATURE}
                        System.out.print(compare(result, expect));
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
            """;

        code = code.replace("${TESTCASE_DECLARATION}", testcaseDeclaration)
                .replace("${METHOD_SIGNATURE}", methodSignature)
                .replace("${SOLUTION_METHOD}", extractedCode);

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

                    passed = Boolean.valueOf(stdoutValue);
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

        return passed;
    }
}
