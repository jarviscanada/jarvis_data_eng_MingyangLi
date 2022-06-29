package ca.jrvs.practice.codingChallenge;

public class FibonacciNumber {
    public int fib(int n) {
        if (n < 2) {
            return n;
        }
        int[] integers = new int[n+1];
        integers[0] = 0;
        integers[1] = 1;
        for (int i = 2; i <= n; i++){
            integers[i] = integers[i-1] + integers[i-2];
        }

        return integers[n];
    }
}
