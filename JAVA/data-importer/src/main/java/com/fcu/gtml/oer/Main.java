package com.fcu.gtml.oer;

import java.util.NoSuchElementException;
import java.util.Scanner;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Main {
    private static final Logger L = LogManager.getLogger();
    
    public static void main(String[] args) {
        DataSyncer syncer = new DataSyncer();
        cliStop(syncer);
        shutdownHook(syncer);
        // 主任務
        try {
            syncer.start();
        } catch (Exception e) {
            L.info("DataSyncer start fail:", e);
        }
    }
    private static final void shutdownHook(final DataSyncer syncer) {
        final Thread mainThread = Thread.currentThread();
        Runtime.getRuntime().addShutdownHook(new Thread() {
            public void run() {
                L.info("Shutdown...");
                syncer.stop();
                try {
                    mainThread.join();
                } catch (InterruptedException e) {
                    L.error("DataSyncer main interruptedException.", e);
                }
            }
        });
    }
    
    // Command-line interface Stop
    private static final void cliStop(final DataSyncer syncer) {
        final Thread mainThread = Thread.currentThread();
        // Command Line Stop
        Thread t = new Thread() {
            public void run() {
                try (Scanner scanner = new Scanner(System.in)) {
                    while (true) {
                        try {
                            String command = scanner.nextLine();
                            L.info("command: " + command);
                            if (command.equals("doStop")) {
                                L.info("stop by command");
                                syncer.stop();
                                try {
                                    mainThread.join();
                                } catch (InterruptedException e) {
                                    e.printStackTrace();
                                }
                                break;
                            }
                        } catch (NoSuchElementException | IllegalStateException e) {
                            if (syncer.isStop())
                                break;
                        }
                    }
                }
            }
        };
        t.setDaemon(true);
        t.start();
    }
}
