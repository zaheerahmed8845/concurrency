package com.example.statistics.controller;

import com.example.statistics.config.ControllerConfig;
import com.example.statistics.processor.Processor;
import com.example.statistics.service.StatisticsWithALLockImpl;
import com.example.statistics.service.StatisticsWithStorageCLQImpl;
import com.example.statistics.service.StatisticsWithStorageCOWAImpl;
import com.example.statistics.service.StatisticsWithoutStorageImpl;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

@Slf4j
@Component
@EnableConfigurationProperties(ControllerConfig.class)
@RequiredArgsConstructor
public class RunControllerImpl implements RunController {

    private final Processor readProcessor;

    private final Processor writeProcessor;

    private final ControllerConfig controllerConfig;

    public void run() {
        var writeThroughputs = new ArrayList<Long>();
        var readThroughputs = new ArrayList<Long>();

        for (int i = 0; i < controllerConfig.getRuns(); i++) {
            log.info("Start Run: {}", i);

            var statistics = switch (controllerConfig.getAlgo()) {
                case 1 -> new StatisticsWithoutStorageImpl();
                case 2 -> new StatisticsWithStorageCOWAImpl();
                case 3 -> new StatisticsWithStorageCLQImpl();
                case 4 -> new StatisticsWithALLockImpl();
                default -> throw new IllegalStateException("Unexpected value: " + controllerConfig.getAlgo());
            };

            try {
                // Run write and read operations in parallel
                var writeFuture = CompletableFuture.supplyAsync(() -> writeProcessor.process(statistics));
                var readFuture = CompletableFuture.supplyAsync(() -> readProcessor.process(statistics));

                // Wait for both tasks to complete
                var writeThroughput = writeFuture.get();
                var readThroughput = readFuture.get();

                writeThroughputs.add(writeThroughput);
                readThroughputs.add(readThroughput);
            } catch (InterruptedException | ExecutionException e) {
                log.error("Error in run {}: {}", i, e.getMessage(), e);
                Thread.currentThread().interrupt(); // Restore the interrupted status
            }

            log.info("End Run: {}", i);
        }

        // Compute average throughput
        log.info("Average Write throughput: {} requests per second across {} runs", average(writeThroughputs), controllerConfig.getRuns());
        log.info("Average Read throughput: {} requests per second across {} runs", average(readThroughputs), controllerConfig.getRuns());
    }


    private long average(List<Long> list) {
        return (long) list.stream()
                .mapToDouble(num -> num)
                .average()
                .orElse(0.0);
    }
}
