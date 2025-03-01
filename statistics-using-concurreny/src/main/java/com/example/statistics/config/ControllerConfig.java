package com.example.statistics.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(ControllerConfig.PREFIX)
@Data
public class ControllerConfig {

    public static final String PREFIX = "controller";

    private Integer runs;

    private Integer algo;
}
