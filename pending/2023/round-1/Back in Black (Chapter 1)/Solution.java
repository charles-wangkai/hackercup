import java.util.Scanner;

public class Solution {
  public static void main(String[] args) {
    Scanner sc = new Scanner(System.in);

    int T = sc.nextInt();
    for (int tc = 1; tc <= T; ++tc) {
      sc.nextInt();
      String S = sc.next();
      int Q = sc.nextInt();
      int[] B = new int[Q];
      for (int i = 0; i < B.length; ++i) {
        B[i] = sc.nextInt();
      }

      System.out.println(String.format("Case #%d: %d", tc, solve(S, B)));
    }

    sc.close();
  }

  static int solve(String S, int[] B) {
    boolean[] whites = new boolean[S.length()];
    for (int i = 0; i < whites.length; ++i) {
      whites[i] = S.charAt(i) == '1';
    }

    boolean[] pushed = new boolean[S.length()];
    for (int Bi : B) {
      pushed[Bi - 1] ^= true;
    }

    for (int i = 0; i < pushed.length; ++i) {
      if (pushed[i]) {
        for (int j = i; j < whites.length; j += i + 1) {
          whites[j] ^= true;
        }
      }
    }

    int result = 0;
    for (int i = 0; i < whites.length; ++i) {
      if (whites[i]) {
        for (int j = i; j < whites.length; j += i + 1) {
          whites[j] ^= true;
        }

        ++result;
      }
    }

    return result;
  }
}
