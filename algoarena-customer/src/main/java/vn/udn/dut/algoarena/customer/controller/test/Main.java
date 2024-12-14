package vn.udn.dut.algoarena.customer.controller.test;

import java.util.Arrays;

public class Main {
    public static void main(String[] args) {
        // Ví dụ với mảng nhiều chiều
        Object array = new int[][][][]{
                {
                        {{1, 2, 3}, {4, 5, 6}},
                        {{7, 8, 9}, {10, 11, 12}}
                },
                {
                        {{13, 14}, {15, 16, 17}},
                        {{18, 19}, {20, 21}}
                }
        }; // Bạn có thể thay đổi kiểu dữ liệu ở đây (boolean, chuỗi, mảng...)

        // Kiểm tra nếu array là một mảng
        if (array instanceof String) {
            System.out.println(array); // In chuỗi nếu là String
        } else if (array instanceof Boolean || array instanceof Number) {
            System.out.println(array); // In số hoặc boolean trực tiếp
        } else if (isArray(array)) {
            // Xử lý mảng với số chiều không xác định
            System.out.println(deepToString(array)); // Gọi phương thức deepToString cho mảng nhiều chiều
        } else {
            System.out.println(array); // In bất kỳ đối tượng khác
        }
    }

    // Phương thức kiểm tra nếu đối tượng là mảng
    public static boolean isArray(Object obj) {
        return obj.getClass().isArray();
    }

    // Phương thức đệ quy xử lý mảng nhiều chiều
    public static String deepToString(Object obj) {
        if (obj == null) {
            return "null";
        }
        if (!isArray(obj)) {
            return obj.toString();
        }
        // Nếu đối tượng là mảng, xử lý từng phần tử và áp dụng đệ quy
        return Arrays.deepToString((Object[]) obj);
    }
}
