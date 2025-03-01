package com.example.statistics.service;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.DoubleAdder;
import java.util.concurrent.atomic.LongAdder;
import java.util.concurrent.locks.StampedLock;

public class StatisticsWithoutStorageImpl implements Statistics {

    private final LongAdder count = new LongAdder();
    private final DoubleAdder m2 = new DoubleAdder();
    private final AtomicInteger min = new AtomicInteger(Integer.MAX_VALUE);
    private final AtomicInteger max = new AtomicInteger(Integer.MIN_VALUE);
    private final StampedLock lock = new StampedLock(); // Lock for mean & variance updates
    private volatile double mean = 0.0; // Mean stored separately

    @Override
    public void event(int num) {
        count.increment();

        // Atomic min and max updates
        min.accumulateAndGet(num, Math::min);
        max.accumulateAndGet(num, Math::max);

        long stamp = lock.writeLock();
        try {
            long n = count.longValue();
            double delta = num - mean;
            mean += delta / n;
            m2.add(delta * (num - mean)); // Welford's online variance update
        } finally {
            lock.unlockWrite(stamp);
        }
    }

    @Override
    public int min() {

        return min.get();
    }

    @Override
    public int max() {

        return max.get();
    }

    @Override
    public float mean() {

        return (float) mean;
    }

    @Override
    public float variance() {
        long stamp = lock.tryOptimisticRead();
        long n = count.longValue();
        double m2Value = m2.sum();

        // If another thread modified `count` or `m2` during the read, retry with full read lock
        if (!lock.validate(stamp)) {
            stamp = lock.readLock();
            try {
                n = count.longValue();
                m2Value = m2.sum();
            } finally {
                lock.unlockRead(stamp);
            }
        }

        if (n < 2) return 0.0f; // Avoid division by zero in sample variance
        return (float) m2Value / n;
    }
}
