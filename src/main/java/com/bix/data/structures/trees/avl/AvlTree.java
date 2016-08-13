package com.bix.data.structures.trees.avl;

/**
 * A nicer wrapper for AvlNode to avoid null checks on-demand node counting.
 * https://en.wikipedia.org/wiki/AVL_tree
 */
public class AvlTree<T extends Comparable> {

    private AvlNode<T> root;
    private int nodeCounter;

    public AvlTree() {

        this.root = null;
        this.nodeCounter = 0;

    }

    public T getRoot() {
        return root != null ? root.getValue() : null;
    }

    public int getDepth() {
        return root != null ? root.getDepth() : 0;
    }

    public int getNodeCounter() {
        return nodeCounter;
    }

    public void insert(T value) {

        if(root == null)
            root = new AvlNode<>(value);

        else
            root = root.insert(value);

        nodeCounter++;
    }

    public void delete(T value) {

        if(root != null)
            root = root.delete(value);

        nodeCounter--;
    }

    public boolean contains(T value) {
        if(root == null) {
            return false;
        }

        boolean hasValue = root.contains(value);
        return hasValue;
    }

    public void scanAscendingly(FunctionalVisitor<T> visitor) {
        if(root != null)
            root.scanAscendingly(visitor);
    }

    public void scanDescendingly(FunctionalVisitor<T> visitor) {
        if(root != null)
            root.scanDescendingly(visitor);
    }

    public void scanPreOrder(FunctionalVisitor<T> visitor) {
        if(root != null)
            root.scanPreOrder(visitor);
    }

    public void scanPostOrder(FunctionalVisitor<T> visitor) {
        if(root != null)
            root.scanPostOrder(visitor);
    }
}
