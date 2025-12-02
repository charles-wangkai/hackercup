import java.util.Arrays;
import java.util.Comparator;
import java.util.Scanner;
import java.util.stream.IntStream;

public class Solution {
  public static void main(String[] args) {
    Scanner sc = new Scanner(System.in);

    int T = sc.nextInt();
    for (int tc = 0; tc < T; ++tc) {
      int N = sc.nextInt();
      int M = sc.nextInt();
      int[] A = new int[N];
      for (int i = 0; i < A.length; ++i) {
        A[i] = sc.nextInt();
      }
      int[] B = new int[M];
      for (int i = 0; i < B.length; ++i) {
        B[i] = sc.nextInt();
      }

      System.out.println("Case #%d: %d".formatted(tc + 1, solve(A, B)));
    }

    sc.close();
  }

  static int solve(int[] A, int[] B) {
    Arrays.sort(A);

    B =
        Arrays.stream(B)
            .boxed()
            .sorted(Comparator.reverseOrder())
            .mapToInt(Integer::intValue)
            .toArray();

    int[] lengths =
        IntStream.range(0, A.length)
            .filter(i -> i == 0 || A[i] != A[i - 1])
            .map(i -> A.length - i)
            .sorted()
            .toArray();

    int result = 0;
    int lower = 0;
    int upper = lengths.length - 1;
    while (lower <= upper) {
      int middle = (lower + upper) / 2;
      if (check(A, B, lengths[middle])) {
        result = lengths[middle];
        lower = middle + 1;
      } else {
        upper = middle - 1;
      }
    }

    return result;
  }

  static boolean check(int[] A, int[] B, int rewardNum) {
    int[] binaryIndexedTree = new int[Integer.highestOneBit(B.length) * 2 + 1];
    for (int i = 0; i < B.length; ++i) {
      add(binaryIndexedTree, i + 1, B[i] - ((i == 0) ? 0 : B[i - 1]));
    }

    int unit = 0;
    int prev = -1;
    for (int i = A.length - rewardNum; i < A.length; ++i) {
      if (A[i] != prev) {
        ++unit;
      }

      if (unit > B.length) {
        return false;
      }

      int tailValue = computeSum(binaryIndexedTree, unit);
      if (tailValue == 0) {
        return false;
      }

      int tailNum = computeTailNum(binaryIndexedTree, tailValue, unit - 1);

      int endTailIndex = findEndTailIndex(binaryIndexedTree, B.length, tailValue, unit - 1);

      add(binaryIndexedTree, 1, -1);
      add(binaryIndexedTree, unit - tailNum + 1, 1);

      add(binaryIndexedTree, endTailIndex - tailNum + 2, -1);
      add(binaryIndexedTree, endTailIndex + 2, 1);

      prev = A[i];
    }

    return true;
  }

  static int findEndTailIndex(int[] binaryIndexedTree, int length, int tailValue, int tailIndex) {
    int result = -1;
    int lower = tailIndex;
    int upper = length - 1;
    while (lower <= upper) {
      int middle = (lower + upper) / 2;
      if (computeSum(binaryIndexedTree, middle + 1) == tailValue) {
        result = middle;
        lower = middle + 1;
      } else {
        upper = middle - 1;
      }
    }

    return result;
  }

  static int computeTailNum(int[] binaryIndexedTree, int tailValue, int endIndex) {
    int beginIndex = -1;
    int lower = 0;
    int upper = endIndex;
    while (lower <= upper) {
      int middle = (lower + upper) / 2;
      if (computeSum(binaryIndexedTree, middle + 1) == tailValue) {
        beginIndex = middle;
        upper = middle - 1;
      } else {
        lower = middle + 1;
      }
    }

    return endIndex - beginIndex + 1;
  }

  static void add(int[] binaryIndexedTree, int i, int x) {
    while (i < binaryIndexedTree.length) {
      binaryIndexedTree[i] += x;
      i += i & -i;
    }
  }

  static int computeSum(int[] binaryIndexedTree, int i) {
    int result = 0;
    while (i != 0) {
      result += binaryIndexedTree[i];
      i -= i & -i;
    }

    return result;
  }
}
