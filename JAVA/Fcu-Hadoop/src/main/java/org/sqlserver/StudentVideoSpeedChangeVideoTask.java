package org.sqlserver;

import java.util.ArrayList;
import java.util.List;

import org.common.utils.JsonParser;
import org.common.utils.ParseException;
import org.common.utils.StudentEventTool;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fcu.gtml.domain.StudentLog;
import com.fcu.gtml.domain.StudentVideoSpeedChangeVideo;

public class StudentVideoSpeedChangeVideoTask extends StudentEventTool {
    private static final Logger L = LoggerFactory.getLogger(StudentVideoSpeedChangeVideoTask.class);
    
    public static void main(String[] args) {
        List<StudentLog> list = studentService.listStudentLogByEventType(args[0]);
        List<StudentVideoSpeedChangeVideo> result = new ArrayList<>();

        for (StudentLog studentLog : list) {
            try {
                String event = studentLog.getEvent().equals("null") ? null : studentLog.getEvent().replace("'", "").replace("\\", "'");
                L.info("event:{}", event);
                StudentVideoSpeedChangeVideo ssc = null;
                if(event != null) {
                    ssc = JsonParser.parse(event, StudentVideoSpeedChangeVideo.class);
                }else{
                    ssc = new StudentVideoSpeedChangeVideo();
                }
                ssc.setUserName(studentLog.getUserName());
                ssc.setEventType(studentLog.getEventType());
                ssc.setTime(studentLog.getTime());
                L.info("ssc:{}", ssc);
                result.add(ssc);
            } catch (ParseException e) {
                L.error("StudentVideoShowTranscript ParseException:{}", e);
            }
        }
        L.info("result size:{}", result.size());
        if(result.size() > 0) {
            studentEventService.batchInsertBySpeedChangeVideo(result);
        }
        L.info("done.");
    }
}
