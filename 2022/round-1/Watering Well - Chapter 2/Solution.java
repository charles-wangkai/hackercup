import java.util.Arrays;
import java.util.Comparator;
import java.util.Scanner;
import java.util.stream.Stream;

public class Solution {
  static final int MODULUS = 1_000_000_007;

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
    return addMod(computeDistanceSum(A, X), computeDistanceSum(B, Y));
  }

  static int computeDistanceSum(int[] trees, int[] wells) {
    Element[] elements =
        Stream.concat(
                Arrays.stream(trees).mapToObj(tree -> new Element(tree, false)),
                Arrays.stream(wells).mapToObj(well -> new Element(well, true)))
            .sorted(Comparator.comparing(Element::value))
            .toArray(Element[]::new);

    int result = 0;
    int value = 0;
    int leftSquareSum = 0;
    int leftSum = 0;
    int leftCount = 0;
    int rightSquareSum =
        Arrays.stream(trees)
            .map(tree -> multiplyMod(tree, tree))
            .reduce(Solution::addMod)
            .getAsInt();
    int rightSum = Arrays.stream(trees).reduce(Solution::addMod).getAsInt();
    int rightCount = trees.length;
    for (Element element : elements) {
      int delta = element.value() - value;

      leftSquareSum =
          addMod(
              leftSquareSum,
              addMod(
                  multiplyMod(multiplyMod(2, delta), leftSum),
                  multiplyMod(multiplyMod(delta, delta), leftCount)));
      leftSum = addMod(leftSum, multiplyMod(delta, leftCount));

      rightSquareSum =
          addMod(
              rightSquareSum,
              addMod(
                  -multiplyMod(multiplyMod(2, delta), rightSum),
                  multiplyMod(multiplyMod(delta, delta), rightCount)));
      rightSum = addMod(rightSum, -multiplyMod(delta, rightCount));

      if (element.wellOrTree()) {
        result = addMod(result, addMod(leftSquareSum, rightSquareSum));
      } else {
        ++leftCount;
        --rightCount;
      }

      value = element.value();
    }

    return result;
  }

  static int addMod(int x, int y) {
    return Math.floorMod(x + y, MODULUS);
  }

  static int multiplyMod(int x, int y) {
    return Math.floorMod((long) x * y, MODULUS);
  }
}

record Element(int value, boolean wellOrTree) {}
