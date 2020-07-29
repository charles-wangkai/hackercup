// java -Xmx1g -Xss1g

import java.util.Scanner;

public class Solution {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        int T = sc.nextInt();
        for (int tc = 1; tc <= T; ++tc) {
            long N = sc.nextLong();
            int K = sc.nextInt();

            System.out.println(String.format("Case #%d: %d", tc, solve(N, K)));
        }

        sc.close();
    }

    static long solve(long N, int K) {
        return findLastIndex(K, N) + 1;
    }

    static long findLastIndex(int K, long n) {
        if (n == 1) {
            return 0;
        }
        if (K + 1 >= n) {
            return (findLastIndex(K, n - 1) + K + 1) % n;
        }

        long removedNum = n / (K + 1);
        long offset = findLastIndex(K, n - removedNum);

        long currentIndex = removedNum * (K + 1);
        long distanceToEnd = n - 1 - currentIndex;
        if (offset <= distanceToEnd) {
            return currentIndex + offset;
        }

        offset -= distanceToEnd;

        long number = offset;
        while (true) {
            long diff = offset - (number - number / (K + 1));
            if (diff == 0) {
                break;
            }

            number += diff;
        }

        return number - 1;
    }
}