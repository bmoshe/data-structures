package com.bix.data.structures;

import com.bix.data.structures.helpers.IteratorHelper;
import com.bix.data.structures.trees.bst.BST;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import static java.util.Spliterator.ORDERED;

/**
 * Created by bmoshe on 23/07/16.
 */
public class BSTTests {

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
    public void testEmptyTree() {
        BST<String> emptyTree = new BST<>();

        Assert.assertEquals(0, emptyTree.size());
        Assert.assertFalse(emptyTree.preOrderIterator().hasNext());
        Assert.assertFalse(emptyTree.inOrderIterator().hasNext());
        Assert.assertFalse(emptyTree.postOrderIterator().hasNext());
    }

    @Test
    public void testInOrderIterator() {
        add("Aloha");
        add("Of");
        add("Freedom");
        add("World");

        List<String> expected = List.of("Aloha", "Freedom", "Hello", "New", "Of", "World", "World");
        List<String> actual = IteratorHelper.convertToList(tree.inOrderIterator());

        Assert.assertEquals(expected, actual);
    }

    @Test
    public void testPreOrderIterator() {
        add("Aloha");
        add("Of");
        add("Freedom");
        add("World");

        List<String> expected = List.of("Hello", "Aloha", "Freedom", "New", "World", "Of", "World");
        List<String> actual = IteratorHelper.convertToList(tree.preOrderIterator());

        Assert.assertEquals(expected, actual);
    }

    @Test
    public void testPostOrderIterator() {
        add("Aloha");
        add("Of");
        add("Freedom");
        add("World");

        List<String> expected = List.of("Freedom", "Aloha", "Of", "World", "World", "New", "Hello");
        List<String> actual = IteratorHelper.convertToList(tree.postOrderIterator());

        Assert.assertEquals(expected, actual);
    }

    private int expectedNumberOfElements() {
        return additionOpCounter - deletionOpCounter;
    }
}
