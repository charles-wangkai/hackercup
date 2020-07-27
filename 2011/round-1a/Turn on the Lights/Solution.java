import java.util.Scanner;
import java.util.stream.IntStream;

public class Solution {
    static final int[] R_OFFSETS = { 0, -1, 0, 1, 0 };
    static final int[] C_OFFSETS = { 0, 0, 1, 0, -1 };

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        int N = sc.nextInt();
        for (int tc = 1; tc <= N; ++tc) {
            int R = sc.nextInt();
            int C = sc.nextInt();
            char[][] grid = new char[R][C];
            for (int r = 0; r < R; ++r) {
                String line = sc.next();
                for (int c = 0; c < C; ++c) {
                    grid[r][c] = line.charAt(c);
                }
            }

            System.out.println(String.format("Case #%d: %d", tc, solve(grid)));
        }

        sc.close();
    }

    static int solve(char[][] grid) {
        int R = grid.length;
        int C = grid[0].length;

        int result = -1;
        for (int code = 0; code < 1 << C; ++code) {
            boolean[][] buttons = new boolean[R][C];
            for (int r = 0; r < R; ++r) {
                for (int c = 0; c < C; ++c) {
                    buttons[r][c] = grid[r][c] == 'X';
                }
            }

            int pressCount = 0;
            for (int c = 0; c < C; ++c) {
                if ((code & (1 << c)) != 0) {
                    press(buttons, 0, c);
                    ++pressCount;
                }
            }
            for (int r = 1; r < R; ++r) {
                for (int c = 0; c < C; ++c) {
                    if (!buttons[r - 1][c]) {
                        press(buttons, r, c);
                        ++pressCount;
                    }
                }
            }

            if (IntStream.range(0, C).allMatch(c -> buttons[R - 1][c]) && (result == -1 || pressCount < result)) {
                result = pressCount;
            }
        }

        return result;
    }

    static void press(boolean[][] buttons, int r, int c) {
        int R = buttons.length;
        int C = buttons[0].length;

        for (int i = 0; i < R_OFFSETS.length; ++i) {
            int adjR = r + R_OFFSETS[i];
            int adjC = c + C_OFFSETS[i];

            if (adjR >= 0 && adjR < R && adjC >= 0 && adjC < C) {
                buttons[adjR][adjC] = !buttons[adjR][adjC];
            }
        }
    }
}