import java.util.Scanner;

public class Solution {
  static final int MODULUS = 998_244_353;

  public static void main(String[] args) {
    Scanner sc = new Scanner(System.in);

    int T = sc.nextInt();
    for (int tc = 1; tc <= T; ++tc) {
      long W = sc.nextLong();
      long G = sc.nextLong();
      long L = sc.nextLong();

      System.out.println("Case #%d: %d".formatted(tc, solve(W, G, L)));
    }

    sc.close();
  }

  static int solve(long W, long G, long L) {
    return mod((long) mod(W - G) * mod(2 * L + 1));
  }

  static int mod(long x) {
    return (int) (x % MODULUS);
  }
}