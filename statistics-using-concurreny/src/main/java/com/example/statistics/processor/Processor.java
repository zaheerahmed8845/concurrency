package com.example.statistics.processor;

import com.example.statistics.service.Statistics;

public interface Processor {

    long process(Statistics statistics);

}
