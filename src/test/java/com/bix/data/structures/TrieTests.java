package com.bix.data.structures;

import com.bix.data.structures.trees.trie.TrieNode;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Created by bmoshe on 30/07/16.
 */
public class TrieTests {

    private TrieNode trie;

    @BeforeEach
    public void setupTest() {
        trie = new TrieNode();

        trie.addNode("Hello");
        trie.addNode("World");
        trie.addNode("Flyout");
        trie.addNode("System");
        trie.addNode("Systematic");
        trie.addNode("Super");
        trie.addNode("Superlative");
        trie.addNode("Supernatural");
        trie.addNode("Superb");
        trie.addNode("Supernature");
        trie.addNode("Sup");
        trie.addNode("Supernaturally");
    }

    @Test
    public void testTrieRemove() {

        // Try to remove a value that doesn't exist at all.
        trie.removeNode("Brother");

        // Try to remove a value that isn't a word, but has a valid path in the tree.
        trie.removeNode("Supernat");

        // Try to remove a non-leaf word.
        assertTrue(trie.contains("Super"));

        trie.removeNode("Super");

        assertFalse(trie.contains("Super"));
        assertTrue(trie.contains("Supernatural"));

        // Try to remove a leaf word.
        trie.removeNode("Supernaturally");
    }

    @Test
    public void testTrieContains() {

        // Existing top-level word.
        assertTrue(trie.contains("Sup"));

        // Existing middle-level word.
        assertTrue(trie.contains("Super"));

        // Existing leaf word.
        assertTrue(trie.contains("Supernaturally"));

        // Non-existing word with existing path.
        assertFalse(trie.contains("Supernat"));

        // Non-existing word with non-existing path.
        assertFalse(trie.contains("Brother"));
    }

    @Test
    public void testTrieStartsWith() {

        // Existing top-level word.
        assertEquals(7, trie.startsWith("Sup").size());

        // Existing leaf word.
        assertEquals(1, trie.startsWith("Superb").size());

        // Non-existing word with existing path.
        assertEquals(3, trie.startsWith("Supernat").size());

        // Non-existing word with partial path.
        assertEquals(0, trie.startsWith("Superf").size());

        // Non-existing word with non-existing path.
        assertEquals(0, trie.startsWith("ABC").size());

    }
}
