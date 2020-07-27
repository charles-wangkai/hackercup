import java.util.Scanner;

public class Solution {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        int T = sc.nextInt();
        for (int tc = 1; tc <= T; ++tc) {
            sc.nextInt();
            String C = sc.next();

            System.out.println(String.format("Case #%d: %c", tc, solve(C) ? 'Y' : 'N'));
        }

        sc.close();
    }

    static boolean solve(String C) {
        int countA = (int) C.chars().filter(ch -> ch == 'A').count();
        int countB = C.length() - countA;

        return Math.abs(countA - countB) == 1;
    }
}