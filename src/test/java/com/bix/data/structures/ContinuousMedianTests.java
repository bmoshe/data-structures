package com.bix.data.structures;

import com.bix.data.structures.heaps.ContinuousMedian;
import com.bix.data.structures.helpers.ArrayHelper;
import com.bix.data.structures.helpers.RandomHelper;
import org.junit.Assert;
import org.junit.Test;

import java.util.Arrays;
import java.util.Random;

/**
 * Created by bmoshe on 24/07/16.
 */
public class ContinuousMedianTests {

    private static final int N = 10000;

    @Test
    public void testGetMedian() {
        ContinuousMedian<Integer> continuousMedian = new ContinuousMedian<>();

        final Random random = RandomHelper.createRandom();
        final Integer[] allValues = ArrayHelper.createIntArray((i, N) -> random.nextInt(N), N);

        for(Integer value : allValues) {
            continuousMedian.add(value);
        }

        Arrays.sort(allValues);
        Assert.assertEquals(continuousMedian.getMedian(), allValues[(allValues.length - 1) / 2]);
    }

    @Test
    public void testRemoveMedian() {
        ContinuousMedian<Integer> continuousMedian = new ContinuousMedian<>();

        final Random random = RandomHelper.createRandom();
        final Integer[] allValues = ArrayHelper.createIntArray((i, N) -> random.nextInt(N), N);

        for(Integer value : allValues) {
            continuousMedian.add(value);
        }

        continuousMedian.removeMedian();
        Integer newMedian = continuousMedian.getMedian();

        Arrays.sort(allValues);
        Integer onLeft = allValues[(allValues.length - 1) / 2 - 1];
        Integer onRight = allValues[(allValues.length - 1) / 2 + 1];
        Assert.assertTrue(newMedian.equals(onLeft) || newMedian.equals(onRight));
    }

}
