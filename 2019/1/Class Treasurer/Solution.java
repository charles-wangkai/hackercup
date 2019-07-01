import java.util.Scanner;

public class Solution {
	static final int MODULUS = 1_000_000_007;

	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);

		int T = sc.nextInt();
		for (int tc = 1; tc <= T; tc++) {
			sc.nextInt();
			int K = sc.nextInt();
			String V = sc.next();

			System.out.println(String.format("Case #%d: %d", tc, solve(V, K)));
		}

		sc.close();
	}

	static int solve(String V, int K) {
		int result = 0;
		int bMore = 0;
		for (int i = V.length() - 1; i >= 0; i--) {
			if (V.charAt(i) == 'A') {
				bMore--;
			} else {
				if (bMore == K) {
					result = addMod(result, 1);

					bMore--;
				} else {
					bMore++;
				}
			}

			bMore = Math.max(0, bMore);
			result = multiplyMod(result, 2);
		}

		return result;
	}

	static int addMod(int x, int y) {
		return (x + y) % MODULUS;
	}

	static int multiplyMod(int x, int y) {
		return (int) ((long) x * y % MODULUS);
	}
}
