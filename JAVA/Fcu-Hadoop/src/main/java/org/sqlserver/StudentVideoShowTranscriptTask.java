package org.sqlserver;

import java.util.ArrayList;
import java.util.List;

import org.common.utils.JsonParser;
import org.common.utils.ParseException;
import org.common.utils.StudentEventTool;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fcu.gtml.domain.StudentLog;
import com.fcu.gtml.domain.StudentVideoShowTranscript;

public class StudentVideoShowTranscriptTask extends StudentEventTool {
    private static final Logger L = LoggerFactory.getLogger(StudentVideoShowTranscriptTask.class);
    
    public static void main(String[] args) {
        List<StudentLog> list = studentService.listStudentLogByEventType(args[0]);
        List<StudentVideoShowTranscript> result = new ArrayList<>();

        for (StudentLog studentLog : list) {
            try {
                String event = studentLog.getEvent().equals("null") ? null : studentLog.getEvent().replace("'", "").replace("\\", "'");
                L.info("event:{}", event);
                StudentVideoShowTranscript svt = null;
                if(event != null) {
                    svt = JsonParser.parse(event, StudentVideoShowTranscript.class);
                }else{
                    svt = new StudentVideoShowTranscript();
                }
                svt.setUserName(studentLog.getUserName());
                svt.setEventType(studentLog.getEventType());
                svt.setTime(studentLog.getTime());
                L.info("svt:{}", svt);
                result.add(svt);
            } catch (ParseException e) {
                L.error("StudentVideoShowTranscript ParseException:{}", e);
            }
        }
        L.info("result size:{}", result.size());
        if(result.size() > 0) {
            studentEventService.batchInsertByShowTranscript(result);
        }
        L.info("done.");
    }
}
