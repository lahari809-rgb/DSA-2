import java.util.*;

public class AVLTree {

    static int rotations = 0;

    static class Node {
        int id, height;
        Node left, right;
        Node(int id) { this.id = id; height = 1; }
    }

    static int height(Node n) { return n == null ? 0 : n.height; }
    static int bf(Node n) { return n == null ? 0 : height(n.left) - height(n.right); }
    static void updateHeight(Node n) { n.height = 1 + Math.max(height(n.left), height(n.right)); }

    static Node rotateLeft(Node x) {
        Node y = x.right;
        x.right = y.left;
        y.left = x;
        updateHeight(x); updateHeight(y);
        rotations++;
        return y;
    }

    static Node rotateRight(Node y) {
        Node x = y.left;
        y.left = x.right;
        x.right = y;
        updateHeight(y); updateHeight(x);
        rotations++;
        return x;
    }

    static Node insert(Node node, int id) {
        if (node == null) return new Node(id);
        if (id < node.id) node.left = insert(node.left, id);
        else node.right = insert(node.right, id);

        updateHeight(node);
        int bal = bf(node);

        if (bal < -1 && id > node.right.id) {
            System.out.println("  [RR] Imbalance at node " + node.id + " BF=" + bal + " -> Left rotation");
            Node newRoot = rotateLeft(node);
            rotations--; // already counted above
            rotations++;
            System.out.println("  New subtree root: " + newRoot.id);
            return newRoot;
        }
        if (bal > 1 && id < node.left.id) {
            System.out.println("  [LL] Imbalance at node " + node.id + " BF=" + bal + " -> Right rotation");
            Node newRoot = rotateRight(node);
            rotations--;
            rotations++;
            System.out.println("  New subtree root: " + newRoot.id);
            return newRoot;
        }
        if (bal > 1 && id > node.left.id) {
            node.left = rotateLeft(node.left);
            return rotateRight(node);
        }
        if (bal < -1 && id < node.right.id) {
            node.right = rotateRight(node.right);
            return rotateLeft(node);
        }
        return node;
    }

    static void inOrder(Node node) {
        if (node == null) return;
        inOrder(node.left);
        System.out.print(node.id + " ");
        inOrder(node.right);
    }

    static void printTree(Node node, String indent) {
        if (node == null) return;
        printTree(node.right, indent + "    ");
        System.out.println(indent + node.id + " (BF=" + bf(node) + ")");
        printTree(node.left, indent + "    ");
    }

    public static void main(String[] args) {
        int[] patients = {50, 70, 90, 60, 80, 100, 110, 120};
        Node root = null;

        System.out.println("------ Insertion Trace ------");
        for (int id : patients) {
            System.out.println("Inserting: " + id);
            root = insert(root, id);
            System.out.println("  Root=" + root.id + " Height=" + root.height + " BF=" + bf(root));
        }

        System.out.println("\n------ AVL Tree (sideways) ------");
        printTree(root, "");

        System.out.print("\nIn-order: ");
        inOrder(root);

        System.out.println("\nTotal rotations: " + rotations);
    }
}