import java.util.Arrays;
import java.util.Scanner;
import java.util.stream.Collectors;

public class Solution {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        int T = sc.nextInt();
        for (int tc = 1; tc <= T; ++tc) {
            int N = sc.nextInt();
            double P = sc.nextDouble();

            System.out.println(String.format("Case #%d:\n%s", tc, solve(N, P)));
        }

        sc.close();
    }

    static String solve(int N, double P) {
        double[] matchNums = { 1, 1 };
        for (int size = matchNums.length + 1; size <= N; ++size) {
            double[] nextMatchNums = new double[size];

            double[] lostProbs = new double[size];
            for (int i = 0; i < lostProbs.length; ++i) {
                lostProbs[i] = (2.0 / size) * (i / (size - 1.0) * (1 - P) + (size - 1 - i) / (size - 1.0) * P);
            }

            double leftLostProbSum = 0;
            double rightLostProbSum = 1;
            for (int i = 0; i < nextMatchNums.length; ++i) {
                rightLostProbSum -= lostProbs[i];

                nextMatchNums[i] = lostProbs[i] + ((i == 0) ? 0 : leftLostProbSum * (1 + matchNums[i - 1]))
                        + ((i == size - 1) ? 0 : rightLostProbSum * (1 + matchNums[i]));

                leftLostProbSum += lostProbs[i];
            }

            matchNums = nextMatchNums;
        }

        return Arrays.stream(matchNums).mapToObj(x -> String.format("%.8f", x)).collect(Collectors.joining("\n"));
    }
}