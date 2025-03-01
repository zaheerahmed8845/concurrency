package com.example.statistics.util;

import java.util.ArrayList;
import java.util.List;

public class StatisticsUtil {
    List<Integer> list = new ArrayList<>();

    public void event(int n) {
        list.add(n);
    }

    public int min() {
        return list.stream()
                .mapToInt(num -> num)
                .min()
                .orElse(Integer.MIN_VALUE);
    }

    public int max() {
        return list.stream()
                .mapToInt(num -> num)
                .max()
                .orElse(Integer.MAX_VALUE);
    }

    public float mean() {
        return (float) list.stream()
                .mapToDouble(num -> num)
                .average()
                .orElse(0.0f);

    }

    public float variance() {
        if (list.isEmpty())
            return 0.0f;

        float mean = (float) list.stream()
                .mapToDouble(num -> num)
                .average()
                .orElse(0.0);

        return (float) list.stream()
                .mapToDouble(num -> Math.pow(num - mean, 2))
                .average()
                .orElse(0.0);
    }
}
