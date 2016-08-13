package com.bix.data.structures.heaps;

import java.util.Comparator;

/**
 * Min/Max Heap implemented with an array.
 */
public class ArrayHeap<T extends Comparable>
implements Heap<T> {

    private Comparator<T> comparator;

    private Comparable[] data;
    private int size;
    private int maxSize;

    public static <T extends Comparable> void sort(T[] array) {
        sort(array, null);
    }

    public static <T extends Comparable> void sort(T[] array, Comparator<T> comparator) {
        Heap<T> heap = new ArrayHeap<>(array, comparator);

        for(int i = 0; i < array.length; i++) {
            T max = heap.removeTop();
            array[array.length - i - 1] = max;
        }
    }

    public ArrayHeap(int maxSize) {
        this(maxSize, null);
    }

    public ArrayHeap(int maxSize, Comparator<T> comparator) {
        this.data = new Comparable[maxSize];

        this.size = 0;
        this.maxSize = maxSize;

        this.comparator = comparator;
    }

    // Used by ArrayHeap.sort to Heap-sort an array.
    // https://en.wikipedia.org/wiki/Heapsort
    private ArrayHeap(T[] array, Comparator<T> comparator) {
        this.data = array;

        this.size = array.length;
        this.maxSize = array.length;

        this.comparator = comparator;

        heapify();
    }

    private void heapify() {
        for(int i = size / 2; i >= 0; i--) {
            bubbleDown(i);
        }
    }

    @Override
    public T getTop() {
        return size > 0 ? (T) data[0] : null;
    }

    @Override
    public void add(T value) {
        data[size] = value;
        bubbleUp(size);
        size++;
    }

    // Fixes a one change from bottom to top.
    private void bubbleUp(int n) {
        if(n > 0) {
            int idxParent = (n - 1) / 2;

            boolean shouldBubbleUp = compare(data[n], data[idxParent]) > 0;
            if(shouldBubbleUp) {
                swap(n, idxParent);
                bubbleUp(idxParent);
            }
        }
    }

    private int compare(Comparable a, Comparable b) {
        if(comparator != null) {
            return comparator.compare((T) a, (T) b);
        } else {
            return a.compareTo(b);
        }
    }

    @Override
    public T removeTop() {
        T ret = getTop();

        size--;
        data[0] = data[size];
        bubbleDown(0);

        return ret;
    }

    // Fixes a one change from top to bottom.
    private void bubbleDown(int n) {
        if(n <= size / 2) {
            int idxLeftChild = 2 * n + 1;
            int idxRightChild = 2 * n + 2;

            boolean hasChildA = idxLeftChild < size;
            boolean hasChildB = idxRightChild < size;

            int idxChild = n;
            if(hasChildA && hasChildB) {
                boolean aIsGreaterThanB = compare(data[idxLeftChild], data[idxRightChild]) > 0;
                idxChild = aIsGreaterThanB ? idxLeftChild : idxRightChild;
            }
            else if(hasChildA || hasChildB) {
                idxChild = hasChildA ? idxLeftChild : idxRightChild;
            }

            if(n != idxChild) {
                boolean shouldBubbleWithChild = compare(data[idxChild], data[n]) > 0;
                if(shouldBubbleWithChild) {
                    swap(idxChild, n);
                    bubbleDown(idxChild);
                }
            }
        }
    }

    private void swap(int idxA, int idxB) {
        Comparable tmp = data[idxA];
        data[idxA] = data[idxB];
        data[idxB] = tmp;
    }

    @Override
    public T remove(T value) {
        for(int i = 0; i < size; i++)
            if(data[i].equals(value))
                return removeAt(i);

        return null;
    }

    private T removeAt(int index) {
        T ret = (T) data[index];

        data[index] = data[size - 1];
        size--;

        bubbleDown(index);

        return ret;
    }

    @Override
    public boolean isEmpty() {
        return getTop() == null;
    }

    @Override
    public int getSize() {
        return size;
    }

    @Override
    public int getMaxSize() {
        return maxSize;
    }
}