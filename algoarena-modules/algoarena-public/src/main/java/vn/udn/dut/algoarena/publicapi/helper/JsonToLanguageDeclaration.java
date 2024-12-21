package vn.udn.dut.algoarena.publicapi.helper;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.util.Map;

public class JsonToLanguageDeclaration {
    public static String convertJsonToDeclaration(String jsonString, String index, String language) {
        StringBuilder code = new StringBuilder();
        Gson gson = new Gson();
        JsonObject jsonObject = JsonParser.parseString(jsonString).getAsJsonObject();

        for (Map.Entry<String, JsonElement> entry : jsonObject.entrySet()) {
            String key = entry.getKey();
            JsonElement value = entry.getValue();

            if (value.isJsonPrimitive()) {
                if (value.getAsJsonPrimitive().isNumber()) {
                    Number number = value.getAsNumber();
                    code.append(handlePrimitiveNumber(key, index, number, language));
                } else if (value.getAsJsonPrimitive().isBoolean()) {
                    code.append(handlePrimitiveBoolean(key, index, value.getAsBoolean(), language));
                } else if (value.getAsJsonPrimitive().isString()) {
                    code.append(handlePrimitiveString(key, index, value.getAsString(), language));
                }
            } else if (value.isJsonArray()) {
                code.append(handleJsonArray(key, value.getAsJsonArray(), index, language));
            }
        }
        return code.toString();
    }

    private static String handlePrimitiveNumber(String key, String index, Number number, String language) {
        StringBuilder result = new StringBuilder();
        if (language.equals("java")) {
            if (number.longValue() == number.intValue()) {
                result.append("int ").append(key).append(index).append(" = ").append(number.intValue()).append(";\n");
            } else {
                result.append("double ").append(key).append(index).append(" = ").append(number.doubleValue()).append(";\n");
            }
        } else if (language.equals("javascript")) {
            result.append("let ").append(key).append(index).append(" = ").append(number.doubleValue()).append(";\n");
        } else if (language.equals("python")) {
            result.append(key).append(index).append(" = ").append(number.doubleValue()).append("\n");
        } else if (language.equals("go")) {
            result.append("var ").append(key).append(index).append(" = ").append(number.doubleValue()).append("\n");
        } else if (language.equals("php")) {
            result.append("$").append(key).append(index).append(" = ").append(number.doubleValue()).append(";\n");
        } else if (language.equals("c") || language.equals("c++")) {
            if (number.longValue() == number.intValue()) {
                result.append("int ").append(key).append(index).append(" = ").append(number.intValue()).append(";\n");
            } else {
                result.append("double ").append(key).append(index).append(" = ").append(number.doubleValue()).append(";\n");
            }
        } else if (language.equals("c#")) {
            if (number.longValue() == number.intValue()) {
                result.append("int ").append(key).append(index).append(" = ").append(number.intValue()).append(";\n");
            } else {
                result.append("double ").append(key).append(index).append(" = ").append(number.doubleValue()).append(";\n");
            }
        }
        return result.toString();
    }

    private static String handlePrimitiveBoolean(String key, String index, boolean value, String language) {
        StringBuilder result = new StringBuilder();
        if (language.equals("java")) {
            result.append("boolean ").append(key).append(index).append(" = ").append(value).append(";\n");
        } else if (language.equals("javascript")) {
            result.append("let ").append(key).append(index).append(" = ").append(value).append(";\n");
        } else if (language.equals("python")) {
            result.append(key).append(index).append(" = ").append(value).append("\n");
        } else if (language.equals("go")) {
            result.append("var ").append(key).append(index).append(" = ").append(value).append("\n");
        } else if (language.equals("php")) {
            result.append("$").append(key).append(index).append(" = ").append(value).append(";\n");
        } else if (language.equals("c") || language.equals("c++")) {
            result.append("bool ").append(key).append(index).append(" = ").append(value).append(";\n");
        } else if (language.equals("c#")) {
            result.append("bool ").append(key).append(index).append(" = ").append(value).append(";\n");
        }
        return result.toString();
    }

