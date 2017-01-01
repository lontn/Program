package org.edx;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Properties;

import org.apache.commons.io.FileUtils;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.hbase.TableName;
import org.apache.hadoop.hbase.client.Connection;
import org.apache.hadoop.hbase.client.ConnectionFactory;
import org.apache.hadoop.hbase.client.Put;
import org.apache.hadoop.hbase.client.Table;
import org.apache.hadoop.hbase.util.Bytes;
import org.common.utils.JsonParser;
import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fcu.gtml.domain.TrackingLog;

public class TrackingLogProcess {
    private static final Logger L = LoggerFactory.getLogger(TrackingLogProcess.class);
    private static final String TABLENAME = "TrackingLogTest";
    private static Properties prop = new Properties();
    private static Configuration conf = null;
    static {
        conf = HBaseConfiguration.create();
        try {
            prop.load(TrackingLogProcess.class.getResourceAsStream("FilePath.properties"));
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
        Collection<File> files = FileUtils.listFiles(FileUtils.getFile(filePath), null, true);
        L.info("Start.."+dt.toString("yyyy-MM-dd HH:mm:ss:mmm"));
        try (Connection connection = ConnectionFactory.createConnection(conf)) {
            try (Table table = connection.getTable(TableName.valueOf(TABLENAME))) {
                for (File file : files) {
                    List<TrackingLog> trackingLogs = LoadFile(file);
                    putHBaseData(table, trackingLogs);
                    L.info("file put done.");
                    file.delete();
                }
                L.info("process done.");
            }
        } catch(Exception e) {
            L.error("Connection fail.", e);
        }
    }

    private static void putHBaseData(Table table, List<TrackingLog> trackingLogs) throws IOException {
        for (TrackingLog trackingLog : trackingLogs) {
            Put put = new Put(Bytes.toBytes(trackingLog.getEventType() + trackingLog.getTime().getTime())); //Set Row Key
            put.addColumn(Bytes.toBytes("Info"), Bytes.toBytes("username"), Bytes.toBytes(trackingLog.getUserName()));
            put.addColumn(Bytes.toBytes("Info"), Bytes.toBytes("event_type"), Bytes.toBytes(trackingLog.getEventType()));
            put.addColumn(Bytes.toBytes("Info"), Bytes.toBytes("ip"), Bytes.toBytes(trackingLog.getIp()));
            put.addColumn(Bytes.toBytes("Info"), Bytes.toBytes("agent"), Bytes.toBytes(trackingLog.getAgent()));
            put.addColumn(Bytes.toBytes("Info"), Bytes.toBytes("host"), Bytes.toBytes(trackingLog.getHost()));
            put.addColumn(Bytes.toBytes("Info"), Bytes.toBytes("session"), Bytes.toBytes(trackingLog.getSession() != null ? trackingLog.getSession() : ""));
            put.addColumn(Bytes.toBytes("Info"), Bytes.toBytes("event"), Bytes.toBytes(trackingLog.getEvent()));
            put.addColumn(Bytes.toBytes("Info"), Bytes.toBytes("event_source"), Bytes.toBytes(trackingLog.getEventSource()));
            put.addColumn(Bytes.toBytes("Info"), Bytes.toBytes("context"), Bytes.toBytes(trackingLog.getContext()));
            put.addColumn(Bytes.toBytes("Info"), Bytes.toBytes("time"), Bytes.toBytes(new DateTime(trackingLog.getTime()).toString("yyyy-MM-dd HH:mm:ss.SSS")));
            put.addColumn(Bytes.toBytes("Info"), Bytes.toBytes("page"), Bytes.toBytes(trackingLog.getPage() != null ? trackingLog.getPage() : ""));
            table.put(put);
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
