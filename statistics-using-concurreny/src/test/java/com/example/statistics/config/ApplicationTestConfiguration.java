package com.example.statistics.config;

import com.example.statistics.service.Statistics;
import com.example.statistics.service.StatisticsWithStorageCOWAImpl;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;

@TestConfiguration
public class ApplicationTestConfiguration {

    @Bean
    public Statistics statistics() {
        return new StatisticsWithStorageCOWAImpl();
    }
}
