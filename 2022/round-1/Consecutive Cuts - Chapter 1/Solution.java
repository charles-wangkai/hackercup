import java.util.Scanner;
import java.util.stream.IntStream;

public class Solution {
  public static void main(String[] args) {
    Scanner sc = new Scanner(System.in);

    int T = sc.nextInt();
    for (int tc = 1; tc <= T; ++tc) {
      int N = sc.nextInt();
      int K = sc.nextInt();
      int[] A = new int[N];
      for (int i = 0; i < A.length; ++i) {
        A[i] = sc.nextInt();
      }
      int[] B = new int[N];
      for (int i = 0; i < B.length; ++i) {
        B[i] = sc.nextInt();
      }

      System.out.println(String.format("Case #%d: %s", tc, solve(A, B, K) ? "YES" : "NO"));
    }

    sc.close();
  }

  static boolean solve(int[] A, int[] B, int K) {
    int N = A.length;

    int beginIndex = IntStream.range(0, N).filter(i -> B[i] == A[0]).findAny().getAsInt();
    if (!IntStream.range(0, N).allMatch(i -> B[(beginIndex + i) % N] == A[i])) {
      return false;
    }
    if (N == 2) {
      return (beginIndex == 0) == (K % 2 == 0);
    }

    return (beginIndex == 0 && K != 1) || (beginIndex != 0 && K != 0);
  }
}