package com.bix.data.structures.sets;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

/**
 * Created by bmoshe on 30/07/16.
 */
public class UnionFindTests {

    private UnionFind<String> unionFind;

    @BeforeEach
    public void setupTest() {
        unionFind = new UnionFind<>();

        for(char i = 'A'; i <= 'Z'; i++) {
            unionFind.makeSet(String.valueOf(i));
        }
    }

    @Test
    public void testShallowUnion() {
        unionFind.union("A", "B");

        assertEquals(unionFind.find("A"), unionFind.find("B"));
        assertNotEquals(unionFind.find("A"), unionFind.find("C"));
    }

    @Test
    public void testDeeperUnion() {
        unionFind.union("A", "B");
        unionFind.union("A", "C");
        unionFind.union("C", "D");
        unionFind.union("D", "E");

        assertEquals(unionFind.find("B"), unionFind.find("D"));
    }

    @Test
    public void testDeeperUnionSize() {
        unionFind.union("A", "B");
        unionFind.union("A", "C");
        unionFind.union("C", "D");
        unionFind.union("D", "E");

        assertEquals(unionFind.sizeOf("B"),5);
    }


    @Test
    public void testPathCompression() {
        unionFind.union("A", "B");
        unionFind.union("C", "D");
        unionFind.union("D", "E");
        unionFind.union("A", "C");

        // Must call find before calling immediateRepresentative, as find compresses its own path.
        String initialImmediateRepresentative = unionFind.immediateRepresentativeOf("B");
        String finalRepresentative = unionFind.find("B");
        String immediateRepresentative = unionFind.immediateRepresentativeOf("B");
        assertNotEquals(initialImmediateRepresentative, finalRepresentative);
        assertEquals(finalRepresentative,immediateRepresentative);
    }
}
