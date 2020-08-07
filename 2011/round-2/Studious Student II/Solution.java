// https://www.facebook.com/async/codingproblems/download/submission/source_code/?submission_id=698573254313703

import java.util.Scanner;

public class Solution {
    static final int MODULUS = 1_000_000_007;

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        int N = sc.nextInt();
        for (int tc = 1; tc <= N; ++tc) {
            String s = sc.next();

            System.out.println(String.format("Case #%d: %d", tc, solve(s)));
        }

        sc.close();
    }

    static int solve(String s) {
        int[][] combs = new int[s.length()][s.length()];
        combs[0][0] = 1;
        for (int i = 1; i < combs.length; ++i) {
            combs[i][0] = 1;
            for (int j = 1; j <= i; ++j) {
                combs[i][j] = addMod(combs[i - 1][j], combs[i - 1][j - 1]);
            }
        }

        int[][][][][][] wayNums = new int[s.length()][s.length()][s.length()][2][2][2];
        for (int length = 1; length <= s.length(); ++length) {
            for (int leftIndex = 0; leftIndex + length - 1 < s.length(); ++leftIndex) {
                int rightIndex = leftIndex + length - 1;

                if (length == 1) {
                    int hasA = (s.charAt(leftIndex) == 'a') ? 1 : 0;
                    int hasB = (s.charAt(leftIndex) == 'b') ? 1 : 0;

                    wayNums[leftIndex][rightIndex][0][hasA][hasB][1] = 1;
                } else {
                    for (int operCount = 0; operCount < s.length(); ++operCount) {
                        for (int hasA = 0; hasA <= 1; ++hasA) {
                            for (int hasB = 0; hasB <= 1; ++hasB) {
                                for (int isSingle = 0; isSingle <= 1; ++isSingle) {
                                    int nextHasA = hasA | ((s.charAt(leftIndex) == 'a') ? 1 : 0);
                                    int nextHasB = hasB | ((s.charAt(leftIndex) == 'b') ? 1 : 0);

                                    wayNums[leftIndex][rightIndex][operCount][nextHasA][nextHasB][0] = addMod(
                                            wayNums[leftIndex][rightIndex][operCount][nextHasA][nextHasB][0],
                                            wayNums[leftIndex + 1][rightIndex][operCount][hasA][hasB][isSingle]);
                                }
                            }
                        }
                    }

                    for (int middleIndex = leftIndex; middleIndex <= rightIndex; ++middleIndex) {
                        for (int leftOperCount = 0; leftOperCount < s.length(); ++leftOperCount) {
                            for (int leftHasA = 0; leftHasA <= 1; ++leftHasA) {
                                for (int leftHasB = 0; leftHasB <= 1; ++leftHasB) {
                                    int leftWayNum = wayNums[leftIndex][middleIndex][leftOperCount][leftHasA][leftHasB][0];
                                    if (leftWayNum == 0) {
                                        continue;
                                    }

                                    for (int transformTo = 0; transformTo <= 1; ++transformTo) {
                                        if ((transformTo == 0 && leftHasA == 0)
                                                || (transformTo == 1 && leftHasB == 0)) {
                                            continue;
                                        }

                                        if (middleIndex != rightIndex) {
                                            for (int rightOperCount = 0; rightOperCount < s
                                                    .length(); ++rightOperCount) {
                                                for (int rightHasA = 0; rightHasA <= 1; ++rightHasA) {
                                                    for (int rightHasB = 0; rightHasB <= 1; ++rightHasB) {
                                                        for (int rightIsSingle = 0; rightIsSingle <= 1; ++rightIsSingle) {
                                                            int rightWayNum = wayNums[middleIndex
                                                                    + 1][rightIndex][rightOperCount][rightHasA][rightHasB][rightIsSingle];
                                                            if (rightWayNum == 0) {
                                                                continue;
                                                            }

                                                            int nextOperCount = leftOperCount + rightOperCount + 1;
                                                            int nextHasA = ((transformTo == 0) ? 1 : 0) | rightHasA;
                                                            int nextHasB = ((transformTo == 1) ? 1 : 0) | rightHasB;

                                                            wayNums[leftIndex][rightIndex][nextOperCount][nextHasA][nextHasB][0] = addMod(
                                                                    wayNums[leftIndex][rightIndex][nextOperCount][nextHasA][nextHasB][0],
                                                                    multiplyMod(multiplyMod(leftWayNum, rightWayNum),
                                                                            combs[nextOperCount][rightOperCount]));
                                                        }
                                                    }
                                                }
                                            }
                                        } else {
                                            int nextOperCount = leftOperCount + 1;
                                            int nextHasA = (transformTo == 0) ? 1 : 0;
                                            int nextHasB = (transformTo == 1) ? 1 : 0;

                                            wayNums[leftIndex][rightIndex][nextOperCount][nextHasA][nextHasB][1] = addMod(
                                                    wayNums[leftIndex][rightIndex][nextOperCount][nextHasA][nextHasB][1],
                                                    leftWayNum);
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }

        int result = 0;
        for (int operCount = 0; operCount < s.length(); ++operCount) {
            for (int hasA = 0; hasA <= 1; ++hasA) {
                for (int hasB = 0; hasB <= 1; ++hasB) {
                    for (int isSingle = 0; isSingle <= 1; ++isSingle) {
                        result = addMod(result, wayNums[0][s.length() - 1][operCount][hasA][hasB][isSingle]);
                    }
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