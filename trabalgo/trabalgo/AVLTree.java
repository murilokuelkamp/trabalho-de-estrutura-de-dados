import java.util.*;

public class AVLTree {
    static class Node {
        int key, height;
        Node left, right;
        Node(int k) { key = k; height = 1; }
    }

    Node root;

    int height(Node n) { return n == null ? 0 : n.height; }

    int balance(Node n) { return n == null ? 0 : height(n.left) - height(n.right); }

    void update(Node n) { n.height = 1 + Math.max(height(n.left), height(n.right)); }

    Node rotateRight(Node y) {
        Node x = y.left;
        Node T2 = x.right;
        x.right = y;
        y.left = T2;
        update(y); update(x);
        return x;
    }

    Node rotateLeft(Node x) {
        Node y = x.right;
        Node T2 = y.left;
        y.left = x;
        x.right = T2;
        update(x); update(y);
        return y;
    }

    Node insert(Node node, int key) {
        if (node == null) return new Node(key);
        if (key < node.key) node.left = insert(node.left, key);
        else if (key > node.key) node.right = insert(node.right, key);
        else return node; // ignora duplicatas

        update(node);
        int bf = balance(node);

        // LL
        if (bf > 1 && key < node.left.key) return rotateRight(node);
        // RR
        if (bf < -1 && key > node.right.key) return rotateLeft(node);
        // LR
        if (bf > 1 && key > node.left.key) {
            node.left = rotateLeft(node.left);
            return rotateRight(node);
        }
        // RL
        if (bf < -1 && key < node.right.key) {
            node.right = rotateRight(node.right);
            return rotateLeft(node);
        }
        return node;
    }

    void insert(int key) { root = insert(root, key); }

    List<Integer> inorder() {
        List<Integer> res = new ArrayList<>();
        inorder(root, res);
        return res;
    }

    void inorder(Node n, List<Integer> res) {
        if (n == null) return;
        inorder(n.left, res);
        res.add(n.key);
        inorder(n.right, res);
    }

    // Impressao por niveis sem enfileirar nulls
    void printLevels() {
        if (root == null) return;
        Queue<Node> q = new LinkedList<>();
        q.add(root);
        while (!q.isEmpty()) {
            int sz = q.size();
            StringBuilder line = new StringBuilder();
            boolean anyNext = false;
            for (int i = 0; i < sz; i++) {
                Node n = q.poll();
                if (n == null) continue;
                line.append(n.key).append(" ");
                if (n.left != null)  { q.add(n.left);  anyNext = true; }
                if (n.right != null) { q.add(n.right); anyNext = true; }
            }
            System.out.println(line.toString().trim());
            if (!anyNext) break;
        }
    }

    static AVLTree build(int... arr) {
        AVLTree t = new AVLTree();
        for (int x : arr) t.insert(x);
        return t;
    }

    public static void main(String[] args) {
        int[] A = {40, 20, 60, 10, 30, 25};
        int[] B = {60, 40, 80, 35, 50, 90, 20, 38, 37};
        int[] C = {30, 20, 10, 25, 40, 50, 5, 35, 45};

        System.out.println("Sequencia A: " + Arrays.toString(A));
        AVLTree tA = build(A);
        tA.printLevels();
        System.out.println("Inordem: " + tA.inorder());
        System.out.println();

        System.out.println("Sequencia B: " + Arrays.toString(B));
        AVLTree tB = build(B);
        tB.printLevels();
        System.out.println("Inordem: " + tB.inorder());
        System.out.println();

        System.out.println("Sequencia C: " + Arrays.toString(C));
        AVLTree tC = build(C);
        tC.printLevels();
        System.out.println("Inordem: " + tC.inorder());
    }
}
