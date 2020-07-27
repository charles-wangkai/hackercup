import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Scanner;

public class Solution implements Runnable {
    public static void main(String[] args) {
        new Thread(null, new Solution(), "main", 1 << 30).start();
    }

    @Override
    public void run() {
        Scanner sc = new Scanner(System.in);

        int T = sc.nextInt();
        for (int tc = 1; tc <= T; ++tc) {
            int N = sc.nextInt();
            int M = sc.nextInt();
            int A = sc.nextInt() - 1;
            int B = sc.nextInt() - 1;
            int[] P = new int[N];
            int[] C = new int[N];
            for (int i = 0; i < N; ++i) {
                P[i] = sc.nextInt() - 1;
                C[i] = sc.nextInt();
            }

            System.out.println(String.format("Case #%d: %d", tc, solve(P, C, M, A, B)));
        }

        sc.close();
    }

    static long solve(int[] P, int[] C, int M, int A, int B) {
        int N = P.length;

        @SuppressWarnings("unchecked")
        List<Integer>[] childLists = new List[N];
        for (int i = 0; i < childLists.length; ++i) {
            childLists[i] = new ArrayList<>();
        }
        int[] depths = new int[N];
        reroot(P, A, childLists, depths);

        List<Integer> path = findPath(childLists, new ArrayList<>(), B, A);

        int[] ancestors = new int[N];
        buildAncestors(ancestors, childLists, depths, path, -1, A);

        PriorityQueue<Element> pq = new PriorityQueue<>((e1, e2) -> Long.compare(e1.costSum, e2.costSum));
        pq.offer(new Element(0, M));

        Queue<Integer> queue = new LinkedList<>();
        queue.offer(A);
        while (true) {
            int head = queue.poll();

            while (!pq.isEmpty() && pq.peek().maxDepth < depths[head]) {
                pq.poll();
            }
            if (pq.isEmpty()) {
                return -1;
            }

            if (head == B) {
                return pq.peek().costSum;
            }

            if (C[head] != 0) {
                pq.offer(new Element(pq.peek().costSum + C[head], M - depths[head] + 2 * depths[ancestors[head]]));
            }

            for (int child : childLists[head]) {
                queue.offer(child);
            }
        }
    }

    static void buildAncestors(int[] ancestors, List<Integer>[] childLists, int[] depths, List<Integer> path,
            int ancestor, int node) {
        if (depths[node] < path.size() && path.get(depths[node]) == node) {
            ancestor = node;
        }
        ancestors[node] = ancestor;

        for (int child : childLists[node]) {
            buildAncestors(ancestors, childLists, depths, path, ancestor, child);
        }
    }

    static List<Integer> findPath(List<Integer>[] childLists, List<Integer> current, int B, int node) {
        current.add(node);

        if (node == B) {
            return current;
        }

        for (int child : childLists[node]) {
            List<Integer> subResult = findPath(childLists, current, B, child);
            if (subResult != null) {
                return subResult;
            }
        }

        current.remove(current.size() - 1);

        return null;
    }

    static void reroot(int[] P, int A, List<Integer>[] childLists, int[] depths) {
        int N = P.length;

        @SuppressWarnings("unchecked")
        List<Integer>[] adjLists = new List[N];
        for (int i = 0; i < adjLists.length; ++i) {
            adjLists[i] = new ArrayList<>();
        }
        for (int i = 0; i < P.length; ++i) {
            if (P[i] != -1) {
                adjLists[i].add(P[i]);
                adjLists[P[i]].add(i);
            }
        }

        depths[A] = 0;
        buildTree(childLists, depths, adjLists, new boolean[N], A);
    }

    static void buildTree(List<Integer>[] childLists, int[] depths, List<Integer>[] adjLists, boolean[] visited,
            int node) {
        visited[node] = true;

        for (int adj : adjLists[node]) {
            if (!visited[adj]) {
                childLists[node].add(adj);
                depths[adj] = depths[node] + 1;

                buildTree(childLists, depths, adjLists, visited, adj);
            }
        }
    }
}

class Element {
    long costSum;
    int maxDepth;

    Element(long costSum, int maxDepth) {
        this.costSum = costSum;
        this.maxDepth = maxDepth;
    }
}