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
public class HomepageSearchService {

    public static List<String> submitSolution(String submittedCode, String language, String version, List<TestcaseVo> testcaseVoList) {
        List<String> testcaseResult = new ArrayList<>();

        // Extract method signatures for each test case
        String methodSignature1 = MethodExtractorHelper.extractMethodSignature(submittedCode, "1", language);
        String methodSignature2 = MethodExtractorHelper.extractMethodSignature(submittedCode, "2", language);
        String methodSignature3 = MethodExtractorHelper.extractMethodSignature(submittedCode, "3", language);
        String methodSignature4 = MethodExtractorHelper.extractMethodSignature(submittedCode, "4", language);
        String methodSignature5 = MethodExtractorHelper.extractMethodSignature(submittedCode, "5", language);

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

        // Get test case JSON
        String testcase1 = testcaseVoList.get(0).getTestcaseJson();
        String testcase2 = testcaseVoList.get(1).getTestcaseJson();
        String testcase3 = testcaseVoList.get(2).getTestcaseJson();
        String testcase4 = testcaseVoList.get(3).getTestcaseJson();
        String testcase5 = testcaseVoList.get(4).getTestcaseJson();

        // Convert test case to declarations for the specified language
        String testcaseDeclaration1 = JsonToLanguageDeclaration.convertJsonToDeclaration(testcase1, "1", language);
        String testcaseDeclaration2 = JsonToLanguageDeclaration.convertJsonToDeclaration(testcase2, "2", language);
        String testcaseDeclaration3 = JsonToLanguageDeclaration.convertJsonToDeclaration(testcase3, "3", language);
        String testcaseDeclaration4 = JsonToLanguageDeclaration.convertJsonToDeclaration(testcase4, "4", language);
        String testcaseDeclaration5 = JsonToLanguageDeclaration.convertJsonToDeclaration(testcase5, "5", language);

        // Switch based on the language
        String code = switch (language.toLowerCase()) {
            case "java" -> getJavaCode(testcaseDeclaration1, methodSignature1, testcaseDeclaration2, methodSignature2,
                    testcaseDeclaration3, methodSignature3, testcaseDeclaration4, methodSignature4,
                    testcaseDeclaration5, methodSignature5, submittedCode);
            case "javascript" -> getJavaScriptCode(testcaseDeclaration1, methodSignature1, testcaseDeclaration2, methodSignature2,
                    testcaseDeclaration3, methodSignature3, testcaseDeclaration4, methodSignature4,
                    testcaseDeclaration5, methodSignature5, submittedCode);
            case "php" -> getPhpCode(testcaseDeclaration1, methodSignature1, testcaseDeclaration2, methodSignature2,
                    testcaseDeclaration3, methodSignature3, testcaseDeclaration4, methodSignature4,
                    testcaseDeclaration5, methodSignature5, submittedCode);
            case "c" -> getCCode(testcaseDeclaration1, methodSignature1, testcaseDeclaration2, methodSignature2,
                    testcaseDeclaration3, methodSignature3, testcaseDeclaration4, methodSignature4,
                    testcaseDeclaration5, methodSignature5, submittedCode);
            case "c++" -> getCppCode(testcaseDeclaration1, methodSignature1, testcaseDeclaration2, methodSignature2,
                    testcaseDeclaration3, methodSignature3, testcaseDeclaration4, methodSignature4,
                    testcaseDeclaration5, methodSignature5, submittedCode);
            case "c#" -> getCSharpCode(testcaseDeclaration1, methodSignature1, testcaseDeclaration2, methodSignature2,
                    testcaseDeclaration3, methodSignature3, testcaseDeclaration4, methodSignature4,
                    testcaseDeclaration5, methodSignature5, submittedCode);
            case "python" -> getPythonCode(testcaseDeclaration1, methodSignature1, testcaseDeclaration2, methodSignature2,
                    testcaseDeclaration3, methodSignature3, testcaseDeclaration4, methodSignature4,
                    testcaseDeclaration5, methodSignature5, submittedCode);
            case "go" -> getGoCode(testcaseDeclaration1, methodSignature1, testcaseDeclaration2, methodSignature2,
                    testcaseDeclaration3, methodSignature3, testcaseDeclaration4, methodSignature4,
                    testcaseDeclaration5, methodSignature5, submittedCode);
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

                    if (!"".equals(stdoutValue)) {
                        if ("python".equals(language)) {
                            testcaseResult = Arrays.stream((stdoutValue.trim().toLowerCase().replaceAll(" ", "").replaceAll("\n", "")).split(",")).toList();
                        } else {
                            testcaseResult = Arrays.stream((stdoutValue.trim().replaceAll("\n", "")).split(",")).toList();//Replace \n for javascript
                        }
                    } else {
                        String stderr = runObject.getString("stderr");
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

    private static String getJavaCode(String testcaseDeclaration1, String methodSignature1, String testcaseDeclaration2,
                                      String methodSignature2, String testcaseDeclaration3, String methodSignature3,
                                      String testcaseDeclaration4, String methodSignature4, String testcaseDeclaration5,
                                      String methodSignature5, String submittedCode) {
        return """
                import java.util.*;

                public class Main {
                    public static void main(String[] args) {
                        try {
                            ${TESTCASE_DECLARATION1}
                            ${METHOD_SIGNATURE1}
                            System.out.print(compare(result1, expect1));
                            System.out.print(",");
                            ${TESTCASE_DECLARATION2}
                            ${METHOD_SIGNATURE2}
                            System.out.print(compare(result2, expect2));
                            System.out.print(",");
                            ${TESTCASE_DECLARATION3}
                            ${METHOD_SIGNATURE3}
                            System.out.print(compare(result3, expect3));
                            System.out.print(",");
                            ${TESTCASE_DECLARATION4}
                            ${METHOD_SIGNATURE4}
                            System.out.print(compare(result4, expect4));
                            System.out.print(",");
                            ${TESTCASE_DECLARATION5}
                            ${METHOD_SIGNATURE5}
                            System.out.print(compare(result5, expect5));
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
                """.replace("${TESTCASE_DECLARATION1}", testcaseDeclaration1)
                .replace("${METHOD_SIGNATURE1}", methodSignature1)
                .replace("${TESTCASE_DECLARATION2}", testcaseDeclaration2)
                .replace("${METHOD_SIGNATURE2}", methodSignature2)
                .replace("${TESTCASE_DECLARATION3}", testcaseDeclaration3)
                .replace("${METHOD_SIGNATURE3}", methodSignature3)
                .replace("${TESTCASE_DECLARATION4}", testcaseDeclaration4)
                .replace("${METHOD_SIGNATURE4}", methodSignature4)
                .replace("${TESTCASE_DECLARATION5}", testcaseDeclaration5)
                .replace("${METHOD_SIGNATURE5}", methodSignature5)
                .replace("${SOLUTION_METHOD}", submittedCode);
    }

    private static String getJavaScriptCode(String testcaseDeclaration1, String methodSignature1, String testcaseDeclaration2,
                                            String methodSignature2, String testcaseDeclaration3, String methodSignature3,
                                            String testcaseDeclaration4, String methodSignature4, String testcaseDeclaration5,
                                            String methodSignature5, String submittedCode) {
        return """
                ${SOLUTION_METHOD}
                
                try {
                    ${TESTCASE_DECLARATION1}
                    ${METHOD_SIGNATURE1}
                    console.log(compare(result1, expect1));
                    console.log(",");
                    ${TESTCASE_DECLARATION2}
                    ${METHOD_SIGNATURE2}
                    console.log(compare(result2, expect2));
                    console.log(",");
                    ${TESTCASE_DECLARATION3}
                    ${METHOD_SIGNATURE3}
                    console.log(compare(result3, expect3));
                    console.log(",");
                    ${TESTCASE_DECLARATION4}
                    ${METHOD_SIGNATURE4}
                    console.log(compare(result4, expect4));
                    console.log(",");
                    ${TESTCASE_DECLARATION5}
                    ${METHOD_SIGNATURE5}
                    console.log(compare(result5, expect5));
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
                """.replace("${TESTCASE_DECLARATION1}", testcaseDeclaration1)
                .replace("${METHOD_SIGNATURE1}", methodSignature1)
                .replace("${TESTCASE_DECLARATION2}", testcaseDeclaration2)
                .replace("${METHOD_SIGNATURE2}", methodSignature2)
                .replace("${TESTCASE_DECLARATION3}", testcaseDeclaration3)
                .replace("${METHOD_SIGNATURE3}", methodSignature3)
                .replace("${TESTCASE_DECLARATION4}", testcaseDeclaration4)
                .replace("${METHOD_SIGNATURE4}", methodSignature4)
                .replace("${TESTCASE_DECLARATION5}", testcaseDeclaration5)
                .replace("${METHOD_SIGNATURE5}", methodSignature5)
                .replace("${SOLUTION_METHOD}", submittedCode);
    }

    private static String getPhpCode(String testcaseDeclaration1, String methodSignature1, String testcaseDeclaration2,
                                     String methodSignature2, String testcaseDeclaration3, String methodSignature3,
                                     String testcaseDeclaration4, String methodSignature4, String testcaseDeclaration5,
                                     String methodSignature5, String submittedCode) {
        return """
            <?php

            function test() {
                try {
                    ${TESTCASE_DECLARATION1}
                    ${METHOD_SIGNATURE1}
                    echo compare($result1, $expect1) ? "true" : "false";
                    echo ",";
                    ${TESTCASE_DECLARATION2}
                    ${METHOD_SIGNATURE2}
                    echo compare($result2, $expect2) ? "true" : "false";
                    echo ",";
                    ${TESTCASE_DECLARATION3}
                    ${METHOD_SIGNATURE3}
                    echo compare($result3, $expect3) ? "true" : "false";
                    echo ",";
                    ${TESTCASE_DECLARATION4}
                    ${METHOD_SIGNATURE4}
                    echo compare($result4, $expect4) ? "true" : "false";
                    echo ",";
                    ${TESTCASE_DECLARATION5}
                    ${METHOD_SIGNATURE5}
                    echo compare($result5, $expect5) ? "true" : "false";
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
            """.replace("${TESTCASE_DECLARATION1}", testcaseDeclaration1)
                .replace("${METHOD_SIGNATURE1}", methodSignature1)
                .replace("${TESTCASE_DECLARATION2}", testcaseDeclaration2)
                .replace("${METHOD_SIGNATURE2}", methodSignature2)
                .replace("${TESTCASE_DECLARATION3}", testcaseDeclaration3)
                .replace("${METHOD_SIGNATURE3}", methodSignature3)
                .replace("${TESTCASE_DECLARATION4}", testcaseDeclaration4)
                .replace("${METHOD_SIGNATURE4}", methodSignature4)
                .replace("${TESTCASE_DECLARATION5}", testcaseDeclaration5)
                .replace("${METHOD_SIGNATURE5}", methodSignature5)
                .replace("${SOLUTION_METHOD}", submittedCode);
    }

    private static String getCCode(String testcaseDeclaration1, String methodSignature1, String testcaseDeclaration2,
                                   String methodSignature2, String testcaseDeclaration3, String methodSignature3,
                                   String testcaseDeclaration4, String methodSignature4, String testcaseDeclaration5,
                                   String methodSignature5, String submittedCode) {
        return """
            #include <stdio.h>
            #include <string.h>

            int compare(void* a, void* b) {
                if (memcmp(a, b, sizeof(a)) == 0) {
                    return 1;
                }
                return 0;
            }

            int main() {
                try {
                    ${TESTCASE_DECLARATION1}
                    ${METHOD_SIGNATURE1}
                    printf("%s", compare(result1, expect1) ? "true" : "false");
                    printf(",");
                    ${TESTCASE_DECLARATION2}
                    ${METHOD_SIGNATURE2}
                    printf("%s", compare(result2, expect2) ? "true" : "false");
                    printf(",");
                    ${TESTCASE_DECLARATION3}
                    ${METHOD_SIGNATURE3}
                    printf("%s", compare(result3, expect3) ? "true" : "false");
                    printf(",");
                    ${TESTCASE_DECLARATION4}
                    ${METHOD_SIGNATURE4}
                    printf("%s", compare(result4, expect4) ? "true" : "false");
                    printf(",");
                    ${TESTCASE_DECLARATION5}
                    ${METHOD_SIGNATURE5}
                    printf("%s", compare(result5, expect5) ? "true" : "false");
                } catch (Exception e) {
                    printf("%s", e.getMessage());
                }
            }

            ${SOLUTION_METHOD}
            """.replace("${TESTCASE_DECLARATION1}", testcaseDeclaration1)
                .replace("${METHOD_SIGNATURE1}", methodSignature1)
                .replace("${TESTCASE_DECLARATION2}", testcaseDeclaration2)
                .replace("${METHOD_SIGNATURE2}", methodSignature2)
                .replace("${TESTCASE_DECLARATION3}", testcaseDeclaration3)
                .replace("${METHOD_SIGNATURE3}", methodSignature3)
                .replace("${TESTCASE_DECLARATION4}", testcaseDeclaration4)
                .replace("${METHOD_SIGNATURE4}", methodSignature4)
                .replace("${TESTCASE_DECLARATION5}", testcaseDeclaration5)
                .replace("${METHOD_SIGNATURE5}", methodSignature5)
                .replace("${SOLUTION_METHOD}", submittedCode);
    }

    private static String getCppCode(String testcaseDeclaration1, String methodSignature1, String testcaseDeclaration2,
                                     String methodSignature2, String testcaseDeclaration3, String methodSignature3,
                                     String testcaseDeclaration4, String methodSignature4, String testcaseDeclaration5,
                                     String methodSignature5, String submittedCode) {
        return """
            #include <iostream>
            #include <unordered_map>
            #include <vector>
            using namespace std;

            template <typename T>
            bool compare(const T& a, const T& b) {
                return a == b;
            }

            int main() {
                try {
                    ${TESTCASE_DECLARATION1}
                    ${METHOD_SIGNATURE1}
                    cout << (compare(result1, expect1) ? "true" : "false");
                    cout << ",";
                    ${TESTCASE_DECLARATION2}
                    ${METHOD_SIGNATURE2}
                    cout << (compare(result2, expect2) ? "true" : "false");
                    cout << ",";
                    ${TESTCASE_DECLARATION3}
                    ${METHOD_SIGNATURE3}
                    cout << (compare(result3, expect3) ? "true" : "false");
                    cout << ",";
                    ${TESTCASE_DECLARATION4}
                    ${METHOD_SIGNATURE4}
                    cout << (compare(result4, expect4) ? "true" : "false");
                    cout << ",";
                    ${TESTCASE_DECLARATION5}
                    ${METHOD_SIGNATURE5}
                    cout << (compare(result5, expect5) ? "true" : "false");
                } catch (exception& e) {
                    cout << e.what();
                }
            }

            ${SOLUTION_METHOD}
            """.replace("${TESTCASE_DECLARATION1}", testcaseDeclaration1)
                .replace("${METHOD_SIGNATURE1}", methodSignature1)
                .replace("${TESTCASE_DECLARATION2}", testcaseDeclaration2)
                .replace("${METHOD_SIGNATURE2}", methodSignature2)
                .replace("${TESTCASE_DECLARATION3}", testcaseDeclaration3)
                .replace("${METHOD_SIGNATURE3}", methodSignature3)
                .replace("${TESTCASE_DECLARATION4}", testcaseDeclaration4)
                .replace("${METHOD_SIGNATURE4}", methodSignature4)
                .replace("${TESTCASE_DECLARATION5}", testcaseDeclaration5)
                .replace("${METHOD_SIGNATURE5}", methodSignature5)
                .replace("${SOLUTION_METHOD}", submittedCode);
    }

    private static String getCSharpCode(String testcaseDeclaration1, String methodSignature1, String testcaseDeclaration2,
                                        String methodSignature2, String testcaseDeclaration3, String methodSignature3,
                                        String testcaseDeclaration4, String methodSignature4, String testcaseDeclaration5,
                                        String methodSignature5, String submittedCode) {
        return """
            using System;
            using System.Linq;
            using System.Text;
            using System.Collections;
            using System.Collections.Generic;

            class Program {
                static void Main() {
                    try {
                        ${TESTCASE_DECLARATION1}
                        ${METHOD_SIGNATURE1}
                        Console.Write(Compare(result1, expect1) ? "true" : "false");
                        Console.Write(",");
                        ${TESTCASE_DECLARATION2}
                        ${METHOD_SIGNATURE2}
                        Console.Write(Compare(result2, expect2) ? "true" : "false");
                        Console.Write(",");
                        ${TESTCASE_DECLARATION3}
                        ${METHOD_SIGNATURE3}
                        Console.Write(Compare(result3, expect3) ? "true" : "false");
                        Console.Write(",");
                        ${TESTCASE_DECLARATION4}
                        ${METHOD_SIGNATURE4}
                        Console.Write(Compare(result4, expect4) ? "true" : "false");
                        Console.Write(",");
                        ${TESTCASE_DECLARATION5}
                        ${METHOD_SIGNATURE5}
                        Console.Write(Compare(result5, expect5) ? "true" : "false");
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
            """.replace("${TESTCASE_DECLARATION1}", testcaseDeclaration1)
                .replace("${METHOD_SIGNATURE1}", methodSignature1)
                .replace("${TESTCASE_DECLARATION2}", testcaseDeclaration2)
                .replace("${METHOD_SIGNATURE2}", methodSignature2)
                .replace("${TESTCASE_DECLARATION3}", testcaseDeclaration3)
                .replace("${METHOD_SIGNATURE3}", methodSignature3)
                .replace("${TESTCASE_DECLARATION4}", testcaseDeclaration4)
                .replace("${METHOD_SIGNATURE4}", methodSignature4)
                .replace("${TESTCASE_DECLARATION5}", testcaseDeclaration5)
                .replace("${METHOD_SIGNATURE5}", methodSignature5)
                .replace("${SOLUTION_METHOD}", submittedCode);
    }

    private static String getPythonCode(String testcaseDeclaration1, String methodSignature1, String testcaseDeclaration2,
                                        String methodSignature2, String testcaseDeclaration3, String methodSignature3,
                                        String testcaseDeclaration4, String methodSignature4, String testcaseDeclaration5,
                                        String methodSignature5, String submittedCode) {
        return """
            from typing import List
            
            def compare(a, b):
                if isinstance(a, list) and isinstance(b, list):
                    return a == b
                return a == b
            
            ${SOLUTION_METHOD}

            def main():
                try:
                    ${TESTCASE_DECLARATION1}
                    ${METHOD_SIGNATURE1}
                    print(compare(result1, expect1))
                    print(",", end=" ")
                    ${TESTCASE_DECLARATION2}
                    ${METHOD_SIGNATURE2}
                    print(compare(result2, expect2))
                    print(",", end=" ")
                    ${TESTCASE_DECLARATION3}
                    ${METHOD_SIGNATURE3}
                    print(compare(result3, expect3))
                    print(",", end=" ")
                    ${TESTCASE_DECLARATION4}
                    ${METHOD_SIGNATURE4}
                    print(compare(result4, expect4))
                    print(",", end=" ")
                    ${TESTCASE_DECLARATION5}
                    ${METHOD_SIGNATURE5}
                    print(compare(result5, expect5))
                except Exception as e:
                    print(str(e))

            if __name__ == "__main__":
                main()
            """.replace("${TESTCASE_DECLARATION1}", testcaseDeclaration1)
                .replace("${METHOD_SIGNATURE1}", methodSignature1)
                .replace("${TESTCASE_DECLARATION2}", testcaseDeclaration2)
                .replace("${METHOD_SIGNATURE2}", methodSignature2)
                .replace("${TESTCASE_DECLARATION3}", testcaseDeclaration3)
                .replace("${METHOD_SIGNATURE3}", methodSignature3)
                .replace("${TESTCASE_DECLARATION4}", testcaseDeclaration4)
                .replace("${METHOD_SIGNATURE4}", methodSignature4)
                .replace("${TESTCASE_DECLARATION5}", testcaseDeclaration5)
                .replace("${METHOD_SIGNATURE5}", methodSignature5)
                .replace("${SOLUTION_METHOD}", submittedCode);
    }

    private static String getGoCode(String testcaseDeclaration1, String methodSignature1, String testcaseDeclaration2,
                                    String methodSignature2, String testcaseDeclaration3, String methodSignature3,
                                    String testcaseDeclaration4, String methodSignature4, String testcaseDeclaration5,
                                    String methodSignature5, String submittedCode) {
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

                ${TESTCASE_DECLARATION1}
                ${METHOD_SIGNATURE1}
                fmt.Println(compare(result1, expect1))
                fmt.Println(",",)
                ${TESTCASE_DECLARATION2}
                ${METHOD_SIGNATURE2}
                fmt.Println(compare(result2, expect2))
                fmt.Println(",",)
                ${TESTCASE_DECLARATION3}
                ${METHOD_SIGNATURE3}
                fmt.Println(compare(result3, expect3))
                fmt.Println(",",)
                ${TESTCASE_DECLARATION4}
                ${METHOD_SIGNATURE4}
                fmt.Println(compare(result4, expect4))
                fmt.Println(",",)
                ${TESTCASE_DECLARATION5}
                ${METHOD_SIGNATURE5}
                fmt.Println(compare(result5, expect5))
            }

            ${SOLUTION_METHOD}
            """.replace("${TESTCASE_DECLARATION1}", testcaseDeclaration1)
                .replace("${METHOD_SIGNATURE1}", methodSignature1)
                .replace("${TESTCASE_DECLARATION2}", testcaseDeclaration2)
                .replace("${METHOD_SIGNATURE2}", methodSignature2)
                .replace("${TESTCASE_DECLARATION3}", testcaseDeclaration3)
                .replace("${METHOD_SIGNATURE3}", methodSignature3)
                .replace("${TESTCASE_DECLARATION4}", testcaseDeclaration4)
                .replace("${METHOD_SIGNATURE4}", methodSignature4)
                .replace("${TESTCASE_DECLARATION5}", testcaseDeclaration5)
                .replace("${METHOD_SIGNATURE5}", methodSignature5)
                .replace("${SOLUTION_METHOD}", submittedCode);
    }
}
