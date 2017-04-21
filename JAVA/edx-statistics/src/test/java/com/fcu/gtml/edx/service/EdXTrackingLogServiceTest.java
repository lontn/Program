package com.fcu.gtml.edx.service;

import java.util.List;

import javax.annotation.Resource;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Test;

import com.fcu.gtml.edx.SupportSpringTest;
import com.fcu.gtml.edx.domain.EdXTrackingLog;

public class EdXTrackingLogServiceTest extends SupportSpringTest {
    private static final Logger L = LogManager.getLogger();
    @Resource
    private EdXTrackingLogService edXTrackingLogService;

    @Test
    public void test() {
        String courseId = "course-v1:CTTLRCx+PE11+16004";
        String datetimeString = "2016-04-08 00:00";
        String datetimeEnd = "2016-04-08 23:00";
        String[] roles = {"instructor"};
        List<EdXTrackingLog> list = edXTrackingLogService.listEdXTrackingLog(courseId, datetimeString, datetimeEnd, roles);
        L.info("list:{}", list.size());
        for (EdXTrackingLog edXTrackingLog : list) {
            L.info("edXTrackingLog:{}", edXTrackingLog);
        }
    }
}
