import java.util.Scanner;

public class Solution {
	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);

		int T = sc.nextInt();
		for (int tc = 1; tc <= T; tc++) {
			String L = sc.next();

			System.out.println(String.format("Case #%d: %c", tc, solve(L) ? 'Y' : 'N'));
		}

		sc.close();
	}

	static boolean solve(String L) {
		int dotCount = (int) L.chars().filter(ch -> ch == '.').count();
		int bCount = (int) L.chars().filter(ch -> ch == 'B').count();

		return dotCount != 0 && (bCount >= dotCount || bCount >= 2);
	}
}
