package com.bix.data.structures.trees.bst;

import java.util.Iterator;
import java.util.Stack;

class PostOrderIterator<T extends Comparable<T>>
implements Iterator<T> {

    private final Stack<Node<T>> stack;
    private Node<T> prev;

    public PostOrderIterator(Node<T> root) {
        this.stack = new Stack<>();
        recurse(root);
        this.prev = this.stack.isEmpty() ? null : this.stack.peek();
    }

    private void recurse(Node<T> node) {
        if (node == null) {
            return;
        }

        while (node != null) {
            stack.push(node);
            node = node.left;
        }
        recurse(stack.peek().right);
    }

    @Override
    public boolean hasNext() {
        return !stack.isEmpty();
    }

    @Override
    public T next() {
        if (stack.peek().right != this.prev) {
            recurse(stack.peek().right);
        }

        Node<T> next = stack.pop();
        this.prev = next;
        return next.value;
    }
}
