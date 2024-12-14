package vn.udn.dut.algoarena.publicapi.helper;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.util.Map;

public class JsonToJavaDeclaration {
    public static String convertJsonToJavaDeclaration(String jsonString) {
        StringBuilder javaCode = new StringBuilder();

        Gson gson = new Gson();
        JsonObject jsonObject = JsonParser.parseString(jsonString).getAsJsonObject();

        for (Map.Entry<String, JsonElement> entry : jsonObject.entrySet()) {
            String key = entry.getKey();
            JsonElement value = entry.getValue();

            if (value.isJsonPrimitive()) {
                if (value.getAsJsonPrimitive().isNumber()) {
                    Number number = value.getAsNumber();
                    if (number.longValue() == number.intValue()) {
                        javaCode.append("int ").append(key).append(" = ").append(number.intValue()).append(";").append("\n");
                    } else {
                        javaCode.append("double ").append(key).append(" = ").append(number.doubleValue()).append(";").append("\n");
                    }
                } else if (value.getAsJsonPrimitive().isBoolean()) {
                    javaCode.append("boolean ").append(key).append(" = ").append(value.getAsBoolean()).append(";").append("\n");
                } else if (value.getAsJsonPrimitive().isString()) {
                    javaCode.append("String ").append(key).append(" = \"").append(value.getAsString()).append("\";").append("\n");
                }
            } else if (value.isJsonArray()) {
                javaCode.append(handleJsonArray(key, value.getAsJsonArray()));
            }
        }

        return javaCode.toString();
    }

    private static String handleJsonArray(String key, JsonArray jsonArray) {
        StringBuilder arrayString = new StringBuilder();
        String type = getArrayType(jsonArray);

        arrayString.append(type).append(" ").append(key).append(" = new ").append(type).append(" {");
        appendArrayContent(arrayString, jsonArray);
        arrayString.append("};\n");

        return arrayString.toString();
    }

    private static void appendArrayContent(StringBuilder arrayString, JsonArray jsonArray) {
        for (int i = 0; i < jsonArray.size(); i++) {
            JsonElement element = jsonArray.get(i);
            if (element.isJsonArray()) {
                arrayString.append("{");
                appendArrayContent(arrayString, element.getAsJsonArray());
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

    private static String getArrayType(JsonArray jsonArray) {
        if (jsonArray.size() > 0) {
            JsonElement firstElement = jsonArray.get(0);
            if (firstElement.isJsonArray()) {
                return getArrayType(firstElement.getAsJsonArray()) + "[]";
            } else if (firstElement.isJsonPrimitive()) {
                if (firstElement.getAsJsonPrimitive().isString()) {
                    return "String[]";
                } else {
                    return "int[]";
                }
            }
        }
        return "Object[]";
    }
}
