package com.bix.data.structures.helpers;

/**
 * Created by bmoshe on 13/08/16.
 */
public class ArrayHelper {

    public static Integer[] createIntArray(FunctionalConstructVisitor<Integer> visitor, int size) {

        final Integer[] array = new Integer[size];
        for(int i = 0; i < size; i++) {
            array[i] = visitor.visit(i, size);
        }

        return array;
    }
}
