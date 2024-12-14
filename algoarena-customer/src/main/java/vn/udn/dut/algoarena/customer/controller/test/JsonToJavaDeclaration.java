package vn.udn.dut.algoarena.customer.controller.test;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.util.Map;

public class JsonToJavaDeclaration {
    public static void main(String[] args) {
        String jsonString = "{ \"input\": \"[0,1,2]\", \"target\": 9, \"flag\": true, \"name\": \"example\", \"stringarray\": [\"0\",\"1\",\"2\"], \"nestedarray\": [[1,2],[3,4]], \"threeDimArray\": [[[1,2],[3,4]],[[5,6],[7,8]]], \"twoDimStringArray\": [[\"a\",\"b\"],[\"c\",\"d\"]] }";

        // Tạo một đối tượng Gson
        Gson gson = new Gson();

        // Chuyển đổi chuỗi JSON thành đối tượng JsonObject
        JsonObject jsonObject = JsonParser.parseString(jsonString).getAsJsonObject();

        // Duyệt qua từng phần tử trong JSON
        for (Map.Entry<String, JsonElement> entry : jsonObject.entrySet()) {
            String key = entry.getKey();
            JsonElement value = entry.getValue();

            // Xử lý từng kiểu dữ liệu
            if (value.isJsonPrimitive()) {
                if (value.getAsJsonPrimitive().isNumber()) {
                    Number number = value.getAsNumber();
                    if (number.longValue() == number.intValue()) {
                        System.out.println(key + " = " + number.intValue() + ";");
                    } else {
                        System.out.println(key + " = " + number.longValue() + ";");
                    }
                } else if (value.getAsJsonPrimitive().isBoolean()) {
                    System.out.println(key + " = " + value.getAsBoolean() + ";");
                } else if (value.getAsJsonPrimitive().isString()) {
                    System.out.println(key + " = \"" + value.getAsString() + "\";");
                }
            } else if (value.isJsonArray()) {
                handleJsonArray(key, value.getAsJsonArray(), 0);
            }
        }
    }

    private static void handleJsonArray(String key, JsonArray jsonArray, int depth) {
        if (jsonArray.size() > 0 && jsonArray.get(0).isJsonArray()) {
            // Xử lý mảng nhiều chiều
            StringBuilder arrayString = new StringBuilder();
            arrayString.append(getArrayType(jsonArray, depth)).append(" {");
            for (JsonElement element : jsonArray) {
                arrayString.append("{");
                handleJsonArrayContent(arrayString, element.getAsJsonArray(), depth + 1);
                arrayString.append("},");
            }
            arrayString.deleteCharAt(arrayString.length() - 1); // Xóa dấu phẩy cuối cùng
            arrayString.append("}");
            System.out.println(key + " = " + arrayString.toString() + ";");
        } else if (jsonArray.size() > 0 && jsonArray.get(0).isJsonPrimitive() && jsonArray.get(0).getAsJsonPrimitive().isString()) {
            // Xử lý mảng chữ
            StringBuilder arrayString = new StringBuilder("new String[] {");
            for (int i = 0; i < jsonArray.size(); i++) {
                arrayString.append("\"").append(jsonArray.get(i).getAsString()).append("\"");
                if (i < jsonArray.size() - 1) {
                    arrayString.append(",");
                }
            }
            arrayString.append("}");
            System.out.println(key + " = " + arrayString.toString() + ";");
        } else {
            // Xử lý mảng số
            StringBuilder arrayString = new StringBuilder("new int[] {");
            for (int i = 0; i < jsonArray.size(); i++) {
                arrayString.append(jsonArray.get(i).getAsInt());
                if (i < jsonArray.size() - 1) {
                    arrayString.append(",");
                }
            }
            arrayString.append("}");
            System.out.println(key + " = " + arrayString.toString() + ";");
        }
    }

    private static void handleJsonArrayContent(StringBuilder arrayString, JsonArray jsonArray, int depth) {
        if (jsonArray.size() > 0 && jsonArray.get(0).isJsonArray()) {
            for (JsonElement element : jsonArray) {
                arrayString.append("{");
                handleJsonArrayContent(arrayString, element.getAsJsonArray(), depth + 1);
                arrayString.append("},");
            }
            arrayString.deleteCharAt(arrayString.length() - 1); // Xóa dấu phẩy cuối cùng
        } else if (jsonArray.size() > 0 && jsonArray.get(0).isJsonPrimitive() && jsonArray.get(0).getAsJsonPrimitive().isString()) {
            for (int i = 0; i < jsonArray.size(); i++) {
                arrayString.append("\"").append(jsonArray.get(i).getAsString()).append("\"");
                if (i < jsonArray.size() - 1) {
                    arrayString.append(",");
                }
            }
        } else {
            for (int i = 0; i < jsonArray.size(); i++) {
                arrayString.append(jsonArray.get(i).getAsInt());
                if (i < jsonArray.size() - 1) {
                    arrayString.append(",");
                }
            }
        }
    }

    private static String getArrayType(JsonArray jsonArray, int depth) {
        if (jsonArray.size() > 0 && jsonArray.get(0).isJsonArray()) {
            return getArrayType(jsonArray.get(0).getAsJsonArray(), depth + 1);
        } else if (jsonArray.size() > 0 && jsonArray.get(0).isJsonPrimitive() && jsonArray.get(0).getAsJsonPrimitive().isString()) {
            return "String" + "[]".repeat(depth + 1);
        } else {
            return "int" + "[]".repeat(depth + 1);
        }
    }
}

