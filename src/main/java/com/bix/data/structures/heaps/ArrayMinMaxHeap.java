package com.bix.data.structures.heaps;

import java.lang.reflect.Array;
import java.util.*;

/**
 * Min-Max Heap implemented with an array.
 */
public class ArrayMinMaxHeap<T extends Comparable<T>>
implements MinMaxHeap<T> {

    private final Comparator<T> minComparator;
    private final Comparator<T> maxComparator;

    private final T[] data;
    private int size;

    private int prevLevel;
    private int nextLevel;
    private boolean isMinLevel;

    public ArrayMinMaxHeap(Class<T> tClass, int maxSize) {
        this(tClass, maxSize, Comparator.naturalOrder());
    }

    @SuppressWarnings("unchecked")
    public ArrayMinMaxHeap(Class<T> tClass, int maxSize, Comparator<T> minComparator) {
        this.minComparator = minComparator;
        this.maxComparator = minComparator.reversed();


        this.data = (T[]) Array.newInstance(tClass, maxSize);

        this.size = 0;
        this.prevLevel = 0;
        this.nextLevel = 1;
        this.isMinLevel = true;
    }

    public int minCompare(T a, T b) {
        return compare(minComparator, a, b);
    }

    public int maxCompare(T a, T b) {
        return compare(maxComparator, a, b);
    }

    private int compare(Comparator<T> comparator, T a, T b) {
        return comparator.compare(a, b);
    }

    @Override
    public T getMin() {
        return size > 0 ? data[0] : null;
    }

    @Override
    public T getMax() {
        switch(size) {
            case 0: return null;
            case 1: return data[0];
            case 2: return data[1];
            default: return maxCompare(data[1], data[2]) < 0 ? data[1] : data[2];
        }
    }

    @Override
    public void add(T value) {
        data[size] = value;
        size++;

        bubbleUp(size - 1, isMinLevel);
        readjustCurrentLevelIfNecessary();
    }

    private void bubbleUp(int n, boolean isMinLevel) {
        if(n > 0) {
            int idxParent = (n - 1) / 2;
            if(compareAtLevel(n, idxParent, !isMinLevel) < 0) {
                swap(n, idxParent);
                bubbleGrandUp(idxParent, !isMinLevel);
            } else {
                bubbleGrandUp(n, isMinLevel);
            }
        }
    }

    private void bubbleGrandUp(int n, boolean isMinLevel) {
        if(n >= 3) {
            int idxParent = (n - 1) / 2;
            int idxGrandparent = (idxParent - 1) / 2;
            if(compareAtLevel(n, idxGrandparent, isMinLevel) < 0) {
                swap(n, idxGrandparent);
                bubbleGrandUp(idxGrandparent, isMinLevel);
            }
        }
    }

    private void bubbleDown(int n, boolean isMinLevel) {
        List<Integer> descendents = new ArrayList<>(6);
        getDescendents(n, 2, descendents);

        Optional<Integer> idxMin = descendents.stream().min((a, b) -> compareAtLevel(a, b, isMinLevel));
        if(idxMin.isPresent() && compareAtLevel(idxMin.get(), n, isMinLevel) < 0) {
            swap(n, idxMin.get());

            boolean isGrandchild = !isChildOf(n, idxMin.get());
            if(isGrandchild) {
                int idxGrandchild = idxMin.get();
                int idxChild = (idxGrandchild - 1) / 2;
                if(compareAtLevel(idxGrandchild, idxChild, !isMinLevel) < 0) {
                    swap(idxChild, idxGrandchild);
                }
                bubbleDown(idxGrandchild, isMinLevel);
            }
        }
    }

    private void getDescendents(int idx, int depth, List<Integer> descendents) {
        if(depth == 0) {
            return;
        }

        int idxLeft = 2 * idx + 1;
        if(idxLeft < size) {
            descendents.add(idxLeft);
            getDescendents(idxLeft, depth - 1, descendents);
        }

        int idxRight = 2 * idx + 2;
        if(idxRight < size) {
            descendents.add(idxRight);
            getDescendents(idxRight, depth - 1, descendents);
        }
    }

    @Override
    public T removeMin() {
        T min = getMin();

        size--;
        data[0] = data[size];
        data[size] = null;

        bubbleDown(0, true);
        readjustCurrentLevelIfNecessary();

        return min;
    }

    private boolean isChildOf(int n, int m) {
        return 2 * n + 1 == m || m == 2 * n + 2;
    }

    @Override
    public T removeMax() {
        T max = getMax();
        if(max == null) {
            return null;
        }

        if(size == 1) {
            size--;
        } else {
            int idxMax = max.equals(data[1]) ? 1 : 2;

            size--;
            data[idxMax] = data[size];
            data[size] = null;

            if (idxMax < size) {
                bubbleDown(idxMax, false);
            }
        }

        readjustCurrentLevelIfNecessary();
        return max;
    }

    private void readjustCurrentLevelIfNecessary() {
        if(size == nextLevel) {
            int lastLevel = prevLevel;
            prevLevel = nextLevel;
            nextLevel += 2 * (nextLevel - lastLevel);
            isMinLevel = !isMinLevel;
        } else if(size < prevLevel) {
            int lastLevel = nextLevel - prevLevel;
            nextLevel = prevLevel;
            prevLevel -= (lastLevel / 2);
            isMinLevel = !isMinLevel;
        }
    }

    private int compareAtLevel(int a, int b, boolean isMinLevel) {
        return compare(isMinLevel ? minComparator : maxComparator, data[a], data[b]);
    }

    private void swap(int idxA, int idxB) {
        T tmp = data[idxA];
        data[idxA] = data[idxB];
        data[idxB] = tmp;
    }

    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    @Override
    public int getSize() {
        return size;
    }
}