package vn.udn.dut.algoarena.customer.controller.test;

import java.util.*;

public class Main {
    public static void main(String[] args) {
        try {
            int[] nums1 = new int[] {2, 7, 11, 15};
            int target1 = 9;
            int[] expect1 = new int[] {0, 1};

            int[] nums2 = new int[] {3, 2, 4};
            int target2 = 6;
            int[] expect2 = new int[] {1, 2};

            int[] nums3 = new int[] {3, 3};
            int target3 = 6;
            int[] expect3 = new int[] {0, 1};

            int[] result1 = twoSum(nums1, target1);
            int[] result2 = twoSum(nums2, target2);
            int[] result3 = twoSum(nums3, target3);

            System.out.print(compare(result1, expect1));
            System.out.print(",");
            System.out.print(compare(result2, expect2));
            System.out.print(",");
            System.out.print(compare(result3, expect3));
            System.out.print(",");

        } catch (Exception e) {
            System.out.print(e.getMessage());
        }
    }

    public static int[] twoSum(int[] nums, int target) {
        HashMap<Integer, Integer> map = new HashMap<>();
        for (int i = 0; i < nums.length; i++) {
            int complement = target - nums[i];

            if (map.containsKey(complement)) {
                return new int[]{map.get(complement), i};
            }

            map.put(nums[i], i);
        }
        throw new IllegalArgumentException("No solution found");
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
