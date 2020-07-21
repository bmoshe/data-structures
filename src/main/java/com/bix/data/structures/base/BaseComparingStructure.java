package com.bix.data.structures.base;

import java.util.Comparator;

public abstract class BaseComparingStructure<T extends Comparable> {

    private Comparator<T> comparator;

    public BaseComparingStructure() {
        this(null);
    }

    public BaseComparingStructure(Comparator<T> comparator) {
        this.comparator = comparator;
    }

    protected int compare(Comparable a, Comparable b) {
        if(comparator != null) {
            return comparator.compare((T) a, (T) b);
        } else {
            return a.compareTo(b);
        }
    }

}
