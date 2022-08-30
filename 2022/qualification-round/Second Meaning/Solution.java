import java.util.Scanner;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Solution {
  public static void main(String[] args) {
    Scanner sc = new Scanner(System.in);

    int T = sc.nextInt();
    for (int tc = 1; tc <= T; ++tc) {
      int N = sc.nextInt();
      String C1 = sc.next();

      System.out.println(String.format("Case #%d:\n%s", tc, solve(N, C1)));
    }

    sc.close();
  }

  static String solve(int N, String C1) {
    char suffix = (char) ('.' + '-' - C1.charAt(0));

    return IntStream.range(1, N)
        .mapToObj(i -> C1 + String.valueOf(suffix).repeat(i))
        .collect(Collectors.joining("\n"));
  }
}