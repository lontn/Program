package com.fcu.gtml.edx.service;

import java.util.List;

import javax.annotation.Resource;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Test;
import com.fcu.gtml.edx.SupportSpringTest;
import com.fcu.gtml.edx.domain.TrackingCourse;

public class TrackingCourseServiceTest extends SupportSpringTest {
    private static final Logger L = LogManager.getLogger();

    @Resource
    private TrackingCourseService trackingCourseService;

    @Test
    public void test() {
       List<TrackingCourse> list = trackingCourseService.listTrackingCourse();
       L.info("list:{}", list.size());
    }
}
