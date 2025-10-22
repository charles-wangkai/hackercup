import java.util.ArrayDeque;
import java.util.Arrays;
import java.util.Queue;
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
    int result = -1;
    int lower = 1;
    int upper = Arrays.stream(A).max().getAsInt();
    while (lower <= upper) {
      int middle = (lower + upper) / 2;
      if (check(A, middle)) {
        result = middle;
        upper = middle - 1;
      } else {
        lower = middle + 1;
      }
    }

    return result;
  }

  static boolean check(int[] A, int ladder) {
    boolean[] reaches = new boolean[A.length];
    Queue<Integer> queue = new ArrayDeque<>();
    for (int i = 0; i < A.length; ++i) {
      if (A[i] <= ladder) {
        reaches[i] = true;
        queue.offer(i);
      }
    }

    while (!queue.isEmpty()) {
      int head = queue.poll();
      for (int offset : new int[] {-1, 1}) {
        int next = head + offset;
        if (next >= 0
            && next < A.length
            && Math.abs(A[next] - A[head]) <= ladder
            && !reaches[next]) {
          reaches[next] = true;
          queue.offer(next);
        }
      }
    }

    return IntStream.range(0, reaches.length).allMatch(i -> reaches[i]);
  }
}