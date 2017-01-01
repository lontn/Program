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

import com.fcu.gtml.domain.PDFThumbnailsToggled;
import com.fcu.gtml.domain.TrackingLog;
import com.fcu.gtml.service.TrackingLogService;

public class PDFThumbnailsToggledProcess {
    private static final Logger L = LoggerFactory.getLogger(PDFThumbnailsToggledProcess.class);
    private static final String EVENTTYPE = "textbook.pdf.thumbnails.toggled";
    private static ApplicationContext applicationContext = new ClassPathXmlApplicationContext("classpath:com/fcu/gtml/spring-config.xml", "classpath:com/fcu/gtml/spring-datasource.xml");
    private static TrackingLogService trackingLogService = applicationContext.getBean(TrackingLogService.class);

    public static void main(String[] args) {
        //讀取資料
        List<TrackingLog> listLogs = trackingLogService.listTrackingLogByEventType(EVENTTYPE);
        //parser PDF
        List<PDFThumbnailsToggled> parserPDF = listPDFThumbnailsToggled(listLogs);
        trackingLogService.batchInsertByEventType(parserPDF);
        L.info("Done!!");
    }

    private static List<PDFThumbnailsToggled> listPDFThumbnailsToggled(List<TrackingLog> listLogs) {
        List<PDFThumbnailsToggled> listPDF = new ArrayList<>();
        for (TrackingLog tLog : listLogs) {
            String event = JSON.parse(tLog.getEvent()).toString();
            int tLogId = tLog.getId();
            int userId = tLog.getUserId();
            try {
                PDFThumbnailsToggled pdf = JsonParser.parse(event, PDFThumbnailsToggled.class);
                pdf.setTrackingLogId(tLogId);
                pdf.setUserId(userId);
                listPDF.add(pdf);
            } catch (ParseException e) {
                L.error("TrackingLog Event:{}", tLog.getEvent());
                L.error("ParseException:" + e);
            }
        }
        return listPDF;
    }
}
