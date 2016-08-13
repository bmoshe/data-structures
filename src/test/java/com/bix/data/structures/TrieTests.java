package com.bix.data.structures;

import com.bix.data.structures.trees.trie.TrieNode;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 * Created by bmoshe on 30/07/16.
 */
public class TrieTests {

    private TrieNode trie;

    @Before
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
        Assert.assertTrue(trie.contains("Super"));

        trie.removeNode("Super");

        Assert.assertFalse(trie.contains("Super"));
        Assert.assertTrue(trie.contains("Supernatural"));

        // Try to remove a leaf word.
        trie.removeNode("Supernaturally");
    }

    @Test
    public void testTrieContains() {

        // Existing top-level word.
        Assert.assertTrue(trie.contains("Sup"));

        // Existing middle-level word.
        Assert.assertTrue(trie.contains("Super"));

        // Existing leaf word.
        Assert.assertTrue(trie.contains("Supernaturally"));

        // Non-existing word with existing path.
        Assert.assertFalse(trie.contains("Supernat"));

        // Non-existing word with non-existing path.
        Assert.assertFalse(trie.contains("Brother"));
    }

    @Test
    public void testTrieStartsWith() {

        // Existing top-level word.
        Assert.assertEquals(7, trie.startsWith("Sup").size());

        // Existing leaf word.
        Assert.assertEquals(1, trie.startsWith("Superb").size());

        // Non-existing word with existing path.
        Assert.assertEquals(3, trie.startsWith("Supernat").size());

        // Non-existing word with partial path.
        Assert.assertEquals(0, trie.startsWith("Superf").size());

        // Non-existing word with non-existing path.
        Assert.assertEquals(0, trie.startsWith("ABC").size());

    }
}
