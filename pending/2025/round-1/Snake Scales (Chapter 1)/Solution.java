import java.util.Scanner;
import java.util.stream.IntStream;

public class Solution {
  public static void main(String[] args) {
    Scanner sc = new Scanner(System.in);

    int T = sc.nextInt();
    for (int tc = 0; tc < T; ++tc) {
      int N = sc.nextInt();
      int[] A = new int[N];
      for (int i = 0; i < A.length; ++i) {
        A[i] = sc.nextInt();
      }

      System.out.println("Case #%d: %d".formatted(tc + 1, solve(A)));
    }

    sc.close();
  }

  static int solve(int[] A) {
    return IntStream.range(0, A.length - 1).map(i -> Math.abs(A[i] - A[i + 1])).max().orElse(0);
  }
}