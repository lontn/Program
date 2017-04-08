package com.fcu.gtml.edx.service;

import java.util.List;

import javax.annotation.Resource;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Test;

import com.fcu.gtml.edx.SupportSpringTest;
import com.fcu.gtml.edx.domain.TrackingStatistics;

public class TrackingStatisticsServiceTest extends SupportSpringTest {
    private static final Logger L = LogManager.getLogger();
    @Resource
    private TrackingStatisticsService service;

    @Test
    public void test() {
        //List<TrackingStatistics> list = service.listTrackingStatistics("");
        //L.info("list:{}", list.size());
    }
}
