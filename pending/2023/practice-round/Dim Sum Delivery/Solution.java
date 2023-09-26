import java.util.Scanner;

public class Solution {
  public static void main(String[] args) {
    Scanner sc = new Scanner(System.in);

    int T = sc.nextInt();
    for (int tc = 1; tc <= T; ++tc) {
      int R = sc.nextInt();
      int C = sc.nextInt();
      int A = sc.nextInt();
      int B = sc.nextInt();

      System.out.println(String.format("Case #%d: %s", tc, solve(R, C, A, B) ? "YES" : "NO"));
    }

    sc.close();
  }

  static boolean solve(int R, int C, int A, int B) {
    return R > C;
  }
}
