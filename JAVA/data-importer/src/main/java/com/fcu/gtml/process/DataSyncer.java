package com.fcu.gtml.process;

import java.io.IOException;
import java.util.Properties;
import java.util.concurrent.atomic.AtomicInteger;

import javax.annotation.Resource;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.fcu.gtml.service.WebMetadataService;
import com.fcu.gtml.service.WordNetService;

public class DataSyncer implements Syncer {
    private static final Logger L = LogManager.getLogger();
    private volatile boolean start;
    private AtomicInteger runningThreads = new AtomicInteger();
    private static Properties prop = new Properties();
    private static Configuration conf = null;
    @Resource 
    private WebMetadataService service;
    @Resource
    private WordNetService wordNetService;
    static {
        //conf = HBaseConfiguration.create();
        conf = new Configuration();
        try {
            prop.load(DataSyncer.class.getResourceAsStream("FilePath.properties"));
        } catch (IOException e) {
            // log exception
            L.error("read *.properties fail.", e);
            throw new RuntimeException("read properties Fail.", e);
        }
    }

    @Override
    public void start() {
        start = true;
        while (start) {
            L.info("Start Sync....");
            syncModelData();
            L.info("End Sync....");
            sleep(500);
            L.info("sleep 500 Sync....");
        }
    }

    @Override
    public void stop() {
        start = false;
    }

    @Override
    public boolean isStop() {
        return runningThreads.get() == 0;
    }

    private void sleep(int millis) {
        try {
            Thread.sleep(millis);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private void syncModelData() {
        Syncer_ProductData syncData = new Syncer_ProductData(prop, conf);
        syncData.addSyncProcessor(new SyncProcessor_EdX());
        syncData.doSyncProcess();
        Syncer_ProductOERData syncOERData = new Syncer_ProductOERData(prop, conf, service, wordNetService);
        syncOERData.addSyncProcessor(new SyncProcessor_OER_WebMetaData());
        syncOERData.doSyncProcess();
    }
}
