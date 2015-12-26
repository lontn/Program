package com.fcu.gtml.oer;

import java.util.concurrent.atomic.AtomicInteger;

public class DataSyncer implements Syncer {
    private volatile boolean start;
    private AtomicInteger runningThreads = new AtomicInteger();

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
