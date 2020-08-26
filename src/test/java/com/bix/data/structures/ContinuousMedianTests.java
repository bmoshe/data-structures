package com.bix.data.structures;

import com.bix.data.structures.heaps.ContinuousMedian;
import com.bix.data.structures.helpers.ArrayHelper;
import com.bix.data.structures.helpers.RandomHelper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Created by bmoshe on 2020-07-21.
 */
public class ContinuousMedianTests {

    private static final int N = 250000;

    private ContinuousMedian<Integer> continuousMedian;
    private Integer[] allValues;

    @BeforeEach
    public void setupTest() {
        continuousMedian = new ContinuousMedian<>();

        final Random random = RandomHelper.createRandom();
        allValues = ArrayHelper.createIntArray((i, N) -> random.nextInt(N), N);
    }

    @Test
    public void testGetMedian() {

        for(Integer value : allValues) {
            continuousMedian.add(value);
        }

        Arrays.sort(allValues);
        assertEquals(continuousMedian.getMedian(), allValues[(allValues.length - 1) / 2]);
    }

    @Test
    public void testRemoveMedian() {
        for(Integer value : allValues) {
            continuousMedian.add(value);
        }

        continuousMedian.removeMedian();
        Integer newMedian = continuousMedian.getMedian();

        Arrays.sort(allValues);
        Integer onLeft = allValues[(allValues.length - 1) / 2 - 1];
        Integer onRight = allValues[(allValues.length - 1) / 2 + 1];
        assertTrue(newMedian.equals(onLeft) || newMedian.equals(onRight));
    }

}
