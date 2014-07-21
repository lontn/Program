package org.hbase;

import java.util.UUID;

import org.apache.commons.configuration.Configuration;
import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.PropertiesConfiguration;
import org.apache.commons.lang3.time.StopWatch;
import org.domain.ProxyLog;
import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class HBaseDataProcessTest {
    private static final Logger L = LoggerFactory.getLogger(HBaseDataProcessTest.class);

    public static void main(String args[]){
        L.info("ProxyLog.LABORATORYINFO:"+ProxyLog.LABORATORYINFO.toString());
        L.info("ProxyLog.LIBRARYINFO:"+ProxyLog.LIBRARYINFO);
        L.info("ProxyLog.LIBRARYINFO:"+ProxyLog.getColumnFamily(1));
        L.info("ProxyLog.LIBRARYINFO:");
        DateTime dt = new DateTime();
        StopWatch sw = new StopWatch();
        sw.start();
        L.info("dt"+dt.toString("yyyy-MM-dd HH:mm:ss:mmm"));
        L.info("GUID:"+UUID.randomUUID().toString().toUpperCase());
        int count = 0;
        count++;
        L.info("111:"+count);
                
    }
}
