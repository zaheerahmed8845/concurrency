package com.example.statistics.util;

import java.util.Random;

public class RandomUtil {

    private static final Random random = new Random();

    public static int getValue() {
        return random.nextInt(100);
    }

}
