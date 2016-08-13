package com.bix.data.structures.trees.avl;

import java.util.logging.Logger;

/**
 * AVL Tree data structure.
 * https://en.wikipedia.org/wiki/AVL_tree
 *
 * This class is marked as internal to encourage the use of AvlTree as a wrapper for AvlNode.
 */
class AvlNode<T extends Comparable> {

    private static Logger log = Logger.getLogger(AvlNode.class.getName());

    private T value;
    private int depth;

    private AvlNode<T> left;
    private AvlNode<T> right;

    AvlNode(T value) {
        this.value = value;
        this.depth = 1;

        this.left = null;
        this.right = null;
    }

    public T getValue() {

        return value;

    }

    private int getLeftDepth() {
        return hasLeft() ? left.depth : 0;
    }

    private int getRightDepth() {
        return hasRight() ? right.depth : 0;
    }

    // Inserts the value and returns the new (balanced) subtree.
    AvlNode<T> insert(T newValue) {

        // Put item in place.
        int comparision = newValue.compareTo(value);
        if(comparision <= 0) {
            left = hasLeft() ? left.insert(newValue) : new AvlNode<>(newValue);
        }
        else if(comparision > 0) {
            right = hasRight() ? right.insert(newValue) : new AvlNode<>(newValue);
        }

        // Rebalance if needed.
        depth = Math.max(getLeftDepth(), getRightDepth()) + 1;
        AvlNode<T> newNode = rebalanceIfNeeded();

        return newNode;
    }

    // Rotation is required whenever the depth of one branch is greater than the depth of the other
    // branch by at least 2 layers.
    private AvlNode<T> rebalanceIfNeeded() {
        AvlNode<T> node = this;

        boolean isRightBranchOverweight = balance() > 1;
        if(isRightBranchOverweight) {
            if(right.balance() > 0) {
                node = rotateRightRight();
            }
            else {
                node = rotateRightLeft();
            }
        }
        else {
            boolean isLeftBranchOverweight = balance() < -1;
            if (isLeftBranchOverweight) {
                if (left.balance() < 0) {
                    node = rotateLeftLeft();
                }
                else {
                    node = rotateLeftRight();
                }
            }
        }

        return node;
    }

    /* Rotating the case of

                    X
                   / \
                  Y   D
                 / \
                Z   C
               / \
              A   B

       into

                     Y
                   /   \
                  Z     X
                 / \   / \
                A   B C   D

    */
    private AvlNode<T> rotateLeftLeft() {

        AvlNode<T> x = this;
        AvlNode<T> y = this.left;
        AvlNode<T> z = this.left.left;

        AvlNode<T> a = z.left;
        AvlNode<T> b = z.right;
        AvlNode<T> c = y.right;
        AvlNode<T> d = x.right;

        y.left = z;
        y.right = x;

        x.left = c;

        x.recalculateDepth();
        z.recalculateDepth();
        y.recalculateDepth();

        return y;
    }

    /* Rotating the case of

                    X
                   / \
                  Y   D
                 / \
                C   Z
                   / \
                  A   B

       into

                     Z
                   /   \
                  Y     X
                 / \   / \
                C   A B   D

    */
    private AvlNode<T> rotateLeftRight() {

        AvlNode<T> x = this;
        AvlNode<T> y = this.left;
        AvlNode<T> z = this.left.right;

        AvlNode<T> a = z.left;
        AvlNode<T> b = z.right;
        AvlNode<T> c = y.right;
        AvlNode<T> d = x.right;

        z.left = y;
        z.right = x;

        x.left = b;
        y.right = a;

        x.recalculateDepth();
        y.recalculateDepth();
        z.recalculateDepth();

        return z;
    }

    /* Rotating the case of

                    X
                   / \
                  D   Y
                     / \
                    C   Z
                       / \
                      A   B

       into

                     Y
                   /   \
                  X     Z
                 / \   / \
                D   C A   B

    */
    private AvlNode<T> rotateRightRight() {

        AvlNode<T> x = this;
        AvlNode<T> y = this.right;
        AvlNode<T> z = this.right.right;

        AvlNode<T> a = z.left;
        AvlNode<T> b = z.right;
        AvlNode<T> c = y.left;
        AvlNode<T> d = x.left;

        y.left = x;
        y.right = z;

        x.right = c;

        x.recalculateDepth();
        z.recalculateDepth();
        y.recalculateDepth();

        return y;
    }


