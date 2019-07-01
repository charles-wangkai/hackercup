import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Scanner;
import java.util.stream.IntStream;

public class Solution {
	static final int INF = 10_000_000;

	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);

		int T = sc.nextInt();
		for (int tc = 1; tc <= T; tc++) {
			int N = sc.nextInt();
			int H = sc.nextInt();
			int[] X = new int[N];
			int[] A = new int[N];
			int[] B = new int[N];
			for (int i = 0; i < N; i++) {
				X[i] = sc.nextInt();
				A[i] = sc.nextInt();
				B[i] = sc.nextInt();
			}

			System.out.println(String.format("Case #%d: %d", tc, solve(X, A, B, H)));
		}

		sc.close();
	}

	static int solve(int[] X, int[] A, int[] B, int H) {
		int N = X.length;

		if (IntStream.range(0, N).anyMatch(i -> A[i] == 0 && B[i] == H)) {
			return -1;
		}

		int[][] graph = new int[N + 2][N + 2];
		for (int i = 0; i < N; i++) {
			if (A[i] == 0) {
				graph[0][i + 1] = INF;
				graph[i + 1][0] = INF;
			}

			if (B[i] == H) {
				graph[i + 1][N + 1] = INF;
				graph[N + 1][i + 1] = INF;
			}
		}

		for (int i = 0; i < N; i++) {
			for (int j = i + 1; j < N; j++) {
				if (X[i] == X[j]) {
					continue;
				}

				List<Range> ranges = initRanges(A[i], B[i], A[j], B[j]);

				for (int k = 0; k < N; k++) {
					if ((X[k] > X[i] && X[k] < X[j]) || (X[k] > X[j] && X[k] < X[i])) {
						ranges = removeRange(ranges, A[k], B[k]);
					}
				}

				int capacity = ranges.stream().mapToInt(range -> range.end - range.begin).sum();
				graph[i + 1][j + 1] = capacity;
				graph[j + 1][i + 1] = capacity;
			}
		}

		return fordFulkerson(graph, 0, N + 1);
	}

	static List<Range> initRanges(int begin1, int end1, int begin2, int end2) {
		int begin = Math.max(begin1, begin2);
		int end = Math.min(end1, end2);

		if (begin < end) {
			return Collections.singletonList(new Range(begin, end));
		} else {
			return Collections.emptyList();
		}
	}

	static List<Range> removeRange(List<Range> ranges, int removedBegin, int removedEnd) {
		List<Range> result = new ArrayList<>();
		for (Range range : ranges) {
			if (range.begin >= removedEnd || range.end <= removedBegin) {
				result.add(range);
			} else {
				if (range.begin < removedBegin) {
					result.add(new Range(range.begin, removedBegin));
				}

				if (range.end > removedEnd) {
					result.add(new Range(removedEnd, range.end));
				}
			}
		}
		return result;
	}

	static int fordFulkerson(int graph[][], int s, int t) {
		int V = graph.length;

		int[][] rGraph = new int[V][V];
		for (int u = 0; u < V; u++) {
			for (int v = 0; v < V; v++) {
				rGraph[u][v] = graph[u][v];
			}
		}

		int maxFlow = 0;

		while (true) {
			int[] parents = bfs(rGraph, s, t);
			if (parents == null) {
				break;
			}

			int pathFlow = Integer.MAX_VALUE;
			for (int v = t; v != s; v = parents[v]) {
				int u = parents[v];

				pathFlow = Math.min(pathFlow, rGraph[u][v]);
			}

			for (int v = t; v != s; v = parents[v]) {
				int u = parents[v];

				rGraph[u][v] -= pathFlow;
				rGraph[v][u] += pathFlow;
			}

			maxFlow += pathFlow;
		}

		return maxFlow;
	}

	static int[] bfs(int rGraph[][], int s, int t) {
		int V = rGraph.length;

		int[] parents = new int[V];
		boolean[] visited = new boolean[V];

		Queue<Integer> queue = new LinkedList<>();
		queue.offer(s);
		parents[s] = -1;
		visited[s] = true;

		while (!queue.isEmpty()) {
			int u = queue.poll();

			for (int v = 0; v < V; v++) {
				if (!visited[v] && rGraph[u][v] > 0) {
					queue.offer(v);
					parents[v] = u;
					visited[v] = true;
				}
			}
		}

		return visited[t] ? parents : null;
	}
}

class Range {
	int begin;
	int end;

	Range(int begin, int end) {
		this.begin = begin;
		this.end = end;
	}
}
