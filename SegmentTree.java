public class SegmentTree {

    static int[] tree;
    static int n;

    static void build(int[] arr, int node, int start, int end) {
        if (start == end) {
            tree[node] = arr[start];
        } else {
            int mid = (start + end) / 2;
            build(arr, 2*node, start, mid);
            build(arr, 2*node+1, mid+1, end);
            tree[node] = tree[2*node] + tree[2*node+1];
        }
    }

    static void update(int node, int start, int end, int idx, int val) {
        if (start == end) {
            tree[node] = val;
        } else {
            int mid = (start + end) / 2;
            if (idx <= mid) update(2*node, start, mid, idx, val);
            else update(2*node+1, mid+1, end, idx, val);
            tree[node] = tree[2*node] + tree[2*node+1];
        }
    }

    static int query(int node, int start, int end, int l, int r) {
        if (r < start || end < l) return 0;
        if (l <= start && end <= r) return tree[node];
        int mid = (start + end) / 2;
        return query(2*node, start, mid, l, r) +
               query(2*node+1, mid+1, end, l, r);
    }

    public static void main(String[] args) {
        int[] arr = {120, 340, 85, 670, 230, 510, 95, 430};
        n = arr.length;
        tree = new int[4 * n];

        System.out.print("Input Array:\n");
        for (int x : arr) System.out.print(x + " ");
        System.out.println();

        build(arr, 1, 0, n-1);

        System.out.println("\n--- Segment Tree Built (Range-Sum) ---");
        System.out.println("Level 0 (root): " + tree[1]);
        System.out.println("Level 1      : " + tree[2] + " | " + tree[3]);
        System.out.println("Level 2      : " + tree[4] + " | " + tree[5] + " | " + tree[6] + " | " + tree[7]);
        System.out.println("Leaves       : " + tree[8] + " | " + tree[9] + " | " + tree[10] + " | " + tree[11]
                         + " | " + tree[12] + " | " + tree[13] + " | " + tree[14] + " | " + tree[15]);

        System.out.println("\n--- Range Queries ---");
        System.out.println("query(0,3) = " + query(1, 0, n-1, 0, 3));
        System.out.println("query(2,5) = " + query(1, 0, n-1, 2, 5));
        System.out.println("query(4,7) = " + query(1, 0, n-1, 4, 7));

        System.out.println("\n--- Point Update ---");
        System.out.println("Updating index 3 from 670 to 890");
        update(1, 0, n-1, 3, 890);
        System.out.println("query(0,7) after update = " + query(1, 0, n-1, 0, 7));
        System.out.println("query(2,5) after update = " + query(1, 0, n-1, 2, 5));
    }
}