package com.bix.data.structures.trees.bst;

import java.util.Iterator;
import java.util.Stack;

class InOrderIterator<T extends Comparable<T>>
implements Iterator<T> {

    private final Stack<Node<T>> stack;

    public InOrderIterator(Node<T> root) {
        this.stack = new Stack<>();

        recurse(root);
    }

    private void recurse(Node<T> root) {
        Node<T> it = root;
        while (it != null) {
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
        if (node.right != null) {
            recurse(node.right);
        }

        return node.value;
    }
}
