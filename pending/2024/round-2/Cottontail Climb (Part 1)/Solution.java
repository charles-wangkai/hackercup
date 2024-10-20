import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Solution {
  static List<Long> peaks;

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
    peaks = new ArrayList<>();
    for (int begin = 1; begin <= 9; ++begin) {
      for (int end = begin; end <= 9; ++end) {
        String half =
            IntStream.rangeClosed(begin, end)
                .mapToObj(String::valueOf)
                .collect(Collectors.joining());

        peaks.add(Long.parseLong(half + new StringBuilder(half).reverse().substring(1)));
      }
    }
  }

  static int solve(long A, long B, int M) {
    return (int) peaks.stream().filter(x -> x >= A && x <= B && x % M == 0).count();
  }
}