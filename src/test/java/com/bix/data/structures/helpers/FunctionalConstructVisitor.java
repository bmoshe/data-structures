package com.bix.data.structures.helpers;

/**
 * Functional visitor for constructing data structures.
 */
@FunctionalInterface
public interface FunctionalConstructVisitor<T> {

    T visit(int index, int total);

}
