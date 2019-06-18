import java.util.Scanner;

public class Solution {
	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);

		int T = sc.nextInt();
		for (int tc = 1; tc <= T; tc++) {
			String E = sc.next();

			System.out.println(String.format("Case #%d: %d", tc, solve(E)));
		}

		sc.close();
	}

	static int solve(String E) {
		char value = evaluate(E);

		return (value == '0' || value == '1') ? 0 : 1;
	}

	static char evaluate(String expression) {
		if (expression.length() == 1) {
			return expression.charAt(0);
		}

		int operatorIndex;
		if (expression.charAt(1) == '(') {
			operatorIndex = 1;
			int depth = 0;
			while (true) {
				char ch = expression.charAt(operatorIndex);

				if (ch == '(') {
					depth++;
				} else if (ch == ')') {
					depth--;
				} else if (depth == 0) {
					break;
				}

				operatorIndex++;
			}
		} else {
			operatorIndex = 2;
		}

		char operator = expression.charAt(operatorIndex);
		char operand1 = evaluate(expression.substring(1, operatorIndex));
		char operand2 = evaluate(expression.substring(operatorIndex + 1, expression.length() - 1));

		if (operator == '|') {
			if (operand1 == '1' || operand2 == '1') {
				return '1';
			} else if (operand1 == '0') {
				return operand2;
			} else if (operand2 == '0') {
				return operand1;
			} else if (operand1 == operand2) {
				return operand1;
			} else {
				return '1';
			}
		} else if (operator == '&') {
			if (operand1 == '0' || operand2 == '0') {
				return '0';
			} else if (operand1 == '1') {
				return operand2;
			} else if (operand2 == '1') {
				return operand1;
			} else if (operand1 == operand2) {
				return operand1;
			} else {
				return '0';
			}
		} else {
			if (operand1 == '0') {
				return operand2;
			} else if (operand2 == '0') {
				return operand1;
			} else if (operand1 == '1') {
				return negate(operand2);
			} else if (operand2 == '1') {
				return negate(operand1);
			} else if (operand1 == operand2) {
				return '0';
			} else {
				return '1';
			}
		}
	}

	static char negate(char a) {
		if (a == '0') {
			return '1';
		} else if (a == '1') {
			return '0';
		} else if (a == 'x') {
			return 'X';
		} else {
			return 'x';
		}
	}
}
