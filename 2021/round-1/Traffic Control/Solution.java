import java.util.Arrays;
import java.util.Scanner;
import java.util.stream.Collectors;

public class Solution {
  static final int WAIT_LIMIT = 1000;

  public static void main(String[] args) {
    Scanner sc = new Scanner(System.in);

    int T = sc.nextInt();
    for (int tc = 1; tc <= T; ++tc) {
      int N = sc.nextInt();
      int M = sc.nextInt();
      int A = sc.nextInt();
      int B = sc.nextInt();

      System.out.println(String.format("Case #%d: %s", tc, solve(N, M, A, B)));
    }

    sc.close();
  }

  static String solve(int N, int M, int A, int B) {
    if (Math.min(A, B) < N + M - 1) {
      return "Impossible";
    }

    int[][] grid = new int[N][M];
    for (int r = 0; r < N; ++r) {
      for (int c = 0; c < M; ++c) {
        grid[r][c] = (r == N - 1 || c == 0 || c == M - 1) ? 1 : WAIT_LIMIT;
      }
    }
    grid[0][0] = A - (N + M - 2);
    grid[0][M - 1] = B - (N + M - 2);

    return String.format(
        "Possible\n%s",
        Arrays.stream(grid)
            .map(
                line ->
                    Arrays.stream(line).mapToObj(String::valueOf).collect(Collectors.joining(" ")))
            .collect(Collectors.joining("\n")));
  }
}
