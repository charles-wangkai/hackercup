import java.util.Scanner;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Solution {
  public static void main(String[] args) {
    Scanner sc = new Scanner(System.in);

    int T = sc.nextInt();
    for (int tc = 0; tc < T; ++tc) {
      int N = sc.nextInt();
      int A = sc.nextInt();
      int B = sc.nextInt();

      System.out.println("Case #%d: %s".formatted(tc + 1, solve(N, A, B)));
    }

    sc.close();
  }

  static String solve(int N, int A, int B) {
    return IntStream.range(0, 2 * N)
        .map(i -> (i == 2 * N - 1) ? B : 1)
        .mapToObj(String::valueOf)
        .collect(Collectors.joining(" "));
  }
}