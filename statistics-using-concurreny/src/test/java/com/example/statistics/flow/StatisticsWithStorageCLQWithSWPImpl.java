package com.example.statistics.flow;

import com.example.statistics.service.Statistics;
import com.example.statistics.service.VarianceCompute;
import com.example.statistics.service.VarianceComputeImpl;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.*;
import java.util.concurrent.atomic.LongAdder;

@Slf4j
public class StatisticsWithStorageCLQWithSWPImpl implements Statistics {

    BlockingQueue<Integer> queue = new ArrayBlockingQueue<>(1000);

    private final ExecutorService writerExecutor = Executors.newSingleThreadExecutor();

    ConcurrentLinkedQueue<Integer> values = new ConcurrentLinkedQueue<>();
    private static final int CHUNK_SIZE = 100_000_000;  // Process in batches
    private LongAdder size = new LongAdder();

    public StatisticsWithStorageCLQWithSWPImpl() {
        writerExecutor.submit(this::processEvents);
    }

    @Override
    public void event(int n) {
        try {
            queue.put(n); //Blocking put
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    private void processEvents() {
        try {
            while (!Thread.currentThread().isInterrupted()) {
                Integer n = queue.take();  // Blocking take()
                values.add(n);
                size.increment();
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    @Override
    public int min() {
        return values.stream()
                .mapToInt(num -> num)
                .min()
                .orElse(Integer.MIN_VALUE);
    }

    @Override
    public int max() {
        return values.stream()
                .mapToInt(num -> num)
                .max()
                .orElse(Integer.MAX_VALUE);
    }

    @Override
    public float mean() {
        return (float) values.stream()
                .mapToDouble(num -> num)
                .average()
                .orElse(0.0f);
    }

    @Override
    public float variance() {
        if (values.isEmpty()) {
            return 0.0f;
        }
        VarianceCompute varianceCompute = new VarianceComputeImpl();
        if (size.sum() < CHUNK_SIZE) {
            return varianceCompute.sequential(values);
        } else {
            return varianceCompute.parallel(values, CHUNK_SIZE);
        }
    }
}
