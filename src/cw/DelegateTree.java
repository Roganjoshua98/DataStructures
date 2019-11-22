package cw;

/**
 * @author Joshua Reyes-Traverso (17065838)
 */

public class DelegateTree implements IDelegateDB {
    private class Node {
        private Delegate data;
        private Node left, right, parent;

        public Node(Delegate data) {
            this.data = data;
            this.left = null;
            this.right = null;
        }
    }

    private Node root;
    private int numDelegates;

    public DelegateTree() {
        this.root = null;
        this.numDelegates = 0;
    }

    private Node addBranch (Node tree, Delegate delegate) {
        assert delegate != null;

        if (tree == null)   //Create new root
            tree = new Node(delegate);
        else if (delegate.getName().compareTo(tree.data.getName()) < 0) {   //New value is less than currently selected node
            System.out.println("Visited " + tree.data.getName());
            tree.left = addBranch(tree.left, delegate);
            tree.left.parent = tree;
        }
        else if (delegate.getName().compareTo(tree.data.getName()) > 0) {   //New value is greater than currently selected node
            System.out.println("Visited " + tree.data.getName());
            tree.right = addBranch(tree.right, delegate);
            tree.right.parent = tree;
        }
        return tree;
    }

    private Node getBranch (Node tree, String name) {
        if (tree == null)
            return null;
        System.out.println("Visited " + tree.data.getName());
        if (name.compareTo(tree.data.getName()) < 0)
            tree = getBranch(tree.left, name);
        else if (name.compareTo(tree.data.getName()) > 0)
            tree = getBranch(tree.right, name);
        return tree;
    }

    private void displayBranch(Node tree) {
        if (tree.left != null)
            displayBranch(tree.left);
        System.out.println(tree.data.toString());
        System.out.println("------------------------");
        if (tree.right != null)
            displayBranch(tree.right);
    }

    /*
    getDepth() quoted from https://www.geeksforgeeks.org/write-a-c-program-to-find-the-maximum-depth-or-height-of-a-tree/
     */
    private int getDepth(Node tree) {
        if (tree == null)
            return -1;
        else {
            int leftDepth = getDepth(tree.left);
            int rightDepth = getDepth(tree.right);

            if (leftDepth > rightDepth)
                return (leftDepth + 1);
            else
                return (rightDepth + 1);
        }
    }

    //IMPLEMENTED METHODS
    @Override
    public void clearDB() {
        this.root = null;
    }

    @Override
    public boolean containsName(String name) {
        assert name != null && !name.equals("");

        Delegate delegate = get(name);
        if (delegate == null)
            return false;
        else
            return name.equals(delegate.getName());
    }

    @Override
    public Delegate get(String name) {
        assert name != null && !name.equals("");    //@pre name not null or empty string

        System.out.println("~~STARTLOG~~");
        System.out.println("Getting " + name);
        Node getTree = getBranch(this.root, name);
        if (getTree == null) {
            System.out.println("Delegate not found");
            System.out.println("~~ENDLOG~~");
            return null;
        }
        else {
            System.out.println(name + " from " + getTree.data.getAffiliation());
            System.out.println("~~ENDLOG~~");
            return getTree.data;
        }
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

        System.out.println("~~STARTLOG~~");
        System.out.println("Putting " + delegate.getName() + " from " + delegate.getAffiliation());
        this.root = addBranch(this.root, delegate);
        this.numDelegates++;
        System.out.println("Size: " + size());
        System.out.println("Depth: " + getDepth(this.root));
        System.out.println("~~ENDLOG~~");
        return delegate;
    }

    @Override
    public Delegate remove(String name) {
        assert name != null && !name.equals("");    //@pre name not null or empty string

        System.out.println("~~STARTLOG~~");
        Node tree = getBranch(this.root, name);
        System.out.println("~~ENDLOG~~");
        Delegate delegate;
        if (tree == null)
            return null;
        else {
            Node parent = tree.parent;
            delegate = tree.data;
            System.out.println("~~STARTLOG~~");
            System.out.println("Removing " + delegate.getName() + " from " + delegate.getAffiliation());

            if (tree.left == null && tree.right == null) {  //If node is a leaf
                System.out.println("Node is a leaf");
                if (tree.data.getName().compareTo(tree.parent.data.getName()) < 0)  //If node is left of parent
                    tree.parent.left = null;
                else
                    tree.parent.right = null;
            }

            else if (tree.left != null && tree.right == null) {   //If node has one branch to the left
                System.out.println("Node has one branch to its left");
                if (tree.data.getName().compareTo(tree.parent.data.getName()) < 0)
                    tree.parent.left = tree.left;
                else
                    tree.parent.right = tree.left;
                tree.left.parent = parent;
            }

            else if (tree.left == null && tree.right != null) {   //If node has one branch to the right
                System.out.println("Node has one branch two its right");
                if (tree.data.getName().compareTo(tree.parent.data.getName()) < 0)
                    tree.parent.left = tree.right;
                else
                    tree.parent.right = tree.right;
                tree.right.parent = parent;
            }

            else {    //If node has two branches
                System.out.println("Node has two branches");
                Node toMove = tree.left;    //Cut out left subtree which will be moved
                /* Remove link to user-specified node*/
                if (tree.data.getName().compareTo(tree.parent.data.getName()) < 0)
                    tree.parent.left = tree.right;
                else
                    tree.parent.right = tree.right;
                /*Create new link between parent and the now-deleted node's right branch*/
                tree.right.parent = parent;
                tree = tree.right;
                /*Go to the leftmost branch of subtree*/
                while (tree.left != null)
                    tree = tree.left;
                /*Place cut out subtree in new correct location*/
                tree.left = toMove;
                toMove.parent = tree.left;
            }
        }
        numDelegates--;
        System.out.println("Size: " + size());
        System.out.println("Depth: " + getDepth(this.root));
        System.out.println("~~ENDLOG~~");
        return delegate;
    }

    @Override
    public void displayDB() {
        if (numDelegates == 0)
            System.out.println("There are no delegates currently");
        else
            displayBranch(this.root);
    }
}
