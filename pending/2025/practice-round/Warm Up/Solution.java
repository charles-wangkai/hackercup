import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;
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
      int[] B = new int[N];
      for (int i = 0; i < B.length; ++i) {
        B[i] = sc.nextInt();
      }

      System.out.println("Case #%d: %s".formatted(tc + 1, solve(A, B)));
    }

    sc.close();
  }

  static String solve(int[] A, int[] B) {
    if (IntStream.range(0, A.length).anyMatch(i -> A[i] > B[i])) {
      return "-1";
    }

    Map<Integer, Set<Integer>> valueToIndices = new HashMap<>();
    for (int i = 0; i < A.length; ++i) {
      addIndex(valueToIndices, A[i], i);
    }

    List<String> operations = new ArrayList<>();
    int[] sortedIndices =
        IntStream.range(0, A.length)
            .boxed()
            .sorted(Comparator.comparing(i -> A[i]))
            .mapToInt(Integer::intValue)
            .toArray();
    for (int index : sortedIndices) {
      if (A[index] != B[index]) {
        if (!valueToIndices.containsKey(B[index])) {
          return "-1";
        }

        operations.add(
            "%d %d".formatted(index + 1, valueToIndices.get(B[index]).iterator().next() + 1));

        removeIndex(valueToIndices, A[index], index);
        addIndex(valueToIndices, B[index], index);
      }
    }

    return "%d\n%s".formatted(operations.size(), String.join("\n", operations));
  }

  static void addIndex(Map<Integer, Set<Integer>> valueToIndices, int value, int index) {
    valueToIndices.putIfAbsent(value, new HashSet<>());
    valueToIndices.get(value).add(index);
  }

  static void removeIndex(Map<Integer, Set<Integer>> valueToIndices, int value, int index) {
    valueToIndices.get(value).remove(index);

    if (valueToIndices.get(value).isEmpty()) {
      valueToIndices.remove(value);
    }
  }
}