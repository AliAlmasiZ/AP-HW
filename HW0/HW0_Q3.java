import java.util.*;

/* STDID : 403100043 */
public class HW0_Q3 {
    /* directions */
    static int[] dx = {-1, 0, 1, 1, 1, 0, -1, -1};
    static int[] dy = {1, 1, 1, 0, -1, -1, -1, 0};

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int n = sc.nextInt();
        sc.nextLine();
        char[][] table = new char[n][n];
        for (int i = 0; i < n; i++) {
            /* put inputs in a table of characters */
            String word = sc.nextLine();
            table[i] = word.toCharArray();

        }
        int m = sc.nextInt();
        sc.nextLine();
        for (int i = 0; i < m; i++) {
            String word = sc.nextLine();
            if (isMatch(word, table)) System.out.println("FOUND");
            else System.out.println("NOT FOUND");
        }
        sc.close();
    }

    private static boolean isMatch(String target, char[][] table) {
        for (int i = 0; i < table.length * table.length; i++) {
            int row = i / table.length;
            int col = i % table.length;
            if (target.charAt(0) == table[row][col]) {
                if (finder(target, table, row, col, 1)) return true;
            }
        }
        return false;
    }
    /* bactrack to find the word */
    private static boolean finder(String target, char[][] table, int row, int col, int index) {
        char[][] tableCopy = new char[table.length][];
        copy(tableCopy, table);
        tableCopy[row][col] = 0;
        if (index >= target.length()) return true;
        char[] targetChar = target.toCharArray();
        for (int direction = 0; direction < 8; direction++) {
            /* for all 8 directions recursively checks if there is same character at index and that neighbor or not*/
            if (row + dy[direction] < 0 ||
                    row + dy[direction] >= table.length ||
                    col + dx[direction] < 0 ||
                    col + dx[direction] >= table.length) continue;

            if (targetChar[index] == table[row + dy[direction]][col + dx[direction]]) {
                tableCopy[row + dy[direction]][col + dx[direction]] = '\0'; // to dont count a letter twice
                if (finder(target, tableCopy, row + dy[direction], col + dx[direction], index + 1)) {
                    return true;
                }
                copy(tableCopy, table);
            }
        }
        return false;
    }
    private static void copy(char[][] tableCopy, char[][] table) {
        for (int i = 0; i < table.length; i++) {
            tableCopy[i] = table[i].clone();
        }
    }
}