package org.common.utils;

import java.util.ArrayList;
import java.util.List;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.fcu.gtml.domain.TrackingLog;
import com.fcu.gtml.domain.VideoByPlayVideo;
import com.fcu.gtml.service.StudentEventService;
import com.fcu.gtml.service.StudentLogService;
import com.fcu.gtml.service.StudentTrainDataService;
import com.fcu.gtml.service.TextBookEventService;

abstract public class StudentEventTool {
    private static ApplicationContext applicationContext = new ClassPathXmlApplicationContext("classpath:com/fcu/gtml/spring-config.xml", "classpath:com/fcu/gtml/spring-datasource.xml");
    protected static StudentLogService studentService = applicationContext.getBean(StudentLogService.class);
    protected static StudentEventService studentEventService = applicationContext.getBean(StudentEventService.class);
    protected static TextBookEventService textBookService = applicationContext.getBean(TextBookEventService.class);
    protected static StudentTrainDataService trainDataService = applicationContext.getBean(StudentTrainDataService.class);

    static {
        JsonParserConfig.config();
    }

//    public static <T> List<T> listPlayVideo(List<TrackingLog> listLogs, Class<T> clazz) {
//        List<T> listPlayVideo = new ArrayList<>();
//        for (TrackingLog tLog : listLogs) {
//            String event = tLog.getEvent();
//            int tLogId = tLog.getId();
//            try {
//                T sv = JsonParser.parse(event, clazz);
//                ((VideoByPlayVideo) sv).setTrackingLogId(tLogId);
//                listPlayVideo.add(sv);
//            } catch (ParseException e) {
//                //L.error("TrackingLog Event:{}", tLog.getEvent());
//                //L.error("ParseException:" + e);
//            }
//            
//        }
//        return listPlayVideo;
//    }
}
