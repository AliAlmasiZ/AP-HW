import java.util.*;
/* STDID : 403100043*/
public class HW0_Q2 {
    static List<Vertex> vertices = new ArrayList<>();

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int N = sc.nextInt();
        for (int i = 0; i < N; i++) {
            vertices.add(new Vertex());
        }
        int M = sc.nextInt();
        for (int i = 0; i < M; i++) {
            int v1, v2;
            v1 = sc.nextInt();
            v2 = sc.nextInt();
            vertices.get(v1).neighbors.add(vertices.get(v2));
            vertices.get(v2).neighbors.add(vertices.get(v1));
        }
        if (colorGraph(N)) {
            System.out.println("Yes");
        }
        else {
            System.out.println("No");
        }

    }

    private static boolean colorGraph(int N) {
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < vertices.get(i).neighbors.size(); j++) {
                if(vertices.get(i).color == vertices.get(i).neighbors.get(j).color && vertices.get(i).color != -1) return false;
                if (vertices.get(i).color != 1) {
                    vertices.get(i).neighbors.get(j).color = 1;
                    vertices.get(i).color = 0;
                } else if (vertices.get(i).color == 1) {
                    vertices.get(i).neighbors.get(j).color = 0;
                }
            }
        }
        return true;
    }

    private static class Vertex {
        List<Vertex> neighbors = new ArrayList<Vertex>();
        int color = -1;
    }
}

