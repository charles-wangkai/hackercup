import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class Solution {
  public static void main(String[] args) {
    Scanner sc = new Scanner(System.in);

    int T = sc.nextInt();
    for (int tc = 1; tc <= T; ++tc) {
      int N = sc.nextInt();
      int K = sc.nextInt();
      int[] S = new int[N];
      for (int i = 0; i < S.length; ++i) {
        S[i] = sc.nextInt();
      }

      System.out.println(String.format("Case #%d: %s", tc, solve(S, K) ? "YES" : "NO"));
    }

    sc.close();
  }

  static boolean solve(int[] S, int K) {
    Map<Integer, Integer> styleToCount = new HashMap<>();
    for (int style : S) {
      styleToCount.put(style, styleToCount.getOrDefault(style, 0) + 1);
    }

    if (styleToCount.values().stream().anyMatch(count -> count > 2)) {
      return false;
    }

    int twoStyleNum = (int) styleToCount.values().stream().filter(count -> count == 2).count();

    return twoStyleNum + (S.length - twoStyleNum * 2 + 1) / 2 <= K;
  }
}