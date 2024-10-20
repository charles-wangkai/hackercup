import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Solution {
  static List<Long> mountains;

  public static void main(String[] args) {
    precompute();

    Scanner sc = new Scanner(System.in);

    int T = sc.nextInt();
    for (int tc = 1; tc <= T; ++tc) {
      long A = sc.nextLong();
      long B = sc.nextLong();
      int M = sc.nextInt();

      System.out.println("Case #%d: %d".formatted(tc, solve(A, B, M)));
    }

    sc.close();
  }

  static void precompute() {
    mountains = new ArrayList<>();
    for (int halfLength = 1; halfLength <= 9; ++halfLength) {
      for (int middle = 1; middle <= 9; ++middle) {
        List<String> halfs = new ArrayList<>();
        search(halfs, halfLength, middle, 0, new StringBuilder());

        for (String half1 : halfs) {
          for (String half2 : halfs) {
            mountains.add(Long.parseLong(new StringBuilder(half1).reverse() + half2.substring(1)));
          }
        }
      }
    }
  }

  static void search(
      List<String> halfs, int halfLength, int digit, int index, StringBuilder current) {
    if (index == halfLength) {
      halfs.add(current.toString());
    } else {
      for (int i = 0; i < digit; ++i) {
        if ((i == 0 && index != 1) || (i != 0 && index != 0)) {
          current.append(digit - i);
          search(halfs, halfLength, digit - i, index + 1, current);
          current.deleteCharAt(current.length() - 1);
        }
      }
    }
  }

  static int solve(long A, long B, int M) {
    return (int) mountains.stream().filter(x -> x >= A && x <= B && x % M == 0).count();
  }
}