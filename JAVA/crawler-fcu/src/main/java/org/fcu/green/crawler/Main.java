package org.fcu.green.crawler;

import java.util.NoSuchElementException;
import java.util.Scanner;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Main {
    private static final Logger L = LogManager.getLogger(Main.class);
    public static void main(String[] args) {
        IntechopenCrawler syncer = new IntechopenCrawler();
        syncer.start();
    }

//    private static void shutdownHook(IntechopenCrawler syncer) {
//        Thread mainThread = Thread.currentThread();
//        Runtime.getRuntime().addShutdownHook(new Thread() {
//            public void run() {
//                L.info("Shutdown...");
//                syncer.stop();
//                try {
//                    mainThread.join();
//                } catch (InterruptedException e) {
//                    e.printStackTrace();
//                }
//            }
//        });
//    }
//    
//    // Command-line interface Stop
//    private static final void cliStop(final IntechopenCrawler syncer) {
//        final Thread mainThread = Thread.currentThread();
//        // Command Line Stop
//        Thread t = new Thread() {
//            public void run() {
//                try (Scanner scanner = new Scanner(System.in)) {
//                    while (true) {
//                        try {
//                            String command = scanner.nextLine();
//                            L.info("command: " + command);
//                            if (command.equals("doStop")) {
//                                L.info("stop by command");
//                                syncer.stop();
//                                try {
//                                    mainThread.join();
//                                } catch (InterruptedException e) {
//                                    e.printStackTrace();
//                                }
//                                break;
//                            }
//                        } catch (NoSuchElementException | IllegalStateException e) {
//                            if (syncer.isStop())
//                                break;
//                        }
//                    }
//                }
//            }
//        };
//        t.setDaemon(true);
//        t.start();
//    }
}
