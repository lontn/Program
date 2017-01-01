package org.edx;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Properties;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.hbase.TableName;
import org.apache.hadoop.hbase.client.Connection;
import org.apache.hadoop.hbase.client.ConnectionFactory;
import org.apache.hadoop.hbase.client.Put;
import org.apache.hadoop.hbase.client.Table;
import org.apache.hadoop.hbase.util.Bytes;
import org.domain.OpenEdxLog;
import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import au.com.bytecode.opencsv.CSVParser;
import au.com.bytecode.opencsv.CSVReader;



public class OpenEdxDataProcess {
    private static final Logger L = LoggerFactory.getLogger(OpenEdxDataProcess.class);
    private static Properties prop = new Properties();
    private static Configuration conf = null;
    static {
        conf = HBaseConfiguration.create();
        try {
            prop.load(OpenEdxDataProcess.class.getResourceAsStream("FilePath.properties"));
        } catch (IOException e) {
            // log exception
            L.error("read *.properties fail.", e);
            throw new RuntimeException("read properties Fail.", e);
        }
    }
    public static void main(String[] args) {
        System.out.println("AAA");
        DateTime dt = new DateTime();
        String filePath = prop.getProperty("FilePath").toString();
        String[] extensions = prop.getProperty("extensions").toString().split(",");
        L.info("filePath:"+filePath);
        L.info("extensions:"+extensions[0]);
        Collection<File> files = FileUtils.listFiles(FileUtils.getFile(filePath), extensions, true);
        L.info("Start.."+dt.toString("yyyy-MM-dd HH:mm:ss:mmm"));
        //L.info("conf:{}", conf);
        try (Connection connection = ConnectionFactory.createConnection(conf)) {
            try (Table table = connection.getTable(TableName.valueOf("OpenEdxHistory"))) {
                for (File file : files) {
                    List<OpenEdxLog> results = LoadFile(file);
                    for (OpenEdxLog openEdxLog : results) {
                        L.info("openEdxLog:{}", openEdxLog);
                        Put put = new Put(Bytes.toBytes(openEdxLog.getUserName() + openEdxLog.getLongTime())); //Set Row Key
                        put.addColumn(Bytes.toBytes("Info"), Bytes.toBytes("Username"), Bytes.toBytes(openEdxLog.getUserName()));
                        put.addColumn(Bytes.toBytes("Info"), Bytes.toBytes("Eventtype"), Bytes.toBytes(openEdxLog.getEventType()));
                        put.addColumn(Bytes.toBytes("Info"), Bytes.toBytes("IP"), Bytes.toBytes(openEdxLog.getIp()));
                        put.addColumn(Bytes.toBytes("Info"), Bytes.toBytes("Agent"), Bytes.toBytes(openEdxLog.getAgent()));
                        put.addColumn(Bytes.toBytes("Info"), Bytes.toBytes("Host"), Bytes.toBytes(openEdxLog.getHost()));
                        put.addColumn(Bytes.toBytes("Info"), Bytes.toBytes("Event"), Bytes.toBytes(openEdxLog.getEvent()));
                        put.addColumn(Bytes.toBytes("Info"), Bytes.toBytes("EventSource"), Bytes.toBytes(openEdxLog.getEventSource()));
                        put.addColumn(Bytes.toBytes("Info"), Bytes.toBytes("ContextUserid"), Bytes.toBytes(openEdxLog.getContextUserId()));
                        put.addColumn(Bytes.toBytes("Info"), Bytes.toBytes("ContextOrgid"), Bytes.toBytes(openEdxLog.getContextOrgId()));
                        put.addColumn(Bytes.toBytes("Info"), Bytes.toBytes("ContextSession"), Bytes.toBytes(openEdxLog.getContextSession()));
                        put.addColumn(Bytes.toBytes("Info"), Bytes.toBytes("ContextCourseid"), Bytes.toBytes(openEdxLog.getContextCourseId()));
                        put.addColumn(Bytes.toBytes("Info"), Bytes.toBytes("ContextPath"), Bytes.toBytes(openEdxLog.getContextPath()));
                        put.addColumn(Bytes.toBytes("Info"), Bytes.toBytes("ContextModuleDisplayName"), Bytes.toBytes(openEdxLog.getContextModuleDisplayName()));
                        put.addColumn(Bytes.toBytes("Info"), Bytes.toBytes("Time"), Bytes.toBytes(openEdxLog.getTime()));
                        put.addColumn(Bytes.toBytes("Info"), Bytes.toBytes("Page"), Bytes.toBytes(openEdxLog.getPage()));
                        table.put(put);
                        L.info("table put done.");
                    }
                    file.delete();
                }
                L.info("OpenEdxHistory Done.");
            }
        } catch (Exception e) {
            // TODO Auto-generated catch block
            L.error("Table fail.", e);
        }
    }
    
    private static List<OpenEdxLog> LoadFile(File file) {
        List<OpenEdxLog> resultData = new ArrayList<>();
        CSVReader reader = null;
        try {
            FileReader fr = new FileReader(file.getAbsoluteFile());
            //Get the CSVReader instance with specifying the delimiter to be used
            reader = new CSVReader(fr, ',', CSVParser.DEFAULT_QUOTE_CHARACTER, 1);
            String [] strRow = {};
            while ((strRow = reader.readNext()) != null) {
                try {
                    if (strRow[0].equals("null") || strRow[13].equals("null")) {
                        L.info("null Data:" + Arrays.toString(strRow));
                        continue;
                    }
                    OpenEdxLog edx = new OpenEdxLog(strRow[0], strRow[1],
                            strRow[2], strRow[3], strRow[4], strRow[5],
                            strRow[6], strRow[7], strRow[8], strRow[9],
                            strRow[10], strRow[11], strRow[12],
                            new DateTime(strRow[13]).toString("yyyy-MM-dd HH:mm:ss.SSS"),
                            strRow[14], new DateTime(strRow[13]).toDate().getTime());
                    resultData.add(edx);
                } catch (Exception e) {
                    L.error("Error Data:" + Arrays.toString(strRow));
                    L.error("FileReader Data fail.", e);
                }
            }
            fr.close();
        } catch (IOException e) {
            L.error("read *.csv fail.", e);
        } finally {
            try {
                reader.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return resultData;
    }
}
