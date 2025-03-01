package com.example.statistics.threads;

import com.example.statistics.service.Statistics;
import com.example.statistics.util.RandomUtil;
import com.example.statistics.util.ThreadUtil;
import lombok.extern.slf4j.Slf4j;

import java.time.Duration;

@Slf4j
public class WriterThread implements Runnable {

    private final Duration writeInterval;

    private final Statistics statistics;

    private final int writeCount;

    public WriterThread(Statistics statistics, int writeCount, Duration writeInterval) {
        this.statistics = statistics;
        this.writeCount = writeCount;
        this.writeInterval = writeInterval;
    }

    @Override
    public void run() {
        for (int i = 0; i < writeCount; i++) {
            int value = RandomUtil.getValue();
            statistics.event(value);
            log.debug("WriteCounter  : {} records", i);
            ThreadUtil.sleep(writeInterval);
        }
    }
}
