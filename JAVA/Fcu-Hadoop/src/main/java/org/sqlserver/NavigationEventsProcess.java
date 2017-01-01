package org.sqlserver;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.common.utils.JsonParser;
import org.common.utils.ParseException;
import org.mortbay.util.ajax.JSON;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.fcu.gtml.domain.EnrollmentEventsLog;
import com.fcu.gtml.domain.NavigationEventsLog;
import com.fcu.gtml.domain.TrackingLog;
import com.fcu.gtml.service.TrackingLogService;

public class NavigationEventsProcess {
    private static final Logger L = LoggerFactory.getLogger(NavigationEventsProcess.class);
    private static final String EVENTTYPE = "seq_prev";
    private static ApplicationContext applicationContext = new ClassPathXmlApplicationContext("classpath:com/fcu/gtml/spring-config.xml", "classpath:com/fcu/gtml/spring-datasource.xml");
    private static TrackingLogService trackingLogService = applicationContext.getBean(TrackingLogService.class);

    public static void main(String[] args) {
        //讀取資料
        List<TrackingLog> listLogs = trackingLogService.listTrackingLogByEventType(EVENTTYPE);
        
        List<NavigationEventsLog> parserNavigationEvents = listNavigationEventsLog(listLogs);
        //Insert
        trackingLogService.batchInsertByEventType(parserNavigationEvents);
        L.info("Done!!");
    }

    private static List<NavigationEventsLog> listNavigationEventsLog(List<TrackingLog> listLogs) {
        List<NavigationEventsLog> listNavigation = new ArrayList<>();
        for (TrackingLog tLog : listLogs) {
            String event = JSON.parse(tLog.getEvent()).toString();
            int tLogId = tLog.getId();
            try {
                NavigationEventsLog nLog;
                if (StringUtils.isBlank(event)) {
                    nLog = new NavigationEventsLog();
                    nLog.setEventId(null);
                    nLog.setEventNew(null);
                    nLog.setEventOld(null);
                } else {
                    nLog = JsonParser.parse(event, NavigationEventsLog.class);
                }
                nLog.setTrackingLogId(tLogId);
                listNavigation.add(nLog);
            } catch (ParseException e) {
                L.error("TrackingLog Event:{}", tLog.getEvent());
                L.error("ParseException:" + e);
            }
        }
        return listNavigation;
    }
}
