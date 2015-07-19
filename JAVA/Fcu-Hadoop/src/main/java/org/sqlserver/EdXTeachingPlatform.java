package org.sqlserver;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Properties;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.time.StopWatch;
import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.fcu.gtml.domain.StudentLog;
import com.fcu.gtml.service.StudentLogService;

import org.supercsv.io.CsvListReader;
import org.supercsv.prefs.CsvPreference;

public class EdXTeachingPlatform {
    private static final Logger L = LoggerFactory.getLogger(EdXTeachingPlatform.class);
    private static Properties prop = new Properties();
    private static ApplicationContext applicationContext = new ClassPathXmlApplicationContext("classpath:com/fcu/gtml/spring-config.xml", "classpath:com/fcu/gtml/spring-datasource.xml");
    private static StudentLogService studentService = applicationContext.getBean(StudentLogService.class);
    static{
        try {
            prop.load(ProxyLogIntoSQLServer.class.getResourceAsStream("FilePath.properties"));
        } catch (IOException e) {
            // log exception
            L.error("read *.properties fail.", e);
            throw new RuntimeException("read properties Fail.", e);
        }
    }
    
    public static void main(String[] args){
        String filePath = prop.getProperty("EdxPath").toString();
        String[] extensions = prop.getProperty("extensions").toString().split(",");
        L.info("filePath:" + filePath);
        L.info("extensions[0]:{}", extensions[0], "extensions[1]:{}", extensions[1]);
        Collection<File> files = FileUtils.listFiles(FileUtils.getFile(filePath), extensions, true);
        L.info("files:{}", files);
        for(File file : files){
            try {
                loadFile(file);
                file.delete();
            } catch (Exception e) {
                L.error("EdXTeachingPlatform fail.", e);
            }
        }
        L.info("EdXTeachingPlatform Done.");
    }

    @SuppressWarnings("resource")
    private static void loadFile(File file) {
        try {
            List<StudentLog> results = new ArrayList<>();
            //DataInputStream in = new DataInputStream(new FileInputStream(file)); 
            //BufferedReader br= new BufferedReader(new InputStreamReader(in,"UTF-8"));
            //InputStreamReader isr = new InputStreamReader(in,"UTF-8");
            FileReader fr = new FileReader(file);
            //InputStreamReader freader = new InputStreamReader(new FileInputStream(file), "UTF-8");
            CsvListReader listReader = new CsvListReader(fr, CsvPreference.EXCEL_PREFERENCE);
            listReader.getHeader(true); // skip the header
            StopWatch stopWatch = new StopWatch();
            stopWatch.start();
            List<String> row = new ArrayList<>();

            while ((row = listReader.read()) !=null) {
                StudentLog student = new StudentLog();
                student.setUserName(row.get(0));
                student.setEventType(row.get(1));
                student.setEvent(row.get(5));
                student.setEventSource(row.get(6));
                student.setContextUserId(row.get(7) != null ? Integer.parseInt(row.get(7)) : null);
                student.setContextOrgId(row.get(8));
                student.setContextSession(row.get(9).equals("null") ? null : row.get(9));
                student.setContextCourseid(row.get(10));
                student.setContextPath(row.get(11));
                student.setContextModuleDisplayName(row.get(12).equals("null") ? null : row.get(12));
                student.setTime(new DateTime(row.get(13)).toDate());
                student.setPage(row.get(14).equals("null") ? null : row.get(14));
                results.add(student);
                //L.info("row:{}", row.get(12));
                if (results.size() % 1000 == 0) {
                    studentService.batchInsert(results);
                    L.info("1000:{}, %:{}", stopWatch.toString(), results.size() % 1000);
                    results.clear(); //重新塞一次
                }
            }
            //處理小於1000的
            if (results.size() > 0 && results.size() < 1000) {
                studentService.batchInsert(results);
                L.info("result:{}", results.size());
            }
            stopWatch.stop();
            //in.close();
            //isr.close();
            fr.close();
        }catch(IOException e) {
            L.error("read *.txt fail.", e);
        }
    }
}
