import java.util.ArrayDeque;
import java.util.Arrays;
import java.util.Queue;
import java.util.Scanner;

public class Solution {
  static final int[] R_OFFSETS = {-1, 0, 1, 0};
  static final int[] C_OFFSETS = {0, 1, 0, -1};

  public static void main(String[] args) {
    Scanner sc = new Scanner(System.in);

    int T = sc.nextInt();
    for (int tc = 0; tc < T; ++tc) {
      int R = sc.nextInt();
      int C = sc.nextInt();
      int S = sc.nextInt();
      char[][] grid = new char[R][C];
      for (int r = 0; r < R; ++r) {
        String line = sc.next();
        for (int c = 0; c < C; ++c) {
          grid[r][c] = line.charAt(c);
        }
      }

      System.out.println("Case #%d: %d".formatted(tc + 1, solve(grid, S)));
    }

    sc.close();
  }

  static int solve(char[][] grid, int S) {
    int R = grid.length;
    int C = grid[0].length;

    int[][] distances = new int[R][C];
    for (int r = 0; r < distances.length; ++r) {
      Arrays.fill(distances[r], -1);
    }

    Queue<Point> queue = new ArrayDeque<>();
    for (int r = 0; r < R; ++r) {
      for (int c = 0; c < C; ++c) {
        if (grid[r][c] == '#') {
          distances[r][c] = 0;
          queue.offer(new Point(r, c));
        }
      }
    }
    for (int r = 0; r < R; ++r) {
      for (int c = 0; c < C; ++c) {
        if ((r == 0 || r == R - 1 || c == 0 || c == C - 1) && grid[r][c] == '.') {
          distances[r][c] = 1;
          queue.offer(new Point(r, c));
        }
      }
    }

    while (!queue.isEmpty()) {
      Point head = queue.poll();

      for (int i = 0; i < R_OFFSETS.length; ++i) {
        int adjR = head.r() + R_OFFSETS[i];
        int adjC = head.c() + C_OFFSETS[i];
        if (adjR >= 0 && adjR < R && adjC >= 0 && adjC < C && distances[adjR][adjC] == -1) {
          distances[adjR][adjC] = distances[head.r()][head.c()] + 1;
          queue.offer(new Point(adjR, adjC));
        }
      }
    }

    int result = 0;
    for (int r = 0; r < R; ++r) {
      for (int c = 0; c < C; ++c) {
        result = Math.max(result, fill(S, distances, r, c));
      }
    }

    return result;
  }

  static int fill(int S, int[][] distances, int r, int c) {
    int R = distances.length;
    int C = distances[0].length;

    if (distances[r][c] <= S) {
      return 0;
    }

    distances[r][c] = -2;

    int result = 1;
    for (int i = 0; i < R_OFFSETS.length; ++i) {
      int adjR = r + R_OFFSETS[i];
      int adjC = c + C_OFFSETS[i];
      if (adjR >= 0 && adjR < R && adjC >= 0 && adjC < C) {
        result += fill(S, distances, adjR, adjC);
      }
    }

    return result;
  }
}

record Point(int r, int c) {}
