package com.example.statistics.processor;

import com.example.statistics.config.WriterProcessorConfig;
import com.example.statistics.service.Statistics;
import com.example.statistics.threads.WriterThread;
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
@EnableConfigurationProperties(WriterProcessorConfig.class)
@RequiredArgsConstructor
public class WriteProcessor implements Processor {

    private final WriterProcessorConfig writerProcessorConfig;

    @Override
    public long process(Statistics statistics) {
        long start = System.currentTimeMillis();
        writeProcess(statistics);
        long end = System.currentTimeMillis();
        return calculateThroughPut(start, end);
    }

    private void writeProcess(Statistics statistics) {
        ExecutorService writeExecutor = Executors.newFixedThreadPool(writerProcessorConfig.getNoOfThreads());
        for (int i = 0; i < writerProcessorConfig.getNoOfThreads(); i++) {
            writeExecutor.execute(new WriterThread(statistics, writerProcessorConfig.getCount(), writerProcessorConfig.getInterval()));
        }
        ExecutorShutdownUtil.shutdown(writeExecutor);
    }

    private long calculateThroughPut(long start, long end) {
        int totalNoOfRequest = writerProcessorConfig.getNoOfThreads() * writerProcessorConfig.getCount();
        long throughput = ThroughputUtil.getThroughput(start, end, totalNoOfRequest);
        log.info("Write throughput : {} requests per second", throughput);
        return throughput;
    }

}
