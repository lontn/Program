package org.hbase;

import java.util.UUID;

import org.apache.commons.lang3.time.StopWatch;
import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class HBaseDataProcessTest {
    private static final Logger L = LoggerFactory.getLogger(HBaseDataProcessTest.class);

    public static void main(String args[]){
        DateTime dt = new DateTime();
        StopWatch sw = new StopWatch();
        sw.start();
        L.info("dt"+dt.toString("yyyy-MM-dd HH:mm:ss:mmm"));
        L.info("GUID:"+UUID.randomUUID().toString().toUpperCase());
    }
}
