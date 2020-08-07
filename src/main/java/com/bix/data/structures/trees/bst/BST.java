package com.bix.data.structures.trees.bst;

import java.util.Iterator;

public class BST<T extends Comparable<T>> {
    private Node<T> root;
    private int size;

    public BST() {
        this.root = null;
        this.size = 0;
    }

    public void add(T value) {
        if(this.root == null) {
            this.root = new Node<>(value);
        } else {
            this.root.add(value);
        }

        size++;
    }

    public int size() {
        return this.size;
    }

    public Iterator<T> inOrderIterator() {
        return new InOrderIterator<>(root);
    }

    public Iterator<T> preOrderIterator() {
        return new PreOrderIterator<>(root);
    }

    public Iterator<T>  postOrderIterator() {
        return new PostOrderIterator<>(root);
    }

}
