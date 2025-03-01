package com.example.statistics.service;

import com.example.statistics.domain.Result;
import com.example.statistics.util.ExecutorShutdownUtil;
import com.example.statistics.util.QueueUtil;
import lombok.extern.slf4j.Slf4j;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.Collectors;

@Slf4j
public class VarianceComputeImpl implements VarianceCompute {

    private static final int THREADS = Runtime.getRuntime().availableProcessors();
    private static final ExecutorService executor = Executors.newFixedThreadPool(THREADS);

    @Override
    public float sequential(ConcurrentLinkedQueue<Integer> queue) {
        log.debug("Computing Variance Sequentially");
        List<Integer> snapshot = getSnapshot(queue);
        return sequential(snapshot);
    }

    @Override
    public float parallel(ConcurrentLinkedQueue<Integer> queue, Integer chunkSize) {
        log.debug("Computing Variance Parallely");
        return computeVarianceFromPartitions(QueueUtil.partitionQueue(queue, chunkSize));
    }

    @Override
    public float sequential(List<Integer> values) {

        float mean = (float) values.stream()
                .mapToDouble(num -> num)
                .average()
                .orElse(0.0f);

        return (float) values.stream()
                .mapToDouble(num -> Math.pow(num - mean, 2))
                .average()
                .orElse(0.0f);
    }

    @Override
    public float parallel(List<Integer> values) {
        float mean = (float) values.parallelStream()
                .mapToDouble(num -> num)
                .average()
                .orElse(0.0f);

        return (float) values.parallelStream()
                .mapToDouble(num -> Math.pow(num - mean, 2))
                .average()
                .orElse(0.0f);
    }

    private List<Integer> getSnapshot(ConcurrentLinkedQueue<Integer> queue) {
        return queue.stream().collect(Collectors.toList());
    }

    // Compute mean and variance for a single partition
    private CompletableFuture<Result> computeVariance(List<Integer> partition) {
        return CompletableFuture.supplyAsync(() -> {
            if (partition.isEmpty()) return new Result(0, 0.0f, 0.0f);

            float mean = (float) partition.stream()
                    .mapToDouble(num -> num)
                    .average()
                    .orElse(0.0);
            float variance = (float) partition.stream()
                    .mapToDouble(num -> Math.pow(num - mean, 2))
                    .average()
                    .orElse(0.0);

            return new Result(partition.size(), mean, variance);
        }, executor);
    }

    private Result mergeResults(List<Result> results) {
        int totalCount = 0;
        float globalMean = 0.0f;
        float globalVariance = 0.0f;

        for (Result res : results) {
            int count = res.getCount();
            if (count == 0) continue;

            float delta = res.getMean() - globalMean;
            totalCount += count;
            globalMean += (delta * count) / totalCount;
            globalVariance += res.getVariance() * count + delta * delta * totalCount * count / (float) totalCount;
        }

        return new Result(totalCount, globalMean, globalVariance / totalCount);
    }

    // Compute variance from multiple partitions
    private float computeVarianceFromPartitions(List<List<Integer>> partitions) {
        List<CompletableFuture<Result>> futures = partitions.stream().map(this::computeVariance).collect(Collectors.toList());

        List<Result> results = futures.stream()
                .map(CompletableFuture::join)
                .collect(Collectors.toList());

        ExecutorShutdownUtil.shutdown(executor);
        return mergeResults(results).getVariance();
    }
}
