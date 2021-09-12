import java.util.Scanner;

public class Solution {
  static final int MODULUS = 1_000_000_007;

  public static void main(String[] args) {
    Scanner sc = new Scanner(System.in);

    int T = sc.nextInt();
    for (int tc = 1; tc <= T; ++tc) {
      sc.nextInt();
      String U = sc.next();

      System.out.println(String.format("Case #%d: %d", tc, solve(U)));
    }

    sc.close();
  }

  static int solve(String U) {
    int result = 0;
    int switchNum = 0;
    int firstIndex = -1;
    char firstLetter = 0;
    int lastIndex = -1;
    char lastLetter = 0;
    int prefixSum = 0;
    int suffixSum = 0;
    int length = 0;
    for (char ch : U.toCharArray()) {
      if (ch == 'F') {
        result = addMod(result, suffixSum);
        prefixSum = addMod(prefixSum, switchNum);
        ++length;
      } else if (ch == 'X') {
        if (lastLetter == 'O') {
          ++switchNum;
          suffixSum = addMod(suffixSum, lastIndex + 1);
        }
        result = addMod(result, suffixSum);
        if (firstIndex == -1) {
          firstIndex = length;
          firstLetter = ch;
        }
        lastIndex = length;
        lastLetter = ch;
        prefixSum = addMod(prefixSum, switchNum);
        ++length;
      } else if (ch == 'O') {
        if (lastLetter == 'X') {
          ++switchNum;
          suffixSum = addMod(suffixSum, lastIndex + 1);
        }
        result = addMod(result, suffixSum);
        if (firstIndex == -1) {
          firstIndex = length;
          firstLetter = ch;
        }
        lastIndex = length;
        lastLetter = ch;
        prefixSum = addMod(prefixSum, switchNum);
        ++length;
      } else {
        result = addMod(result, result);
        result =
            addMod(result, addMod(multiplyMod(length, suffixSum), multiplyMod(length, prefixSum)));
        if (firstIndex != -1 && firstLetter != lastLetter) {
          result = addMod(result, multiplyMod(lastIndex + 1, length - firstIndex));
        }

        prefixSum = addMod(prefixSum, addMod(prefixSum, multiplyMod(length, switchNum)));
        if (firstLetter != lastLetter) {
          prefixSum = addMod(prefixSum, length - firstIndex);
        }

        suffixSum = addMod(suffixSum, addMod(suffixSum, multiplyMod(length, switchNum)));
        if (firstLetter != lastLetter) {
          suffixSum = addMod(suffixSum, lastIndex + 1);
        }

        switchNum = addMod(switchNum, switchNum);
        if (firstLetter != lastLetter) {
          ++switchNum;
        }

        if (lastIndex != -1) {
          lastIndex = addMod(lastIndex, length);
        }

        length = addMod(length, length);
      }
    }

    return result;
  }

  static int addMod(int x, int y) {
    return (x + y) % MODULUS;
  }

  static int multiplyMod(int x, int y) {
    return (int) ((long) x * y % MODULUS);
  }
}
