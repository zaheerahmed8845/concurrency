package com.example.completable.future.parallelism.util;

import java.util.Random;

public class RandomUtil {

    private static Random random = new Random();

    public static Random getRandom() {
        return random;
    }
}
