package com.bix.data.structures;

import com.bix.data.structures.heaps.ArrayMinMaxHeap;
import com.bix.data.structures.heaps.MinMaxHeap;
import com.bix.data.structures.helpers.ArrayHelper;
import com.bix.data.structures.helpers.RandomHelper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Created by bmoshe on 2020-07-21.
 */
public class MinMaxHeapTests {

    private static final int N = 100000;

    private Integer[] values;
    private MinMaxHeap<Integer> minMaxHeap;

    @BeforeEach
    public void setupTest() {
        final Random random = RandomHelper.createRandom();
        values = ArrayHelper.createIntArray((i, N) -> random.nextInt(), N);

        minMaxHeap = new ArrayMinMaxHeap<>(Integer.class, N);
    }

    @Test
    public void testMin() {

        Integer min = values[0];
        for(Integer value : values) {
            minMaxHeap.add(value);
            if(value < min) {
                min = value;
            }
        }

        assertEquals(min, minMaxHeap.getMin());
    }

    @Test
    public void testMax() {

        Integer max = values[0];
        for(Integer value : values) {
            minMaxHeap.add(value);
            if(max < value) {
                max = value;
            }
        }

        assertEquals(max, minMaxHeap.getMax());
    }

    @Test
    public void testRemoveMin() {
        Integer min = values[0];
        for(Integer value : values) {
            minMaxHeap.add(value);
            if(value < min) {
                min = value;
            }
        }


        Integer[] actual = new Integer[N];
        for(int i = 0; !minMaxHeap.isEmpty(); i++) {
            actual[i] = minMaxHeap.removeMin();
        }

        Arrays.sort(values);
        assertArrayEquals(values, actual);
    }

    @Test
    public void testRemoveMax() {
        for(Integer value : values) {
            minMaxHeap.add(value);
        }

        Integer[] actual = new Integer[N];
        for(int i = 0; !minMaxHeap.isEmpty(); i++) {
            actual[i] = minMaxHeap.removeMax();
        }

        Arrays.sort(values, Comparator.reverseOrder());
        assertArrayEquals(values, actual);
    }

    @Test
    public void testAlternatedRemoveMinAndMax() {
        for(Integer value : values) {
            minMaxHeap.add(value);
        }

        Integer[] actual = new Integer[N];
        for(int i = 0, t = 0, s = actual.length - 1; !minMaxHeap.isEmpty(); i++) {
            if(i % 2 == 0) {
                actual[t++] = minMaxHeap.removeMax();
            } else {
                actual[s--] = minMaxHeap.removeMin();
            }
        }

        Arrays.sort(values, Comparator.reverseOrder());
        assertArrayEquals(values, actual);
    }

    @Test
    public void testAlternatedAdditionsAndRemovals() {
        final Random rand = new Random();

        List<Integer> quirks = new LinkedList<>();
        for(Integer value : values) {
            minMaxHeap.add(value);
            int op = rand.nextInt(8);
            switch (op) {
                case 0 -> quirks.add(minMaxHeap.removeMax());
                case 1 -> quirks.add(minMaxHeap.removeMin());
            }
        }

        for(Integer value : quirks) {
            minMaxHeap.add(value);
        }

        Integer[] actual = new Integer[N];
        for(int i = 0; !minMaxHeap.isEmpty(); i++) {
            actual[i] = minMaxHeap.removeMin();
        }

        assertTrue(minMaxHeap.isEmpty());

        Arrays.sort(values);
        assertArrayEquals(values, actual);
    }
}
