package org.sqlserver;

import java.util.ArrayList;
import java.util.List;

import org.common.utils.JsonParser;
import org.common.utils.ParseException;
import org.common.utils.StudentEventTool;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fcu.gtml.domain.StudentLog;
import com.fcu.gtml.domain.StudentVideoStopVideo;

public class StudentVideoStopVideoTask extends StudentEventTool {
    private static final Logger L = LoggerFactory.getLogger(StudentVideoStopVideoTask.class);
    
    public static void main(String[] args) {
        List<StudentLog> list = studentService.listStudentLogByEventType(args[0]);
        List<StudentVideoStopVideo> result = new ArrayList<>();

        for (StudentLog studentLog : list) {
            try {
                String event = studentLog.getEvent().equals("null") ? null : studentLog.getEvent().replace("'", "").replace("\\", "'");
                L.info("event:{}", event);
                StudentVideoStopVideo ssc = null;
                if(event != null) {
                    ssc = JsonParser.parse(event, StudentVideoStopVideo.class);
                }else{
                    ssc = new StudentVideoStopVideo();
                }
                ssc.setUserName(studentLog.getUserName());
                ssc.setEventType(studentLog.getEventType());
                ssc.setTime(studentLog.getTime());
                L.info("ssc:{}", ssc);
                result.add(ssc);
            } catch (ParseException e) {
                L.error("StudentVideoStopVideo ParseException:{}", e);
            }
        }
        L.info("result size:{}", result.size());
        if (result.size() > 0) {
            studentEventService.batchInsertByStopVideo(result);
        }
        L.info("done.");
    }
}
