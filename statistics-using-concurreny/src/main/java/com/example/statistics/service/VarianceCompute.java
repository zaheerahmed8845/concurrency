package com.example.statistics.service;

import java.util.List;
import java.util.concurrent.ConcurrentLinkedQueue;

public interface VarianceCompute {

    float sequential(ConcurrentLinkedQueue<Integer> queue);

    float parallel(ConcurrentLinkedQueue<Integer> queue, Integer chunkSize);

    float sequential(List<Integer> values);

    float parallel(List<Integer> values);
}
