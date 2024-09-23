import java.util.Arrays;
import java.util.Scanner;

public class Solution {
  public static void main(String[] args) {
    Scanner sc = new Scanner(System.in);

    int T = sc.nextInt();
    for (int tc = 1; tc <= T; ++tc) {
      int N = sc.nextInt();
      int K = sc.nextInt();
      int[] S = new int[N];
      for (int i = 0; i < S.length; ++i) {
        S[i] = sc.nextInt();
      }

      System.out.println("Case #%d: %s".formatted(tc, solve(S, K) ? "YES" : "NO"));
    }

    sc.close();
  }

  static boolean solve(int[] S, int K) {
    return (long) Arrays.stream(S).min().getAsInt() * Math.max(1, S.length * 2 - 3) <= K;
  }
}