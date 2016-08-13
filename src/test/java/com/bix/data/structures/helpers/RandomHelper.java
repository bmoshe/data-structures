package com.bix.data.structures.helpers;

import java.util.Random;

/**
 * Created by bmoshe on 03/08/16.
 */
public class RandomHelper {

    public static Random createRandom() {

        final long seed = System.currentTimeMillis();
        final Random random = createRandom(seed);

        return random;
    }

    public static Random createRandom(long seed) {

        LogHelper.infoAsAbove("Using " + seed + " as seed");

        final Random random = new Random(seed);
        return random;
    }
}
