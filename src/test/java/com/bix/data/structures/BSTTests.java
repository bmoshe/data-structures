package com.bix.data.structures;

import com.bix.data.structures.helpers.IteratorHelper;
import com.bix.data.structures.trees.bst.BST;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

/**
 * Created by bmoshe on 23/07/16.
 */
public class BSTTests {

    private BST<String> tree;
    private int additionOpCounter;
    private int deletionOpCounter;

    @BeforeEach
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
        assertEquals(expectedNumberOfElements(), tree.size(), "Tree size must match number of additions - number of deletions.");
        add("Of");
        add("Freedom");
        assertEquals(expectedNumberOfElements(), tree.size(), "Tree size must match number of additions - number of deletions.");
        add("World");
        assertEquals(expectedNumberOfElements(), tree.size(), "Tree size must match number of additions - number of deletions, with duplicates");
    }

    @Test
    public void testEmptyTree() {
        BST<String> emptyTree = new BST<>();

        assertEquals(0, emptyTree.size());
        assertFalse(emptyTree.preOrderIterator().hasNext());
        assertFalse(emptyTree.inOrderIterator().hasNext());
        assertFalse(emptyTree.postOrderIterator().hasNext());
    }

    @Test
    public void testInOrderIterator() {
        add("Aloha");
        add("Of");
        add("Freedom");
        add("World");

        List<String> expected = List.of("Aloha", "Freedom", "Hello", "New", "Of", "World", "World");
        List<String> actual = IteratorHelper.convertToList(tree.inOrderIterator());

        assertEquals(expected, actual);
    }

    @Test
    public void testPreOrderIterator() {
        add("Aloha");
        add("Of");
        add("Freedom");
        add("World");

        List<String> expected = List.of("Hello", "Aloha", "Freedom", "New", "World", "Of", "World");
        List<String> actual = IteratorHelper.convertToList(tree.preOrderIterator());

        assertEquals(expected, actual);
    }

    @Test
    public void testPostOrderIterator() {
        add("Aloha");
        add("Of");
        add("Freedom");
        add("World");

        List<String> expected = List.of("Freedom", "Aloha", "Of", "World", "World", "New", "Hello");
        List<String> actual = IteratorHelper.convertToList(tree.postOrderIterator());

        assertEquals(expected, actual);
    }

    private int expectedNumberOfElements() {
        return additionOpCounter - deletionOpCounter;
    }
}
