package vn.udn.dut.algoarena.util;

import java.util.*;
import java.util.stream.Collectors;

public class FunctionDefinitionInferer {

    public static void main(String[] args) {
        // Ví dụ đầu vào
        String sourceFunction = "public int scoreOfString(String s)";

        // Định dạng hàm cho các ngôn ngữ
        Map<String, String> formattedFunctions = formatFunctionForLanguages(sourceFunction);

        // In kết quả
        formattedFunctions.forEach((language, defineFunction) -> {
            System.out.println("{language=" + language + ", defineFunction=" + defineFunction + "}");
        });
    }

    public static Map<String, String> formatFunctionForLanguages(String sourceFunction) {
        FunctionDetails functionDetails = extractFunctionDetails(sourceFunction);

        Map<String, String> result = new LinkedHashMap<>();
        result.put("Java", formatForJava(functionDetails));
        result.put("PHP", formatForPHP(functionDetails));
        result.put("Python", formatForPython(functionDetails));
        result.put("JavaScript", formatForJavaScript(functionDetails));
        result.put("Go", formatForGo(functionDetails));
        result.put("C#", formatForCSharp(functionDetails));

        return result;
    }

    private static String formatForJava(FunctionDetails details) {
        String params = formatParamsForLanguage("Java", details.params);
        return details.accessModifier + " " + details.returnType + " " + details.name + "(" + params + ")";
    }

    private static String formatForPHP(FunctionDetails details) {
        String params = formatParamsForLanguage("PHP", details.params);
        return "function " + details.name + "(" + params + ")";
    }

    private static String formatForPython(FunctionDetails details) {
        String params = formatPythonParams(details.params);
        String returnType = mapReturnTypeForLanguage("Python", details.returnType);
        return "def " + details.name + "(" + params + ") -> " + returnType + ":";
    }

    private static String formatForJavaScript(FunctionDetails details) {
        String params = formatParamsForLanguage("JavaScript", details.params);
        return "var " + details.name + " = function(" + params + ")";
    }

    private static String formatForGo(FunctionDetails details) {
        String params = formatParamsForLanguage("Go", details.params);
        String returnType = mapReturnTypeForLanguage("Go", details.returnType);
        return "func " + details.name + "(" + params + ") " + returnType;
    }

    private static String formatForCSharp(FunctionDetails details) {
        String params = formatParamsForLanguage("C#", details.params);
        String nameInPascalCase = toPascalCase(details.name); // Chuyển sang PascalCase
        String returnType = mapReturnTypeForLanguage("C#", details.returnType);
        return details.accessModifier + " " + returnType + " " + nameInPascalCase + "(" + params + ")";
    }

    private static FunctionDetails extractFunctionDetails(String sourceFunction) {
        String name = "";
        List<String> params = new ArrayList<>();
        String returnType = "void";
        String accessModifier = "public";

        // Lấy tên và kiểu trả về của hàm
        var headerMatch = sourceFunction.matches("(.*)\\b(\\w+)\\s*\\((.*)\\)");
        if (headerMatch) {
            name = sourceFunction.replaceAll(".*\\b(\\w+)\\s*\\(.*\\)", "$1");
            String header = sourceFunction.replaceAll("\\s*\\b\\w+\\s*\\(.*\\)", "").trim();
            returnType = header.substring(header.lastIndexOf(" ") + 1).trim();
            accessModifier = header.split(" ")[0].trim();
        }

        // Lấy danh sách tham số
        var paramsMatch = sourceFunction.matches(".*\\(([^)]*)\\).*");
        if (paramsMatch) {
            String paramStr = sourceFunction.replaceAll(".*\\(([^)]*)\\).*", "$1");
            params = Arrays.stream(paramStr.split(","))
                    .map(String::trim)
                    .map(param -> {
                        String[] parts = param.split("\\s+");
                        if (parts.length == 2) {
                            return parts[1] + ":" + parts[0];
                        }
                        return param; // Trường hợp lỗi
                    })
                    .collect(Collectors.toList());
        }

        return new FunctionDetails(name, params, returnType, accessModifier);
    }

    private static String formatParamsForLanguage(String language, List<String> params) {
        switch (language) {
            case "Java":
            case "C#":
                return params.stream()
                        .map(p -> {
                            String[] parts = p.split(":");
                            return parts[1] + " " + parts[0];
                        })
                        .collect(Collectors.joining(", "));
            case "PHP":
                return params.stream()
                        .map(p -> "$" + p.split(":")[0])
                        .collect(Collectors.joining(", "));
            case "Go":
                return params.stream()
                        .map(p -> {
                            String[] parts = p.split(":");
                            return parts[0] + " " + parts[1].replace("int[]", "[]int");
                        })
                        .collect(Collectors.joining(", "));
            case "JavaScript":
                return params.stream()
                        .map(p -> p.split(":")[0])
                        .collect(Collectors.joining(", "));
            default:
                return params.stream()
                        .map(p -> p.split(":")[0])
                        .collect(Collectors.joining(", "));
        }
    }

    private static String formatPythonParams(List<String> params) {
        if (params.isEmpty()) {
            return "self";
        }
        return "self, " + params.stream()
                .map(p -> {
                    String[] parts = p.split(":");
                    String pythonType = parts[1].equals("String") ? "str" : (parts[1].equals("int[]") ? "List[int]" : parts[1]);
                    return parts[0] + ": " + pythonType;
                })
                .collect(Collectors.joining(", "));
    }

    private static String mapReturnTypeForLanguage(String language, String returnType) {
        switch (language) {
            case "Python":
                if (returnType.equals("boolean")) {
                    return "bool";
                } else if (returnType.equals("String")) {
                    return "str";
                }
                return returnType.equals("int[]") ? "List[int]" : returnType;
            case "Go":
            case "C#":
                if (returnType.equals("boolean")) {
                    return "bool";
                } else if (returnType.equals("String")) {
                    return "string";
                }
                return returnType.equals("int[]") ? "List[int]" : returnType;
            default:
                return returnType;
        }
    }

    private static String toPascalCase(String name) {
        if (name == null || name.isEmpty()) {
            return name;
        }
        return Character.toUpperCase(name.charAt(0)) + name.substring(1);
    }

    private static class FunctionDetails {
        String name;
        List<String> params;
        String returnType;
        String accessModifier;

        public FunctionDetails(String name, List<String> params, String returnType, String accessModifier) {
            this.name = name;
            this.params = params;
            this.returnType = returnType;
            this.accessModifier = accessModifier;
        }
    }
}