    private static String handlePrimitiveString(String key, String index, String value, String language) {
        StringBuilder result = new StringBuilder();
        if (language.equals("java")) {
            result.append("String ").append(key).append(index).append(" = \"").append(value).append("\";\n");
        } else if (language.equals("javascript")) {
            result.append("let ").append(key).append(index).append(" = \"").append(value).append("\";\n");
        } else if (language.equals("python")) {
            result.append(key).append(index).append(" = \"").append(value).append("\"\n");
        } else if (language.equals("go")) {
            result.append("var ").append(key).append(index).append(" = \"").append(value).append("\"\n");
        } else if (language.equals("php")) {
            result.append("$").append(key).append(index).append(" = \"").append(value).append("\";\n");
        } else if (language.equals("c") || language.equals("c++")) {
            result.append("char ").append(key).append(index).append("[] = \"").append(value).append("\";\n");
        } else if (language.equals("c#")) {
            result.append("string ").append(key).append(index).append(" = \"").append(value).append("\";\n");
        }
        return result.toString();
    }

    private static String handleJsonArray(String key, JsonArray jsonArray, String index, String language) {
        StringBuilder arrayString = new StringBuilder();
        String type = getArrayType(jsonArray, language);

        if (language.equals("java")) {
            arrayString.append(type).append("[] ").append(key).append(index).append(" = new ").append(type).append("[] {");
        } else if (language.equals("c") || language.equals("c++")) {
            arrayString.append(type).append(" ").append(key).append(index).append("[] = {");
        } else if (language.equals("c#")) {
            arrayString.append(type).append("[] ").append(key).append(index).append(" = new ").append(type).append("[] {");
        } else if (language.equals("javascript")) {
            arrayString.append("let ").append(key).append(index).append(" = [");
        } else if (language.equals("python")) {
            arrayString.append(key).append(index).append(" = [");
        } else if (language.equals("go")) {
            arrayString.append("var ").append(key).append(index).append(" = []").append(type).append("{");
        } else if (language.equals("php")) {
            arrayString.append("$").append(key).append(index).append(" = [");
        }

        appendArrayContent(arrayString, jsonArray, language);
        arrayString.append(language.equals("javascript") || language.equals("python") || language.equals("go") || language.equals("php") ? "];\n" : "};\n");

        return arrayString.toString();
    }

    private static void appendArrayContent(StringBuilder arrayString, JsonArray jsonArray, String language) {
        for (int i = 0; i < jsonArray.size(); i++) {
            JsonElement element = jsonArray.get(i);
            if (element.isJsonArray()) {
                arrayString.append("{");
                appendArrayContent(arrayString, element.getAsJsonArray(), language);
                arrayString.append("}");
            } else if (element.isJsonPrimitive()) {
                if (element.getAsJsonPrimitive().isString()) {
                    arrayString.append("\"").append(element.getAsString()).append("\"");
                } else {
                    arrayString.append(element.getAsInt());
                }
            }
            if (i < jsonArray.size() - 1) {
                arrayString.append(", ");
            }
        }
    }

    private static String getArrayType(JsonArray jsonArray, String language) {
        if (jsonArray.size() > 0) {
            JsonElement firstElement = jsonArray.get(0);
            if (firstElement.isJsonArray()) {
                return getArrayType(firstElement.getAsJsonArray(), language) + (language.equals("c") || language.equals("c++") ? "*" : "[]");
            } else if (firstElement.isJsonPrimitive()) {
                if (firstElement.getAsJsonPrimitive().isString()) {
                    return language.equals("java") || language.equals("c#") ? "String" : "char*";
                } else {
                    return "int";
                }
            }
        }
        return "Object";
    }

    public static void main(String[] args) {
        String jsonString = "{\"nums1\": [2, 7, 11, 15]}";
        String index = "1";
        String language = "java"; // Change the language as needed: "javascript", "python", "go", "c#", "php", "c++", etc.
        String result = convertJsonToDeclaration(jsonString, index, language);
        System.out.println(result);
    }
}
