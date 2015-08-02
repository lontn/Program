package org.sqlserver;

import java.util.ArrayList;
import java.util.List;

import org.common.utils.JsonParser;
import org.common.utils.ParseException;
import org.common.utils.StudentEventTool;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fcu.gtml.domain.StudentLog;
import com.fcu.gtml.domain.StudentTextBook;

public class StudentTextBookTask extends StudentEventTool {
    private static final Logger L = LoggerFactory.getLogger(StudentTextBookTask.class);

    public static void main(String[] args) {
        List<StudentLog> list = studentService.listStudentLogByEventType(args[0]);
        List<StudentTextBook> result = new ArrayList<>();

        for (StudentLog studentLog : list) {
            try{
                String event = studentLog.getEvent().equals("null") ? null : studentLog.getEvent().replace("'", "").replace("\\", "'");
                L.info("event:{}", event);
                StudentTextBook stb = null;
                if (event != null) {
                    stb = JsonParser.parse(event, StudentTextBook.class);
                }else {
                    stb = new StudentTextBook();
                }
                stb.setUserName(studentLog.getUserName());
                stb.setEventType(studentLog.getEventType());
                stb.setTime(studentLog.getTime());
                L.info("stb:{}", stb);
                result.add(stb);
            }catch(ParseException e){
                L.error("StudentTextBookTask ParseException:{}", e);
            }
        }
        L.info("result size:{}", result.size());
        if (result.size() > 0) {
            textBookService.batchInsertByBook(result);
        }
        L.info("done.");
    }

}
