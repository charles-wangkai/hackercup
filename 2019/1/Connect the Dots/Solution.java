import java.util.Arrays;
import java.util.Map;
import java.util.NavigableMap;
import java.util.Scanner;
import java.util.SortedMap;
import java.util.TreeMap;

public class Solution {
	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);

		int T = sc.nextInt();
		for (int tc = 1; tc <= T; tc++) {
			int N = sc.nextInt();
			int H = sc.nextInt();
			int V = sc.nextInt();
			Generator xGenerator = readGenerator(sc);
			Generator yGenerator = readGenerator(sc);

			System.out.println(String.format("Case #%d: %d", tc, solve(N, H, V, xGenerator, yGenerator)));
		}

		sc.close();
	}

	static Generator readGenerator(Scanner sc) {
		int prevPrev = sc.nextInt();
		int prev = sc.nextInt();
		int A = sc.nextInt();
		int B = sc.nextInt();
		int C = sc.nextInt();
		int D = sc.nextInt();

		return new Generator(prevPrev, prev, A, B, C, D);
	}

	static int solve(int N, int H, int V, Generator xGenerator, Generator yGenerator) {
		if (H + V < N) {
			return -1;
		}

		Point[] points = new Point[N];
		for (int i = 0; i < points.length; i++) {
			int x = xGenerator.next();
			int y = yGenerator.next();

			points[i] = new Point(x, y);
		}

		Arrays.sort(points, (point1, point2) -> (point1.y != point2.y) ? Integer.compare(point1.y, point2.y)
				: Integer.compare(point2.x, point1.x));

		SortedMap<Integer, Integer> upperXToCount = new TreeMap<>();
		for (Point point : points) {
			increaseCount(upperXToCount, point.x);
		}

		int result = (H == N) ? upperXToCount.lastKey() : Integer.MAX_VALUE;

		if (V >= 1) {
			int upperCount = N;
			NavigableMap<Integer, Integer> lowerXToCount = new TreeMap<>();
			int lowerMaxX = 0;
			int valueCount = -1;

			for (int i = 0; i < points.length; i++) {
				int maxY = points[i].y;

				decreaseCount(upperXToCount, points[i].x);
				upperCount--;

				int upperMaxX = upperXToCount.isEmpty() ? 0 : upperXToCount.lastKey();

				if (upperCount <= H) {
					if (upperCount == N - V - 1) {
						lowerMaxX = lowerXToCount.firstKey();
						valueCount = 1;
					}

					result = Math.min(result, Math.max(upperMaxX, lowerMaxX) + maxY);
				}

				increaseCount(lowerXToCount, points[i].x);

				if (lowerMaxX != 0 && points[i].x >= lowerMaxX) {
					valueCount++;

					if (valueCount > lowerXToCount.get(lowerMaxX)) {
						lowerMaxX = lowerXToCount.higherKey(lowerMaxX);
						valueCount = 1;
					}
				}
			}
		}

		return result;
	}

	static void increaseCount(Map<Integer, Integer> xToCount, int x) {
		xToCount.put(x, xToCount.getOrDefault(x, 0) + 1);
	}

	static void decreaseCount(Map<Integer, Integer> xToCount, int x) {
		xToCount.put(x, xToCount.get(x) - 1);
		xToCount.remove(x, 0);
	}
}

class Point {
	int x;
	int y;

	Point(int x, int y) {
		this.x = x;
		this.y = y;
	}
}

class Generator {
	int prevPrev;
	int prev;
	int A;
	int B;
	int C;
	int D;
	int count = 0;

	Generator(int prevPrev, int prev, int A, int B, int C, int D) {
		this.prevPrev = prevPrev;
		this.prev = prev;
		this.A = A;
		this.B = B;
		this.C = C;
		this.D = D;
	}

	int addMod(int p, int q) {
		return (p + q) % D;
	}

	int multiplyMod(int p, int q) {
		return (int) ((long) p * q % D);
	}

	int next() {
		int result;
		if (count == 0) {
			result = prevPrev;
		} else if (count == 1) {
			result = prev;
		} else {
			result = addMod(addMod(multiplyMod(A, prevPrev), multiplyMod(B, prev)), C) + 1;

			prevPrev = prev;
			prev = result;
		}

		count++;

		return result;
	}
}