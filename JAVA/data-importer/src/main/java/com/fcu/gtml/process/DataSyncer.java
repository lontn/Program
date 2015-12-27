package com.fcu.gtml.process;

import java.util.Properties;
import java.util.concurrent.atomic.AtomicInteger;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.HBaseConfiguration;

public class DataSyncer implements Syncer {
    private volatile boolean start;
    private AtomicInteger runningThreads = new AtomicInteger();
    private static Properties prop = new Properties();
    private static Configuration conf = null;
    static {
        conf = HBaseConfiguration.create();
    }

    @Override
    public void start() {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void stop() {
        start = false;
    }

    @Override
    public boolean isStop() {
        return runningThreads.get() == 0;
    }

    private void syncModelData() {
        Syncer_ProductData syncData = new Syncer_ProductData();
        syncData.addSyncProcessor(new SyncProcessor_EdX());
        syncData.doSyncProcess();
    }
}
