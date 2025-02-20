import java.util.*;

public class HW0_Q2 {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int n = sc.nextInt();
        sc.nextLine();
        char[][] table = new char[n][n];
        for (int i = 0; i < n; i++) {
//            print(i);
            String word = sc.nextLine();
//            print(word);
            table[i] = word.toCharArray();
        }
        int m = sc.nextInt();
        sc.nextLine();
        for (int i = 0; i < m; i++) {
            String word = sc.nextLine();
            if(isMatch(word, table)) System.out.println("FOUND");
            else System.out.println("NOT FOUND");
        }
    }

    private static boolean isMatch(String target, char[][] table) {
        for (int i = 0; i < table.length * table.length; i++) {
            int row = i / table.length;
            int col = i % table.length;
            if (target.charAt(0) == table[row][col]) {
                if (isMatchHorizontally(target, table, row, col)|| isMatchVertically(target, table, row, col)) {
                    return true;
                }
            }
        }
        return false;
    }
    private static boolean isMatchAll(String target, char[][] table, int row, int col) {
        return isMatchHorizontally(target, table, row, col) || isMatchVertically(target, table, row, col);
    }
    private static boolean isMatchVertically(String target, char[][] table, int row, int col) {
        char[] targetChar = target.toCharArray();
        StringBuilder sbUp = new StringBuilder();
        StringBuilder sbDown = new StringBuilder();
        /* col checker */
        for (int i = 0; i < targetChar.length; i++) {
            if (row + targetChar.length - 1 >= table.length) break;
            sbDown.append(table[row + i][col]);
        }
        for (int i = 0; i < targetChar.length; i++) {
            if (row - targetChar.length + 1 < 0) break;
            sbUp.append(table[row - i][col]);
        }
        return target.contentEquals(sbUp) || target.contentEquals(sbDown);
    }

    private static boolean isMatchHorizontally(String target, char[][] table, int row, int col) {
        char[] targetChar = target.toCharArray();
        StringBuilder sbLeft = new StringBuilder();
        StringBuilder sbRight = new StringBuilder();
        /* row checker */
        for (int i = 0; i < targetChar.length; i++) {
            if (col + targetChar.length - 1 >= table.length) break;
            sbRight.append(table[row][col + i]);
        }

        for (int i = 0; i < targetChar.length; i++) {
            if (col - targetChar.length + 1 < 0) break;
            sbLeft.append(table[row][col - i]);
        }
        return target.contentEquals(sbRight) || target.contentEquals(sbLeft);
    }
    private static void print(String s) {
        System.out.println("-" + s + "-");
    }
    private static void print(int s) {
        System.out.println("-" + s + "-");
    }

}