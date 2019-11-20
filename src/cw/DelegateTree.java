package cw;

/**
 * @author Joshua Reyes-Traverso (17065838)
 */

public class DelegateTree implements IDelegateDB {
    private class Node {
        private Delegate data;
        private Node left;
        private Node right;

        public Node(Delegate data) {
            this.data = data;
        }
    }

    private Node root;
    private int numEntries;

    public DelegateTree() {
        this.root = null;
        this.numEntries = 0;
    }

    private Node addBranch (Node tree, Delegate delegate) {
        if (tree == null)   //Create new root
            tree = new Node(delegate);
        else if (delegate.getName().compareTo(tree.data.getName()) < 0)    //New value is less than currently selected node
            tree.left = addBranch(tree.left, delegate);
        else if (delegate.getName().compareTo(tree.data.getName()) > 0)    //New value is greater than currently selected node
            tree.right = addBranch(tree.right, delegate);
        return tree;
    }

    //IMPLEMENTED METHODS
    @Override
    public void clearDB() {

    }

    @Override
    public boolean containsName(String name) {
        return false;
    }

    @Override
    public Delegate get(String name) {
        return null;
    }

    @Override
    public int size() {
        return 0;
    }

    @Override
    public boolean isEmpty() {
        return false;
    }

    @Override
    public Delegate put(Delegate delegate) {
        this.root = addBranch(this.root, delegate);
        this.numEntries++;
        return null;
    }

    @Override
    public Delegate remove(String name) {
        return null;
    }

    @Override
    public void displayDB() {

    }
}
