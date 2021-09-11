import java.util.Scanner;

public class Solution {
  public static void main(String[] args) {
    Scanner sc = new Scanner(System.in);

    int T = sc.nextInt();
    for (int tc = 1; tc <= T; ++tc) {
      int N = sc.nextInt();
      char[][] C = new char[N][N];
      for (int r = 0; r < N; ++r) {
        String line = sc.next();
        for (int c = 0; c < N; ++c) {
          C[r][c] = line.charAt(c);
        }
      }

      System.out.println(String.format("Case #%d: %s", tc, solve(C)));
    }

    sc.close();
  }

  static String solve(char[][] C) {
    int N = C.length;

    int oneSetCount = 0;
    for (int r = 0; r < N; ++r) {
      for (int c = 0; c < N; ++c) {
        if (C[r][c] == '.') {
          C[r][c] = 'X';
          if (computeNeeded(C, r, 0, 0, 1) == 0 || computeNeeded(C, 0, c, 1, 0) == 0) {
            ++oneSetCount;
          }
          C[r][c] = '.';
        }
      }
    }
    if (oneSetCount != 0) {
      return String.format("1 %d", oneSetCount);
    }

    int minNeeded = Integer.MAX_VALUE;
    int setCount = 0;
    for (int r = 0; r < N; ++r) {
      int needed = computeNeeded(C, r, 0, 0, 1);
      if (needed == minNeeded) {
        ++setCount;
      } else if (needed < minNeeded) {
        minNeeded = needed;
        setCount = 1;
      }
    }
    for (int c = 0; c < N; ++c) {
      int needed = computeNeeded(C, 0, c, 1, 0);
      if (needed == minNeeded) {
        ++setCount;
      } else if (needed < minNeeded) {
        minNeeded = needed;
        setCount = 1;
      }
    }

    return (minNeeded == Integer.MAX_VALUE)
        ? "Impossible"
        : String.format("%d %d", minNeeded, setCount);
  }

  static int computeNeeded(char[][] C, int beginR, int beginC, int offsetR, int offsetC) {
    int N = C.length;

    int needed = 0;
    for (int r = beginR, c = beginC;
        r >= 0 && r < N && c >= 0 && c < N;
        r += offsetR, c += offsetC) {
      if (C[r][c] == 'O') {
        return Integer.MAX_VALUE;
      }
      if (C[r][c] == '.') {
        ++needed;
      }
    }

    return needed;
  }
}
