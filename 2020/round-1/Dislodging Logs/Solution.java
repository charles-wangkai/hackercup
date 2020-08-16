import java.util.Arrays;
import java.util.Scanner;

public class Solution {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        int T = sc.nextInt();
        for (int tc = 1; tc <= T; ++tc) {
            int N = sc.nextInt();
            int M = sc.nextInt();
            int K = sc.nextInt();
            int S = sc.nextInt();
            int[] P = new int[N];
            for (int i = 0; i < K; ++i) {
                P[i] = sc.nextInt();
            }
            int AP = sc.nextInt();
            int BP = sc.nextInt();
            int CP = sc.nextInt();
            int DP = sc.nextInt();
            int[] Q = new int[M];
            for (int i = 0; i < K; ++i) {
                Q[i] = sc.nextInt();
            }
            int AQ = sc.nextInt();
            int BQ = sc.nextInt();
            int CQ = sc.nextInt();
            int DQ = sc.nextInt();

            System.out.println(
                    String.format("Case #%d: %d", tc, solve(N, M, K, S, P, AP, BP, CP, DP, Q, AQ, BQ, CQ, DQ)));
        }

        sc.close();
    }

    static int solve(int N, int M, int K, int S, int[] P, int AP, int BP, int CP, int DP, int[] Q, int AQ, int BQ,
            int CQ, int DQ) {
        generate(K, P, AP, BP, CP, DP);
        generate(K, Q, AQ, BQ, CQ, DQ);

        P = Arrays.stream(P).boxed().sorted().mapToInt(x -> x).toArray();
        Q = Arrays.stream(Q).boxed().sorted().mapToInt(x -> x).toArray();

        int result = -1;
        int lower = 0;
        int upper = 2 * Math.max(P[P.length - 1], Q[Q.length - 1]);
        while (lower <= upper) {
            int middle = (lower + upper) / 2;
            if (check(P, Q, middle)) {
                result = middle;
                upper = middle - 1;
            } else {
                lower = middle + 1;
            }
        }

        return result;
    }

    static void generate(int K, int[] values, int A, int B, int C, int D) {
        for (int i = K; i < values.length; ++i) {
            values[i] = (int) (((long) A * values[i - 2] + (long) B * values[i - 1] + C) % D) + 1;
        }
    }

    static boolean check(int[] P, int[] Q, int time) {
        int qIndex = 0;
        for (int Pi : P) {
            int remain = time;
            int originalQIndex = qIndex;
            int pos = Pi;
            while (qIndex != Q.length && Math.abs(pos - Q[qIndex]) <= remain) {
                remain -= Math.abs(pos - Q[qIndex]);
                pos = Q[qIndex];
                ++qIndex;
            }

            if (qIndex != originalQIndex) {
                remain += Math.abs(Pi - Q[originalQIndex]);
                while (qIndex != Q.length && Math.abs(Pi - Q[qIndex]) + (Q[qIndex] - Q[qIndex - 1]) <= remain) {
                    remain -= Q[qIndex] - Q[qIndex - 1];
                    ++qIndex;
                }
            }

            if (qIndex == Q.length) {
                return true;
            }
        }

        return false;
    }
}