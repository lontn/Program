package org.common.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.fcu.gtml.service.StudentEventService;
import com.fcu.gtml.service.StudentLogService;
import com.fcu.gtml.service.TextBookEventService;

abstract public class StudentEventTool {
    private static ApplicationContext applicationContext = new ClassPathXmlApplicationContext("classpath:com/fcu/gtml/spring-config.xml", "classpath:com/fcu/gtml/spring-datasource.xml");
    protected static StudentLogService studentService = applicationContext.getBean(StudentLogService.class);
    protected static StudentEventService studentEventService = applicationContext.getBean(StudentEventService.class);
    protected static TextBookEventService textBookService = applicationContext.getBean(TextBookEventService.class);

    static {
        JsonParserConfig.config();
    }
}
