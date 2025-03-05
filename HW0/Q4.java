import java.util.*;

public class Q4 {
    static int[][] isValid;

    public static void main(String[] args) {
        PriorityQueue<Pair> minHeap = new PriorityQueue<>();
        Scanner sc = new Scanner(System.in);
        int n = sc.nextInt();
        int m = sc.nextInt();

        int[][] table = new int[n][m];
        isValid = new int[n][m];

        for (int[] row : isValid) Arrays.fill(row, -1);

        for (int i = 0; i < n; i++) {
            isValid[i][0] = 0;
            isValid[i][m - 1] = 0;
        }
        for (int i = 0; i < m; i++) {
            isValid[0][i] = 0;
            isValid[n - 1][i] = 0;
        }

        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                table[i][j] = sc.nextInt();
                minHeap.add(new Pair(new Pos(i, j), table[i][j]));
            }
        }

        System.out.println(countHole(minHeap, table));
    }

    public static int checkPos(int[][] table, int row, int col) {
        if (isValid[row][col] != -1) return isValid[row][col];

        isValid[row][col] = 2;
        int n = table.length, m = table[0].length;

        int minNeighbor = Integer.MAX_VALUE;
        int[][] directions = {{-1, 0}, {1, 0}, {0, -1}, {0, 1}};

        for (int[] dir : directions) {
            int newRow = row + dir[0], newCol = col + dir[1];
            if (newRow >= 0 && newRow < n && newCol >= 0 && newCol < m) {
                minNeighbor = Math.min(minNeighbor, table[newRow][newCol]);
                if (table[row][col] == table[newRow][newCol] && checkPos(table, newRow, newCol) == 0) {
                    isValid[row][col] = 0;
                    return 0;
                }
            }
        }

        if (table[row][col] > minNeighbor) {
            isValid[row][col] = 0;
            return 0;
        }

        isValid[row][col] = 1;
        return Math.max(minNeighbor - table[row][col], 1);
    }

    public static int countHole(PriorityQueue<Pair> pq, int[][] table) {
        int res = 0;

        while (!pq.isEmpty()) {
            Pair tempPair = pq.poll();
            Pos tempPos = tempPair.position;
            int row = tempPos.row, col = tempPos.col;

            int test = checkPos(table, row, col);
            if (test > 0) {
                res += test;
                table[row][col] += test;
                isValid[row][col] = -1;
                pq.add(new Pair(new Pos(row, col), table[row][col]));
            }
        }
        return res;
    }

    static class Pos {
        int row, col;

        Pos(int row, int col) {
            this.row = row;
            this.col = col;
        }
    }

    static class Pair implements Comparable<Pair> {
        Pos position;
        int value;

        Pair(Pos position, int value) {
            this.position = position;
            this.value = value;
        }

        @Override
        public int compareTo(Pair other) {
            if (this.value == other.value) {
                if (this.position.row == other.position.row) {
                    return Integer.compare(this.position.col, other.position.col);
                }
                return Integer.compare(this.position.row, other.position.row);
            }
            return Integer.compare(this.value, other.value);
        }

        @Override
        public boolean equals(Object obj) {
            if (this == obj) return true;
            if (!(obj instanceof Pair)) return false;
            Pair other = (Pair) obj;
            return this.position.row == other.position.row && this.position.col == other.position.col;
        }

        @Override
        public int hashCode() {
            return Objects.hash(position.row, position.col);
        }
    }
}
