package ee.taltech.algoritmid.subtreeaverage;

/**
 * Please don't change this class, it is only for your reference (it will be overridden by the tester anyway)
 */
public class Node {

    private Node left = null;
    private Node right = null;
    private long value;
    private double average = 0;
    private long numOfAllChildren = 0;

    public Node(long value) {
        this.value = value;
    }

    public Node getLeft() {
        return left;
    }

    public void setLeft(Node left) {
        this.left = left;
    }

    public Node getRight() {
        return right;
    }

    public void setRight(Node right) {
        this.right = right;
    }

    public long getValue() {
        return value;
    }

    public void setValue(long value) {
        this.value = value;
    }

    public double getAverage() {
        return average;
    }

    public void setAverage(double average) {
        this.average = average;
    }

    public long getNumOfAllChildren() {
        return numOfAllChildren;
    }

    public void setNumOfAllChildren(long numOfAllChildren) {
        this.numOfAllChildren = numOfAllChildren;
    }
}