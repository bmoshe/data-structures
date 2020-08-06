package com.bix.data.structures;

import com.bix.data.structures.trees.bst.BST;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by bmoshe on 23/07/16.
 */
public class BSTTests {

    private static final int N = 250000;

    private BST<String> tree;
    private int additionOpCounter;
    private int deletionOpCounter;

    @Before
    public void setUp() {
        this.tree = new BST<>();
        this.additionOpCounter = 0;
        this.deletionOpCounter = 0;

        add("Hello");
        add("New");
        add("World");
    }

    private void add(String value) {
        tree.add(value);
        additionOpCounter++;
    }

    @Test
    public void testAdditions() {
        Assert.assertEquals("Tree size must match number of additions - number of deletions.", expectedNumberOfElements(), tree.size());
        add("Of");
        add("Freedom");
        Assert.assertEquals("Tree size must match number of additions - number of deletions.", expectedNumberOfElements(), tree.size());
        add("World");
        Assert.assertEquals("Tree size must match number of additions - number of deletions, with duplicates", expectedNumberOfElements(), tree.size());
    }

    @Test
    public void testIterator() {
        add("Aloha");
        add("Of");
        add("Freedom");
        add("World");

        List<String> expected = List.of("Aloha", "Freedom", "Hello", "New", "Of", "World", "World");
        List<String> actual = new LinkedList<>();
        tree.iterator()
            .forEachRemaining(actual::add);

        Assert.assertEquals(expected, actual);
    }

    private int expectedNumberOfElements() {
        return additionOpCounter - deletionOpCounter;
    }
}
