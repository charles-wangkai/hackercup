import java.util.NavigableSet;
import java.util.Scanner;
import java.util.TreeSet;

public class Solution {
    static final int MODULUS = 1_000_000_007;

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        int T = sc.nextInt();
        for (int tc = 1; tc <= T; ++tc) {
            int N = sc.nextInt();
            int K = sc.nextInt();
            int[] L = new int[N];
            for (int i = 0; i < K; ++i) {
                L[i] = sc.nextInt();
            }
            int AL = sc.nextInt();
            int BL = sc.nextInt();
            int CL = sc.nextInt();
            int DL = sc.nextInt();
            int[] W = new int[N];
            for (int i = 0; i < K; ++i) {
                W[i] = sc.nextInt();
            }
            int AW = sc.nextInt();
            int BW = sc.nextInt();
            int CW = sc.nextInt();
            int DW = sc.nextInt();
            int[] H = new int[N];
            for (int i = 0; i < K; ++i) {
                H[i] = sc.nextInt();
            }
            int AH = sc.nextInt();
            int BH = sc.nextInt();
            int CH = sc.nextInt();
            int DH = sc.nextInt();

            System.out.println(String.format("Case #%d: %d", tc,
                    solve(N, K, L, AL, BL, CL, DL, W, AW, BW, CW, DW, H, AH, BH, CH, DH)));
        }

        sc.close();
    }

    static int solve(int N, int K, int[] L, int AL, int BL, int CL, int DL, int[] W, int AW, int BW, int CW, int DW,
            int[] H, int AH, int BH, int CH, int DH) {
        generate(K, L, AL, BL, CL, DL);
        generate(K, W, AW, BW, CW, DW);
        generate(K, H, AH, BH, CH, DH);

        NavigableSet<Range> ranges = new TreeSet<>((r1, r2) -> Integer.compare(r1.begin, r2.begin));

        int result = 1;
        int P = 0;
        for (int i = 0; i < N; ++i) {
            Range prev = ranges.floor(new Range(L[i], -1));

            Range next;
            if (prev == null) {
                next = ranges.higher(new Range(-1, -1));
            } else {
                next = ranges.higher(prev);
            }

            if (prev == null || prev.end < L[i]) {
                if (next == null || next.begin > L[i] + W[i]) {
                    P = addMod(P, (H[i] + W[i]) * 2);

                    ranges.add(new Range(L[i], L[i] + W[i]));
                } else {
                    int lastEnd = L[i];
                    while (next != null && next.end <= L[i] + W[i]) {
                        P = addMod(P, (next.begin - lastEnd) * 2);
                        if (lastEnd != L[i]) {
                            P = subtractMod(P, H[i] * 2);
                        }

                        lastEnd = next.end;
                        ranges.remove(next);

                        if (prev == null) {
                            next = ranges.higher(new Range(-1, -1));
                        } else {
                            next = ranges.higher(prev);
                        }
                    }

                    if (next == null || next.begin > L[i] + W[i]) {
                        P = addMod(P, (L[i] + W[i] - lastEnd) * 2);

                        ranges.add(new Range(L[i], L[i] + W[i]));
                    } else {
                        P = addMod(P, (next.begin - lastEnd) * 2);
                        if (lastEnd != L[i]) {
                            P = subtractMod(P, H[i] * 2);
                        }

                        ranges.remove(next);
                        ranges.add(new Range(L[i], next.end));
                    }
                }
            } else if (prev.end < L[i] + W[i]) {
                if (next == null || next.begin > L[i] + W[i]) {
                    P = addMod(P, (L[i] + W[i] - prev.end) * 2);

                    ranges.remove(prev);
                    ranges.add(new Range(prev.begin, L[i] + W[i]));
                } else {
                    int lastEnd = prev.end;
                    while (next != null && next.end <= L[i] + W[i]) {
                        P = addMod(P, (next.begin - lastEnd) * 2);

                        P = subtractMod(P, H[i] * 2);
                        lastEnd = next.end;
                        ranges.remove(next);
                        next = ranges.higher(prev);
                    }

                    if (next == null || next.begin > L[i] + W[i]) {
                        P = addMod(P, (L[i] + W[i] - lastEnd) * 2);

                        ranges.remove(prev);
                        ranges.add(new Range(prev.begin, L[i] + W[i]));
                    } else {
                        P = addMod(P, (next.begin - lastEnd) * 2);
                        P = subtractMod(P, H[i] * 2);

                        ranges.remove(prev);
                        ranges.remove(next);
                        ranges.add(new Range(prev.begin, next.end));
                    }
                }
            }

            result = multiplyMod(result, P);
        }

        return result;
    }

    static void generate(int K, int[] values, int A, int B, int C, int D) {
        for (int i = K; i < values.length; ++i) {
            values[i] = (int) (((long) A * values[i - 2] + (long) B * values[i - 1] + C) % D) + 1;
        }
    }

    static int addMod(int x, int y) {
        return (x + y) % MODULUS;
    }

    static int subtractMod(int x, int y) {
        return ((x - y) % MODULUS + MODULUS) % MODULUS;
    }

    static int multiplyMod(int x, int y) {
        return (int) ((long) x * y % MODULUS);
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