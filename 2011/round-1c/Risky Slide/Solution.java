import java.util.Scanner;

public class Solution {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        int N = sc.nextInt();
        for (int tc = 1; tc <= N; ++tc) {
            int R = sc.nextInt();
            int C = sc.nextInt();
            int[][] tiles = new int[R][C];
            for (int r = 0; r < R; ++r) {
                String line = sc.next();
                for (int c = 0; c < C; ++c) {
                    tiles[r][c] = line.charAt(c) - '0';
                }
            }

            System.out.println(String.format("Case #%d: %d", tc, solve(tiles)));
        }

        sc.close();
    }

    static int solve(int[][] tiles) {
        int R = tiles.length;
        int C = tiles[0].length;

        int result = 0;
        for (int r = 0; r < R; ++r) {
            int leftSum = 0;
            for (int c = 0; c < C; ++c) {
                leftSum += tiles[r][c];

                int remain = leftSum;
                int slide = 0;
                for (int i = c + 1; i < C; ++i) {
                    if (tiles[r][i] > remain) {
                        break;
                    }

                    remain -= tiles[r][i];
                    ++slide;
                }
                result = Math.max(result, slide);
            }

            int rightSum = 0;
            for (int c = C - 1; c >= 0; --c) {
                rightSum += tiles[r][c];

                int remain = rightSum;
                int slide = 0;
                for (int i = c - 1; i >= 0; --i) {
                    if (tiles[r][i] > remain) {
                        break;
                    }

                    remain -= tiles[r][i];
                    ++slide;
                }
                result = Math.max(result, slide);
            }
        }
        for (int c = 0; c < C; ++c) {
            int upSum = 0;
            for (int r = 0; r < R; ++r) {
                upSum += tiles[r][c];

                int remain = upSum;
                int slide = 0;
                for (int i = r + 1; i < R; ++i) {
                    if (tiles[i][c] > remain) {
                        break;
                    }

                    remain -= tiles[i][c];
                    ++slide;
                }
                result = Math.max(result, slide);
            }

            int downSum = 0;
            for (int r = R - 1; r >= 0; --r) {
                downSum += tiles[r][c];

                int remain = downSum;
                int slide = 0;
                for (int i = r - 1; i >= 0; --i) {
                    if (tiles[i][c] > remain) {
                        break;
                    }

                    remain -= tiles[i][c];
                    ++slide;
                }
                result = Math.max(result, slide);
            }
        }

        return result;
    }
}