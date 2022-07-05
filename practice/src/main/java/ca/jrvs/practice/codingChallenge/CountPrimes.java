package ca.jrvs.practice.codingChallenge;

import java.util.Arrays;

public class CountPrimes {
    public int countPrimes(int n) {
        if (n <= 2) {
            return 0;
        }
        boolean[] notPrimes = new boolean[n];
        notPrimes[0] = true;
        notPrimes[1] = true;
        int count = 0;

        for (int i = 2; i * i < n; i++) {
            if (!notPrimes[i]) {
                for (int j = i; i * j < n; j++) {
                    notPrimes[i*j] = true;
                }
            }
        }
        for (int i = 0; i < n; i++) {
            if (!notPrimes[i]) {
                count++;
            }
        }
        return count;
    }
}
