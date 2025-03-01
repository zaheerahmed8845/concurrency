package com.example.statistics.service;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.atomic.LongAdder;

@Slf4j
public class StatisticsWithStorageCLQImpl implements Statistics {

    ConcurrentLinkedQueue<Integer> queue = new ConcurrentLinkedQueue<>();
    private static final int CHUNK_SIZE = 100_000_000;  // Process in batches
    private LongAdder size = new LongAdder();

    @Override
    public void event(int n) {
        queue.add(n);
        size.increment();
    }

    @Override
    public int min() {
        return queue.stream()
                .mapToInt(num -> num)
                .min()
                .orElse(Integer.MIN_VALUE);
    }

    @Override
    public int max() {
        return queue.stream()
                .mapToInt(num -> num)
                .max()
                .orElse(Integer.MAX_VALUE);
    }

    @Override
    public float mean() {
        return (float) queue.stream()
                .mapToDouble(num -> num)
                .average()
                .orElse(0.0f);
    }

    @Override
    public float variance() {
        if (queue.isEmpty()) {
            return 0.0f;
        }
        VarianceCompute varianceCompute = new VarianceComputeImpl();
        if (size.sum() < CHUNK_SIZE) {
            return varianceCompute.sequential(queue);
        } else {
            return varianceCompute.parallel(queue, CHUNK_SIZE);
        }
    }
}
