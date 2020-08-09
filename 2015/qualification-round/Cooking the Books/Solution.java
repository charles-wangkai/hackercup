import java.util.Scanner;

public class Solution {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        int T = sc.nextInt();
        for (int tc = 1; tc <= T; ++tc) {
            int N = sc.nextInt();

            System.out.println(String.format("Case #%d: %s", tc, solve(N)));
        }

        sc.close();
    }

    static String solve(int N) {
        int minValue = N;
        int maxValue = N;

        String s = String.valueOf(N);
        for (int i = 0; i < s.length(); ++i) {
            for (int j = i + 1; j < s.length(); ++j) {
                String swapped = String.format("%s%c%s%c%s", s.substring(0, i), s.charAt(j), s.substring(i + 1, j),
                        s.charAt(i), s.substring(j + 1));
                if (!swapped.startsWith("0")) {
                    int value = Integer.parseInt(swapped);
                    minValue = Math.min(minValue, value);
                    maxValue = Math.max(maxValue, value);
                }
            }
        }

        return String.format("%d %d", minValue, maxValue);
    }
}