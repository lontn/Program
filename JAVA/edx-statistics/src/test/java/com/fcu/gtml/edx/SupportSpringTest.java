package com.fcu.gtml.edx;

import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.fcu.gtml.edx.utils.JsonParserConfig;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({ "classpath:spring-datasource.xml", "classpath:com/fcu/gtml/spring-config.xml" })
abstract public class SupportSpringTest {
    static {
        JsonParserConfig.config();
    }
}
