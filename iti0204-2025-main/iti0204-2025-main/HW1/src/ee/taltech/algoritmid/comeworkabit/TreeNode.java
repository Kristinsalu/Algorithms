package ee.taltech.algoritmid.comeworkabit;

public class TreeNode<Key extends Comparable<Key>, Value> {
    Key key;
    Value data;
    TreeNode<Key, Value> parent;
    TreeNode<Key, Value> left;
    TreeNode<Key, Value> right;

    public TreeNode(Key key, Value data) {
        this.key = key;
        this.data = data;
    }
    public Key getKey() { return key; }
    public Value getData() { return data; }
}