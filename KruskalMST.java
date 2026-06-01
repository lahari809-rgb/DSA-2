import java.util.*;

public class KruskalMST {

    static int[] parent, rank;

    static int find(int x) {
        if (parent[x] != x) parent[x] = find(parent[x]);
        return parent[x];
    }

    static boolean union(int x, int y) {
        int px = find(x), py = find(y);
        if (px == py) return false;
        if (rank[px] < rank[py]) parent[px] = py;
        else if (rank[px] > rank[py]) parent[py] = px;
        else { parent[py] = px; rank[px]++; }
        return true;
    }

    public static void main(String[] args) {
        String[] nodes = {"A", "B", "C", "D", "E", "F"};
        Map<String, Integer> idx = new HashMap<>();
        for (int i = 0; i < nodes.length; i++) idx.put(nodes[i], i);

        int[][] edges = {
            {0,1,4}, {0,2,2}, {1,2,1}, {1,3,5},
            {2,3,8}, {2,4,10},{3,4,2}, {3,5,6}, {4,5,3}
        };

        System.out.println("Input Graph (Branch Connections)");
        System.out.println();
        for (int[] e : edges)
            System.out.println(nodes[e[0]] + " - " + nodes[e[1]] + " : " + e[2]);

        // Sort by weight
        Arrays.sort(edges, (a, b) -> a[2] - b[2]);

        System.out.println("\n--- Edges Sorted By Weight ---\n");
        for (int[] e : edges)
            System.out.println(nodes[e[0]] + " - " + nodes[e[1]] + " : " + e[2]);

        // Kruskal
        parent = new int[nodes.length];
        rank = new int[nodes.length];
        for (int i = 0; i < nodes.length; i++) parent[i] = i;

        System.out.println("\n--- Kruskal MST Construction ---\n");

        List<int[]> mst = new ArrayList<>();
        int totalCost = 0;

        for (int[] e : edges) {
            if (union(e[0], e[1])) {
                System.out.println("Selected: " + nodes[e[0]] + " - " + nodes[e[1]] + " (" + e[2] + ")");
                mst.add(e);
                totalCost += e[2];
            } else {
                System.out.println("Skipped : " + nodes[e[0]] + " - " + nodes[e[1]] + " (Cycle Detected)");
            }
        }

        System.out.println("\n--- Minimum Spanning Tree ---\n");
        for (int[] e : mst)
            System.out.println(nodes[e[0]] + " - " + nodes[e[1]] + " : " + e[2]);

        System.out.println("\nTotal Minimum Cost = " + totalCost);
    }
}