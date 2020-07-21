package com.bix.data.structures.heaps;

/**
 * ADT fpr Min/Max Heaps.
 * https://en.wikipedia.org/wiki/Heap_(data_structure)
 */
public interface MinMaxHeap<T extends Comparable<T>> {
    void add(T value);

    T getMin();
    T removeMin();
    T getMax();
    T removeMax();

    boolean isEmpty();
    int getSize();
}