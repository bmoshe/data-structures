package com.bix.data.structures.sets;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Union-Find data structure.
 * O(1) for making a new singleton set.
 * O(1) for unifying two sets.
 * O(log*(n)) for finding a set representative (because smaller set joins larger sets),
 * but it also compresses path on-the-fly, so it becomes amortized O(1) as finds occur more.
 *
 * Read more: https://en.wikipedia.org/wiki/Disjoint-set_data_structure
 */
public class UnionFind<T> {
    Map<T, Integer> index;
    List<UFContainer<T>> data;
    int size;

    public UnionFind() {
        this.index = new HashMap<>();
        this.data = new ArrayList<>();

        this.size = 0;
    }

    public void makeSet(T value) {
        int idx = data.size();
        index.put(value, idx);
        data.add(new UFContainer<>(value, idx, 1));
        size++;
    }

    public boolean union(T a, T b) {
        int idxA = index.get(a);
        int idxB = index.get(b);
        UFContainer<T> representativeOfA = getRepresentativeOf(idxA);
        UFContainer<T> representativeOfB = getRepresentativeOf(idxB);

        if(representativeOfA != representativeOfB) {
            if(representativeOfA.size < representativeOfB.size) {
                representativeOfA.representative = representativeOfB.representative;
                representativeOfB.size += representativeOfA.size;
            } else {
                representativeOfB.representative = representativeOfA.representative;
                representativeOfA.size += representativeOfB.size;
            }

            size--;
            return true;
        }

        return false;
    }

    public T find(T value) {
        int idx = index.get(value);
        UFContainer<T> representativeContainer = getRepresentativeOf(idx);
        return representativeContainer.value;
    }

    public int sizeOf(T value) {
        int idx = index.get(value);
        UFContainer<T> representativeContainer = getRepresentativeOf(idx);
        return representativeContainer.size;
    }

    public int size() {
        return this.size;
    }

    // Used for testing that path has indeed been compressed.
    // Consider removing if test code isn't wanted in production code.
    T immediateRepresentativeOf(T value) {
        int idx = index.get(value);
        int idxRepresentative = data.get(idx).representative;
        return data.get(idxRepresentative).value;
    }

    private UFContainer<T> getRepresentativeOf(int idx) {
        UFContainer<T> container = data.get(idx);
        if(container.representative != idx) {
            UFContainer<T> representativeContainer = getRepresentativeOf(container.representative);
            container.representative = representativeContainer.representative;
            return representativeContainer;
        }

        return container;
    }

    static class UFContainer<T> {
        final T value;
        int representative;
        int size;

        public UFContainer(T value, int representative, int size) {
            this.value = value;
            this.representative = representative;
            this.size = size;
        }

        public T getValue() {
            return value;
        }

        public int getSize() {
            return size;
        }

        public void setSize(int size) {
            this.size = size;
        }

        public int getRepresentative() {
            return representative;
        }

        public void setRepresentative(int representative) {
            this.representative = representative;
        }
    }
}