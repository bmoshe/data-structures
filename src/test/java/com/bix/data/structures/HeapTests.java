package com.bix.data.structures;

import com.bix.data.structures.heaps.ArrayHeap;
import com.bix.data.structures.heaps.Heap;
import com.bix.data.structures.helpers.ArrayHelper;
import com.bix.data.structures.helpers.FunctionalConstructVisitor;
import com.bix.data.structures.helpers.HeapHelper;
import com.bix.data.structures.helpers.RandomHelper;
import org.junit.jupiter.api.Test;

import java.util.Random;

import static java.util.Comparator.reverseOrder;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Created by bmoshe on 24/07/16.
 */
public class HeapTests {

    private static final int N = 250000;
    private static final int NUM_PASSES = 10;
    private static final int SEGMENT_SIZE = N / NUM_PASSES;

    @Test
    public void testHeapConstruction() {
        final Random random = RandomHelper.createRandom();
        Heap<Integer> heap = HeapHelper.create((i, N) -> random.nextInt(N), N);

        Integer actualMax = heap.removeTop();
        while(!heap.isEmpty()) {
            assertNotNull(actualMax);

            Integer newMax = heap.removeTop();
            assertTrue(actualMax >= newMax);

            actualMax = newMax;
        }

        assertTrue(heap.isEmpty());
        assertNull(heap.getTop());
    }

    @Test
    public void testHeapRemoval() {

        Heap<Integer> heap = HeapHelper.create((i, N) -> i, N);
        for(int i = 0; i < NUM_PASSES; i++) {
            int itemToRemove = SEGMENT_SIZE * i + (int) (System.currentTimeMillis() % SEGMENT_SIZE);

            // Remove existing value.
            int removedItem = heap.remove(itemToRemove);
            assertEquals(removedItem, itemToRemove);

            // Remove not-existing value (by removing the same item twice).
            assertEquals(heap.getSize(), N - i - 1);
            assertNull(heap.remove(itemToRemove));
        }

        assertEquals(heap.getMaxSize(), N);
        assertEquals(heap.getSize(), N - NUM_PASSES);
        assertFalse(heap.isEmpty());
    }

    @Test
    public void testHeapComparator() {
        Heap<Integer> heap = HeapHelper.create((FunctionalConstructVisitor<Integer>) (i, N) -> i, N, reverseOrder());

        // Testing that minimal value is indeed the top.
        assertEquals((int) heap.getTop(), 0);

        // Testing removal of NUM_PASSES items.
        final Random random = RandomHelper.createRandom();
        for(int i = 0; i < NUM_PASSES; i++) {
            int itemToRemove = SEGMENT_SIZE * i + random.nextInt(SEGMENT_SIZE);

            int removedItem = heap.remove(itemToRemove);
            assertEquals(removedItem, itemToRemove);

            assertEquals(heap.getSize(), N - i - 1);
            assertNull(heap.remove(itemToRemove));
        }

        // Testing that removeTop maintains correct order.
        int min = heap.getTop();
        while(!heap.isEmpty()) {
            int currentValue = heap.removeTop();
            assertTrue(min <= currentValue);

            min = currentValue;
        }
    }

    @Test
    public void testHeapSort() {

        final Random random = RandomHelper.createRandom();
        final Integer[] arrayToSort = ArrayHelper.createIntArray((i, N) -> random.nextInt(N), N);

        ArrayHeap.sort(arrayToSort);
        for(int i = 1; i < N; i++) {
            assertTrue(
                arrayToSort[i - 1] <= arrayToSort[i],
                arrayToSort[i - 1] + " should be <= than " + arrayToSort[i]
            );
        }
    }

    @Test
    public void testHeapSortWithComparator() {

        final Random random = RandomHelper.createRandom();
        final Integer[] arrayToSort = ArrayHelper.createIntArray((i, N) -> random.nextInt(N), N);

        ArrayHeap.sort(arrayToSort, reverseOrder());

        for(int i = 1; i < N; i++) {
            assertTrue(
                arrayToSort[i] <= arrayToSort[i - 1],
                arrayToSort[i] + " should be <= than " + arrayToSort[i - 1]
            );
        }
    }
}
