import java.util.Arrays;
import java.util.Scanner;

public class Solution {
  public static void main(String[] args) {
    Scanner sc = new Scanner(System.in);

    int T = sc.nextInt();
    for (int tc = 1; tc <= T; ++tc) {
      int N = sc.nextInt();
      int[] X = new int[N];
      for (int i = 0; i < X.length; ++i) {
        X[i] = sc.nextInt();
      }

      System.out.println(String.format("Case #%d: %.9f", tc, solve(X)));
    }

    sc.close();
  }

  static double solve(int[] X) {
    Arrays.sort(X);

    if (X.length == 5) {
      return Math.max(
          (X[3] + X[4]) / 2.0 - (X[0] + X[2]) / 2.0, (X[2] + X[4]) / 2.0 - (X[0] + X[1]) / 2.0);
    }

    return (X[X.length - 2] + X[X.length - 1]) / 2.0 - (X[0] + X[1]) / 2.0;
  }
}
