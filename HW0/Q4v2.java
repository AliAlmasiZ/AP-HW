/*
import java.util.*;

*/
/* STID : 403100043 *//*


public class Q4v2 {
    static int[][] isValid;
//    static List<Pos> invalids = new ArrayList<>();

    public static void main(String[] args) {
//        HashMap<Integer, Pos> map = new HashMap<>();
        PriorityQueue<Pair> minHeap = new PriorityQueue<>();
        Scanner sc = new Scanner(System.in);
        int n = sc.nextInt();
        int m = sc.nextInt();
        int[][] table = new int[n][m];
        isValid = new int[n][m];
        for (int i = 0; i < n; i++) {

            for (int j = 0; j < m; j++) {
                isValid[i][j] = -1;
            }
            isValid[i][m - 1] = isValid[i][0] = 0;
//            invalids.add(new Pos(i, 0));
//            invalids.add(new Pos(i, m - 1));
        }
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                table[i][j] = sc.nextInt();
                Pos temp = new Pos(i, j);
//                map.put(table[i][j], temp);
                Pair tempPair = new Pair(temp, table[i][j]);
                minHeap.add(tempPair);
            }
        }
        System.out.println(countHole(minHeap, table));

    }

    public static void makeInvalid(int[][] table, Pos pos) {
        int row = pos.getRow();
        int col = pos.getCol();
        if (row + 1 < table.length && table[row + 1][col] <= table[row][col]) {
            isValid[row + 1][col] = 0;
//            invalids.add(new Pos(row + 1, col));
        }
        if (col + 1 < table.length && table[row][col + 1] <= table[row][col]) {
            isValid[row][col + 1] = 0;
//            invalids.add(new Pos(row, col + 1));
        }
        if (row - 1 >= 0 && table[row - 1][col] <= table[row][col]) {
            isValid[row - 1][col] = 0;
//            invalids.add(new Pos(row - 1, col));
        }
        if (col - 1 >= 0 && table[row][col - 1] <= table[row][col]) {
            isValid[row][col - 1] = 0;
//            invalids.add(new Pos(row, col - 1));
        }
    }

//    public static void testValid(int[][] table) {
//        for (int i = 0; i < invalids.size(); i++) {
//            makeInvalid(table, invalids.get(i));
//        }
//    }

    public static int checkPos(Pos pos, int[][] table) {
        int row = pos.getRow();
        int col = pos.getCol();
        if (isValid[row][col] == 0) {
            return 0;
        }
        if (row < 1 || row >= table.length - 1 || col < 1 || col >= table[0].length - 1) {
            isValid[row][col] = 0;
//            invalids.add(new Pos(row, col));
            return 0;
        }


        if (table[row][col] > table[row + 1][col] || table[row][col] > table[row][col + 1] ||
                table[row][col] > table[row - 1][col] || table[row][col] > table[row][col - 1]) {
            isValid[row][col] = 0;
//            invalids.add(new Pos(row, col));
            return 0;
        }

        if (table[row][col] >= table[row + 1][col] || table[row][col] >= table[row][col + 1] ||
                table[row][col] >= table[row - 1][col] || table[row][col] >= table[row][col - 1]) {
            if (table[row][col] == table[row][col + 1] && checkPos(new Pos(row, col + 1), table) == 0) {
                isValid[row][col] = 0;
//                invalids.add(new Pos(row, col));
                return 0;
            }
            if (table[row][col] == table[row + 1][col] && checkPos(new Pos(row + 1, col), table) == 0) {
                isValid[row][col] = 0;
//                invalids.add(new Pos(row, col));
                return 0;
            }
            if (table[row][col] == table[row][col - 1]) {
                isValid[row][col] = 0;
                if (isValid[row][col] == -1) {
                    checkPos(new Pos(row, col + 1), table);

                }
                if (isValid[row][col - 1] == 0) {
                    isValid[row][col] = 0;
//                    invalids.add(new Pos(row, col));
                    return 0;
                }
            }
            if (table[row][col] == table[row - 1][col]) {
                isValid[row][col] = 2;

                if (isValid[row - 1][col] == -1) {
                    checkPos(new Pos(row - 1, col), table);
                }
                if (isValid[row - 1][col] == 0) {
                    isValid[row][col] = 0;
//                    invalids.add(new Pos(row, col));
                    return 0;
                }
            }
        }
        isValid[row][col] = 1;
        return 1;

    }


    public static int countHole(PriorityQueue<Pair> pq, int[][] table) {
//        testValid(table);
        int res = 0;
        int row, col;
        while (!pq.isEmpty()) {
            Pair tempPair = pq.poll();
            Pos tempPos = tempPair.position;
            row = tempPos.getRow();
            col = tempPos.getCol();
            if (checkPos(tempPos, table) == 1) {
                res++;
                table[tempPos.getRow()][tempPos.getCol()]++;
                tempPair.value = table[tempPos.getRow()][tempPos.getCol()];
                pq.add(tempPair);
            }
        }
        return res;
    }
}

*/
/*class Pos {
    int row;
    int col;

    public Pos(int row, int col) {
        this.row = row;
        this.col = col;
    }

    public int getRow() {
        return this.row;
    }

    public int getCol() {
        return this.col;
    }
}*//*


*/
/*class Pair implements Comparable<Pair> {
    Pos position;
    int value;
    int valid = -1;

    public Pair(Pos position, int value) {
        this.position = position;
        this.value = value;
    }

    @Override
    public int compareTo(Pair other) {
        if (this.value == other.value) {
            if (this.position.row == other.position.row) {
                return this.position.col - other.position.col;
            }
            return Integer.compare(this.position.row, other.position.row);
        }
        return Integer.compare(this.value, other.value);
    }

}*//*


*/
