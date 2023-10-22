import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;

public class Solution {
  static int[] R_OFFSETS = {-1, 0, 1, 0};
  static int[] C_OFFSETS = {0, 1, 0, -1};

  public static void main(String[] args) {
    Scanner sc = new Scanner(System.in);

    int T = sc.nextInt();
    for (int tc = 1; tc <= T; ++tc) {
      int R = sc.nextInt();
      int C = sc.nextInt();
      char[][] board = new char[R][C];
      for (int r = 0; r < R; ++r) {
        String line = sc.next();
        for (int c = 0; c < C; ++c) {
          board[r][c] = line.charAt(c);
        }
      }

      System.out.println(String.format("Case #%d: %s", tc, solve(board) ? "YES" : "NO"));
    }

    sc.close();
  }

  static boolean solve(char[][] board) {
    int R = board.length;
    int C = board[0].length;

    boolean[][] visited = new boolean[R][C];
    for (int r = 0; r < R; ++r) {
      for (int c = 0; c < C; ++c) {
        if (board[r][c] == 'W' && !visited[r][c]) {
          Set<Point> adjs = new HashSet<>();
          search(adjs, board, visited, r, c);
          if (adjs.size() == 1) {
            return true;
          }
        }
      }
    }

    return false;
  }

  static void search(Set<Point> adjs, char[][] board, boolean[][] visited, int r, int c) {
    int R = board.length;
    int C = board[0].length;

    visited[r][c] = true;

    for (int i = 0; i < R_OFFSETS.length; ++i) {
      int adjR = r + R_OFFSETS[i];
      int adjC = c + C_OFFSETS[i];
      if (adjR >= 0 && adjR < R && adjC >= 0 && adjC < C) {
        if (board[adjR][adjC] == 'W' && !visited[adjR][adjC]) {
          search(adjs, board, visited, adjR, adjC);
        } else if (board[adjR][adjC] == '.') {
          adjs.add(new Point(adjR, adjC));
        }
      }
    }
  }
}

record Point(int r, int c) {}
