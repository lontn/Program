package com.fcu.gtml;

import org.common.utils.JsonParserConfig;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({ "classpath:com/fcu/gtml/spring-datasource.xml", "classpath:com/fcu/gtml/spring-config.xml" })
abstract public class SupportSpringTest {
    static {
        JsonParserConfig.config();
    }
}
