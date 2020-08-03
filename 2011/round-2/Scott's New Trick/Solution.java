// https://aholzner.wordpress.com/2011/02/07/facebook-hacker-cup-round-2-problem-1-some-discussion/
// https://en.wikipedia.org/wiki/Multiplication_algorithm#Karatsuba_multiplication

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
import java.util.stream.IntStream;

public class Solution {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        int T = sc.nextInt();
        for (int tc = 1; tc <= T; ++tc) {
            int P = sc.nextInt();
            int L = sc.nextInt();
            int[] a = buildSequence(sc, P);
            int[] b = buildSequence(sc, P);

            System.out.println(String.format("Case #%d: %d", tc, solve(P, L, a, b)));
        }

        sc.close();
    }

    static int[] buildSequence(Scanner sc, int P) {
        int size = sc.nextInt();
        int v1 = sc.nextInt();
        int v2 = sc.nextInt();
        int v3 = sc.nextInt();
        int v4 = sc.nextInt();
        int v5 = sc.nextInt();

        int[] sequence = new int[size];
        sequence[0] = v1;
        sequence[1] = v2;
        for (int i = 2; i < sequence.length; ++i) {
            sequence[i] = addMod(addMod(multiplyMod(sequence[i - 2], v3, P), multiplyMod(sequence[i - 1], v4, P), P),
                    v5, P);
        }

        return sequence;
    }

    static long solve(int P, int L, int[] a, int[] b) {
        int primitiveRoot = computePrimitiveRoot(P);
        int[] powers = buildPowers(P, primitiveRoot);
        int[] discreteLogs = buildDiscreteLogs(P, primitiveRoot);

        int[] aPolynomial = buildPolynomial(P, discreteLogs, a);
        int aZeroCount = a.length - Arrays.stream(aPolynomial).sum();

        int[] bPolynomial = buildPolynomial(P, discreteLogs, b);
        int bZeroCount = b.length - Arrays.stream(bPolynomial).sum();

        long[] productPolynomial = multiply(aPolynomial, bPolynomial);

        return (long) aZeroCount * b.length + (long) bZeroCount * a.length - (long) aZeroCount * bZeroCount
                + IntStream.range(0, productPolynomial.length).filter(i -> powers[i % (P - 1)] < L)
                        .mapToLong(i -> productPolynomial[i]).sum();
    }

    static long[] multiply(int[] aPolynomial, int[] bPolynomial) {
        return quickPolynomialMultiply(Arrays.stream(aPolynomial).asLongStream().toArray(),
                Arrays.stream(bPolynomial).asLongStream().toArray());
    }

    static long[] quickPolynomialMultiply(long[] poly1, long[] poly2) {
        if (poly1.length <= 10) {
            long[] result = new long[poly1.length + poly2.length - 1];
            for (int i = 0; i < poly1.length; ++i) {
                for (int j = 0; j < poly2.length; ++j) {
                    result[i + j] += poly1[i] * poly2[j];
                }
            }

            return result;
        }

        int half = (poly1.length + 1) / 2;

        long[] lower1 = new long[half];
        long[] upper1 = new long[half];
        System.arraycopy(poly1, 0, lower1, 0, half);
        System.arraycopy(poly1, half, upper1, 0, poly1.length - half);

        long[] lower2 = new long[half];
        long[] upper2 = new long[half];
        System.arraycopy(poly2, 0, lower2, 0, half);
        System.arraycopy(poly2, half, upper2, 0, poly2.length - half);

        long[] F = quickPolynomialMultiply(upper1, upper2);
        long[] G = quickPolynomialMultiply(lower1, lower2);
        long[] H = quickPolynomialMultiply(addPolynomial(lower1, upper1), addPolynomial(lower2, upper2));

        long[] result = new long[F.length + 2 * half];
        for (int i = 0; i < F.length; ++i) {
            result[i + 2 * half] += F[i];
            result[i + half] -= F[i];
        }
        for (int i = 0; i < G.length; ++i) {
            result[i + half] -= G[i];
            result[i] += G[i];
        }
        for (int i = 0; i < H.length; ++i) {
            result[i + half] += H[i];
        }

        return result;
    }

    static long[] addPolynomial(long[] poly1, long[] poly2) {
        long[] result = new long[poly1.length];
        for (int i = 0; i < result.length; ++i) {
            result[i] = poly1[i] + poly2[i];
        }

        return result;
    }

    static int[] buildPolynomial(int P, int[] discreteLogs, int[] sequence) {
        int[] polynomial = new int[P - 1];
        for (int x : sequence) {
            int r = x % P;
            if (r != 0) {
                ++polynomial[discreteLogs[r]];
            }
        }

        return polynomial;
    }

    static int[] buildPowers(int P, int primitiveRoot) {
        int[] powers = new int[P - 1];
        int power = 1;
        for (int i = 0; i < powers.length; ++i) {
            powers[i] = power;
            power = multiplyMod(power, primitiveRoot, P);
        }

        return powers;
    }

    static int[] buildDiscreteLogs(int P, int primitiveRoot) {
        int[] discreteLogs = new int[P];
        int power = 1;
        for (int i = 0; i <= P - 2; ++i) {
            discreteLogs[power] = i;
            power = multiplyMod(power, primitiveRoot, P);
        }

        return discreteLogs;
    }

    static int computePrimitiveRoot(int P) {
        for (int i = 2;; ++i) {
            if (isPrimitiveRoot(P, i)) {
                return i;
            }
        }
    }

    static boolean isPrimitiveRoot(int P, int x) {
        int phi = P - 1;
        int remain = phi;
        for (int i = 2; i * i <= remain; ++i) {
            if (remain % i == 0) {
                if (powMod(x, phi / i, P) == 1) {
                    return false;
                }

                while (remain % i == 0) {
                    remain /= i;
                }
            }
        }
        if (remain != 1 && powMod(x, phi / remain, P) == 1) {
            return false;
        }

        return true;
    }

    static int addMod(int x, int y, int P) {
        return (x + y) % P;
    }

    static int multiplyMod(int x, int y, int P) {
        return (int) ((long) x * y % P);
    }

    static int powMod(int base, int exponent, int P) {
        return BigInteger.valueOf(base).modPow(BigInteger.valueOf(exponent), BigInteger.valueOf(P)).intValue();
    }
}