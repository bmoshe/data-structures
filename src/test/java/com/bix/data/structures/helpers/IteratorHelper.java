package com.bix.data.structures.helpers;

import java.util.Iterator;
import java.util.List;
import java.util.Spliterators;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import static java.util.Spliterator.ORDERED;

public class IteratorHelper {
    public static <T> List<T> convertToList(Iterator<T> iterator) {
        return StreamSupport.stream(Spliterators.spliteratorUnknownSize(iterator, ORDERED), false).collect(Collectors.toList());
    }
}
