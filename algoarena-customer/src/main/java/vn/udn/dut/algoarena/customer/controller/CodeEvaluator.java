package vn.udn.dut.algoarena.customer.controller;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.IOException;

public class CodeEvaluator {

    public static void main(String[] args) {
        String solutionScript = "<?php\n" +
                "class UserSolution {\n" +
                "    public function twoSum($nums, $target) {\n" +
                "        $map = [];\n" +
                "        foreach ($nums as $i => $num) {\n" +
                "            $complement = $target - $num;\n" +
                "            if (isset($map[$complement])) {\n" +
                "                return [$map[$complement], $i];\n" +
                "            }\n" +
                "            $map[$num] = $i;\n" +
                "        }\n" +
                "        return [];\n" +
                "    }\n" +
                "}\n" +
                "?>";

        String testcaseString = "<?php\n" +
                "$nums = [2, 7, 11, 15];\n" +
                "$target = 9;\n" +
                "$userSolution = new UserSolution();\n" +
                "$result = $userSolution->twoSum($nums, $target);\n" +
                "echo 'Result: ';\n" +
                "print_r($result);\n" +
                "?>";

        try {
            // Kết hợp mã solution và testcase thành một chuỗi PHP hoàn chỉnh
            String fullScript = solutionScript + "\n" + testcaseString;

            // Thiết lập ProcessBuilder để chạy PHP từ bộ nhớ tạm thông qua php://memory
            ProcessBuilder processBuilder = new ProcessBuilder("php", "-r", fullScript);
            processBuilder.redirectErrorStream(true);

            // Chạy process và lấy kết quả từ output stream
            Process process = processBuilder.start();
            BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
            String line;
            while ((line = reader.readLine()) != null) {
                System.out.println(line);  // In kết quả trả về từ PHP
            }

            // Chờ process kết thúc
            int exitCode = process.waitFor();
            if (exitCode == 0) {
                System.out.println("PHP script executed successfully!");
            } else {
                System.out.println("Error during PHP script execution.");
            }

        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }
}
