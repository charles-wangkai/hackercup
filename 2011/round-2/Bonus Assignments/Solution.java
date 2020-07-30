// https://aholzner.wordpress.com/2011/02/08/facebook-hacker-cup-round-2-problem-3-%E2%80%93-some-discussion/

import java.math.BigInteger;
import java.util.Arrays;
import java.util.Scanner;

public class Solution {
    static final int MODULUS = 1_000_000_007;

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        int T = sc.nextInt();
        for (int tc = 1; tc <= T; ++tc) {
            int N = sc.nextInt();
            int A = sc.nextInt();
            int B = sc.nextInt();
            int C = sc.nextInt();
            int D = sc.nextInt();

            System.out.println(String.format("Case #%d: %d", tc, solve(N, A, B, C, D)));
        }

        sc.close();
    }

    static int solve(int N, int A, int B, int C, int D) {
        return subtractMod(addMod(f(N, A, D), f(N, B + 1, C - 1)), addMod(f(N, A, C - 1), f(N, B + 1, D)));
    }

    static int addMod(int x, int y) {
        return (x + y) % MODULUS;
    }

    static int subtractMod(int x, int y) {
        return (x - y + MODULUS) % MODULUS;
    }

    static int multiplyMod(int x, int y) {
        return ((int) ((long) x * y % MODULUS) + MODULUS) % MODULUS;
    }

    static int powMod(int base, int exponent) {
        return BigInteger.valueOf(base).modPow(BigInteger.valueOf(exponent), BigInteger.valueOf(MODULUS)).intValue();
    }

    static int f(int N, int lower, int upper) {
        if (lower > upper) {
            return 0;
        }

        boolean[] primes = new boolean[upper + 1];
        Arrays.fill(primes, true);

        int[] signs = new int[upper + 1];
        Arrays.fill(signs, -1);

        int exclusion = 0;
        for (int i = 2; i <= upper; ++i) {
            if (primes[i]) {
                for (int j = i; j <= upper; j += i) {
                    primes[j] = false;
                    signs[j] *= -1;
                }

                for (long j = (long) i * i; j <= upper; j += (long) i * i) {
                    signs[(int) j] = 0;
                }
            }

            exclusion = addMod(exclusion, multiplyMod(signs[i], powMod(upper / i - (lower + i - 1) / i + 1, N)));
        }

        return subtractMod(powMod(upper - lower + 1, N), exclusion);
    }
}