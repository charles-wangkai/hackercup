import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
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

      System.out.println(String.format("Case #%d: %d", tc, solve(board)));
    }

    sc.close();
  }

  static int solve(char[][] board) {
    int R = board.length;
    int C = board[0].length;

    int[][] groups = new int[R][C];
    int group = 0;
    Map<Integer, Integer> groupToSize = new HashMap<>();
    for (int r = 0; r < R; ++r) {
      for (int c = 0; c < C; ++c) {
        if (board[r][c] == 'W' && groups[r][c] == 0) {
          ++group;
          Set<Point> adjs = new HashSet<>();
          int size = search(adjs, board, groups, group, r, c);
          if (adjs.size() == 1) {
            groupToSize.put(group, size);
          }
        }
      }
    }

    int result = 0;
    for (int r = 0; r < R; ++r) {
      for (int c = 0; c < C; ++c) {
        if (board[r][c] == '.') {
          Set<Integer> seen = new HashSet<>();
          int captured = 0;
          for (int i = 0; i < R_OFFSETS.length; ++i) {
            int adjR = r + R_OFFSETS[i];
            int adjC = c + C_OFFSETS[i];
            if (adjR >= 0
                && adjR < R
                && adjC >= 0
                && adjC < C
                && board[adjR][adjC] == 'W'
                && groupToSize.containsKey(groups[adjR][adjC])
                && !seen.contains(groups[adjR][adjC])) {
              captured += groupToSize.get(groups[adjR][adjC]);
              seen.add(groups[adjR][adjC]);
            }

            result = Math.max(result, captured);
          }
        }
      }
    }

    return result;
  }

  static int search(Set<Point> adjs, char[][] board, int[][] groups, int group, int r, int c) {
    int R = board.length;
    int C = board[0].length;

    groups[r][c] = group;

    int result = 1;
    for (int i = 0; i < R_OFFSETS.length; ++i) {
      int adjR = r + R_OFFSETS[i];
      int adjC = c + C_OFFSETS[i];
      if (adjR >= 0 && adjR < R && adjC >= 0 && adjC < C) {
        if (board[adjR][adjC] == 'W' && groups[adjR][adjC] == 0) {
          result += search(adjs, board, groups, group, adjR, adjC);
        } else if (board[adjR][adjC] == '.') {
          adjs.add(new Point(adjR, adjC));
        }
      }
    }

    return result;
  }
}

record Point(int r, int c) {}
