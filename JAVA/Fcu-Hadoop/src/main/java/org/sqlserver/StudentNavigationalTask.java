package org.sqlserver;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import org.common.utils.JsonParser;
import org.common.utils.JsonParserConfig;
import org.common.utils.ParseException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.fcu.gtml.domain.StudentEnrollment;
import com.fcu.gtml.domain.StudentLog;
import com.fcu.gtml.domain.StudentNavigational;
import com.fcu.gtml.service.StudentEventService;
import com.fcu.gtml.service.StudentLogService;

public class StudentNavigationalTask {
    private static final Logger L = LoggerFactory.getLogger(StudentNavigationalTask.class);
    //private static Properties prop = new Properties();
    private static ApplicationContext applicationContext = new ClassPathXmlApplicationContext("classpath:com/fcu/gtml/spring-config.xml", "classpath:com/fcu/gtml/spring-datasource.xml");
    private static StudentLogService studentService = applicationContext.getBean(StudentLogService.class);
    private static StudentEventService studentEventService = applicationContext.getBean(StudentEventService.class);
    
    static {
        JsonParserConfig.config();
    }
    
    public static void main(String[] args) {
        List<StudentLog> list = studentService.listStudentLogByEventType(args[0]);
        List<StudentNavigational> result = new ArrayList<>();
        for (StudentLog studentLog : list) {
            try {
                String event = studentLog.getEvent().equals("null") ? null : studentLog.getEvent().replace("'", "").replace("\\", "'");
                StudentNavigational sn = null;
                if (event != null) {
                    sn = JsonParser.parse(event, StudentNavigational.class);
                } else {
                    sn = new StudentNavigational();
                }
                sn.setUserName(studentLog.getUserName());
                sn.setEventType(studentLog.getEventType());
                sn.setTime(studentLog.getTime());
                L.info("sn:{}", sn);
                result.add(sn);
            } catch (ParseException e) {
                L.error("ParseException:{}", e);
            }
        }
        L.info("result size:{}", result.size());
        if (result.size() > 0) {
            studentEventService.batchInsert(result);
        }
        L.info("done.");
    }
}
