package com.example.statistics.flow;

import com.example.statistics.service.StatisticsWithStorageCLQImpl;
import com.example.statistics.util.RandomUtil;
import lombok.extern.slf4j.Slf4j;

import java.util.stream.IntStream;

@Slf4j
public class StatisticsWithStorageCLQTest {

    public static void main(String[] args) {
        var statistics = new StatisticsWithStorageCLQImpl();

        //int start = 1, end = 5_000000;
        //int start = 1, end = 10_000000;
        //int start = 1, end = 50_000000;
        //int start = 1, end = 100_000000;
        int start = 1, end = 500_000000;

        IntStream.range(start, end)
                .map(i -> RandomUtil.getValue())
                .forEach(statistics::event);

        log.info("Loaded total records : {}", end);

        long startTime = System.currentTimeMillis();
        statistics.variance();
        long endTime = System.currentTimeMillis();
        log.info("Time taken to calculate variance : {} ms", endTime - startTime);

    }
}
