import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Scanner;

public class Solution {
  public static void main(String[] args) {
    Scanner sc = new Scanner(System.in);

    int T = sc.nextInt();
    for (int tc = 1; tc <= T; ++tc) {
      int N = sc.nextInt();
      int[] C = new int[N];
      for (int i = 0; i < C.length; ++i) {
        C[i] = sc.nextInt();
      }
      int[] A = new int[N - 1];
      int[] B = new int[N - 1];
      for (int i = 0; i < N - 1; ++i) {
        A[i] = sc.nextInt() - 1;
        B[i] = sc.nextInt() - 1;
      }

      System.out.println(String.format("Case #%d: %d", tc, solve(C, A, B)));
    }

    sc.close();
  }

  static int solve(int[] C, int[] A, int[] B) {
    int N = C.length;

    if (N == 1) {
      return C[0];
    }

    @SuppressWarnings("unchecked")
    List<Integer>[] adjLists = new List[N];
    for (int i = 0; i < adjLists.length; ++i) {
      adjLists[i] = new ArrayList<>();
    }
    for (int i = 0; i < A.length; ++i) {
      adjLists[A[i]].add(B[i]);
      adjLists[B[i]].add(A[i]);
    }

    if (adjLists[0].size() == 1) {
      return search(C, adjLists, new boolean[N], 0);
    }

    boolean[] visited = new boolean[N];
    visited[0] = true;
    List<Integer> maxSubtreeSums = new ArrayList<>();
    for (int adj : adjLists[0]) {
      maxSubtreeSums.add(search(C, adjLists, visited, adj));
    }

    return C[0]
        + maxSubtreeSums.stream()
            .mapToInt(x -> x)
            .boxed()
            .sorted(Comparator.reverseOrder())
            .limit(2)
            .mapToInt(x -> x)
            .sum();
  }

  static int search(int[] C, List<Integer>[] adjLists, boolean[] visited, int node) {
    visited[node] = true;

    int maxSubtreeSum = 0;
    for (int adj : adjLists[node]) {
      if (!visited[adj]) {
        maxSubtreeSum = Math.max(maxSubtreeSum, search(C, adjLists, visited, adj));
      }
    }

    return C[node] + maxSubtreeSum;
  }
}
