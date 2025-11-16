import java.util.Scanner;

public class Solution {
  public static void main(String[] args) {
    Scanner sc = new Scanner(System.in);

    int T = sc.nextInt();
    for (int tc = 0; tc < T; ++tc) {
      int N = sc.nextInt();
      int M = sc.nextInt();

      System.out.println("Case #%d: %s".formatted(tc + 1, solve(N, M) ? "YES" : "NO"));
    }

    sc.close();
  }

  static boolean solve(int N, int M) {
    return (N >= 2 * M && N % 2 == 0) || (N >= M && N <= 2 * M - 2);
  }
}