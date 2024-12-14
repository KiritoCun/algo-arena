package vn.udn.dut.algoarena.publicapi.helper;

import java.util.*;

public class Main {
    public static void main(String[] args) {
        String s = "()[]{}";
        boolean expect = true;

        boolean result = isValid(s);
        System.out.print(compare(result, expect));
    }

    public static boolean isValid(String s) {
        Stack<Character> stack = new Stack<>();

        // Duyệt qua từng ký tự trong chuỗi
        for (char c : s.toCharArray()) {
            // Nếu là dấu ngoặc mở, đẩy vào stack
            if (c == '(' || c == '{' || c == '[') {
                stack.push(c);
            }
            // Nếu là dấu ngoặc đóng, kiểm tra xem có khớp với ngoặc mở không
            else if (c == ')' || c == '}' || c == ']') {
                if (stack.isEmpty()) {
                    return false; // Nếu stack rỗng mà gặp ngoặc đóng, không hợp lệ
                }
                char top = stack.pop(); // Lấy phần tử trên đỉnh stack
                if (!isValidPair(top, c)) {
                    return false; // Nếu không khớp với ngoặc mở, không hợp lệ
                }
            }
        }

        // Nếu stack rỗng, tức là tất cả ngoặc đã được đóng đúng cách
        return stack.isEmpty();
    }

    // Hàm kiểm tra xem cặp ngoặc có hợp lệ không
    private  static boolean isValidPair(char open, char close) {
        return (open == '(' && close == ')') ||
                (open == '{' && close == '}') ||
                (open == '[' && close == ']');
    }

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

