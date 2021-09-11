import java.util.Scanner;

public class Solution {
  static final int ALPHABET_SIZE = 26;

  public static void main(String[] args) {
    Scanner sc = new Scanner(System.in);

    int T = sc.nextInt();
    for (int tc = 1; tc <= T; ++tc) {
      String S = sc.next();
      int K = sc.nextInt();
      char[] A = new char[K];
      char[] B = new char[K];
      for (int i = 0; i < K; ++i) {
        String replacement = sc.next();
        A[i] = replacement.charAt(0);
        B[i] = replacement.charAt(1);
      }

      System.out.println(String.format("Case #%d: %d", tc, solve(S, A, B)));
    }

    sc.close();
  }

  static int solve(String S, char[] A, char[] B) {
    int[][] distances = new int[ALPHABET_SIZE][ALPHABET_SIZE];
    for (int i = 0; i < ALPHABET_SIZE; ++i) {
      for (int j = 0; j < ALPHABET_SIZE; ++j) {
        distances[i][j] = (i == j) ? 0 : Integer.MAX_VALUE;
      }
    }
    for (int i = 0; i < A.length; ++i) {
      distances[A[i] - 'A'][B[i] - 'A'] = 1;
    }

    for (int k = 0; k < ALPHABET_SIZE; ++k) {
      for (int i = 0; i < ALPHABET_SIZE; ++i) {
        for (int j = 0; j < ALPHABET_SIZE; ++j) {
          if (distances[i][k] != Integer.MAX_VALUE && distances[k][j] != Integer.MAX_VALUE) {
            distances[i][j] = Math.min(distances[i][j], distances[i][k] + distances[k][j]);
          }
        }
      }
    }

    int result = Integer.MAX_VALUE;
    for (char target = 'A'; target <= 'Z'; ++target) {
      int time = 0;
      for (char ch : S.toCharArray()) {
        if (distances[ch - 'A'][target - 'A'] == Integer.MAX_VALUE) {
          time = Integer.MAX_VALUE;

          break;
        }

        time += distances[ch - 'A'][target - 'A'];
      }

      result = Math.min(result, time);
    }

    return (result == Integer.MAX_VALUE) ? -1 : result;
  }
}
