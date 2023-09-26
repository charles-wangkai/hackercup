import java.util.Arrays;
import java.util.Scanner;

public class Solution {
  public static void main(String[] args) {
    Scanner sc = new Scanner(System.in);

    int T = sc.nextInt();
    for (int tc = 1; tc <= T; ++tc) {
      int N = sc.nextInt();
      int[] A = new int[2 * N - 1];
      for (int i = 0; i < A.length; ++i) {
        A[i] = sc.nextInt();
      }

      System.out.println(String.format("Case #%d: %d", tc, solve(A)));
    }

    sc.close();
  }

  static int solve(int[] A) {
    if (A.length == 1) {
      return 1;
    }

    Arrays.sort(A);

    int result =
        Math.min(
            Math.min(
                computeBoughtWeight(A, A[0] + A[A.length - 1]),
                computeBoughtWeight(A, A[0] + A[A.length - 2])),
            computeBoughtWeight(A, A[1] + A[A.length - 1]));

    return (result == Integer.MAX_VALUE) ? -1 : result;
  }

  static int computeBoughtWeight(int[] A, int sum) {
    int result = Integer.MIN_VALUE;
    int leftIndex = 0;
    int rightIndex = A.length - 1;
    while (leftIndex <= rightIndex) {
      if (leftIndex == rightIndex) {
        result = sum - A[leftIndex];

        ++leftIndex;
      } else if (A[leftIndex] + A[rightIndex] == sum) {
        ++leftIndex;
        --rightIndex;
      } else {
        if (result != Integer.MIN_VALUE) {
          return Integer.MAX_VALUE;
        }

        if (A[leftIndex] + A[rightIndex] < sum) {
          result = sum - A[leftIndex];

          ++leftIndex;
        } else {
          result = sum - A[rightIndex];

          --rightIndex;
        }
      }
    }

    return (result > 0) ? result : Integer.MAX_VALUE;
  }
}
