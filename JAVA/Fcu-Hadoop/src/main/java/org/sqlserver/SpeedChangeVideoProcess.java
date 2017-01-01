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

import com.fcu.gtml.domain.TrackingLog;
import com.fcu.gtml.domain.VideoBySpeedChangeVideo;
import com.fcu.gtml.service.TrackingLogService;

public class SpeedChangeVideoProcess {
    private static final Logger L = LoggerFactory.getLogger(SpeedChangeVideoProcess.class);
    private static final String EVENTTYPE = "speed_change_video";
    private static ApplicationContext applicationContext = new ClassPathXmlApplicationContext("classpath:com/fcu/gtml/spring-config.xml", "classpath:com/fcu/gtml/spring-datasource.xml");
    private static TrackingLogService trackingLogService = applicationContext.getBean(TrackingLogService.class);

    public static void main(String[] args) {
        //讀取資料
        List<TrackingLog> listLogs = trackingLogService.listTrackingLogByEventType(EVENTTYPE);
        //Parser SpeedChangeVideo
        List<VideoBySpeedChangeVideo> parserSpeedChangeVideoList = listSpeedChangeVideo(listLogs);
        //Insert
        trackingLogService.batchInsertByEventType(parserSpeedChangeVideoList);
        L.info("Done!!!");
    }

    private static List<VideoBySpeedChangeVideo> listSpeedChangeVideo(List<TrackingLog> listLogs) {
        List<VideoBySpeedChangeVideo> listSpeedChangeVideo = new ArrayList<>();
        for (TrackingLog tLog : listLogs) {
            String event = JSON.parse(tLog.getEvent()).toString();
            int tLogId = tLog.getId();
            L.info("Context:{}", tLog.getContext());
            int userId = tLog.getUserId();
            try {
                VideoBySpeedChangeVideo scv = JsonParser.parse(event, VideoBySpeedChangeVideo.class);
                scv.setTrackingLogId(tLogId);
                scv.setUserId(userId);
                listSpeedChangeVideo.add(scv);
            } catch (ParseException e) {
                L.error("TrackingLog Event:{}", tLog.getEvent());
                L.error("ParseException:" + e);
            }
        }
        return listSpeedChangeVideo;
    }
}
