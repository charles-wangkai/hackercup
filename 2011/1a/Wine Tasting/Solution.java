// https://en.wikipedia.org/wiki/Derangement

import java.math.BigInteger;
import java.util.Scanner;

public class Solution {
    static final int MODULUS = 1_051_962_371;
    static final int LIMIT = 100;

    static int[] derangements = buildDerangements();
    static int[] modInverses = buildModInverses();

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        int N = sc.nextInt();
        for (int tc = 1; tc <= N; ++tc) {
            int G = sc.nextInt();
            int C = sc.nextInt();

            System.out.println(String.format("Case #%d: %d", tc, solve(G, C)));
        }

        sc.close();
    }

    static int[] buildDerangements() {
        int[] derangements = new int[LIMIT];
        derangements[0] = 1;
        for (int i = 2; i < derangements.length; ++i) {
            derangements[i] = multiplyMod(i - 1, addMod(derangements[i - 1], derangements[i - 2]));
        }

        return derangements;
    }

    static int[] buildModInverses() {
        int[] modInverses = new int[LIMIT + 1];
        for (int i = 1; i < modInverses.length; ++i) {
            modInverses[i] = BigInteger.valueOf(i).modInverse(BigInteger.valueOf(MODULUS)).intValue();
        }

        return modInverses;
    }

    static int solve(int G, int C) {
        int result = 0;
        for (int i = C; i <= G; ++i) {
            result = addMod(result, multiplyMod(C(G, i), derangements[G - i]));
        }

        return result;
    }

    static int addMod(int x, int y) {
        return (x + y) % MODULUS;
    }

    static int multiplyMod(int x, int y) {
        return (int) ((long) x * y % MODULUS);
    }

    static int C(int n, int r) {
        int result = 1;
        for (int i = 0; i < r; ++i) {
            result = multiplyMod(result, multiplyMod(n - i, modInverses[i + 1]));
        }

        return result;
    }
}