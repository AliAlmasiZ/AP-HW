import javax.swing.*;
import java.util.*;
import java.util.regex.*;
import java.util.stream.Collectors;

public class Test {

    static int[][] table;
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int row = sc.nextInt();
        int col = sc.nextInt();
        table = new int[row][col];
        for (int i = 0; i < row; i++) {
            for (int j = 0; j < col; j++) {
                table[i][j] = sc.nextInt();
            }
        }
    }
}