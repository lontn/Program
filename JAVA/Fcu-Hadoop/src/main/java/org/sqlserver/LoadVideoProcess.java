package org.sqlserver;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import org.common.utils.JsonParser;
import org.common.utils.JsonParserConfig;
import org.common.utils.ParseException;
import org.mortbay.util.ajax.JSON;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.fcu.gtml.domain.TrackingLog;
import com.fcu.gtml.domain.VideoByLoadVideo;
import com.fcu.gtml.service.TrackingLogService;

public class LoadVideoProcess {
    private static final Logger L = LoggerFactory.getLogger(LoadVideoProcess.class);
    private static final String EVENTTYPE = "load_video";
    private static ApplicationContext applicationContext = new ClassPathXmlApplicationContext("classpath:com/fcu/gtml/spring-config.xml", "classpath:com/fcu/gtml/spring-datasource.xml");
    private static TrackingLogService trackingLogService = applicationContext.getBean(TrackingLogService.class);

    public static void main(String[] args) {
        //讀取資料
        List<TrackingLog> listLogs = trackingLogService.listTrackingLogByEventType(EVENTTYPE);
        //Parser SeekVideo
        List<VideoByLoadVideo> parserLoadVideoList = listLoadVideo(listLogs);
        //Insert
        trackingLogService.batchInsertByLoadVideo(parserLoadVideoList);
    }

    private static List<VideoByLoadVideo> listLoadVideo(List<TrackingLog> listLogs) {
        List<VideoByLoadVideo> listLoadVideo = new ArrayList<>();
        for (TrackingLog tLog : listLogs) {
            String event = JSON.parse(tLog.getEvent()).toString();
            int tLogId = tLog.getId();
            int userId = tLog.getUserId();
            try {
                VideoByLoadVideo lv = JsonParser.parse(event, VideoByLoadVideo.class);
                lv.setTrackingLogId(tLogId);
                lv.setUserId(userId);
                listLoadVideo.add(lv);
            } catch (ParseException e) {
                L.error("TrackingLog Event:{}", tLog.getEvent());
                L.error("ParseException:" + e);
            }
        }
        return listLoadVideo;
    }
}
