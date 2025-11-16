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
    SegmentTree st =
        new SegmentTree(
            Arrays.stream(B)
                .boxed()
                .sorted(Comparator.reverseOrder())
                .mapToInt(Integer::intValue)
                .asLongStream()
                .toArray());

    int unit = 0;
    int prev = -1;
    for (int i = A.length - rewardNum; i < A.length; ++i) {
      if (A[i] != prev) {
        ++unit;
      }

      if (unit > B.length) {
        return false;
      }

      long tail = st.rangeMax(unit - 1, unit - 1);
      if (tail == 0) {
        return false;
      }

      int tailNum = computeTailNum(st, tail, unit - 1);

      int endTailIndex = findEndTailIndex(st, B.length, tail, unit - 1);

      st.rangeAdd(0, unit - tailNum - 1, -1);
      st.rangeAdd(endTailIndex - tailNum + 1, endTailIndex, -1);

      prev = A[i];
    }

    return true;
  }

  static int findEndTailIndex(SegmentTree st, int length, long tail, int tailIndex) {
    int result = -1;
    int lower = tailIndex;
    int upper = length - 1;
    while (lower <= upper) {
      int middle = (lower + upper) / 2;
      if (st.rangeMin(tailIndex, middle) == tail) {
        result = middle;
        lower = middle + 1;
      } else {
        upper = middle - 1;
      }
    }

    return result;
  }

  static int computeTailNum(SegmentTree st, long tail, int endIndex) {
    int beginIndex = -1;
    int lower = 0;
    int upper = endIndex;
    while (lower <= upper) {
      int middle = (lower + upper) / 2;
      if (st.rangeMax(middle, endIndex) == tail) {
        beginIndex = middle;
        upper = middle - 1;
      } else {
        lower = middle + 1;
      }
    }

    return endIndex - beginIndex + 1;
  }
}

class SegmentTree {
  private static class Node {
    long mn, mx;
    long lazy;
  }

  private final int n;
  private final Node[] seg;

  public SegmentTree(long[] arr) {
    this.n = arr.length;
    seg = new Node[4 * n];
    for (int i = 0; i < seg.length; i++) {
      seg[i] = new Node();
    }
    build(arr, 1, 0, n - 1);
  }

  private void build(long[] arr, int idx, int l, int r) {
    if (l == r) {
      seg[idx].mn = seg[idx].mx = arr[l];
      return;
    }
    int mid = (l + r) >>> 1;
    build(arr, idx * 2, l, mid);
    build(arr, idx * 2 + 1, mid + 1, r);
    pull(idx);
  }

  private void pull(int idx) {
    seg[idx].mn = Math.min(seg[idx * 2].mn, seg[idx * 2 + 1].mn);
    seg[idx].mx = Math.max(seg[idx * 2].mx, seg[idx * 2 + 1].mx);
  }

  private void apply(int idx, long val) {
    seg[idx].mn += val;
    seg[idx].mx += val;
    seg[idx].lazy += val;
  }

  private void push(int idx) {
    if (seg[idx].lazy != 0) {
      apply(idx * 2, seg[idx].lazy);
      apply(idx * 2 + 1, seg[idx].lazy);
      seg[idx].lazy = 0;
    }
  }

  public void rangeAdd(int ql, int qr, long val) {
    update(1, 0, n - 1, ql, qr, val);
  }

  private void update(int idx, int l, int r, int ql, int qr, long val) {
    if (qr < l || r < ql) return;
    if (ql <= l && r <= qr) {
      apply(idx, val);
      return;
    }
    push(idx);
    int mid = (l + r) >>> 1;
    update(idx * 2, l, mid, ql, qr, val);
    update(idx * 2 + 1, mid + 1, r, ql, qr, val);
    pull(idx);
  }

  public long rangeMin(int ql, int qr) {
    return queryMin(1, 0, n - 1, ql, qr);
  }

  private long queryMin(int idx, int l, int r, int ql, int qr) {
    if (qr < l || r < ql) return Long.MAX_VALUE;
    if (ql <= l && r <= qr) return seg[idx].mn;
    push(idx);
    int mid = (l + r) >>> 1;
    return Math.min(queryMin(idx * 2, l, mid, ql, qr), queryMin(idx * 2 + 1, mid + 1, r, ql, qr));
  }

  public long rangeMax(int ql, int qr) {
    return queryMax(1, 0, n - 1, ql, qr);
  }

  private long queryMax(int idx, int l, int r, int ql, int qr) {
    if (qr < l || r < ql) return Long.MIN_VALUE;
    if (ql <= l && r <= qr) return seg[idx].mx;
    push(idx);
    int mid = (l + r) >>> 1;
    return Math.max(queryMax(idx * 2, l, mid, ql, qr), queryMax(idx * 2 + 1, mid + 1, r, ql, qr));
  }
}
