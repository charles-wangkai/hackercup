import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.stream.Collectors;

public class Solution {
  public static void main(String[] args) {
    Scanner sc = new Scanner(System.in);

    int T = sc.nextInt();
    for (int tc = 1; tc <= T; ++tc) {
      int N = sc.nextInt();
      int M = sc.nextInt();
      int[] S = new int[M];
      for (int i = 0; i < S.length; ++i) {
        S[i] = sc.nextInt();
      }
      int[][] P = new int[N][M];
      for (int i = 0; i < N; ++i) {
        for (int j = 0; j < M; ++j) {
          P[i][j] = sc.nextInt();
        }
      }

      System.out.println(String.format("Case #%d: %d", tc, solve(S, P)));
    }

    sc.close();
  }

  static int solve(int[] S, int[][] P) {
    int result = 0;
    List<Integer> extras = Arrays.stream(S).boxed().collect(Collectors.toList());
    List<Integer> singles = List.of();
    for (int[] styles : P) {
      Map<Integer, Integer> styleToCount = new HashMap<>();
      for (int style : styles) {
        updateMap(styleToCount, style, 1);
      }

      List<Integer> nextExtras = new ArrayList<>();
      List<Integer> nextSingles = new ArrayList<>();
      for (int single : singles) {
        if (styleToCount.containsKey(single)) {
          updateMap(styleToCount, single, -1);
          nextSingles.add(single);
        }
      }
      int extraMatched = 0;
      for (int extra : extras) {
        if (styleToCount.containsKey(extra)) {
          updateMap(styleToCount, extra, -1);
          nextExtras.add(extra);
          ++extraMatched;
        }
      }
      result -= extras.size() - extraMatched;
      for (int style : styleToCount.keySet()) {
        for (int i = 0; i < styleToCount.get(style); ++i) {
          nextSingles.add(style);
          ++result;
        }
      }

      extras = nextExtras;
      singles = nextSingles;
    }

    return result;
  }

  static void updateMap(Map<Integer, Integer> styleToCount, int style, int delta) {
    styleToCount.put(style, styleToCount.getOrDefault(style, 0) + delta);
    styleToCount.remove(style, 0);
  }
}
