package ca.jrvs.practice.codingChallenge;

public class ClimbingStairs {
    public int climbStairs(int n) {
        if (n < 2) {
            return n;
        } else {
            return climbStairs(n-1) + climbStairs(n-2);
        }
    }
}
