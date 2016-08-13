package com.bix.data.structures.helpers;

import com.bix.data.structures.trees.avl.AvlTree;

/**
 * Created by bmoshe on 23/07/16.
 */
public class AvlTreeHelper {

    public static <T extends Comparable> AvlTree<T> createEmpty() {

        return new AvlTree<>();

    }
    public static <T extends Comparable> AvlTree<T> create(FunctionalConstructVisitor<T> visitor, int size) {

        AvlTree<T> tree = new AvlTree<>();
        for(int i = 0; i < size; i++) {
            tree.insert(visitor.visit(i, size));
        }

        return tree;
    }
}
