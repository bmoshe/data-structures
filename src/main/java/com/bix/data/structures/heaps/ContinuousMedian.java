package com.bix.data.structures.heaps;

import com.bix.data.structures.base.BaseComparingStructure;

import java.util.Comparator;

import static java.util.Comparator.reverseOrder;

public class ContinuousMedian<T extends Comparable>
extends BaseComparingStructure<T> {

    private static final int DefaultHeapSize = 4;

    private Heap<T> highers;
    private Heap<T> lowers;

    public ContinuousMedian() {
        this(null);
    }

    public ContinuousMedian(Comparator<T> comparator) {
        super(comparator);

        this.lowers = new ArrayHeap<>(DefaultHeapSize, comparator);
        this.highers = new ArrayHeap<T>(DefaultHeapSize, comparator != null ? comparator.reversed() : reverseOrder());
    }

    public void add(T value) {
        addValue(value);
        rebalance();
    }

    private void addValue(T value) {
        if(lowers.isEmpty() || compare(value, lowers.getTop()) <= 0) {
            lowers.add(value);
        } else {
            highers.add(value);
        }
    }

    private void rebalance() {
        if(lowers.getSize() < highers.getSize()) {
            lowers.add(highers.removeTop());
        } else if(highers.getSize() < lowers.getSize() - 1){
            highers.add(lowers.removeTop());
        }
    }

    public T getMedian() {
        return lowers.getTop();
    }

    public T removeMedian() {
        T tmp = lowers.removeTop();
        rebalance();
        return tmp;
    }
}
