import java.util.Scanner;

public class Solution {
  public static void main(String[] args) {
    Scanner sc = new Scanner(System.in);

    int T = sc.nextInt();
    for (int tc = 1; tc <= T; ++tc) {
      int S = sc.nextInt();
      int D = sc.nextInt();
      int K = sc.nextInt();

      System.out.println(String.format("Case #%d: %s", tc, solve(S, D, K) ? "YES" : "NO"));
    }

    sc.close();
  }

  static boolean solve(int S, int D, int K) {
    return 2 * S + 2 * D >= K + 1 && S + 2 * D >= K;
  }
}
