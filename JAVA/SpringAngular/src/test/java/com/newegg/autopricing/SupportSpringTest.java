package com.newegg.autopricing;

import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({ "classpath:spring-datasource.xml", "classpath:com/newegg/autopricing/spring-config.xml" })
abstract public class SupportSpringTest {
}
