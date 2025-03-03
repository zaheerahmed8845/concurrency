package com.example.completable.future.parallelism.util;

import com.example.completable.future.parallelism.domain.Shop;

import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;

public class ExecutorUtil {

    public static Executor getExecutor(List<Shop> shops) {
        Executor executor = Executors.newFixedThreadPool(
                Math.min(shops.size(), 100), new ThreadFactory() {
                    @Override
                    public Thread newThread(Runnable r) {
                        Thread t = new Thread(r);
                        t.setDaemon(true);
                        return t;
                    }
                }
        );
        return executor;
    }
}
