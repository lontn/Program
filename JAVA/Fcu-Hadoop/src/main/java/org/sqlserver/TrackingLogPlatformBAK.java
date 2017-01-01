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

import com.fasterxml.jackson.databind.JsonNode;
import com.fcu.gtml.domain.TrackingLog;
import com.fcu.gtml.domain.TrackingLogContext;
import com.fcu.gtml.service.TrackingLogService;

public class TrackingLogPlatformBAK {
    private static final Logger L = LoggerFactory.getLogger(TrackingLogPlatformBAK.class);
    private static Properties prop = new Properties();
    private static ApplicationContext applicationContext = new ClassPathXmlApplicationContext("classpath:com/fcu/gtml/spring-config.xml", "classpath:com/fcu/gtml/spring-datasource.xml");
    private static TrackingLogService trackingLogService = applicationContext.getBean(TrackingLogService.class);
    static {
        try {
            prop.load(TrackingLogPlatformBAK.class.getResourceAsStream("FilePath.properties"));
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
            for (TrackingLog trackingLog : trackingLogs) {
                try{
                    trackingLogService.batchInsertBak(trackingLog);
                } catch (Exception e) {
                    L.error("studentLog warring:{}", trackingLog);
                    L.error("batchInsert fail.", e);
                }
            }
            //刪除資料
            file.delete();
            L.info("delete File:{}", file.getName());
//            for (TrackingLog trackingLog : trackingLogs) {
//                System.out.println(new DateTime(trackingLog.getTime()).toString("yyyy-MM-dd HH:mm:ss.SSS"));
                //trackingLog.getEventType()
//            }
        }
        L.info("All Done!!");
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
                    JsonNode jnode = JsonParser.parse(strRow);
                    String userName = jnode.path("username").asText();
                    String eventType = jnode.path("event_type").asText();
                    String ip = jnode.path("ip").asText();
                    String agent = jnode.path("agent").asText();
                    String host = jnode.path("host").asText();
                    String session = (jnode.path("session").asText() == "null" || jnode.path("session").asText() == "") ? null : jnode.path("session").asText();
                    String event = jnode.path("event").toString();
                    String eventSource = jnode.path("event_source").asText();
                    String context = jnode.path("context").toString();
                    String time = jnode.path("time").asText();
                    String page = jnode.path("page").asText() == "null" ? null : jnode.path("page").asText();
                    TrackingLog tlog = new TrackingLog();
                    tlog.setUserName(userName);
                    tlog.setEventType(eventType);
                    tlog.setIp(ip);
                    tlog.setAgent(agent);
                    tlog.setHost(host);
                    tlog.setSession(session);
                    tlog.setEvent(event);
                    tlog.setEventSource(eventSource);
                    tlog.setContext(context);
                    tlog.setTime(time);
                    tlog.setPage(page);
                    JsonNode contextNode = JsonParser.parse(context);
                    int userId = contextNode.path("user_id").asInt();
                    String orgId = contextNode.path("org_id").asText();
                    String courseId = contextNode.path("course_id").asText();
                    String path = contextNode.path("path").asText();
                    TrackingLogContext contextObj = new TrackingLogContext();
                    contextObj.setUserId(userId);
                    contextObj.setOrgId(orgId);
                    contextObj.setCourseId(courseId);
                    contextObj.setPath(path);
                    tlog.setTlogContext(contextObj);
                    logs.add(tlog);
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
