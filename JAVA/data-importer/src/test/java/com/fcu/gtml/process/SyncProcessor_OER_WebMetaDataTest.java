package com.fcu.gtml.process;

import java.io.IOException;
import java.util.Properties;

import javax.annotation.Resource;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.PropertiesConfiguration;
import org.junit.Test;

import com.fcu.gtml.SupportSpringTest;
import com.fcu.gtml.service.WebMetadataService;
import com.fcu.gtml.service.WordNetService;

public class SyncProcessor_OER_WebMetaDataTest extends SupportSpringTest {
    private static final Logger L = LogManager.getLogger();
    @Resource 
    private WebMetadataService service;
    @Resource
    private WordNetService wordNetService;
    private static Properties prop = new Properties();
    private static Configuration conf = null;
    static {
        conf = new Configuration();
        try {
            prop.load(DataSyncer.class.getResourceAsStream("FilePath.properties"));
        } catch (IOException e) {
            // log exception
            L.error("read *.properties fail.", e);
            throw new RuntimeException("read properties Fail.", e);
        }
    }
    
    @Test
    public void test() {
        int seq = com.fcu.gtml.process.ConfigurationTool.WebMetaDataInfo.getSeq();
        System.out.println("seq:"+seq);
        com.fcu.gtml.process.ConfigurationTool.WebMetaDataInfo.setConfig(5);
        com.fcu.gtml.process.ConfigurationTool.WebMetaDataInfo.save();
    }

    @Test
    public void testProcess() {
        Syncer_ProductData syncData = new Syncer_ProductData(prop, conf, service, wordNetService);
        syncData.addSyncProcessor(new SyncProcessor_OER_WebMetaData());
        syncData.doSyncProcess();
    }
}
