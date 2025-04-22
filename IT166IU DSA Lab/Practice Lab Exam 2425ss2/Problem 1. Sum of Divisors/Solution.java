import java.util.*;

public class Solution {
    static final long MOD = 1000000007;
    
    public static void main(String[] args) {
        int n;
        try (Scanner sc = new Scanner(System.in)) {
            n = sc.nextInt();
        }

        // Array to store sigma(i) for each i from 1 to n
        long[] sigma = new long[n + 1];

        // Compute sigma(i) using a sieve-like approach
        for (int i = 1; i <= n; i++) {
            for (int j = i; j <= n; j += i) {
                sigma[j] = (sigma[j] + i) % MOD;
            }
        }

        // Compute the cumulative sum of sigma(i)
        long result = 0;
        for (int i = 1; i <= n; i++) {
            result = (result + sigma[i]) % MOD;
        }

        System.out.println(result);
    }
}