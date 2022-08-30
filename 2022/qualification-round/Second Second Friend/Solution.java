import java.util.ArrayDeque;
import java.util.Arrays;
import java.util.Queue;
import java.util.Scanner;
import java.util.stream.Collectors;

public class Solution {
  static final int[] R_OFFSETS = {-1, 0, 1, 0};
  static final int[] C_OFFSETS = {0, 1, 0, -1};

  public static void main(String[] args) {
    Scanner sc = new Scanner(System.in);

    int T = sc.nextInt();
    for (int tc = 1; tc <= T; ++tc) {
      int R = sc.nextInt();
      int C = sc.nextInt();
      char[][] painting = new char[R][C];
      for (int r = 0; r < R; ++r) {
        String line = sc.next();
        for (int c = 0; c < C; ++c) {
          painting[r][c] = line.charAt(c);
        }
      }

      System.out.println(String.format("Case #%d: %s", tc, solve(painting)));
    }

    sc.close();
  }

  static String solve(char[][] painting) {
    int R = painting.length;
    int C = painting[0].length;

    int[][] friendNums = new int[R][C];
    for (int r = 0; r < R; ++r) {
      for (int c = 0; c < C; ++c) {
        if (painting[r][c] != '#') {
          for (int i = 0; i < R_OFFSETS.length; ++i) {
            int adjR = r + R_OFFSETS[i];
            int adjC = c + C_OFFSETS[i];
            if (adjR >= 0 && adjR < R && adjC >= 0 && adjC < C) {
              ++friendNums[r][c];
            }
          }
        }
      }
    }

    Queue<Point> queue = new ArrayDeque<>();
    for (int r = 0; r < R; ++r) {
      for (int c = 0; c < C; ++c) {
        if (friendNums[r][c] < 2) {
          queue.offer(new Point(r, c));
        }
      }
    }
    while (!queue.isEmpty()) {
      Point head = queue.poll();
      for (int i = 0; i < R_OFFSETS.length; ++i) {
        int adjR = head.r() + R_OFFSETS[i];
        int adjC = head.c() + C_OFFSETS[i];
        if (adjR >= 0 && adjR < R && adjC >= 0 && adjC < C) {
          --friendNums[adjR][adjC];
          if (friendNums[adjR][adjC] == 1) {
            queue.offer(new Point(adjR, adjC));
          }
        }
      }
    }

    for (int r = 0; r < R; ++r) {
      for (int c = 0; c < C; ++c) {
        if (friendNums[r][c] >= 2) {
          painting[r][c] = '^';
        } else {
          if (painting[r][c] == '^') {
            return "Impossible";
          }
        }
      }
    }

    return String.format(
        "Possible\n%s", Arrays.stream(painting).map(String::new).collect(Collectors.joining("\n")));
  }
}

record Point(int r, int c) {}
