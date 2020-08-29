import java.util.Scanner;

public class Solution {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        int T = sc.nextInt();
        for (int tc = 1; tc <= T; ++tc) {
            int N = sc.nextInt();
            int K = sc.nextInt();
            int[] S = new int[N];
            for (int i = 0; i < K; ++i) {
                S[i] = sc.nextInt();
            }
            int AS = sc.nextInt();
            int BS = sc.nextInt();
            int CS = sc.nextInt();
            int DS = sc.nextInt();
            int[] X = new int[N];
            for (int i = 0; i < K; ++i) {
                X[i] = sc.nextInt();
            }
            int AX = sc.nextInt();
            int BX = sc.nextInt();
            int CX = sc.nextInt();
            int DX = sc.nextInt();
            int[] Y = new int[N];
            for (int i = 0; i < K; ++i) {
                Y[i] = sc.nextInt();
            }
            int AY = sc.nextInt();
            int BY = sc.nextInt();
            int CY = sc.nextInt();
            int DY = sc.nextInt();

            System.out.println(String.format("Case #%d: %d", tc,
                    solve(N, K, S, AS, BS, CS, DS, X, AX, BX, CX, DX, Y, AY, BY, CY, DY)));
        }

        sc.close();
    }

    static long solve(int N, int K, int[] S, int AS, int BS, int CS, int DS, int[] X, int AX, int BX, int CX, int DX,
            int[] Y, int AY, int BY, int CY, int DY) {
        generate(K, S, AS, BS, CS, DS);
        generate(K, X, AX, BX, CX, DX);
        generate(K, Y, AY, BY, CY, DY);

        long requiredExtra = 0;
        long optionalExtra = 0;
        long requiredLack = 0;
        long optionalLack = 0;
        for (int i = 0; i < N; ++i) {
            requiredExtra += Math.max(0, S[i] - (X[i] + Y[i]));
            optionalExtra += Math.max(0, Math.min(S[i], X[i] + Y[i]) - X[i]);
            requiredLack += Math.max(0, X[i] - S[i]);
            optionalLack += Math.max(0, (X[i] + Y[i]) - Math.max(S[i], X[i]));
        }

        long minRequired = Math.min(requiredExtra, requiredLack);
        requiredExtra -= minRequired;
        requiredLack -= minRequired;

        long result = minRequired;
        if (requiredExtra != 0) {
            if (requiredExtra > optionalLack) {
                return -1;
            }

            result += requiredExtra;
        } else if (requiredLack != 0) {
            if (requiredLack > optionalExtra) {
                return -1;
            }

            result += requiredLack;
        }

        return result;
    }

    static void generate(int K, int[] values, int A, int B, int C, int D) {
        for (int i = K; i < values.length; ++i) {
            values[i] = (int) (((long) A * values[i - 2] + (long) B * values[i - 1] + C) % D);
        }
    }
}