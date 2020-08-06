package com.bix.data.structures.trees.bst;

import java.util.Iterator;
import java.util.Stack;

public class BST<T extends Comparable<T>>
implements Iterable<T> {
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

    public Iterator<T> iterator() {
        return new BSTIterator();
    }

    static class Node<T extends Comparable<T>> {
        private T value;
        private Node<T> left;
        private Node<T> right;

        Node(T value) {
            this.value = value;
        }

        void add(T value) {
            int comparison = this.value.compareTo(value);
            if(comparison > 0) {
               if(this.left != null) {
                   this.left.add(value);
               } else {
                   this.left = new Node<>(value);
               }
            } else {
                if(this.right != null) {
                    this.right.add(value);
                } else {
                    this.right = new Node<>(value);
                }
            }
        }
    }

    class BSTIterator
    implements Iterator<T> {

        private Stack<Node<T>> stack;

        public BSTIterator() {
            this.stack = new Stack<>();

            recurse(root);
        }

        private void recurse(Node<T> root) {
            Node<T> it = root;
            while(it != null) {
                this.stack.push(it);
                it = it.left;
            }
        }

        @Override
        public boolean hasNext() {
            return !stack.isEmpty();
        }

        @Override
        public T next() {
            Node<T> node = stack.pop();
            if(node.right != null) {
                recurse(node.right);
            }

            return node.value;
        }
    }
}