    /* Rotating the case of

                    X
                   / \
                  D   Y
                     / \
                    Z   C
                   / \
                  A   B

       into

                     Z
                   /   \
                  X     Y
                 / \   / \
                D   A B   C

    */
    private AvlNode<T> rotateRightLeft() {

        AvlNode<T> x = this;
        AvlNode<T> y = this.right;
        AvlNode<T> z = this.right.left;

        AvlNode<T> a = z.left;
        AvlNode<T> b = z.right;
        AvlNode<T> c = y.right;
        AvlNode<T> d = x.right;

        z.left = x;
        z.right = y;

        x.right = a;
        y.left = b;

        x.recalculateDepth();
        y.recalculateDepth();
        z.recalculateDepth();

        return z;
    }

    AvlNode<T> delete(T theValue) {
        int comparision = theValue.compareTo(value);
        if(comparision < 0 && hasLeft()) {
            left = left.delete(theValue);
        }
        else if(comparision > 0 && hasRight()) {
            right = right.delete(theValue);
        }
        else if(comparision == 0) {

            if(!hasLeft() && !hasRight()) {

                // Leaf!
                return null;

            }
            else if(getLeftDepth() > getRightDepth()) {
                DeletedNode<T> maxOfLeft = left.deleteMax();
                left = maxOfLeft.getRef();
                value = maxOfLeft.getValue();
            }

            else {
                DeletedNode<T> minOfRight = right.deleteMin();
                right = minOfRight.getRef();
                value = minOfRight.getValue();
            }
        }

        // Rebalance if needed.
        depth = Math.max(getLeftDepth(), getRightDepth()) + 1;
        AvlNode<T> newNode = rebalanceIfNeeded();

        return newNode;
    }

    private DeletedNode<T> deleteMax() {
        if(hasRight()) {
            DeletedNode deletedNode = right.deleteMax();
            right = deletedNode.getRef();

            // Rebalance if needed.
            depth = Math.max(getLeftDepth(), getRightDepth()) + 1;
            AvlNode<T> newNode = rebalanceIfNeeded();

            deletedNode.setRef(newNode);
            return deletedNode;
        }

        return new DeletedNode<>(null, value);
    }

    private DeletedNode<T> deleteMin() {
        if(hasLeft()) {
            DeletedNode deletedNode = left.deleteMin();
            left = deletedNode.getRef();

            // Rebalance if needed.
            depth = Math.max(getLeftDepth(), getRightDepth()) + 1;
            AvlNode<T> newNode = rebalanceIfNeeded();

            deletedNode.setRef(newNode);
            return deletedNode;
        }

        return new DeletedNode<>(null, value);
    }

    void scanAscendingly(FunctionalVisitor<T> visitor) {
        if(hasLeft())
            left.scanAscendingly(visitor);

        visitor.visit(value);

        if(hasRight())
            right.scanAscendingly(visitor);
    }

    void scanDescendingly(FunctionalVisitor<T> visitor) {
        if(hasRight())
            right.scanDescendingly(visitor);

        visitor.visit(value);

        if(hasLeft())
            left.scanDescendingly(visitor);
    }

    // Similar to topological sorting the tree from top to bottom.
    void scanPreOrder(FunctionalVisitor<T> visitor) {
        visitor.visit(value);

        if(hasLeft())
            left.scanPreOrder(visitor);

        if(hasRight())
            right.scanPreOrder(visitor);
    }

    // Similar to topological sorting the tree from bottom to top.
    void scanPostOrder(FunctionalVisitor<T> visitor) {
        if(hasLeft())
            left.scanPostOrder(visitor);

        if(hasRight())
            right.scanPostOrder(visitor);

        visitor.visit(value);
    }

    private boolean hasLeft() {
        return left != null;
    }

    private boolean hasRight() {
        return right != null;
    }

    private void recalculateDepth() {

        int leftDepth = getLeftDepth();
        int rightDepth = getRightDepth();

        this.depth = Math.max(leftDepth, rightDepth) + 1;
    }

    private int balance() {
        int leftDepth = hasLeft() ? left.depth : 0;
        int rightDepth = hasRight() ? right.depth : 0;

        return rightDepth - leftDepth;
    }

    int getDepth() {
        return depth;
    }

    boolean contains(T newValue) {
        AvlNode<T> node = this;

        do {
            int comparision = newValue.compareTo(node.value);
            if (comparision < 0)
                node = node.left;

            else if (comparision > 0)
                node = node.right;
        }
        while(node != null && !node.value.equals(newValue));

        return node != null;
    }

    private class DeletedNode<S extends Comparable> {

        private AvlNode<S> ref;
        private S value;

        DeletedNode(AvlNode<S> ref, S value) {
            this.ref = ref;
            this.value = value;
        }

        AvlNode<S> getRef() {
            return ref;
        }

        void setRef(AvlNode<S> ref) {
            this.ref = ref;
        }

        S getValue() {
            return value;
        }
    }
}