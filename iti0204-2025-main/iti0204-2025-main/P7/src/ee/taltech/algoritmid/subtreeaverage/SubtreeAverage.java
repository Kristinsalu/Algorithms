package ee.taltech.algoritmid.subtreeaverage;

public class SubtreeAverage {

    /**
     * Calculate the avarage value of the node and all of it's children for every node.
     * @param rootNode root node of the tree. Use it to traverse the tree.
     * @return root node of the tree where for every node is computed difference of sums of it's left and right children
     */
    public Node calculateAverage(Node rootNode) {
        computeAverage(rootNode);
        return rootNode;
    }

    private long[] computeAverage(Node node) {
        if (node == null) {
            return new long[]{0, 0};
        }

        // rekursiivselt vasak ja parem
        long[] left = computeAverage(node.getLeft());
        long[] right = computeAverage(node.getRight());

        long sum = left[0] + right[0] + node.getValue();
        long count = left[1] + right[1] + 1;

        // salvestame keskmise otse Node'i sisse
        node.setAverage((double) sum / count);

        // tagastame summad ja loenduse vanemale
        return new long[]{sum, count};
    }

    /**
     *  Use this example to test your solution
     *
     *           10 (13.5)
     *         /    \
     *    8 (6)      20 (19.67)
     *     /           /     \
     *  4 (4)   14 (14)   25 (25)
     */

    public static void main(String[] args) throws Exception {

        Node rootNode = new Node(10);
        Node a = new Node(8);
        Node b = new Node(20);
        Node c = new Node(4);
        Node d = new Node(14);
        Node e = new Node(25);

        rootNode.setLeft(a);
        rootNode.setRight(b);
        a.setLeft(c);
        b.setLeft(d);
        b.setRight(e);

        SubtreeAverage solution = new SubtreeAverage();
        solution.calculateAverage(rootNode);

        if (rootNode.getAverage() != 13.5 ||
                a.getAverage() != 6 ||
                b.getAverage() != ((double) 59 / 3) ||
                c.getAverage() != 4 ||
                d.getAverage() != 14 ||
                e.getAverage() != 25) {
            throw new Exception("There is a mistake in your solution.");
        }

        System.out.println("Your solution should be working fine in basic cases, try to push.");

    }

}