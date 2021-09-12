import java.util.Scanner;

public class Solution {
  static final int MODULUS = 1_000_000_007;

  public static void main(String[] args) {
    Scanner sc = new Scanner(System.in);

    int T = sc.nextInt();
    for (int tc = 1; tc <= T; ++tc) {
      sc.nextInt();
      String S = sc.next();

      System.out.println(String.format("Case #%d: %d", tc, solve(S)));
    }

    sc.close();
  }

  static int solve(String S) {
    int result = 0;
    int lastIndex = -1;
    int prevSum = 0;
    for (int i = 0; i < S.length(); ++i) {
      int currentSum;
      if (S.charAt(i) == 'F') {
        currentSum = prevSum;
      } else {
        if (lastIndex != -1 && S.charAt(lastIndex) != S.charAt(i)) {
          currentSum = addMod(prevSum, lastIndex + 1);
        } else {
          currentSum = prevSum;
        }

        lastIndex = i;
      }
      result = addMod(result, currentSum);

      prevSum = currentSum;
    }

    return result;
  }

  static int addMod(int x, int y) {
    return (x + y) % MODULUS;
  }
}
