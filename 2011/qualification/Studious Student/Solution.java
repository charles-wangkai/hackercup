import java.util.Arrays;
import java.util.Scanner;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Solution {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        int N = sc.nextInt();
        for (int tc = 1; tc <= N; ++tc) {
            int M = sc.nextInt();
            String[] words = new String[M];
            for (int i = 0; i < words.length; ++i) {
                words[i] = sc.next();
            }

            System.out.println(String.format("Case #%d: %s", tc, solve(words)));
        }

        sc.close();
    }

    static String solve(String[] words) {
        return search(words, IntStream.range(0, words.length).toArray(), 0);
    }

    static String search(String[] words, int[] indices, int depth) {
        if (depth == indices.length) {
            return Arrays.stream(indices).mapToObj(i -> words[i]).collect(Collectors.joining());
        }

        String result = null;
        for (int i = depth; i < indices.length; ++i) {
            swap(indices, i, depth);

            String subResult = search(words, indices, depth + 1);
            if (result == null || subResult.compareTo(result) < 0) {
                result = subResult;
            }

            swap(indices, i, depth);
        }

        return result;
    }

    static void swap(int[] a, int index1, int index2) {
        int temp = a[index1];
        a[index1] = a[index2];
        a[index2] = temp;
    }
}