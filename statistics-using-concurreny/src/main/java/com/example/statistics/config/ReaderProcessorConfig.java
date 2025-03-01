package com.example.statistics.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.time.Duration;

@ConfigurationProperties(ReaderProcessorConfig.PREFIX)
@Data
public class ReaderProcessorConfig {

    public static final String PREFIX = "processor.read";

    private Integer noOfThreads;
    private Integer count;
    private Duration interval;
}
