import java.util.Scanner;

public class Solution {
  public static void main(String[] args) {
    Scanner sc = new Scanner(System.in);

    int T = sc.nextInt();
    for (int tc = 1; tc <= T; ++tc) {
      sc.nextInt();
      String W = sc.next();

      System.out.println(String.format("Case #%d: %d", tc, solve(W)));
    }

    sc.close();
  }

  static int solve(String W) {
    return Math.min(computeSwitchNum(W, false), computeSwitchNum(W, true));
  }

  static int computeSwitchNum(String W, boolean leftOrRight) {
    int result = 0;
    for (char ch : W.toCharArray()) {
      if (ch == 'X') {
        if (!leftOrRight) {
          ++result;
          leftOrRight = true;
        }
      } else if (ch == 'O') {
        if (leftOrRight) {
          ++result;
          leftOrRight = false;
        }
      }
    }

    return result;
  }
}
