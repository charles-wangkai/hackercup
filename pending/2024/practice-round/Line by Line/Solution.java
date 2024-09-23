import java.util.Scanner;

public class Solution {
  public static void main(String[] args) {
    Scanner sc = new Scanner(System.in);

    int T = sc.nextInt();
    for (int tc = 1; tc <= T; ++tc) {
      int N = sc.nextInt();
      int P = sc.nextInt();

      System.out.println("Case #%d: %.9f".formatted(tc, solve(N, P)));
    }

    sc.close();
  }

  static double solve(int N, int P) {
    return (Math.pow(P / 100.0, (N - 1.0) / N) - P / 100.0) * 100;
  }
}