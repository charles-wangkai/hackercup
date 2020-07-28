import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;

public class Solution {
    static final int MODULUS = 1_000_000_007;

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        int T = sc.nextInt();
        for (int tc = 1; tc <= T; ++tc) {
            int M = sc.nextInt();
            int[] a = new int[M];
            for (int i = 0; i < a.length; ++i) {
                a[i] = sc.nextInt();
            }

            System.out.println(String.format("Case #%d: %d", tc, solve(a)));
        }

        sc.close();
    }

    static int solve(int[] a) {
        int M = a.length;

        int[][] diversities = new int[M + 1][M + 1];
        diversities[0][0] = 1;
        int result = computediversitySum(a, diversities);
        for (int i = 0; i < a.length; ++i) {
            int[][] nextDiversities = new int[M + 1][M + 1];

            for (int leftLength = 0; leftLength <= M; ++leftLength) {
                for (int rightLength = 0; rightLength <= M; ++rightLength) {
                    int beginIndex = leftLength;
                    int endIndex = M - rightLength - 1;

                    Set<Integer> leftSeen = new HashSet<>();
                    for (int j = beginIndex; j <= endIndex; ++j) {
                        if (!leftSeen.contains(a[j]) && (leftLength == 0 || a[j] >= a[leftLength - 1])
                                && (rightLength == 0 || a[j] > a[M - rightLength])) {
                            int nextLeftLength = j + 1;
                            nextDiversities[nextLeftLength][rightLength] = addMod(
                                    nextDiversities[nextLeftLength][rightLength],
                                    multiplyMod(diversities[leftLength][rightLength], Math.max(0, a[j] - i)));

                            leftSeen.add(a[j]);
                        }
                    }

                    Set<Integer> rightSeen = new HashSet<>();
                    for (int j = endIndex; j >= beginIndex; --j) {
                        if (!rightSeen.contains(a[j]) && (leftLength == 0 || a[j] >= a[leftLength - 1])
                                && (rightLength == 0 || a[j] >= a[M - rightLength])) {
                            int nextRightLength = M - j;
                            nextDiversities[leftLength][nextRightLength] = addMod(
                                    nextDiversities[leftLength][nextRightLength],
                                    multiplyMod(diversities[leftLength][rightLength], Math.max(0, a[j] - i)));

                            rightSeen.add(a[j]);
                        }
                    }
                }
            }

            diversities = nextDiversities;
            result = addMod(result, computediversitySum(a, diversities));
        }

        return result;
    }

    static int computediversitySum(int[] a, int[][] diversities) {
        int M = a.length;

        int result = 0;
        for (int leftLength = 0; leftLength <= M; ++leftLength) {
            for (int rightLength = 0; rightLength <= M; ++rightLength) {
                if (rightLength == 0 || (leftLength != 0 && a[leftLength - 1] > a[M - rightLength])) {
                    result = addMod(result, diversities[leftLength][rightLength]);
                }
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