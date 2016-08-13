package com.bix.data.structures.trees.avl;

/**
 * Functional visitor for tree traversing.
 */
@FunctionalInterface
public interface FunctionalVisitor<T> {
    void visit(T node);

}
