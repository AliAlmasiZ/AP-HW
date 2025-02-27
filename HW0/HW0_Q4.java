import javax.swing.*;
import java.util.*;


public class HW0_Q4 {
    static int result = 0, size = 0;
    static int[][] holeIndex;
    static int[][] isChecked;
    static List<Hole> holeList = new ArrayList<>();

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int n = sc.nextInt();
        int m = sc.nextInt();
        holeIndex = new int[n][m];
        isChecked = new int[n][m];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                holeIndex[i][j] = -1;
                isChecked[i][j] = 0;
            }
        }
        int[][] table = new int[n][m];

        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                table[i][j] = sc.nextInt();
            }
        }
//        System.out.println("==========0==========");
//        printTable(table);
//        System.out.println("====================");

        sc.close();
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                if(isChecked[i][j] == 0) {

                    if(makeHole(table, i, j, size)) size++;

                }
            }
        }
//        System.out.println("=========1===========");
//        printTable(table);
//        System.out.println("====================");

        setMinHeight(table);
//
//        System.out.println("==========2==========");
//        printTable(table);
//        System.out.println("====================");
        result = 0;
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
//                System.out.println(holeIndex[i][j]);
                if (holeIndex[i][j] != -1) {
//                    System.out.print(i);
//                    System.out.print(" ");
//                    System.out.print(j);
//                    System.out.println(" ");
                    System.out.println("------>");
                    System.out.println("(" + i + ", " + j + ")");
                    System.out.println(holeList.get(holeIndex[i][j]).minHeight);
                    System.out.println(table[i][j]);
                    System.out.println("<--------");

                    if (holeList.get(holeIndex[i][j]).minHeight < table[i][j] || holeList.get(holeIndex[i][j]).minHeight == Integer.MAX_VALUE) continue;
                    System.out.println("Added!");
                    result += holeList.get(holeIndex[i][j]).minHeight - table[i][j];
//                    System.out.println(result);
                }
            }
        }
        System.out.println("==============3======");
        printTable(isChecked);
        System.out.println("====================");
        System.out.println(result);
    }

    private static boolean makeHole(int[][] table, int row, int col, int index) {
        if (isChecked[row][col] == 1) return true;
        if(isChecked[row][col] == 2) return false;
        if (row == 0 || col == 0 || row == table.length - 1 || col == table[0].length - 1)  {
            isChecked[row][col] = 2;
            return false;
        }
        if (table[row][col + 1] < table[row][col]) {
            if (!makeHole(table, row, col + 1, index)) {
                isChecked[row][col] = 2;
                return false;
            }
        }
        if (table[row][col - 1] <= table[row][col]) {
            if (!makeHole(table, row, col - 1, index))  {
                isChecked[row][col] = 2;
                return false;
            }
        }
        if (table[row + 1][col] <= table[row][col]) {
            if (!makeHole(table, row + 1, col, index)) {
                isChecked[row][col] = 2;
                return false;
            }
        }
        if (table[row - 1][col] < table[row][col]) {
            if (!makeHole(table, row - 1, col, index)) {
                isChecked[row][col] = 2;
                return false;
            }
        }
        if(isChecked[row + 1][col] != 2 && isChecked[row - 1][col] != 2 && isChecked[row][col + 1] != 2 && isChecked[row][col - 1] != 2) {
            result++;
//            table[row][col]++;
        }
//        int min = Integer.MAX_VALUE;
//        if(isChecked[row + 1][col] == 2) min = Math.min(table[row + 1][col], min);
//        if(isChecked[row - 1][col] == 2) min = Math.min(table[row - 1][col], min);
//        if(isChecked[row][col + 1] == 2) min = Math.min(table[row][col + 1], min);
//        if(isChecked[row][col - 1] == 2) min = Math.min(table[row][col - 1], min);
//        if(min == Integer.MAX_VALUE) {
//            makeHole(table, row + 1, col, index);
//        }
        if(holeList.size() <= size) {
            holeList.add(new Hole());
//            holeList.get(index).minHeight = min;
        }
//        if(holeList.get(index).minHeight <= table[row][col] && holeList.get(index).minHeight != 0) return false;
//        if(holeList.get(index).minHeight <= table[row][col]) {
//            isChecked[row][col] = 2;
//            return false;
//        }
//        if(holeList.get(index).minHeight == 0) holeList.get(index).minHeight = min;
//        holeList.get(index).minHeight = Math.min(holeList.get(index).minHeight, min);
        Pos pos = new Pos();
        pos.x = col;
        pos.y = row;
        holeList.get(index).posList.add(pos);
        isChecked[row][col] = 1;
        holeIndex[row][col] = index;
        return true;
    }

    public static void setMinHeight(int[][] table) {
        for (Hole hole : holeList) {
            int minHeight = Integer.MAX_VALUE;
            for (int j = 0; j < hole.posList.size(); j++) {
                minHeight = Math.min(minHeight, getMinNear(table, hole.posList.get(j)));
            }
            hole.minHeight = minHeight;
        }
    }

    public static int getMinNear(int[][] table, Pos pos) {
        int min = Integer.MAX_VALUE;
        int[][] d = {{1, 0}, {-1, 0}, {0, 1}, {0, -1}};
        for (int[] ints : d) {
            if (isChecked[pos.y + ints[0]][pos.x + ints[1]] == 1) continue;
            min = Math.min(min, table[pos.y + ints[0]][pos.x + ints[1]]);
        }
        return min;
    }

    public static class Hole {
        int minHeight = 0;
        List<Pos> posList = new ArrayList<>();
    }

    public static class Pos {
        int x;
        int y;
    }

    public static void printTable(int[][] table) {
        for(int i = 0; i < table.length; i++) {
            for(int j = 0; j < table[i].length; j++) {
                System.out.print(table[i][j] + " ");
            }
            System.out.println();
        }
    }
}
/*5 7
5 5 5 5 5 5 5
5 1 2 1 2 1 5
5 2 5 4 5 2 5
5 1 2 1 2 1 5
5 5 5 5 5 5 5
*/


/*

4 + 3 + 4 + 3 + 4 = 18
3 + 1 + 3 = 7
4 + 3 + 4 + 3 + 4 = 18

 */

/*
7  7
6 6 6 6 6 6 6
6 1 1 1 1 1 6
6 1 8 8 8 1 6
6 1 8 2 8 1 6
6 1 8 8 8 1 6
6 1 1 1 1 1 6
6 6 6 6 6 6 6
86
 */