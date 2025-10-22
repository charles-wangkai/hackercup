import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Solution {
  static final int MODULUS = 1_000_000_007;

  static int[][] assignNumMaps;

  public static void main(String[] args) {
    precompute();

    Scanner sc = new Scanner(System.in);

    int T = sc.nextInt();
    for (int tc = 0; tc < T; ++tc) {
      long N = sc.nextLong();
      long A = sc.nextLong();
      long B = sc.nextLong();

      System.out.println("Case #%d: %d".formatted(tc + 1, solve(N, A, B)));
    }

    sc.close();
  }

  static void precompute() {
    assignNumMaps = new int[48][48];
    for (int i = 1; i < assignNumMaps.length; ++i) {
      for (int j = 1; j <= i; ++j) {
        assignNumMaps[i][j] = CMod(i - 1, j - 1);
      }
    }
  }

  static int solve(long N, long A, long B) {
    List<Long> divisors = buildDivisors(B);

    int result = 0;
    for (long divisor : divisors) {
      if (divisor <= A) {
        result =
            addMod(result, multiplyMod(computeWayNum(N, divisor), computeWayNum(N, B / divisor)));
      }
    }

    return result;
  }

  static int mod(long x) {
    return (int) (x % MODULUS);
  }

  static int addMod(int x, int y) {
    return Math.floorMod(x + y, MODULUS);
  }

  static int multiplyMod(int x, int y) {
    return Math.floorMod((long) x * y, MODULUS);
  }

  static int divideMod(int x, int y) {
    return multiplyMod(x, BigInteger.valueOf(y).modInverse(BigInteger.valueOf(MODULUS)).intValue());
  }

  static int CMod(long n, int r) {
    int result = 1;
    for (int i = 0; i < r; ++i) {
      result = multiplyMod(result, divideMod(mod(n - i), i + 1));
    }

    return result;
  }

  static int computeAssignNum(long N, int exponent) {
    int result = 0;
    for (int length = 1; length < assignNumMaps[exponent].length && length <= N; ++length) {
      result = addMod(result, multiplyMod(CMod(N, length), assignNumMaps[exponent][length]));
    }

    return result;
  }

  static int computeWayNum(long N, long product) {
    int result = 1;
    for (int i = 2; (long) i * i <= product; ++i) {
      int exponent = 0;
      while (product % i == 0) {
        product /= i;
        ++exponent;
      }

      if (exponent != 0) {
        result = multiplyMod(result, computeAssignNum(N, exponent));
      }
    }
    if (product != 1) {
      result = multiplyMod(result, computeAssignNum(N, 1));
    }

    return result;
  }

  static List<Long> buildDivisors(long x) {
    List<Long> result = new ArrayList<>();
    for (int i = 1; (long) i * i <= x; ++i) {
      if (x % i == 0) {
        result.add((long) i);
        if (x / i != i) {
          result.add(x / i);
        }
      }
    }

    return result;
  }
}