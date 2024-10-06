import java.util.Arrays;
import java.util.Scanner;

public class Solution {
  static final int LIMIT = 10_000_000;

  static int[] subtractorizationNums;

  public static void main(String[] args) {
    precompute();

    Scanner sc = new Scanner(System.in);

    int T = sc.nextInt();
    for (int tc = 1; tc <= T; ++tc) {
      int N = sc.nextInt();

      System.out.println("Case #%d: %d".formatted(tc, solve(N)));
    }

    sc.close();
  }

  static void precompute() {
    boolean[] primes = new boolean[LIMIT + 1];
    Arrays.fill(primes, true);
    primes[0] = false;
    primes[1] = false;
    for (int i = 2; i < primes.length; ++i) {
      if (primes[i]) {
        for (int j = i * 2; j < primes.length; j += i) {
          primes[j] = false;
        }
      }
    }

    subtractorizationNums = new int[LIMIT + 1];
    subtractorizationNums[5] = 2;
    for (int i = 6; i < subtractorizationNums.length; ++i) {
      subtractorizationNums[i] =
          subtractorizationNums[i - 1] + ((primes[i] && primes[i - 2]) ? 1 : 0);
    }
  }

  static int solve(int N) {
    return subtractorizationNums[N];
  }
}