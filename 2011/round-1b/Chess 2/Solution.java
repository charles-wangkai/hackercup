import java.util.Scanner;

public class Solution {
    static final int SIZE = 16;

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        int N = sc.nextInt();
        for (int tc = 1; tc <= N; ++tc) {
            int P = sc.nextInt();
            char[] C = new char[P];
            int[] R = new int[P];
            int[] F = new int[P];
            for (int i = 0; i < P; ++i) {
                C[i] = sc.next().charAt(0);
                R[i] = sc.nextInt();
                F[i] = sc.nextInt();
            }

            System.out.println(String.format("Case #%d: %d", tc, solve(C, R, F)));
        }

        sc.close();
    }

    static int solve(char[] C, int[] R, int[] F) {
        int P = C.length;

        boolean[][] pieces = new boolean[SIZE + 1][SIZE + 1];
        for (int i = 0; i < P; ++i) {
            pieces[R[i]][F[i]] = true;
        }

        boolean[][] threatened = new boolean[SIZE + 1][SIZE + 1];
        for (int i = 0; i < P; ++i) {
            if (C[i] == 'K') {
                for (int rOffset = -1; rOffset <= 1; ++rOffset) {
                    for (int cOffset = -1; cOffset <= 1; ++cOffset) {
                        if (rOffset != 0 || cOffset != 0) {
                            threat(threatened, pieces, R[i], F[i], rOffset, cOffset, 1);
                        }
                    }
                }
            }
            if (C[i] == 'Q') {
                for (int rOffset = -1; rOffset <= 1; ++rOffset) {
                    for (int cOffset = -1; cOffset <= 1; ++cOffset) {
                        if (rOffset != 0 || cOffset != 0) {
                            threat(threatened, pieces, R[i], F[i], rOffset, cOffset, Integer.MAX_VALUE);
                        }
                    }
                }
            }
            if (C[i] == 'R') {
                threat(threatened, pieces, R[i], F[i], -1, 0, Integer.MAX_VALUE);
                threat(threatened, pieces, R[i], F[i], 1, 0, Integer.MAX_VALUE);
                threat(threatened, pieces, R[i], F[i], 0, -1, Integer.MAX_VALUE);
                threat(threatened, pieces, R[i], F[i], 0, 1, Integer.MAX_VALUE);
            }
            if (C[i] == 'B' || C[i] == 'A') {
                threat(threatened, pieces, R[i], F[i], -1, -1, Integer.MAX_VALUE);
                threat(threatened, pieces, R[i], F[i], -1, 1, Integer.MAX_VALUE);
                threat(threatened, pieces, R[i], F[i], 1, -1, Integer.MAX_VALUE);
                threat(threatened, pieces, R[i], F[i], 1, 1, Integer.MAX_VALUE);
            }
            if (C[i] == 'N' || C[i] == 'A') {
                for (int rOffset = -2; rOffset <= 2; ++rOffset) {
                    for (int cOffset = -2; cOffset <= 2; ++cOffset) {
                        if (Math.abs(rOffset * cOffset) == 2) {
                            threat(threatened, pieces, R[i], F[i], rOffset, cOffset, 1);
                        }
                    }
                }
            }
            if (C[i] == 'S') {
                for (int rOffset = -2; rOffset <= 2; ++rOffset) {
                    for (int cOffset = -2; cOffset <= 2; ++cOffset) {
                        if (Math.abs(rOffset * cOffset) == 2) {
                            threat(threatened, pieces, R[i], F[i], rOffset, cOffset, Integer.MAX_VALUE);
                        }
                    }
                }
            }
            if (C[i] == 'E') {
                for (int r = 1; r <= SIZE; ++r) {
                    for (int c = 1; c <= SIZE; ++c) {
                        if (r != R[i] || c != F[i]) {
                            threatened[r][c] = true;
                        }
                    }
                }
            }
        }

        int result = 0;
        for (int r = 1; r <= SIZE; ++r) {
            for (int c = 1; c <= SIZE; ++c) {
                if (pieces[r][c] && threatened[r][c]) {
                    ++result;
                }
            }
        }

        return result;
    }

    static void threat(boolean[][] threatened, boolean[][] pieces, int r, int c, int rOffset, int cOffset,
            int maxStep) {
        for (int i = 0; i < maxStep; ++i) {
            r += rOffset;
            c += cOffset;

            if (!(r >= 1 && r <= SIZE && c >= 1 && c <= SIZE)) {
                break;
            }

            threatened[r][c] = true;

            if (pieces[r][c]) {
                break;
            }
        }
    }
}