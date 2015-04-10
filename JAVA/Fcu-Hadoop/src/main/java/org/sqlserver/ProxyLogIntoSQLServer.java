package org.sqlserver;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Properties;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.time.StopWatch;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.fcu.gtml.domain.UserVisitorAccess;
import com.fcu.gtml.service.UserVisitorAccessService;

public class ProxyLogIntoSQLServer {
    private static final Logger L = LoggerFactory.getLogger(ProxyLogIntoSQLServer.class);
    private static Properties prop = new Properties();
    //private static Configuration conf = null;
    private static ApplicationContext applicationContext = new ClassPathXmlApplicationContext("classpath:com/fcu/gtml/spring-config.xml", "classpath:com/fcu/gtml/spring-datasource.xml");
    private static UserVisitorAccessService visitorService = applicationContext.getBean(UserVisitorAccessService.class);
    static{
        try {
            prop.load(ProxyLogIntoSQLServer.class.getResourceAsStream("FilePath.properties"));
        } catch (IOException e) {
            // log exception
            L.error("read *.properties fail.", e);
            throw new RuntimeException("read properties Fail.", e);
        }
    }

    public static void main(String[] args) {
        String filePath = prop.getProperty("FilePath").toString();
        String[] extensions = prop.getProperty("extensions").toString().split(",");
        L.info("filePath:" + filePath);
        L.info("extensions:" + extensions[0]);
        Collection<File> files = FileUtils.listFiles(FileUtils.getFile(filePath), null, true);
        L.info("files:{}", files);
        for(File file : files){
            try {
                loadFile(file);
                file.delete();
            } catch (Exception e) {
                L.error("ProxyLogIntoSQLServer fail.", e);
            }
        }
        L.info("ProxyLogIntoSQLServer Done.");
        
    }
    
    private static void loadFile(File file) {
        try {
            List<UserVisitorAccess> results = new ArrayList<>();
            FileReader fr = new FileReader(file.getAbsoluteFile());
            BufferedReader br = new BufferedReader(fr);
            String strRow = ""; // 讀第一行
            StopWatch stopWatch = new StopWatch();
            stopWatch.start();
            
            while ((strRow = br.readLine()) != null) {
                //L.info("strRow:" + strRow);
                String[] row = strRow.split("-@-");
                String clientId = row[3].split("\t")[0].equals("-") ? null : row[3].split("\t")[0];

                UserVisitorAccess visitor = new UserVisitorAccess(DateTime.parse(row[0], DateTimeFormat.forPattern("yyyy-MM-dd HH:mm:ss.sss")).toDate(), Integer.parseInt(row[1]), row[2], clientId);
                results.add(visitor);
                //L.info("stopWatch Time:{}, visitor:{}", stopWatch.toString(), visitor);
                if (results.size() % 1000 == 0) {
                    visitorService.batchInsert(results);
                    L.info("1000:{}, %:{}", stopWatch.toString(), results.size() % 1000);
                    results.clear(); //重新塞一次
                }

            }
            stopWatch.stop();
            br.close();
            fr.close();
        } catch (IOException e) {
            L.error("read *.txt fail.", e);
        }
    }
}
