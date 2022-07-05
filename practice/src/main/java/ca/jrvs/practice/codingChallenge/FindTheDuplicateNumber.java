package ca.jrvs.practice.codingChallenge;


import java.util.HashSet;

public class FindTheDuplicateNumber {
    public int findDuplicate(int[] nums) {
        HashSet<Integer> seen = new HashSet<Integer>();
        for (int num : nums) {
            if (seen.contains(num))
                return num;
            seen.add(num);
        }
        return -1;
    }

    public boolean containsDuplicate(int[] nums) {
        HashSet<Integer> seen = new HashSet<Integer>();
        for (int num : nums) {
            if (seen.contains(num))
                return true;
            seen.add(num);
        }
        return false;
    }
}
