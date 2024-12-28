package vn.udn.dut.algoarena.publicapi.helper;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MethodExtractorHelper {

    // Hàm giúp trích xuất khai báo phương thức từ mã nguồn
    public static String extractMethodSignature(String code, String index, String language) {
        // Regex để tìm khai báo hàm tùy theo ngôn ngữ lập trình
        String methodRegex = "";

        switch (language.toLowerCase()) {
            case "java":
            case "c#":
                methodRegex = "(public|private|protected)?\\s+(static\\s+)?([\\w\\[\\]]+)\\s+(\\w+)\\s*\\(([^\\)]*)\\)";
                break;

            case "javascript":
                methodRegex = "var\\s+(\\w+)\\s*=\\s*function\\s*\\(([^\\)]*)\\)";
                break;

            case "php":
                methodRegex = "function\\s+(\\w+)\\s*\\(([^\\)]*)\\)";
                break;

            case "c":
            case "c++":
                methodRegex = "([\\w\\[\\]]+)\\s+(\\w+)\\s*\\(([^\\)]*)\\)";
                break;

            case "python":
                methodRegex = "def\\s+(\\w+)\\s*\\(([^\\)]*)\\)";
                break;

            case "go":
                methodRegex = "func\\s+(\\w+)\\s*\\(([^\\)]*)\\)";
                break;

            default:
                return "Unsupported language.";
        }

        Pattern pattern = Pattern.compile(methodRegex);
        Matcher matcher = pattern.matcher(code);

        if (matcher.find()) {
            String methodName = "";
            String parameters = "";
            String returnType = "";

            switch (language.toLowerCase()) {
                case "java":
                case "c#":
                case "c":
                case "c++":
                    returnType = matcher.group(3); // Kiểu trả về
                    methodName = matcher.group(4); // Tên phương thức
                    parameters = matcher.group(5).trim(); // Các tham số
                    break;

                case "javascript":
                    methodName = matcher.group(1); // Tên phương thức
                    parameters = matcher.group(2).trim(); // Các tham số
                    break;
                case "php":
                case "python":
                case "go":
                    methodName = matcher.group(1); // Tên phương thức
                    parameters = matcher.group(2); // Các tham số
                    break;
            }
            if ("python".equals(language)) {
                // Xử lý tham số: loại bỏ self và kiểu dữ liệu
                if (!parameters.isEmpty()) {
                    String[] paramArray = parameters.split(",");
                    for (int i = 0; i < paramArray.length; i++) {
                        String param = paramArray[i].trim();

                        // Loại bỏ kiểu dữ liệu: chỉ giữ tên biến (bỏ phần sau dấu ":")
                        param = param.replaceAll(":.*", "").trim();

                        // Loại bỏ 'self' nếu nó tồn tại
                        if (!param.equals("self")) {
                            paramArray[i] = param;
                        } else {
                            paramArray[i] = ""; // Xóa self khỏi danh sách
                        }
                    }

                    // Gộp lại các tham số, loại bỏ những tham số trống
                    parameters = String.join(index + ", ", paramArray).replaceAll(",\\s*,", ",").trim();
                    parameters = parameters.substring(2);
                    // Xóa dấu phẩy thừa nếu 'self' bị xóa và nó đứng đầu
                    if (parameters.startsWith(",")) {
                        parameters = parameters.substring(1).trim();
                    }
                }
            } else {
                // Xử lý tham số: chỉ giữ lại tên tham số, bỏ kiểu dữ liệu
                if (!parameters.isEmpty()) {
                    String[] paramArray = parameters.split(",");
                    for (int i = 0; i < paramArray.length; i++) {
                        String param = paramArray[i].trim();

                        if (language.equalsIgnoreCase("java") || language.equalsIgnoreCase("c#") || language.equalsIgnoreCase("c") || language.equalsIgnoreCase("c++")) {
                            paramArray[i] = param.replaceAll("[\\w\\[\\]]+\\s+", "");
                        } else if (language.equalsIgnoreCase("go")) {
                            paramArray[i] = param.replaceAll("\\s*\\[.*?\\]|\\s*\\S+$", "");
                        }
                    }
                    parameters = String.join(index + ", ", paramArray);
                }
            }

            // Trả về chuỗi theo định dạng mong muốn
            if (!returnType.isEmpty()) {
                return returnType + " result" + index + " = " + methodName + "(" + parameters + index + ");";
            } else {
                // Thêm khai báo biến phù hợp với các ngôn ngữ không có kiểu trả về rõ ràng
                switch (language.toLowerCase()) {
                    case "javascript":
                        return "let result" + index + " = " + methodName + "(" + parameters + index + ");";
                    case "python":
                        return "result" + index + " = " + methodName + "(" + parameters + index + ")";
                    case "php":
                        return "$result" + index + " = " + methodName + "(" + parameters + index + ");";
                    case "go":
                        return "result" + index + " := " + methodName + "(" + parameters + index + ")";
                    default:
                        return "result" + index + " = " + methodName + "(" + parameters + index + ");";
                }
            }
        }

        return "No method signature found.";
    }
}

