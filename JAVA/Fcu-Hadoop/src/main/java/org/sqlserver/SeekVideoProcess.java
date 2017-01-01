package org.sqlserver;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import org.common.utils.JsonParser;
import org.common.utils.ParseException;
import org.mortbay.util.ajax.JSON;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.fcu.gtml.domain.TrackingLog;
import com.fcu.gtml.domain.VideoBySeekVideo;
import com.fcu.gtml.service.TrackingLogService;

public class SeekVideoProcess {
    private static final Logger L = LoggerFactory.getLogger(SeekVideoProcess.class);
    private static Properties prop = new Properties();
    private static final String EVENTTYPE = "seek_video";
    private static ApplicationContext applicationContext = new ClassPathXmlApplicationContext("classpath:com/fcu/gtml/spring-config.xml", "classpath:com/fcu/gtml/spring-datasource.xml");
    private static TrackingLogService trackingLogService = applicationContext.getBean(TrackingLogService.class);

    public static void main(String[] args) {
        //讀取資料
        List<TrackingLog> listLogs = trackingLogService.listTrackingLogByEventType(EVENTTYPE);
        //Parser SeekVideo
        List<VideoBySeekVideo> parserSeekVideoList = listSeekVideo(listLogs);
        //insert
        trackingLogService.batchInsertBySeekVideo(parserSeekVideoList);
        L.info("Done..");
    }

    private static List<VideoBySeekVideo> listSeekVideo(List<TrackingLog> listLogs) {
        List<VideoBySeekVideo> listSeekVideo = new ArrayList<>();
        for (TrackingLog tLog : listLogs) {
            String event = JSON.parse(tLog.getEvent()).toString();
            int tLogId = tLog.getId();
            int userId = tLog.getUserId();
            try {
                VideoBySeekVideo sv = JsonParser.parse(event, VideoBySeekVideo.class);
                sv.setTrackingLogId(tLogId);
                sv.setUserId(userId);
                listSeekVideo.add(sv);
            } catch (ParseException e) {
                L.error("TrackingLog Event:{}", tLog.getEvent());
                L.error("ParseException:" + e);
            }
            
        }
        return listSeekVideo;
    }

}
