package com.example.statistics.service;

import com.example.statistics.config.ApplicationTestConfiguration;
import com.example.statistics.util.StatisticsUtil;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
@Import(ApplicationTestConfiguration.class)
public class StatisticsTest {

    @Autowired
    private Statistics statistics;

    @Test
    public void test1() {
        statistics.event(2);
        statistics.event(4);
        statistics.event(6);
        statistics.event(8);
        statistics.event(10);

        StatisticsUtil statisticsUtil = new StatisticsUtil();
        statisticsUtil.event(2);
        statisticsUtil.event(4);
        statisticsUtil.event(6);
        statisticsUtil.event(8);
        statisticsUtil.event(10);

        Assertions.assertEquals(statistics.min(), statisticsUtil.min());
        Assertions.assertEquals(statistics.max(), statisticsUtil.max());
        Assertions.assertEquals(statistics.mean(), statisticsUtil.mean());
        Assertions.assertEquals(statistics.variance(), statisticsUtil.variance());

    }
}
