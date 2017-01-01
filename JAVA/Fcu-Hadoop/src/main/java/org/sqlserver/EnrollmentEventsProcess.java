package org.sqlserver;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.common.utils.JsonParser;
import org.common.utils.ParseException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.fcu.gtml.domain.EnrollmentEventsLog;
import com.fcu.gtml.domain.TrackingLog;
import com.fcu.gtml.domain.VideoByLoadVideo;
import com.fcu.gtml.service.TrackingLogService;

public class EnrollmentEventsProcess {
    private static final Logger L = LoggerFactory.getLogger(EnrollmentEventsProcess.class);
    private static final String EVENTTYPE = "edx.course.enrollment.deactivated";
    private static ApplicationContext applicationContext = new ClassPathXmlApplicationContext("classpath:com/fcu/gtml/spring-config.xml", "classpath:com/fcu/gtml/spring-datasource.xml");
    private static TrackingLogService trackingLogService = applicationContext.getBean(TrackingLogService.class);
    
    public static void main(String[] args) {
        //讀取資料
        List<TrackingLog> listLogs = trackingLogService.listTrackingLogByEventType(EVENTTYPE);
        List<EnrollmentEventsLog> parserEnrollmentEventsLog = listEnrollmentEventsLog(listLogs);
        //Insert
        trackingLogService.batchInsertByEventType(parserEnrollmentEventsLog);
        L.info("Done!!");
    }

    private static List<EnrollmentEventsLog> listEnrollmentEventsLog(List<TrackingLog> listLogs) {
        List<EnrollmentEventsLog> listEnrollment = new ArrayList<>();
        for (TrackingLog tLog : listLogs) {
            String event = tLog.getEvent();
            int tLogId = tLog.getId();
            int userId = tLog.getUserId();
            try {
                EnrollmentEventsLog enrollment = JsonParser.parse(event, EnrollmentEventsLog.class);
                enrollment.setTrackingLogId(tLogId);
                enrollment.setUserId(userId);
                listEnrollment.add(enrollment);
            } catch (ParseException e) {
                L.error("TrackingLog Event:{}", tLog.getEvent());
                L.error("ParseException:" + e);
            }
        }
        return listEnrollment;
    }
}
