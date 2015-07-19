package org.sqlserver;

import java.util.ArrayList;
import java.util.List;

import org.common.utils.JsonParser;
import org.common.utils.ParseException;
import org.common.utils.StudentEventTool;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fcu.gtml.domain.StudentLog;
import com.fcu.gtml.domain.StudentVideoSeekVideo;

public class StudentVideoSeekVideoTask extends StudentEventTool {
    private static final Logger L = LoggerFactory.getLogger(StudentVideoSeekVideoTask.class);
    
    public static void main(String[] args) {
        List<StudentLog> list = studentService.listStudentLogByEventType(args[0]);
        List<StudentVideoSeekVideo> result = new ArrayList<>();

        for (StudentLog studentLog : list) {
            try {
                String event = studentLog.getEvent().equals("null") ? null : studentLog.getEvent().replace("'", "").replace("\\", "'");
                L.info("event:{}", event);
                StudentVideoSeekVideo spv = null;
                if(event != null) {
                    spv = JsonParser.parse(event, StudentVideoSeekVideo.class);
                }else{
                    spv = new StudentVideoSeekVideo();
                }
                spv.setUserName(studentLog.getUserName());
                spv.setEventType(studentLog.getEventType());
                spv.setTime(studentLog.getTime());
                L.info("spv:{}", spv);
                result.add(spv);
            } catch (ParseException e) {
                L.error("StudentVideoSeekVideo ParseException:{}", e);
            }
        }
        L.info("result size:{}", result.size());
        if(result.size() > 0) {
            studentEventService.batchInsertBySeekVideo(result);
        }
        L.info("done.");
    }
}
