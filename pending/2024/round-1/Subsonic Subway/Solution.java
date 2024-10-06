import java.util.Scanner;

public class Solution {
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

      System.out.println(String.format("Case #%d: %.9f", tc, solve(A, B)));
    }

    sc.close();
  }

  static double solve(int[] A, int[] B) {
    Rational min = new Rational(0, 1);
    Rational max = new Rational(Integer.MAX_VALUE, 1);
    for (int i = 0; i < A.length; ++i) {
      min = max(min, new Rational(i + 1, B[i]));
      max = min(max, new Rational(i + 1, A[i]));
    }

    return isLessEqual(min, max) ? ((double) min.numer() / min.denom()) : -1;
  }

  static boolean isLessEqual(Rational r1, Rational r2) {
    return (long) r1.numer() * r2.denom() <= (long) r2.numer() * r1.denom();
  }

  static Rational min(Rational r1, Rational r2) {
    return isLessEqual(r1, r2) ? r1 : r2;
  }

  static Rational max(Rational r1, Rational r2) {
    return isLessEqual(r1, r2) ? r2 : r1;
  }
}

record Rational(int numer, int denom) {}
