import java.util.Arrays;
import java.util.Scanner;
import java.util.stream.IntStream;

public class Solution {
    static final int LIMIT = 10_000_000;
    static int[] primeFactorCounts = buildPrimeFactorCounts();

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        int T = sc.nextInt();
        for (int tc = 1; tc <= T; ++tc) {
            int a = sc.nextInt();
            int b = sc.nextInt();
            int n = sc.nextInt();

            System.out.println(String.format("Case #%d: %d", tc, solve(a, b, n)));
        }

        sc.close();
    }

    static int[] buildPrimeFactorCounts() {
        boolean[] primes = new boolean[LIMIT + 1];
        Arrays.fill(primes, true);

        int[] primeFactorCounts = new int[LIMIT + 1];

        for (int i = 2; i <= LIMIT; ++i) {
            if (primes[i]) {
                for (int j = i; j <= LIMIT; j += i) {
                    primes[j] = false;
                    ++primeFactorCounts[j];
                }
            }
        }

        return primeFactorCounts;
    }

    static int solve(int a, int b, int n) {
        return (int) IntStream.rangeClosed(a, b).filter(i -> primeFactorCounts[i] == n).count();
    }
}