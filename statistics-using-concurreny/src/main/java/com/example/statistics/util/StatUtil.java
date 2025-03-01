package com.example.statistics.util;

import java.util.concurrent.ThreadLocalRandom;

public class StatUtil {

    public static Stat getRandomStat() {
        var stats = Stat.values();
        var randomIndex = ThreadLocalRandom.current().nextInt(stats.length);
        return stats[randomIndex];
    }
}
