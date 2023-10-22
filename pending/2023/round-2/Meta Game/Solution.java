import java.math.BigInteger;
import java.util.Scanner;

public class Solution {
  static final int MODULUS = 1_000_000_007;
  static final int BASE1 = 31;
  static final int BASE1_INV =
      BigInteger.valueOf(BASE1).modInverse(BigInteger.valueOf(MODULUS)).intValue();
  static final int BASE2 = 37;
  static final int BASE2_INV =
      BigInteger.valueOf(BASE2).modInverse(BigInteger.valueOf(MODULUS)).intValue();

  public static void main(String[] args) {
    Scanner sc = new Scanner(System.in);

    int T = sc.nextInt();
    for (int tc = 1; tc <= T; ++tc) {
      int N = sc.nextInt();
      int[] A = new int[N];
      for (int i = 0; i < A.length; ++i) {
        A[i] = sc.nextInt();
      }
      int[] B = new int[N];
      for (int i = 0; i < B.length; ++i) {
        B[i] = sc.nextInt();
      }

      System.out.println(String.format("Case #%d: %d", tc, solve(A, B)));
    }

    sc.close();
  }

  static int solve(int[] A, int[] B) {
    int N = A.length;

    int mostPlaceValue1 = 1;
    int mostPlaceValue2 = 1;
    for (int i = 0; i < N - 1; ++i) {
      mostPlaceValue1 = multiplyMod(mostPlaceValue1, BASE1);
      mostPlaceValue2 = multiplyMod(mostPlaceValue2, BASE2);
    }

    int hashA1 = 0;
    int hashA2 = 0;
    for (int i = 0; i < N; ++i) {
      hashA1 = addMod(multiplyMod(hashA1, BASE1), A[i]);
      hashA2 = addMod(multiplyMod(hashA2, BASE2), A[i]);
    }

    int hashB1 = 0;
    int hashB2 = 0;
    for (int i = N - 1; i >= 0; --i) {
      hashB1 = addMod(multiplyMod(hashB1, BASE1), B[i]);
      hashB2 = addMod(multiplyMod(hashB2, BASE2), B[i]);
    }

    int leftBeginIndex = 0;
    int leftEndIndex = N / 2 - 1;
    int rightBeginIndex = (N + 1) / 2;
    int rightEndIndex = N - 1;

    int leftNonLessCount = 0;
    for (int i = leftBeginIndex; i <= leftEndIndex; ++i) {
      if (A[i] >= B[i]) {
        ++leftNonLessCount;
      }
    }

    int rightNonGreaterCount = 0;
    for (int i = rightBeginIndex; i <= rightEndIndex; ++i) {
      if (A[i] <= B[i]) {
        ++rightNonGreaterCount;
      }
    }

    for (int i = 0; i < 2 * N; ++i) {
      if (leftNonLessCount == 0
          && rightNonGreaterCount == 0
          && hashA1 == hashB1
          && hashA2 == hashB2) {
        return i;
      }

      hashA1 =
          addMod(
              multiplyMod(addMod(hashA1, -multiplyMod(A[leftBeginIndex], mostPlaceValue1)), BASE1),
              B[leftBeginIndex]);
      hashA2 =
          addMod(
              multiplyMod(addMod(hashA2, -multiplyMod(A[leftBeginIndex], mostPlaceValue2)), BASE2),
              B[leftBeginIndex]);

      hashB1 =
          addMod(
              multiplyMod(addMod(hashB1, -B[leftBeginIndex]), BASE1_INV),
              multiplyMod(A[leftBeginIndex], mostPlaceValue1));
      hashB2 =
          addMod(
              multiplyMod(addMod(hashB2, -B[leftBeginIndex]), BASE2_INV),
              multiplyMod(A[leftBeginIndex], mostPlaceValue2));

      if (A[leftBeginIndex] >= B[leftBeginIndex]) {
        --leftNonLessCount;
      }
      if (A[rightBeginIndex] <= B[rightBeginIndex]) {
        --rightNonGreaterCount;
      }

      int temp = A[leftBeginIndex];
      A[leftBeginIndex] = B[leftBeginIndex];
      B[leftBeginIndex] = temp;

      leftBeginIndex = (leftBeginIndex + 1) % N;
      leftEndIndex = (leftEndIndex + 1) % N;
      rightBeginIndex = (rightBeginIndex + 1) % N;
      rightEndIndex = (rightEndIndex + 1) % N;

      if (A[leftEndIndex] >= B[leftEndIndex]) {
        ++leftNonLessCount;
      }
      if (A[rightEndIndex] <= B[rightEndIndex]) {
        ++rightNonGreaterCount;
      }
    }

    return -1;
  }

  static int addMod(int x, int y) {
    return Math.floorMod(x + y, MODULUS);
  }

  static int multiplyMod(int x, int y) {
    return Math.floorMod((long) x * y, MODULUS);
  }
}
