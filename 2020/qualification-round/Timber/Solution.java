import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.stream.IntStream;

public class Solution {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        int T = sc.nextInt();
        for (int tc = 1; tc <= T; ++tc) {
            int N = sc.nextInt();
            int[] P = new int[N];
            int[] H = new int[N];
            for (int i = 0; i < N; ++i) {
                P[i] = sc.nextInt();
                H[i] = sc.nextInt();
            }

            System.out.println(String.format("Case #%d: %d", tc, solve(P, H)));
        }

        sc.close();
    }

    static long solve(int[] P, int[] H) {
        int[] sortedIndices = IntStream.range(0, P.length).boxed().sorted((i1, i2) -> Integer.compare(P[i1], P[i2]))
                .mapToInt(x -> x).toArray();
        Map<Integer, Integer> posToLength = new HashMap<>();
        for (int sortedIndex : sortedIndices) {
            posToLength.put(P[sortedIndex] + H[sortedIndex],
                    Math.max(posToLength.getOrDefault(P[sortedIndex] + H[sortedIndex], 0),
                            posToLength.getOrDefault(P[sortedIndex], 0) + H[sortedIndex]));

            posToLength.put(P[sortedIndex], Math.max(posToLength.getOrDefault(P[sortedIndex], 0),
                    posToLength.getOrDefault(P[sortedIndex] - H[sortedIndex], 0) + H[sortedIndex]));
        }

        return posToLength.values().stream().mapToInt(x -> x).max().getAsInt();
    }
}