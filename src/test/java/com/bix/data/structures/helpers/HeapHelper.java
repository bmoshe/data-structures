package com.bix.data.structures.helpers;

import com.bix.data.structures.heaps.ArrayHeap;
import com.bix.data.structures.heaps.Heap;

import java.util.Comparator;

/**
 * Created by bmoshe on 13/08/16.
 */
public class HeapHelper {

    public static <T extends Comparable> Heap<T> createEmpty(int maxSize) {
        return new ArrayHeap<>(maxSize);
    }

    public static <T extends Comparable> Heap<T> createEmpty(int maxSize, Comparator<T> comparator) {
        return new ArrayHeap<>(maxSize, comparator);
    }

    public static <T extends Comparable> Heap<T> create(FunctionalConstructVisitor<T> visitor, int maxSize) {

        Heap<T> heap = new ArrayHeap<>(maxSize);
        for(int i = 0; i < maxSize; i++) {
            heap.add(visitor.visit(i, maxSize));
        }

        return heap;
    }

    public static <T extends Comparable> Heap<T> create(FunctionalConstructVisitor<T> visitor, int maxSize, Comparator<T> comparator) {

        Heap<T> heap = new ArrayHeap<>(maxSize, comparator);
        for(int i = 0; i < maxSize; i++) {
            heap.add(visitor.visit(i, maxSize));
        }

        return heap;
    }
}
