import java.util.Arrays;
import java.util.Scanner;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Solution {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        int N = sc.nextInt();
        for (int tc = 1; tc <= N; ++tc) {
            int Df = sc.nextInt();
            int[] F = new int[Df + 1];
            for (int i = 0; i < F.length; ++i) {
                F[i] = sc.nextInt();
            }

            int Dg = sc.nextInt();
            int[] G = new int[Dg + 1];
            for (int i = 0; i < G.length; ++i) {
                G[i] = sc.nextInt();
            }

            System.out.println(String.format("Case #%d: %s", tc, solve(F, G)));
        }

        sc.close();
    }

    static String solve(int[] F, int[] G) {
        if (F.length < G.length) {
            return "no solution";
        }

        int[] H = new int[F.length - G.length + 1];
        for (int i = H.length - 1; i >= 0; --i) {
            if (F[i + G.length - 1] % G[G.length - 1] != 0) {
                return "no solution";
            }

            H[i] = F[i + G.length - 1] / G[G.length - 1];

            for (int j = 0; j < G.length; ++j) {
                F[i + j] -= H[i] * G[j];
            }
        }

        if (IntStream.range(0, G.length).anyMatch(i -> F[i] != 0)) {
            return "no solution";
        }

        return Arrays.stream(H).mapToObj(String::valueOf).collect(Collectors.joining(" "));
    }
}