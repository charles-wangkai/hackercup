import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Solution {
  public static void main(String[] args) {
    Scanner sc = new Scanner(System.in);

    int T = sc.nextInt();
    for (int tc = 1; tc <= T; ++tc) {
      int R = sc.nextInt();
      int C = sc.nextInt();
      int K = sc.nextInt() - 1;
      char[][] G = new char[R][C];
      for (int r = 0; r < R; ++r) {
        String line = sc.next();
        for (int c = 0; c < C; ++c) {
          G[r][c] = line.charAt(c);
        }
      }

      System.out.println(String.format("Case #%d: %d", tc, solve(G, K)));
    }

    sc.close();
  }

  static int solve(char[][] G, int K) {
    return Math.min(computeMinMoveNumForUp(G, K), computeMinMoveNumForDown(G, K));
  }

  static int computeMinMoveNumForUp(char[][] G, int K) {
    int R = G.length;
    int C = G[0].length;

    @SuppressWarnings("unchecked")
    List<Integer>[] rowLists = new List[C];
    for (int i = 0; i < rowLists.length; ++i) {
      rowLists[i] = new ArrayList<>();
    }
    for (int r = 0; r < R; ++r) {
      for (int c = 0; c < C; ++c) {
        if (G[r][c] == 'X') {
          rowLists[c].add(r);
        }
      }
    }

    int result = computeTypeOneMoveNum(rowLists, K);
    for (int i = 1; ; ++i) {
      boolean changed = false;
      for (List<Integer> rowList : rowLists) {
        for (int j = 0; j < rowList.size(); ++j) {
          if (rowList.get(j) != 0 && (j == 0 || rowList.get(j - 1) != rowList.get(j) - 1)) {
            rowList.set(j, rowList.get(j) - 1);
            changed = true;
          }
        }
      }

      if (!changed) {
        break;
      }

      result = Math.min(result, i + computeTypeOneMoveNum(rowLists, K));
    }

    return result;
  }

  static int computeMinMoveNumForDown(char[][] G, int K) {
    int R = G.length;
    int C = G[0].length;

    @SuppressWarnings("unchecked")
    List<Integer>[] rowLists = new List[C];
    for (int i = 0; i < rowLists.length; ++i) {
      rowLists[i] = new ArrayList<>();
    }
    for (int r = 0; r < R; ++r) {
      for (int c = 0; c < C; ++c) {
        if (G[r][c] == 'X') {
          rowLists[c].add(r);
        }
      }
    }

    int result = computeTypeOneMoveNum(rowLists, K);
    for (int i = 1; ; ++i) {
      boolean changed = false;
      for (List<Integer> rowList : rowLists) {
        for (int j = rowList.size() - 1; j >= 0; --j) {
          if (rowList.get(j) != R - 1
              && (j == rowList.size() - 1 || rowList.get(j + 1) != rowList.get(j) + 1)) {
            rowList.set(j, rowList.get(j) + 1);
            changed = true;
          }
        }
      }

      if (!changed) {
        break;
      }

      result = Math.min(result, i + computeTypeOneMoveNum(rowLists, K));
    }

    return result;
  }

  static int computeTypeOneMoveNum(List<Integer>[] rowLists, int K) {
    int result = 0;
    for (List<Integer> rowList : rowLists) {
      for (int row : rowList) {
        if (row == K) {
          ++result;
        }
      }
    }

    return result;
  }
}
