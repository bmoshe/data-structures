package com.bix.data.structures.trees.bst;

import java.util.Iterator;
import java.util.Stack;

class PreOrderIterator<T extends Comparable<T>>
implements Iterator<T> {

    private final Stack<Node<T>> stack;

    public PreOrderIterator(Node<T> root) {
        this.stack = new Stack<>();
        if(root != null) {
            this.stack.add(root);
        }
    }

    private void recurse(Node<T> node) {
        if (node.right != null) {
            stack.push(node.right);
        }

        if (node.left != null) {
            stack.push(node.left);
        }
    }

    @Override
    public boolean hasNext() {
        return !stack.isEmpty();
    }

    @Override
    public T next() {
        Node<T> node = stack.pop();
        recurse(node);

        return node.value;
    }
}
