import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

public class Solution {
  static List<Integer> solution;

  public static void main(String[] args) {
    Scanner sc = new Scanner(System.in);

    int T = sc.nextInt();
    for (int tc = 1; tc <= T; ++tc) {
      int P = sc.nextInt();

      System.out.println(String.format("Case #%d: %s", tc, solve(P)));
    }

    sc.close();
  }

  static String solve(int P) {
    solution = null;
    search(41, P, new ArrayList<>());

    return (solution == null)
        ? "-1"
        : String.format(
            "%d %s",
            solution.size(),
            solution.stream().map(String::valueOf).collect(Collectors.joining(" ")));
  }

  static void search(int sum, int product, List<Integer> array) {
    if (product == 1) {
      List<Integer> sol = new ArrayList<>(array);
      for (int i = 0; i < sum; ++i) {
        sol.add(1);
      }

      if (solution == null || solution.size() > sol.size()) {
        solution = List.copyOf(sol);
      }

      return;
    }

    for (int i = 2; i <= sum; ++i) {
      if (product % i == 0) {
        array.add(i);

        search(sum - i, product / i, array);

        array.remove(array.size() - 1);
      }
    }
  }
}
