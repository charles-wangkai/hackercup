import java.util.Scanner;

public class Solution {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        int T = sc.nextInt();
        for (int tc = 1; tc <= T; ++tc) {
            int gp = sc.nextInt();
            int gc = sc.nextInt();
            int gf = sc.nextInt();
            int N = sc.nextInt();
            int[] P = new int[N];
            int[] C = new int[N];
            int[] F = new int[N];
            for (int i = 0; i < N; ++i) {
                P[i] = sc.nextInt();
                C[i] = sc.nextInt();
                F[i] = sc.nextInt();
            }

            System.out.println(String.format("Case #%d: %s", tc, solve(gp, gc, gf, P, C, F) ? "yes" : "no"));
        }

        sc.close();
    }

    static boolean solve(int gp, int gc, int gf, int[] P, int[] C, int[] F) {
        int N = P.length;

        for (int code = 0; code < 1 << N; ++code) {
            int pSum = 0;
            int cSum = 0;
            int fSum = 0;
            for (int i = 0; i < N; ++i) {
                if ((code & (1 << i)) != 0) {
                    pSum += P[i];
                    cSum += C[i];
                    fSum += F[i];
                }
            }

            if (pSum == gp && cSum == gc && fSum == gf) {
                return true;
            }
        }

        return false;
    }
}