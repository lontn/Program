package org.sqlserver;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Properties;

import org.apache.commons.io.FileUtils;
import org.common.utils.JsonParser;
import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.fcu.gtml.domain.TrackingLog;
import com.fcu.gtml.service.TrackingLogService;

public class TrackingLogPlatform {
    private static final Logger L = LoggerFactory.getLogger(TrackingLogPlatform.class);
    private static Properties prop = new Properties();
    private static ApplicationContext applicationContext = new ClassPathXmlApplicationContext("classpath:com/fcu/gtml/spring-config.xml", "classpath:com/fcu/gtml/spring-datasource.xml");
    private static TrackingLogService trackingLogService = applicationContext.getBean(TrackingLogService.class);
    static {
        try {
            prop.load(TrackingLogPlatform.class.getResourceAsStream("FilePath.properties"));
        } catch (IOException e) {
            // log exception
            L.error("read *.properties fail.", e);
            throw new RuntimeException("read properties Fail.", e);
        }
    }

    public static void main(String[] args){
        String filePath = prop.getProperty("EdxPath").toString();
        L.info("filePath:" + filePath);
        Collection<File> files = FileUtils.listFiles(FileUtils.getFile(filePath), null, true);
        List<TrackingLog> trackingLogs = new ArrayList<>();
        for (File file : files) {
            trackingLogs = LoadFile(file);
            //寫入資料
            trackingLogService.batchInsert(trackingLogs);
            //刪除資料
            file.delete();
            for (TrackingLog trackingLog : trackingLogs) {
                System.out.println(new DateTime(trackingLog.getTime()).toString("yyyy-MM-dd HH:mm:ss.SSS"));
                //trackingLog.getEventType()
            }
        }
    }

    private static List<TrackingLog> LoadFile(File file) {
        List<TrackingLog> logs = new ArrayList<>();
        try {
            FileReader fr = new FileReader(file.getAbsoluteFile());
            BufferedReader br = new BufferedReader(fr);
            String strRow = ""; // 讀第一行
            while ((strRow = br.readLine()) != null) {
                L.info("strRow:" + strRow);
                try {
                    TrackingLog tLog = JsonParser.parse(strRow, TrackingLog.class);
                    logs.add(tLog);
                } catch (Exception e) {
                    L.error("Error Data:{}", strRow);
                    L.error("FileReader Data fail.", e);
                }
            }
            br.close();
            fr.close();
        } catch (IOException e) {
            L.error("read *.txt fail.", e);
        }

        return logs;
    }
}
