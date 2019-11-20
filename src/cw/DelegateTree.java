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
    private int numDelegates;

    public DelegateTree() {
        this.root = null;
        this.numDelegates = 0;
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

    private Node getBranch (Node tree, String name) {
        if (name.compareTo(tree.data.getName()) == 0)
            return tree;
        else if (name.compareTo(tree.data.getName()) < 0)
            return getBranch(tree.left, name);
        else if (name.compareTo(tree.data.getName()) > 0)
            return getBranch(tree.right, name);
        else
            return null;
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
        assert name != null && !name.equals("");    //@pre name not null or empty string

        Node getTree = getBranch(this.root, name);
        return getTree.data;
    }

    @Override
    public int size() {
        return numDelegates;
    }

    @Override
    public boolean isEmpty() {
        return numDelegates == 0;
    }

    @Override
    public Delegate put(Delegate delegate) {
        assert delegate != null;    //@pre delegate not null

        this.root = addBranch(this.root, delegate);
        this.numDelegates++;
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
