import java.util.Arrays;
import java.util.Scanner;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Solution {
  public static void main(String[] args) {
    Scanner sc = new Scanner(System.in);

    int T = sc.nextInt();
    for (int tc = 1; tc <= T; ++tc) {
      int R = sc.nextInt();
      int C = sc.nextInt();
      char[][] painting = new char[R][C];
      for (int r = 0; r < R; ++r) {
        String line = sc.next();
        for (int c = 0; c < C; ++c) {
          painting[r][c] = line.charAt(c);
        }
      }

      System.out.println(String.format("Case #%d: %s", tc, solve(painting)));
    }

    sc.close();
  }

  static String solve(char[][] painting) {
    int R = painting.length;
    int C = painting[0].length;

    if (R == 1 || C == 1) {
      if (IntStream.range(0, R)
          .anyMatch(r -> IntStream.range(0, C).anyMatch(c -> painting[r][c] == '^'))) {
        return "Impossible";
      }
    } else {
      for (int r = 0; r < R; ++r) {
        Arrays.fill(painting[r], '^');
      }
    }

    return String.format(
        "Possible\n%s", Arrays.stream(painting).map(String::new).collect(Collectors.joining("\n")));
  }
}