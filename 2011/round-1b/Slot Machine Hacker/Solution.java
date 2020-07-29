import java.util.Arrays;
import java.util.Scanner;
import java.util.stream.Collectors;

public class Solution {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        int T = sc.nextInt();
        for (int tc = 1; tc <= T; ++tc) {
            int N = sc.nextInt();
            int[] observations = new int[N];
            for (int i = 0; i < observations.length; ++i) {
                observations[i] = sc.nextInt();
            }

            System.out.println(String.format("Case #%d: %s", tc, solve(observations)));
        }

        sc.close();
    }

    static String solve(int[] observations) {
        int seed = -1;
        for (int i = 0; i < 10000001; ++i) {
            if (check(i, observations)) {
                if (seed != -1) {
                    return "Not enough observations";
                }

                seed = i;
            }
        }

        if (seed == -1) {
            return "Wrong machine";
        }

        int secret = seed;
        for (int i = 0; i < observations.length; ++i) {
            secret = computeNextSecret(secret);
        }

        int[] result = new int[10];
        for (int i = 0; i < result.length; ++i) {
            secret = computeNextSecret(secret);
            result[i] = secret % 1000;
        }

        return Arrays.stream(result).mapToObj(String::valueOf).collect(Collectors.joining(" "));
    }

    static boolean check(int seed, int[] observations) {
        int secret = seed;
        for (int i = 0; i < observations.length; ++i) {
            secret = computeNextSecret(secret);

            if (secret % 1000 != observations[i]) {
                return false;
            }
        }

        return true;
    }

    static int computeNextSecret(int secret) {
        return (int) ((secret * 5402147L + 54321) % 10000001);
    }
}