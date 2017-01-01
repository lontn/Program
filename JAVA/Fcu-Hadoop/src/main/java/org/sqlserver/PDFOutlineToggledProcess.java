package org.sqlserver;

import java.util.ArrayList;
import java.util.List;

import org.common.utils.JsonParser;
import org.common.utils.ParseException;
import org.mortbay.util.ajax.JSON;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.fcu.gtml.domain.PDFDisplayScaled;
import com.fcu.gtml.domain.PDFOutlineToggled;
import com.fcu.gtml.domain.TrackingLog;
import com.fcu.gtml.service.TrackingLogService;

public class PDFOutlineToggledProcess {
    private static final Logger L = LoggerFactory.getLogger(PDFOutlineToggledProcess.class);
    private static final String EVENTTYPE = "textbook.pdf.outline.toggled";
    private static ApplicationContext applicationContext = new ClassPathXmlApplicationContext("classpath:com/fcu/gtml/spring-config.xml", "classpath:com/fcu/gtml/spring-datasource.xml");
    private static TrackingLogService trackingLogService = applicationContext.getBean(TrackingLogService.class);

    public static void main(String[] args) {
        //讀取資料
        List<TrackingLog> listLogs = trackingLogService.listTrackingLogByEventType(EVENTTYPE);
        //parser data
        List<PDFOutlineToggled> parserPDF = listPDFOutlineToggled(listLogs);
        trackingLogService.batchInsertByEventType(parserPDF);
        L.info("Done!!");
    }

    private static List<PDFOutlineToggled> listPDFOutlineToggled(List<TrackingLog> listLogs) {
        List<PDFOutlineToggled> listPDF = new ArrayList<>();
        for (TrackingLog tLog : listLogs) {
            String event = JSON.parse(tLog.getEvent()).toString();
            int tLogId = tLog.getId();
            try {
                PDFOutlineToggled pdf = JsonParser.parse(event, PDFOutlineToggled.class);
                pdf.setTrackingLogId(tLogId);
                listPDF.add(pdf);
            } catch (ParseException e) {
                L.error("TrackingLog Event:{}", tLog.getEvent());
                L.error("ParseException:" + e);
            }
        }
        return listPDF;
    }
}
