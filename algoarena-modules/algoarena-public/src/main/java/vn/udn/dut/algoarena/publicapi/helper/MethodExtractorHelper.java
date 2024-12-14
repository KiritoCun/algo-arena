package vn.udn.dut.algoarena.publicapi.helper;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MethodExtractorHelper {

    // Hàm giúp trích xuất khai báo phương thức từ mã nguồn
    public static String extractMethodSignature(String code) {
        // Regex để tìm khai báo hàm
        String methodRegex = "(public|private|protected)?\\s+(static\\s+)?([\\w\\[\\]]+)\\s+(\\w+)\\s*\\(([^\\)]*)\\)";
        Pattern pattern = Pattern.compile(methodRegex);
        Matcher matcher = pattern.matcher(code);

        if (matcher.find()) {
            // Lấy kiểu trả về (returnType), tên phương thức (methodName) và tham số (parameters)
            String returnType = matcher.group(3); // Kiểu trả về (int[])
            String methodName = matcher.group(4); // Tên phương thức (twoSum)
            String parameters = matcher.group(5).trim(); // Các tham số trong dấu ngoặc đơn

            // Xử lý tham số: loại bỏ kiểu dữ liệu và chỉ giữ lại tên tham số
            if (!parameters.isEmpty()) {
                String[] paramArray = parameters.split(",");
                for (int i = 0; i < paramArray.length; i++) {
                    String param = paramArray[i].trim();
                    // Lấy tên tham số (sau khi loại bỏ kiểu dữ liệu)
                    paramArray[i] = param.replaceAll("[\\w\\[\\]]+\\s+", ""); // Xóa kiểu dữ liệu
                }
                // Ghép lại danh sách tham số với dấu phẩy phân cách và khoảng trắng sau dấu phẩy
                parameters = String.join(", ", paramArray);
            }

            // Trả về chuỗi theo định dạng mong muốn
            return returnType + " result = " + methodName + "(" + parameters + ");";
        }
        return "No method signature found.";
    }
}

