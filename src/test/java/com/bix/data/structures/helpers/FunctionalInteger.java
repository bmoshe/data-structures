package com.bix.data.structures.helpers;

/**
 * Created by bmoshe on 13/08/16.
 */
public class FunctionalInteger
extends FunctionalVariable<Integer> {

    public FunctionalInteger() {
        this(0);
    }

    public FunctionalInteger(Integer initialValue) {
        super(initialValue);
    }

    public void increment() {
        value++;
    }

    public Integer getAndIncrement() {
        return value++;
    }
}
