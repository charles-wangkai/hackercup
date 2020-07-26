import java.util.Scanner;

public class Solution {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        int N = sc.nextInt();
        for (int tc = 1; tc <= N; ++tc) {
            int R = sc.nextInt();
            int C = sc.nextInt();
            int K = sc.nextInt();
            int M = sc.nextInt();
            int[] rMissings = new int[M];
            int[] cMissings = new int[M];
            for (int i = 0; i < M; ++i) {
                rMissings[i] = sc.nextInt();
                cMissings[i] = sc.nextInt();
            }

            System.out.println(String.format("Case #%d: %s", tc, solve(R, C, K, rMissings, cMissings)));
        }

        sc.close();
    }

    static String solve(int R, int C, int K, int[] rMissings, int[] cMissings) {
        int row = R;
        int col = C * 2 - 1;

        char[][] game = new char[row][col];
        for (int r = 0; r < row; ++r) {
            for (int c = 0; c < col; ++c) {
                if ((r + c) % 2 == 0) {
                    game[r][c] = 'X';
                } else if (r % 2 != 0 && (c == 0 || c == col - 1)) {
                    game[r][c] = ' ';
                } else {
                    game[r][c] = '.';
                }
            }
        }
        for (int i = 0; i < rMissings.length; ++i) {
            game[rMissings[i]][cMissings[i] * 2 + rMissings[i] % 2] = '.';
        }

        String result = null;
        double maxProb = 0;
        for (int i = 0; i < C - 1; ++i) {
            double[] probs = new double[col];
            probs[i * 2 + 1] = 1;

            for (int r = 1; r < row; ++r) {
                double[] nextProbs = new double[col];
                for (int c = 0; c < col; ++c) {
                    if (game[r][c] == '.') {
                        nextProbs[c] += probs[c];
                    } else if (game[r][c] == 'X' && game[r - 1][c] != ' ') {
                        if (c == 1) {
                            nextProbs[c + 1] += probs[c];
                        } else if (c == col - 2) {
                            nextProbs[c - 1] += probs[c];
                        } else {
                            nextProbs[c - 1] += probs[c] / 2;
                            nextProbs[c + 1] += probs[c] / 2;
                        }
                    }
                }

                probs = nextProbs;
            }

            if (probs[K * 2 + 1] > maxProb) {
                maxProb = probs[K * 2 + 1];
                result = String.format("%d %.6f", i, maxProb);
            }
        }

        return result;
    }
}