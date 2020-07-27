import java.util.PriorityQueue;
import java.util.Scanner;

public class Solution {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        int T = sc.nextInt();
        for (int tc = 1; tc <= T; ++tc) {
            int N = sc.nextInt();
            int M = sc.nextInt();
            int[] C = new int[N];
            for (int i = 0; i < C.length; ++i) {
                C[i] = sc.nextInt();
            }

            System.out.println(String.format("Case #%d: %d", tc, solve(C, M)));
        }

        sc.close();
    }

    static long solve(int[] C, int M) {
        PriorityQueue<Element> pq = new PriorityQueue<>((e1, e2) -> Long.compare(e1.costSum, e2.costSum));
        pq.offer(new Element(0, M));

        for (int i = 1;; ++i) {
            while (!pq.isEmpty() && pq.peek().maxIndex < i) {
                pq.poll();
            }
            if (pq.isEmpty()) {
                return -1;
            }

            if (i == C.length - 1) {
                return pq.peek().costSum;
            }

            if (C[i] != 0) {
                pq.offer(new Element(pq.peek().costSum + C[i], i + M));
            }
        }
    }
}

class Element {
    long costSum;
    int maxIndex;

    Element(long costSum, int maxIndex) {
        this.costSum = costSum;
        this.maxIndex = maxIndex;
    }
}