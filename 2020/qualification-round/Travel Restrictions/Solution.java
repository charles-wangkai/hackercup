import java.util.Arrays;
import java.util.Scanner;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Solution {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        int T = sc.nextInt();
        for (int tc = 1; tc <= T; ++tc) {
            sc.nextInt();
            String I = sc.next();
            String O = sc.next();

            System.out.println(String.format("Case #%d:\n%s", tc, solve(I, O)));
        }

        sc.close();
    }

    static String solve(String I, String O) {
        int N = I.length();

        boolean[][] reaches = new boolean[N][N];
        for (int i = 0; i < N; ++i) {
            reaches[i][i] = true;
        }
        for (int i = 0; i < N - 1; ++i) {
            reaches[i][i + 1] = (O.charAt(i) == 'Y') && (I.charAt(i + 1) == 'Y');
            reaches[i + 1][i] = (O.charAt(i + 1) == 'Y') && (I.charAt(i) == 'Y');
        }

        for (int k = 0; k < N; ++k) {
            for (int i = 0; i < N; ++i) {
                for (int j = 0; j < N; ++j) {
                    if (reaches[i][k] && reaches[k][j]) {
                        reaches[i][j] = true;
                    }
                }
            }
        }

        return Arrays.stream(reaches)
                .map(line -> IntStream.range(0, N).mapToObj(i -> line[i] ? "Y" : "N").collect(Collectors.joining()))
                .collect(Collectors.joining("\n"));
    }
}