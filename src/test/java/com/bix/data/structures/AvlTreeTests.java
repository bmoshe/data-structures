package com.bix.data.structures;

import com.bix.data.structures.helpers.AvlTreeHelper;
import com.bix.data.structures.helpers.FunctionalInteger;
import com.bix.data.structures.helpers.RandomHelper;
import com.bix.data.structures.trees.avl.AvlTree;

import org.junit.Assert;
import org.junit.Test;

import java.util.LinkedList;
import java.util.List;
import java.util.Random;

/**
 * Created by bmoshe on 23/07/16.
 */
public class AvlTreeTests {

    private static final int N = 250000;

    @Test
    public void testAscendingTreeConstruction() {

        AvlTree<Integer> tree = AvlTreeHelper.create((i, N) -> N - i, N);
        Assert.assertTrue("Failed to test random tree construction for depth = " + tree.getDepth() + " and nodeCounter = " + tree.getNodeCounter(),
                          tree.getDepth() <= expectedUpperBoundTreeDepth(tree.getNodeCounter()));
    }

    @Test
    public void testDescendingTreeConstruction() {

        AvlTree<Integer> tree = AvlTreeHelper.create((i, N) -> i, N);
        Assert.assertTrue("Failed to test random tree construction for depth = " + tree.getDepth() + " and nodeCounter = " + tree.getNodeCounter(),
                          tree.getDepth() <= expectedUpperBoundTreeDepth(tree.getNodeCounter()));
    }

    @Test
    public void testRandomTreeConstruction() {

        final Random random = RandomHelper.createRandom();

        AvlTree<Integer> tree = AvlTreeHelper.create((i, N) -> random.nextInt(N), N);
        Assert.assertTrue("Failed to test random tree construction for depth = " + tree.getDepth() + " and nodeCounter = " + tree.getNodeCounter(),
                          tree.getDepth() <= expectedUpperBoundTreeDepth(tree.getNodeCounter()));
    }

    @Test
    public void testContains() {

        final Random random = RandomHelper.createRandom();
        AvlTree<Integer> tree = AvlTreeHelper.create((i, N) -> 2 * random.nextInt(N), N); // all even.

        int itemToRemove = 2 * random.nextInt(N / 2) + 1; // an odd
        Assert.assertFalse("Failed to test contains when item does not exist", tree.contains(itemToRemove));

        tree.insert(itemToRemove);
        Assert.assertTrue("Failed to test contains when item exists", tree.contains(itemToRemove));
    }

    @Test
    public void testAscendingOrder() {
        AvlTree<Integer> tree = AvlTreeHelper.create((i, N) -> i, N);

        final FunctionalInteger k = new FunctionalInteger(Integer.MIN_VALUE);
        tree.scanAscendingly((v) -> {
            Assert.assertTrue(k.getValue() < v);
            k.setValue(v);
        });
    }

    @Test
    public void testDescendingOrder() {
        AvlTree<Integer> tree = AvlTreeHelper.create((i, N) -> i, N);

        final FunctionalInteger k = new FunctionalInteger(Integer.MAX_VALUE);
        tree.scanDescendingly((v) -> {
            Assert.assertTrue(v < k.getValue());
            k.setValue(v);
        });
    }

    @Test
    public void testPreOrder() {
        AvlTree<Integer> tree = AvlTreeHelper.createEmpty();

        tree.insert(5);
        tree.insert(3);         //           5
        tree.insert(8);         //         /   \
        tree.insert(1);         //       3       8
        tree.insert(4);         //      / \     / \
        tree.insert(6);         //     1   4   6   10
        tree.insert(10);

        final Integer[] expectedPreOrderValues = new Integer[] { 5, 3, 1, 4, 8, 6, 10 };
        final FunctionalInteger k = new FunctionalInteger(0);
        tree.scanPreOrder((v) -> Assert.assertEquals(expectedPreOrderValues[k.getAndIncrement()], v));
    }

    @Test
    public void testPostOrder() {
        AvlTree<Integer> tree = AvlTreeHelper.createEmpty();

        tree.insert(5);
        tree.insert(3);         //           5
        tree.insert(8);         //         /   \
        tree.insert(1);         //       3       8
        tree.insert(4);         //      / \     / \
        tree.insert(6);         //     1   4   6   10
        tree.insert(10);

        final Integer[] expectedPostOrderValues = new Integer[] { 1, 4, 3, 6, 10, 8, 5 };
        final FunctionalInteger k = new FunctionalInteger(0);
        tree.scanPostOrder((v) -> Assert.assertEquals(expectedPostOrderValues[k.getAndIncrement()], v));
    }

    @Test
    public void testEmptyTree() {
        AvlTree<Integer> tree = AvlTreeHelper.createEmpty();

        Assert.assertNull(tree.getRoot());
        Assert.assertEquals(tree.getDepth(), 0);
        Assert.assertEquals(tree.getNodeCounter(), 0);
        Assert.assertFalse(tree.contains(0));

        tree.insert(0);
        Assert.assertNotNull(tree.getRoot());
        Assert.assertEquals(tree.getDepth(), 1);
        Assert.assertEquals(tree.getRoot(), (Integer) 0);
        Assert.assertEquals(tree.getNodeCounter(), 1);
    }

    @Test
    public void testDeletions() {

        // Test deletion when items are inserted in ascending order.
        AvlTree<Integer> tree = AvlTreeHelper.create((i, N) -> i, N);
        for(int i = 0; i < N; i += 3)
            tree.delete(i);

        Assert.assertTrue(tree.getDepth() <= expectedUpperBoundTreeDepth(tree.getNodeCounter()));

        final FunctionalInteger k = new FunctionalInteger();
        tree.scanAscendingly((v) -> {
            if(k.getValue() % 3 == 0)
                k.increment();

            Assert.assertEquals(k.getValue(), v);
            k.increment();
        });

        // Test deletion when items are inserted in descending order.
        tree = AvlTreeHelper.create((i, N) -> N - i - 1, N);
        for(int i = 0; i < N; i += 3)
            tree.delete(i);

        Assert.assertTrue(tree.getDepth() <= expectedUpperBoundTreeDepth(tree.getNodeCounter()));

        k.setValue(0);
        tree.scanAscendingly((v) -> {
            if(k.getValue() % 3 == 0)
                k.increment();

            Assert.assertEquals(k.getValue(), v);
            k.increment();
        });
    }

    private int expectedUpperBoundTreeDepth(int nodeCounter) {
        int logn = (int) Math.round(Math.ceil(Math.log(nodeCounter + 1) / Math.log(2)));
        return (int) Math.round(Math.ceil(1.44f * logn));
    }
}
