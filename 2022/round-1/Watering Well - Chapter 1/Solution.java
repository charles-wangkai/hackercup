import java.util.Arrays;
import java.util.Scanner;
import java.util.stream.IntStream;

public class Solution {
  static final int MODULUS = 1_000_000_007;
  static final int LIMIT = 3000;

  public static void main(String[] args) {
    Scanner sc = new Scanner(System.in);

    int T = sc.nextInt();
    for (int tc = 1; tc <= T; ++tc) {
      int N = sc.nextInt();
      int[] A = new int[N];
      int[] B = new int[N];
      for (int i = 0; i < N; ++i) {
        A[i] = sc.nextInt();
        B[i] = sc.nextInt();
      }
      int Q = sc.nextInt();
      int[] X = new int[Q];
      int[] Y = new int[Q];
      for (int i = 0; i < Q; ++i) {
        X[i] = sc.nextInt();
        Y[i] = sc.nextInt();
      }

      System.out.println(String.format("Case #%d: %d", tc, solve(A, B, X, Y)));
    }

    sc.close();
  }

  static int solve(int[] A, int[] B, int[] X, int[] Y) {
    int[] xDistanceSums = computeDistanceSums(A);
    int[] yDistanceSums = computeDistanceSums(B);

    return IntStream.concat(
            Arrays.stream(X).map(x -> xDistanceSums[x]),
            Arrays.stream(Y).map(y -> yDistanceSums[y]))
        .reduce(Solution::addMod)
        .getAsInt();
  }

  static int[] computeDistanceSums(int[] values) {
    return IntStream.rangeClosed(0, LIMIT)
        .map(
            i ->
                Arrays.stream(values)
                    .map(value -> (value - i) * (value - i))
                    .reduce(Solution::addMod)
                    .getAsInt())
        .toArray();
  }

  static int addMod(int x, int y) {
    return Math.floorMod(x + y, MODULUS);
  }
}