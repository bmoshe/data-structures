package com.bix.data.structures.helpers;

/**
 * Created by bmoshe on 13/08/16.
 */
public class FunctionalVariable<T> {

    protected T value;

    public FunctionalVariable(T initialValue) {
        this.value = initialValue;
    }

    public T getValue() {
        return value;
    }

    public void setValue(T value) {
        this.value = value;
    }
}
