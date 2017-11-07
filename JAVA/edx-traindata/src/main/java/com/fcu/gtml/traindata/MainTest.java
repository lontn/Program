package com.fcu.gtml.traindata;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;

import com.fcu.gtml.edx.domain.FeatureEnum;

public class MainTest {
    private static TrainProcess trainProcess;

    /**
     * 1.FCUx/2015004/201509
     * 2.FCUx/2015002/201510
     * @param args
     */
    public static void main(String[] args) {
        ApplicationContext ac = new FileSystemXmlApplicationContext("classpath:com/fcu/gtml/spring-config.xml", "classpath:com/fcu/gtml/spring-datasource.xml");
        trainProcess = ac.getBean(TrainProcess.class);
        trainProcess.setCourseId("FCUx/2015002/201510");
        trainProcess.build();
    }

}
