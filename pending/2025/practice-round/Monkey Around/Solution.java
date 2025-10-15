import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

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

      System.out.println("Case #%d: %s".formatted(tc + 1, solve(A)));
    }

    sc.close();
  }

  static String solve(int[] A) {
    List<Permutation> permutations = new ArrayList<>();
    int beginIndex = 0;
    while (beginIndex != A.length) {
      int oneIndex = beginIndex;
      int endIndex;
      int rotation;
      if (A[beginIndex] == 1) {
        endIndex = beginIndex;
        while (endIndex != A.length - 1 && A[endIndex + 1] == A[endIndex] + 1) {
          ++endIndex;
        }

        rotation = 0;
      } else {
        while (A[oneIndex] != 1) {
          ++oneIndex;
        }

        endIndex = oneIndex;
        while (A[endIndex] != A[beginIndex] - 1) {
          ++endIndex;
        }

        rotation = endIndex - oneIndex + 1;
      }
      permutations.add(new Permutation(endIndex - beginIndex + 1, rotation));

      beginIndex = endIndex + 1;
    }

    List<String> operations = new ArrayList<>();
    int globalRotation = 0;
    for (int i = permutations.size() - 1; i >= 0; --i) {
      while (globalRotation % permutations.get(i).length() != permutations.get(i).rotation()) {
        operations.add("2");
        ++globalRotation;
      }

      operations.add("1 %d".formatted(permutations.get(i).length()));
    }
    Collections.reverse(operations);

    return "%d\n%s".formatted(operations.size(), String.join("\n", operations));
  }
}

record Permutation(int length, int rotation) {}
