package com.bix.data.structures.heaps;

/**
 * ADT fpr Min/Max Heaps.
 * https://en.wikipedia.org/wiki/Heap_(data_structure)
 */
public interface Heap<T extends Comparable> {
    T getTop();
    void add(T value);
    T removeTop();
    T remove(T value);

    boolean isEmpty();

    int getSize();
    int getMaxSize();
}