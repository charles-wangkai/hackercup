import java.util.Scanner;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Solution {
	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);

		int T = sc.nextInt();
		for (int tc = 1; tc <= T; tc++) {
			int N = sc.nextInt();
			int M = sc.nextInt();
			int[] X = new int[M];
			int[] Y = new int[M];
			int[] Z = new int[M];
			for (int i = 0; i < M; i++) {
				X[i] = sc.nextInt();
				Y[i] = sc.nextInt();
				Z[i] = sc.nextInt();
			}

			System.out.println(String.format("Case #%d: %s", tc, solve(N, X, Y, Z)));
		}

		sc.close();
	}

	static String solve(int N, int[] X, int[] Y, int[] Z) {
		int[][] minDistances = buildMinDistances(N, X, Y, Z);

		if (IntStream.range(0, X.length).anyMatch(i -> minDistances[X[i]][Y[i]] != Z[i])) {
			return "Impossible";
		}

		return String.format("%d\n%s", X.length, IntStream.range(0, X.length)
				.mapToObj(i -> String.format("%d %d %d", X[i], Y[i], Z[i])).collect(Collectors.joining("\n")));
	}

	static int[][] buildMinDistances(int N, int[] X, int[] Y, int[] Z) {
		int[][] minDistances = new int[N + 1][N + 1];
		for (int i = 1; i <= N; i++) {
			for (int j = 1; j <= N; j++) {
				minDistances[i][j] = (i == j) ? 0 : Integer.MAX_VALUE;
			}
		}

		for (int i = 0; i < X.length; i++) {
			minDistances[X[i]][Y[i]] = Z[i];
			minDistances[Y[i]][X[i]] = Z[i];
		}

		for (int k = 1; k <= N; k++) {
			for (int i = 1; i <= N; i++) {
				for (int j = 1; j <= N; j++) {
					if (minDistances[i][k] != Integer.MAX_VALUE && minDistances[k][j] != Integer.MAX_VALUE) {
						minDistances[i][j] = Math.min(minDistances[i][j], minDistances[i][k] + minDistances[k][j]);
					}
				}
			}
		}

		return minDistances;
	}
}
