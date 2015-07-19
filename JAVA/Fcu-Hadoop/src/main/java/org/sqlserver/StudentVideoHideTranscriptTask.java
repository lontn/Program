package org.sqlserver;

import java.util.ArrayList;
import java.util.List;

import org.common.utils.JsonParser;
import org.common.utils.JsonParserConfig;
import org.common.utils.ParseException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.fcu.gtml.domain.StudentLog;
import com.fcu.gtml.domain.StudentVideoHideTranscript;
import com.fcu.gtml.service.StudentEventService;
import com.fcu.gtml.service.StudentLogService;

public class StudentVideoHideTranscriptTask {
    private static final Logger L = LoggerFactory.getLogger(StudentVideoHideTranscriptTask.class);
    private static ApplicationContext applicationContext = new ClassPathXmlApplicationContext("classpath:com/fcu/gtml/spring-config.xml", "classpath:com/fcu/gtml/spring-datasource.xml");
    private static StudentLogService studentService = applicationContext.getBean(StudentLogService.class);
    private static StudentEventService studentEventService = applicationContext.getBean(StudentEventService.class);

    static {
        JsonParserConfig.config();
    }
    
    public static void main(String[] args) {
        List<StudentLog> list = studentService.listStudentLogByEventType(args[0]);
        List<StudentVideoHideTranscript> result = new ArrayList<>();

        for (StudentLog studentLog : list) {
            try {
                String event = studentLog.getEvent().equals("null") ? null : studentLog.getEvent().replace("'", "").replace("\\", "'");
                L.info("event:{}", event);
                StudentVideoHideTranscript st = null;
                if(event != null) {
                    st = JsonParser.parse(event, StudentVideoHideTranscript.class);
                }else{
                    st = new StudentVideoHideTranscript();
                }
                st.setUserName(studentLog.getUserName());
                st.setEventType(studentLog.getEventType());
                st.setTime(studentLog.getTime());
                L.info("st:{}", st);
                result.add(st);
            } catch (ParseException e) {
                L.error("StudentVideoHideTranscript ParseException:{}", e);
            }
        }
        L.info("result size:{}", result.size());
        if (result.size() > 0) {
            studentEventService.batchInsertByHideTranscript(result);
        }
        L.info("done.");
    }

}
