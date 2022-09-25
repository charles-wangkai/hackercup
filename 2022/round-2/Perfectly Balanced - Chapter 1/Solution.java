import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.stream.IntStream;

public class Solution {
  public static void main(String[] args) {
    Scanner sc = new Scanner(System.in);

    int T = sc.nextInt();
    for (int tc = 1; tc <= T; ++tc) {
      String S = sc.next();
      int Q = sc.nextInt();
      int[] L = new int[Q];
      int[] R = new int[Q];
      for (int i = 0; i < Q; ++i) {
        L[i] = sc.nextInt() - 1;
        R[i] = sc.nextInt() - 1;
      }

      System.out.println(String.format("Case #%d: %d", tc, solve(S, L, R)));
    }

    sc.close();
  }

  static int solve(String S, int[] L, int[] R) {
    Map<Character, List<Integer>> letterToIndices = new HashMap<>();
    for (char letter = 'a'; letter <= 'z'; ++letter) {
      letterToIndices.put(letter, new ArrayList<>());
    }
    for (int i = 0; i < S.length(); ++i) {
      letterToIndices.get(S.charAt(i)).add(i);
    }

    return (int)
        IntStream.range(0, L.length)
            .filter(
                i -> {
                  int length = R[i] - L[i] + 1;
                  if (length % 2 == 0) {
                    return false;
                  }

                  int deletedIndex;
                  for (char letter = 'a'; ; ++letter) {
                    int beginIndex = findBeginIndex(letterToIndices.get(letter), L[i]);
                    int endIndex = findEndIndex(letterToIndices.get(letter), R[i]);
                    if ((endIndex - beginIndex + 1) % 2 != 0) {
                      deletedIndex = letterToIndices.get(letter).get((beginIndex + endIndex) / 2);

                      break;
                    }
                  }

                  int maxLeftIndex = L[i] + length / 2 - 1;
                  if (deletedIndex <= maxLeftIndex) {
                    ++maxLeftIndex;
                  }

                  int oddCount = 0;
                  for (char letter = 'a'; letter <= 'z'; ++letter) {
                    int beginIndex = findBeginIndex(letterToIndices.get(letter), L[i]);
                    int endIndex = findEndIndex(letterToIndices.get(letter), R[i]);
                    int size = endIndex - beginIndex + 1;
                    if (size % 2 != 0) {
                      ++oddCount;
                    }

                    if (size / 2 > 0
                        && !(letterToIndices.get(letter).get(beginIndex + size / 2 - 1)
                                <= maxLeftIndex
                            && letterToIndices.get(letter).get(endIndex - size / 2 + 1)
                                > maxLeftIndex)) {
                      return false;
                    }
                  }

                  return oddCount == 1;
                })
            .count();
  }

  static int findBeginIndex(List<Integer> indices, int target) {
    int result = indices.size();
    int lower = 0;
    int upper = indices.size() - 1;
    while (lower <= upper) {
      int middle = (lower + upper) / 2;
      if (indices.get(middle) >= target) {
        result = middle;
        upper = middle - 1;
      } else {
        lower = middle + 1;
      }
    }

    return result;
  }

  static int findEndIndex(List<Integer> indices, int target) {
    int result = -1;
    int lower = 0;
    int upper = indices.size() - 1;
    while (lower <= upper) {
      int middle = (lower + upper) / 2;
      if (indices.get(middle) <= target) {
        result = middle;
        lower = middle + 1;
      } else {
        upper = middle - 1;
      }
    }

    return result;
  }
}