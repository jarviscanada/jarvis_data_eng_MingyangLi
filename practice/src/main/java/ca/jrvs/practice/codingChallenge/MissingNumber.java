package ca.jrvs.practice.codingChallenge;

public class MissingNumber {
    public int missingNumber(int[] nums) {
        Integer[] list = new Integer[nums.length + 1];
        for (int i = 0; i < nums.length; i++) {
            list[nums[i]] = nums[i];
        }
        for (int i = 0; i < list.length; i++) {
            if (list[i] == null) {
                return i;
            }
        }
        return -1;

    }
}
