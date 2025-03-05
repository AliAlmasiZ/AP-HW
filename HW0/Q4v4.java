import javax.swing.*;
import java.util.*;

/* STDID : 403100043 */

public class Q4v4 {
    static boolean[][] isVisited;

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int N = sc.nextInt();
        int M = sc.nextInt();
        int[][] table = new int[N][M];
        isVisited = new boolean[N][M];
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < M; j++) {
                table[i][j] = sc.nextInt();
            }
        }
        sc.close();
        PriorityQueue<Pair> pq = new PriorityQueue<>();

        for (int i = 0; i < N; i++) {
            Arrays.fill(isVisited[i], false);
            pq.offer(new Pair(new Pos(i, 0), table[i][0]));
            pq.offer(new Pair(new Pos(i, M - 1), table[i][M - 1]));
            isVisited[i][M - 1] = true;
            isVisited[i][0] = true;
        }

        for (int i = 0; i < M; i++) {
            pq.offer(new Pair(new Pos(0, i), table[0][i]));
            pq.offer(new Pair(new Pos(N - 1, i), table[N - 1][i]));
            isVisited[N - 1][i] = true;
            isVisited[0][i] = true;
        }

        System.out.println(countHole(pq, table));

    }

    public static int countHole(PriorityQueue<Pair> pq, int[][] table) {
        /* counts the answer from out of table to inside of that from minimum number */
        int count = 0;
        int[][] directions = new int[][]{{-1, 0}, {1, 0}, {0, -1}, {0, 1}};
        while (!pq.isEmpty()) {
            Pair tempPair = pq.poll();
            for (int[] dir : directions) {
                /* for all neighbors checks if that neighbor can fill with water and add water to it maximum */
                int newRow = tempPair.position.row + dir[0];
                int newCol = tempPair.position.col + dir[1];
                if (newRow >= 0 && newRow < table.length && newCol >= 0 &&
                        newCol < table[0].length && !isVisited[newRow][newCol]) {
                    isVisited[newRow][newCol] = true;
                    if (tempPair.value > table[newRow][newCol]) {
                        count += tempPair.value - table[newRow][newCol];
                        table[newRow][newCol] = tempPair.value;
                    }
                    Pair newPair = new Pair(new Pos(newRow, newCol), table[newRow][newCol]);
                    pq.offer(newPair);
                }
            }
        }
        return count;
    }


}

class Pos {
    int row;
    int col;

    public Pos(int row, int col) {
        this.row = row;
        this.col = col;
    }
}

class Pair implements Comparable<Pair> {
    Pos position;
    int value;

    public Pair(Pos position, int value) {
        this.position = position;
        this.value = value;
    }

    /* override compare function for sort in priority queue */
    @Override
    public int compareTo(Pair other) {
        /* compare by value and if equals compare by row and cols*/
        if (this.value == other.value) {
            if (this.position.row == other.position.row) {
                return this.position.col - other.position.col;
            }
            return Integer.compare(this.position.row, other.position.row);
        }
        return Integer.compare(this.value, other.value);
    }

}