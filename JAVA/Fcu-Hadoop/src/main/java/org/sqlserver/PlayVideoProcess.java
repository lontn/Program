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
import com.fcu.gtml.domain.VideoByPlayVideo;
import com.fcu.gtml.service.TrackingLogService;

public class PlayVideoProcess {
    private static final Logger L = LoggerFactory.getLogger(PlayVideoProcess.class);
    private static final String EVENTTYPE = "play_video";
    private static ApplicationContext applicationContext = new ClassPathXmlApplicationContext("classpath:com/fcu/gtml/spring-config.xml", "classpath:com/fcu/gtml/spring-datasource.xml");
    private static TrackingLogService trackingLogService = applicationContext.getBean(TrackingLogService.class);
    
    public static void main(String[] args) {
        //讀取資料
        List<TrackingLog> listLogs = trackingLogService.listTrackingLogByEventType(EVENTTYPE);
        //Parser PlayVideo
        List<VideoByPlayVideo> parserPlayVideoList = listPlayVideo(listLogs);
        //Insert
        trackingLogService.batchInsertByEventType(parserPlayVideoList);
        L.info("Done!!");
    }

    private static List<VideoByPlayVideo> listPlayVideo(List<TrackingLog> listLogs) {
        List<VideoByPlayVideo> listPlayVideo = new ArrayList<>();
        for (TrackingLog tLog : listLogs) {
            String event = JSON.parse(tLog.getEvent()).toString();
            int tLogId = tLog.getId();
            int userId = tLog.getUserId();
            try {
                VideoByPlayVideo sv = JsonParser.parse(event, VideoByPlayVideo.class);
                sv.setTrackingLogId(tLogId);
                sv.setUserId(userId);
                listPlayVideo.add(sv);
            } catch (ParseException e) {
                L.error("TrackingLog Event:{}", tLog.getEvent());
                L.error("ParseException:" + e);
            }
            
        }
        return listPlayVideo;
    }
}
