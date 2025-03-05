//import java.util.*;
//
///* STID : 403100043 */
//
//public class Q4v3 {
//    static int[][] isValid;
//
//    public static void main(String[] args) {
//        PriorityQueue<Pair> minHeap = new PriorityQueue<>();
//        Scanner sc = new Scanner(System.in);
//        int n = sc.nextInt();
//        int m = sc.nextInt();
//
//        int[][] table = new int[n][m];
//        isValid = new int[n][m];
//        for (int i = 0; i < n; i++) {
//            Arrays.fill(isValid[i], -1);
//            isValid[i][0] = 0;
//            isValid[i][m - 1] = 0;
//        }
//        for (int i = 0; i < m; i++) {
//            isValid[0][i] = 0;
//            isValid[n - 1][i] = 0;
//        }
//
//        for (int i = 0; i < n; i++) {
//            for (int j = 0; j < m; j++) {
//                table[i][j] = sc.nextInt();
//                Pos temp = new Pos(i, j);
//                Pair tempPair = new Pair(temp, table[i][j]);
//                minHeap.add(tempPair);
//            }
//        }
//
//        System.out.println(countHole(minHeap, table));
//
//
//    }
//
//
//    public static int checkPos(int[][] table, int row, int col) {
////        if(isValid[row][col] != -1) return isValid[row][col];
//        if (isValid[row][col] == 0) return 0;
//        if (isValid[row][col] == 1 || isValid[row][col] == 2) return 1;
//        isValid[row][col] = 2;
//
//       /* if (row < 1 || row >= table.length - 1 || col < 1 || col >= table[0].length - 1) {
//            isValid[row][col] = 0;
//            return 0;
//        }*/
//
//        int minNeighbor = Math.min(
//                Math.min(table[row + 1][col], table[row - 1][col]),
//                Math.min(table[row][col + 1], table[row][col - 1])
//        );
//
//        if (table[row][col] > minNeighbor) {
//            isValid[row][col] = 0;
//            return 0;
//        }
//
//        if (minNeighbor == table[row][col]) {
//            if (table[row][col] == table[row][col + 1] && isValid[row][col + 1] == 0) {
//                isValid[row][col] = 0;
//                return 0;
//            }
//            if (table[row][col] == table[row + 1][col] && isValid[row + 1][col] == 0) {
//                isValid[row][col] = 0;
//                return 0;
//            }
//            if (table[row][col] == table[row][col - 1] && isValid[row][col - 1] == 0) {
//                isValid[row][col] = 0;
//                return 0;
//            }
//            if (table[row][col] == table[row - 1][col] && isValid[row - 1][col] == 0) {
//                isValid[row][col] = 0;
//                return 0;
//            }
//            if (table[row][col] == table[row][col + 1] && checkPos(table, row, col + 1) == 0) {
//                isValid[row][col] = 0;
//                makeNearReset(row, col);
//                return 0;
//            }
//            if (table[row][col] == table[row + 1][col] && checkPos(table, row + 1, col) == 0) {
//                isValid[row][col] = 0;
//                makeNearReset(row, col);
//                return 0;
//            }
//            if (table[row][col] == table[row][col - 1] && checkPos(table, row, col - 1) == 0) {
//                isValid[row][col] = 0;
//                makeNearReset(row, col);
//                return 0;
//            }
//            if (table[row][col] == table[row - 1][col] && checkPos(table, row - 1, col) == 0) {
//                isValid[row][col] = 0;
//                makeNearReset(row, col);
//                return 0;
//            }
//        }
//        isValid[row][col] = 1;
////        makeNearReset(row, col);
//        return Math.max(minNeighbor - table[row][col], 1);
//
//    }
//
//
//    public static int countHole(PriorityQueue<Pair> pq, int[][] table) {
//        int res = 0;
//        int row, col;
//        while (!pq.isEmpty()) {
//            Pair tempPair = pq.poll();
//            Pos tempPos = tempPair.position;
//            row = tempPos.row;
//            col = tempPos.col;
//            int test = checkPos(table, row, col);
//            if (test == 0) {
//                makeNearReset(row, col);
//            }
//            if (test != 0) {
//
//                res += test;
//                table[row][col] += test;
//                tempPair.value = table[row][col];
//                isValid[row][col] = -1;
//                makeNearReset(row, col);
//
//                pq.add(tempPair);
//            }
//        }
//        return res;
//    }
//
//    public static void makeNearReset(int row, int col) {
//        int[][] directions = new int[][]{{-1, 0}, {1, 0}, {0, -1}, {0, 1}};
//        for (int[] dir : directions) {
//            if (row + dir[0] >= 0 && row + dir[0] < isValid.length && col + dir[1] >= 0 && col + dir[1] < isValid[0].length)
//                isValid[row + dir[0]][col + dir[1]] = isValid[row + dir[0]][col + dir[1]] == 0 ? 0 : -1;
//        }
//    }
//}
//
//class Pos {
//    int row;
//    int col;
//
//    public Pos(int row, int col) {
//        this.row = row;
//        this.col = col;
//    }
//
////    public int getRow() {
////        return this.row;
////    }
////
////    public int getCol() {
////        return this.col;
////    }
//}
//
//class Pair implements Comparable<Pair> {
//    Pos position;
//    int value;
//
//    public Pair(Pos position, int value) {
//        this.position = position;
//        this.value = value;
//    }
//
//    @Override
//    public int compareTo(Pair other) {
//        if (this.value == other.value) {
//            if (this.position.row == other.position.row) {
//                return this.position.col - other.position.col;
//            }
//            return Integer.compare(this.position.row, other.position.row);
//        }
//        return Integer.compare(this.value, other.value);
//    }
//
//}
//
