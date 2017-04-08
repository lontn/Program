package com.fcu.gtml.edx.persistence;

import java.util.List;

import javax.annotation.Resource;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Test;

import com.fcu.gtml.edx.SupportSpringTest;
import com.fcu.gtml.edx.domain.TrackingStatistics;

public class TrackingStatisticsMapperTest extends SupportSpringTest {
    private static final Logger L = LogManager.getLogger();
    @Resource
    private TrackingStatisticsMapper mapper;

    @Test
    public void test() {
        List<TrackingStatistics> lists = mapper.listTrackingStatistics(null);
        L.info("lists:{}", lists.size());
        for (TrackingStatistics trackingStatistics : lists) {
            L.info("trackingStatistics:{}", trackingStatistics);
        }
    }
}
