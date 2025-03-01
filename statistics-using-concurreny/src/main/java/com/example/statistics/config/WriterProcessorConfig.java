package com.example.statistics.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.time.Duration;

@ConfigurationProperties(WriterProcessorConfig.PREFIX)
@Data
public class WriterProcessorConfig {

    public static final String PREFIX = "processor.write";

    private Integer noOfThreads;
    private Integer count;
    private Duration interval;
}
