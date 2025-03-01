package com.example.statistics.processor;

import com.example.statistics.config.ReaderProcessorConfig;
import com.example.statistics.service.Statistics;
import com.example.statistics.threads.ReaderThread;
import com.example.statistics.util.ExecutorShutdownUtil;
import com.example.statistics.util.ThroughputUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Slf4j
@Component
@EnableConfigurationProperties(ReaderProcessorConfig.class)
@RequiredArgsConstructor
public class ReadProcessor implements Processor {

    private final ReaderProcessorConfig readerProcessorConfig;

    @Override
    public long process(Statistics statistics) {
        long start = System.currentTimeMillis();
        readProcess(statistics);
        long end = System.currentTimeMillis();
        return calculateThroughPut(start, end);
    }

    private void readProcess(Statistics statistics) {
        ExecutorService readExecutor = Executors.newFixedThreadPool(readerProcessorConfig.getNoOfThreads());
        for (int i = 0; i < readerProcessorConfig.getNoOfThreads(); i++) {
            readExecutor.execute(new ReaderThread(statistics, readerProcessorConfig.getCount(), readerProcessorConfig.getInterval()));
        }
        ExecutorShutdownUtil.shutdown(readExecutor);
    }

    private long calculateThroughPut(long start, long end) {
        int totalNoOfRequest = readerProcessorConfig.getNoOfThreads() * readerProcessorConfig.getCount();
        long throughput = ThroughputUtil.getThroughput(start, end, totalNoOfRequest);
        log.info("Read throughput : {} requests per second", throughput);
        return throughput;
    }
}
