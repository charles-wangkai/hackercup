import java.util.Scanner;

public class Solution {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        int N = sc.nextInt();
        for (int tc = 1; tc <= N; ++tc) {
            int X = sc.nextInt();

            System.out.println(String.format("Case #%d: %d", tc, solve(X)));
        }

        sc.close();
    }

    static int solve(int X) {
        int result = 0;
        for (int i = 0; 2L * i * i <= X; ++i) {
            if (isSquare(X - i * i)) {
                ++result;
            }
        }

        return result;
    }

    static boolean isSquare(int n) {
        long root = Math.round(Math.sqrt(n));

        return root * root == n;
    }
}