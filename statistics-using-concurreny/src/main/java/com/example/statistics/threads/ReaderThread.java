package com.example.statistics.threads;

import com.example.statistics.service.Statistics;
import com.example.statistics.util.Stat;
import com.example.statistics.util.StatUtil;
import com.example.statistics.util.ThreadUtil;
import lombok.extern.slf4j.Slf4j;

import java.time.Duration;

@Slf4j
public class ReaderThread implements Runnable {

    private final Duration readInterval;

    private final int readCount;

    private final Statistics statistics;

    public ReaderThread(Statistics statistics, int readCount, Duration readInterval) {
        this.statistics = statistics;
        this.readInterval = readInterval;
        this.readCount = readCount;
    }

    @Override
    public void run() {
        for (int i = 0; i < readCount; i++) {
            Stat stat = StatUtil.getRandomStat();
            switch (stat) {
                case Stat.MIN -> getMin(i);
                case Stat.MAX -> getMax(i);
                case Stat.MEAN -> getMean(i);
                case Stat.VARIANCE -> getVariance(i);
            }
            ThreadUtil.sleep(readInterval);
        }
    }

    private void getMin(int counter) {
        var start = System.currentTimeMillis();
        var min = statistics.min();
        var end = System.currentTimeMillis();
        var timetaken = end - start;
        log.debug("ReadCounter : {} , Min value : {}, time taken : {} ms", counter, min, timetaken);
    }

    private void getMax(int counter) {
        var start = System.currentTimeMillis();
        var max = statistics.max();
        var end = System.currentTimeMillis();
        var timetaken = end - start;
        log.debug("ReadCounter : {} , Max value : {}, time taken : {} ms", counter, max, timetaken);
    }

    private void getMean(int counter) {
        var start = System.currentTimeMillis();
        var mean = statistics.mean();
        var end = System.currentTimeMillis();
        var timetaken = end - start;
        log.debug("ReadCounter : {} , Mean value : {}, time taken : {} ms", counter, mean, timetaken);
    }

    private void getVariance(int counter) {
        var start = System.currentTimeMillis();
        var variance = statistics.variance();
        var end = System.currentTimeMillis();
        var timetaken = end - start;
        log.debug("ReadCounter : {} , Variance value : {}, time taken : {} ms", counter, variance, timetaken);
    }
}
