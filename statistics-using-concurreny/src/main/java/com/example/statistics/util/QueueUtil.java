package com.example.statistics.util;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentLinkedQueue;

public class QueueUtil {

    // Helper method to partition the queue into smaller lists
    public static List<List<Integer>> partitionQueue(ConcurrentLinkedQueue<Integer> queue, int chunkSize) {
        List<List<Integer>> partitions = new ArrayList<>();
        List<Integer> chunk = new ArrayList<>(chunkSize);

        while (!queue.isEmpty()) {
            Integer num = queue.poll();
            if (num != null) {
                chunk.add(num);
                if (chunk.size() >= chunkSize) {
                    partitions.add(new ArrayList<>(chunk));
                    chunk.clear();
                }
            }
        }
        if (!chunk.isEmpty()) {
            partitions.add(chunk);
        }
        return partitions;
    }
}
