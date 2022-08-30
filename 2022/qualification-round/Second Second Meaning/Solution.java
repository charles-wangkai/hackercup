import java.util.Scanner;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Solution {
  public static void main(String[] args) {
    Scanner sc = new Scanner(System.in);

    int T = sc.nextInt();
    for (int tc = 1; tc <= T; ++tc) {
      int N = sc.nextInt();
      String C1 = sc.next();

      System.out.println(String.format("Case #%d:\n%s", tc, solve(N, C1)));
    }

    sc.close();
  }

  static String solve(int N, String C1) {
    String[] codewords = new String[N - 1];
    int value = 0;
    for (int i = 0; i < codewords.length; ++i) {
      do {
        int value_ = value;
        codewords[i] =
            IntStream.range(0, 10)
                .mapToObj(j -> ((value_ & (1 << j)) == 0) ? "." : "-")
                .collect(Collectors.joining());
        ++value;
      } while (C1.startsWith(codewords[i]) || codewords[i].startsWith(C1));
    }

    return String.join("\n", codewords);
  }
}