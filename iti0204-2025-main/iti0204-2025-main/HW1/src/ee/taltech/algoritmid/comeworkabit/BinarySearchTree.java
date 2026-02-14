package ee.taltech.algoritmid.comeworkabit;

import java.util.ArrayList;
import java.util.List;


public class BinarySearchTree<Key extends Comparable<Key>, Value> {
    private TreeNode<Key, Value> root;

    public BinarySearchTree() {
        root = null;
    }

    public void insert(Key key, Value value){
        TreeNode<Key, Value> z = new TreeNode<>(key, value);
        TreeNode<Key, Value> y = null;
        TreeNode<Key, Value> x = root;

        while (x != null){
            y = x;
            if (z.key.compareTo(x.key) < 0) {
                x = x.left;
            } else {
                x = x.right;
            }
        }

        z.parent = y;

        if (y == null) {
            root = z;
        } else if (z.key.compareTo(y.key) < 0) {
            y.left = z;
        } else {
            y.right = z;
        }
    }

    public void delete(Key key, Value value){
        TreeNode<Key, Value> z = findNode(key, value);
        if (z == null) {
            throw new IllegalArgumentException("Item not found for deletion");
        }
        TreeNode<Key, Value> y;
        TreeNode<Key, Value> x;

        if (z.left == null || z.right == null) {
            y = z;
        } else {
            y = treeMinimum(z.right);
        }

        if (y.left != null) {
            x = y.left;
        } else {
            x = y.right;
        }

        if (x != null) {
            x.parent = y.parent;
        }

        if (y.parent == null) {
            root = x;
        } else if (y == y.parent.left) {
            y.parent.left = x;
        } else {
            y.parent.right = x;
        }

        if (y != z) {
            z.key = y.key;
            z.data = y.data;
        }
    }

    public List<Value> inOrderTraversal() {
        List<Value> list = new ArrayList<>();
        inOrderTraversal(root, list);
        return list;
    }

    public Value findBestSmaller(Key key) {
        TreeNode<Key, Value> current = root;
        Value floorValue = null;

        while (current != null) {
            int comparison = key.compareTo(current.key);

            if (comparison == 0) {
                return current.data;
            } else if (comparison > 0) {
                floorValue = current.data;
                current = current.right;
            } else {
                current = current.left;
            }
        }
        return floorValue;
    }

    public Value findBestLarger(Key key) {
        TreeNode<Key, Value> current = root;
        Value ceilingValue = null;

        while (current != null) {
            int comparison = key.compareTo(current.key);

            if (comparison == 0) {
                return current.data;
            } else if (comparison > 0) {
                current = current.right;
            } else {
                ceilingValue = current.data;
                current = current.left;
            }
        }
        return ceilingValue;
    }

    private TreeNode<Key, Value> findNode(Key key, Value value) {
        TreeNode<Key, Value> x = root;
        while (x != null) {
            int cmp = key.compareTo(x.key);
            if (cmp < 0) {
                x = x.left;
            } else if (cmp > 0) {
                x = x.right;
            } else {
                if (x.data.equals(value)) {
                    return x;
                }
                x = x.right;
            }
        }
        return null;
    }

    private void inOrderTraversal(TreeNode<Key, Value> node, List<Value> list) {
        if (node == null) return;
        inOrderTraversal(node.left, list);
        list.add(node.data);
        inOrderTraversal(node.right, list);
    }

    private TreeNode<Key, Value> treeMinimum(TreeNode<Key, Value> node) {
        if (node == null) return null;
        while (node.left != null) {
            node = node.left;
        }
        return node;
    }
}