import java.util.Scanner;

public class Solution {
  public static void main(String[] args) {
    Scanner sc = new Scanner(System.in);

    int T = sc.nextInt();
    for (int tc = 1; tc <= T; ++tc) {
      String S = sc.next();

      System.out.println(String.format("Case #%d: %d", tc, solve(S)));
    }

    sc.close();
  }

  static int solve(String S) {
    int result = Integer.MAX_VALUE;
    for (char target = 'A'; target <= 'Z'; ++target) {
      boolean vowel = isVowel(target);
      int time = 0;
      for (char ch : S.toCharArray()) {
        if (ch != target) {
          time += (isVowel(ch) != vowel) ? 1 : 2;
        }
      }

      result = Math.min(result, time);
    }

    return result;
  }

  static boolean isVowel(char letter) {
    return "AEIOU".indexOf(letter) != -1;
  }
}
