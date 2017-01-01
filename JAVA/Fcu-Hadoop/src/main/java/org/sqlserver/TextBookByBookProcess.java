package org.sqlserver;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.common.utils.JsonParser;
import org.common.utils.ParseException;
import org.mortbay.util.ajax.JSON;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.fcu.gtml.domain.TextBookByBook;
import com.fcu.gtml.domain.TrackingLog;
import com.fcu.gtml.service.TrackingLogService;

public class TextBookByBookProcess {
    private static final Logger L = LoggerFactory.getLogger(EnrollmentEventsProcess.class);
    private static final String EVENTTYPE = "book";
    private static ApplicationContext applicationContext = new ClassPathXmlApplicationContext("classpath:com/fcu/gtml/spring-config.xml", "classpath:com/fcu/gtml/spring-datasource.xml");
    private static TrackingLogService trackingLogService = applicationContext.getBean(TrackingLogService.class);

    public static void main(String[] args) {
        //讀取資料
        List<TrackingLog> listLogs = trackingLogService.listTrackingLogByEventType(EVENTTYPE);
        //parser book
        List<TextBookByBook> parserBook = listTextBookByBook(listLogs);
        trackingLogService.batchInsertByEventType(parserBook);
        L.info("Done!!");
    }

    private static List<TextBookByBook> listTextBookByBook(List<TrackingLog> listLogs) {
        List<TextBookByBook> listBook = new ArrayList<>();
        for (TrackingLog tLog : listLogs) {
            String event = JSON.parse(tLog.getEvent()).toString();
            int tLogId = tLog.getId();
            int userId = tLog.getUserId();
            try {
                TextBookByBook book = JsonParser.parse(event, TextBookByBook.class);
                book.setTrackingLogId(tLogId);
                book.setUserId(userId);
                listBook.add(book);
            } catch (ParseException e) {
                L.error("TrackingLog Event:{}", tLog.getEvent());
                L.error("ParseException:" + e);
            }
        }
        return listBook;
    }
}
