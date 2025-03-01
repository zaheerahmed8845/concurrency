package com.example.statistics.service;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.locks.StampedLock;

public class StatisticsWithALLockImpl implements Statistics {

    private static final int THRESHOLD = 100_000_000;
    List<Integer> list = new ArrayList<>();
    StampedLock lock = new StampedLock();

    @Override
    public void event(int n) {
        long stamp = lock.writeLock();
        try {
            list.add(n);
        } finally {
            lock.unlock(stamp);
        }
    }

    @Override
    public int min() {
        long stamp = lock.readLock();
        try {
            return list.stream()
                    .mapToInt(num -> num)
                    .min()
                    .orElse(Integer.MIN_VALUE);
        } finally {
            lock.unlock(stamp);
        }
    }

    @Override
    public int max() {
        long stamp = lock.readLock();
        try {
            return list.stream()
                    .mapToInt(num -> num)
                    .max()
                    .orElse(Integer.MAX_VALUE);
        } finally {
            lock.unlock(stamp);
        }
    }

    @Override
    public float mean() {
        long stamp = lock.readLock();
        try {
            return (float) list.stream()
                    .mapToDouble(num -> num)
                    .average()
                    .orElse(0.0f);
        } finally {
            lock.unlock(stamp);
        }
    }

    @Override
    public float variance() {
        if (list.isEmpty()) {
            return 0.0f;
        }
        var varianceCompute = new VarianceComputeImpl();
        long stamp = lock.readLock();
        try {
            if (list.size() < THRESHOLD) {
                return varianceCompute.sequential(list);
            } else {
                return varianceCompute.parallel(list);
            }
        } finally {
            lock.unlock(stamp);
        }
    }

}
