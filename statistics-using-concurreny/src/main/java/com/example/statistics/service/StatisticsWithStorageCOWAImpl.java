package com.example.statistics.service;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.stream.Collectors;

public class StatisticsWithStorageCOWAImpl implements Statistics {

    private static final int THRESHOLD = 100_000_000;
    List<Integer> list = new CopyOnWriteArrayList<>();

    @Override
    public void event(int n) {
        list.add(n);
    }

    @Override
    public int min() {
        return list.stream()
                .mapToInt(num -> num)
                .min()
                .orElse(Integer.MIN_VALUE);
    }

    @Override
    public int max() {
        return list.stream()
                .mapToInt(num -> num)
                .max()
                .orElse(Integer.MAX_VALUE);
    }

    @Override
    public float mean() {

        return (float) list.stream()
                .mapToDouble(num -> num)
                .average()
                .orElse(0.0f);

    }

    @Override
    public float variance() {
        if (list.isEmpty()) {
            return 0.0f;
        }
        var snapshot = getSnapshot();
        var varianceCompute = new VarianceComputeImpl();

        if (snapshot.size() < THRESHOLD) {
            return varianceCompute.sequential(snapshot);
        } else {
            return varianceCompute.parallel(snapshot);
        }
    }

    private List<Integer> getSnapshot() {
        return list.stream().collect(Collectors.toList());
    }
}
