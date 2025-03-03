package com.example.completable.future.pipelining.util;

import java.util.Random;

public class RandomUtil {

    private static Random random = new Random();

    public static Random getRandom() {
        return random;
    }
}
