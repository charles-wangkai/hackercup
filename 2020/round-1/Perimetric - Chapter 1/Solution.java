import java.util.Scanner;

public class Solution {
    static final int MODULUS = 1_000_000_007;

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        int T = sc.nextInt();
        for (int tc = 1; tc <= T; ++tc) {
            int N = sc.nextInt();
            int K = sc.nextInt();
            int W = sc.nextInt();
            int[] L = new int[N];
            for (int i = 0; i < K; ++i) {
                L[i] = sc.nextInt();
            }
            int AL = sc.nextInt();
            int BL = sc.nextInt();
            int CL = sc.nextInt();
            int DL = sc.nextInt();
            int[] H = new int[N];
            for (int i = 0; i < K; ++i) {
                H[i] = sc.nextInt();
            }
            int AH = sc.nextInt();
            int BH = sc.nextInt();
            int CH = sc.nextInt();
            int DH = sc.nextInt();

            System.out.println(String.format("Case #%d: %d", tc, solve(N, K, W, L, AL, BL, CL, DL, H, AH, BH, CH, DH)));
        }

        sc.close();
    }

    static int solve(int N, int K, int W, int[] L, int AL, int BL, int CL, int DL, int[] H, int AH, int BH, int CH,
            int DH) {
        generate(K, L, AL, BL, CL, DL);
        generate(K, H, AH, BH, CH, DH);

        int result = 1;
        int P = 0;
        for (int i = 0; i < N; ++i) {
            if (i == 0 || L[i] - L[i - 1] > W) {
                P = addMod(P, (H[i] + W) * 2);
            } else {
                P = addMod(P, (L[i] - L[i - 1]) * 2);

                int maxH = 0;
                for (int j = i - 1; j >= 0 && L[i] - L[j] <= W; --j) {
                    maxH = Math.max(maxH, H[j]);
                }

                P = addMod(P, Math.max(0, H[i] - maxH) * 2);
            }

            result = multiplyMod(result, P);
        }

        return result;
    }

    static void generate(int K, int[] values, int A, int B, int C, int D) {
        for (int i = K; i < values.length; ++i) {
            values[i] = (int) (((long) A * values[i - 2] + (long) B * values[i - 1] + C) % D) + 1;
        }
    }

    static int addMod(int x, int y) {
        return (x + y) % MODULUS;
    }

    static int multiplyMod(int x, int y) {
        return (int) ((long) x * y % MODULUS);
    }
}