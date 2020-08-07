package com.bix.data.structures.trees.bst;

class Node<T extends Comparable<T>> {
    T value;
    Node<T> left;
    Node<T> right;

    Node(T value) {
        this.value = value;
    }

    void add(T value) {
        int comparison = this.value.compareTo(value);
        if (comparison > 0) {
            if (this.left != null) {
                this.left.add(value);
            } else {
                this.left = new Node<>(value);
            }
        } else {
            if (this.right != null) {
                this.right.add(value);
            } else {
                this.right = new Node<>(value);
            }
        }
    }
}
