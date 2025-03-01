package com.example.statistics.util;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ThroughputUtil {

    public static long getThroughput(long start, long end, int totalNoOfRequest) {
        long timetaken = (end - start) / 1000;
        log.debug("Time Taken : {} seconds" + " , total no of requests : {}", timetaken, totalNoOfRequest);
        if (timetaken > 0) {
            return totalNoOfRequest / timetaken;
        } else {
            return totalNoOfRequest;
        }
    }
}
