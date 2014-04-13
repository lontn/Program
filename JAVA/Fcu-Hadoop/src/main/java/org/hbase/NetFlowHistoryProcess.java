package org.hbase;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Properties;
import java.util.UUID;

import org.apache.commons.io.FileUtils;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.Cell;
import org.apache.hadoop.hbase.CellUtil;
import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.hbase.client.Get;
import org.apache.hadoop.hbase.client.HTable;
import org.apache.hadoop.hbase.client.Put;
import org.apache.hadoop.hbase.client.Result;
import org.apache.hadoop.hbase.util.Bytes;
import org.domain.NetFlowHistory;
import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class NetFlowHistoryProcess {
    
    private static final Logger L = LoggerFactory.getLogger(NetFlowHistoryProcess.class);
    private static Properties prop = new Properties();
    private static Configuration conf = null;
    static{
        conf = HBaseConfiguration.create();
        try {
            prop.load(HBaseDataProcess.class.getResourceAsStream("FilePath.properties"));
        } catch (IOException e) {
            // log exception
            L.error("read *.properties fail.", e);
            throw new RuntimeException("read properties Fail.", e);
        }
    }

    public static void main(String[] args) {
        try {
            DateTime dt = new DateTime();
            String filePath = prop.getProperty("FilePath").toString();
            String[] extensions = prop.getProperty("extensions").toString().split(",");
            L.info("filePath:"+filePath);
            L.info("extensions:"+extensions[0]);
            Collection<File> files = FileUtils.listFiles(FileUtils.getFile(filePath), extensions, true);
            L.info("Start.."+dt.toString("yyyy-MM-dd HH:mm:ss:mmm"));
            HTable table = new HTable(conf, "NetFlowHistoryLog");
            for(File file : files){
                List<NetFlowHistory> results = LoadFile(file);
                for(NetFlowHistory flow : results){
                    Put put = new Put(Bytes.toBytes(flow.getRow())); //Set Row Key
                    put.add(Bytes.toBytes("Info"), Bytes.toBytes("Time"), Bytes.toBytes(flow.getTime()));//Set Time, value:flow.getTime()
                    put.add(Bytes.toBytes("Info"), Bytes.toBytes("Length"), Bytes.toBytes(flow.getLength()));//Set length, value:flow.getLength()
                    put.add(Bytes.toBytes("Info"), Bytes.toBytes("Protocol"), Bytes.toBytes(flow.getProtocol()));//Set Protocol, value:flow.getProtocol()
                    put.add(Bytes.toBytes("Info"), Bytes.toBytes("SIp"), Bytes.toBytes(flow.getsIp()));//Set SIp, value:flow.getsIp()
                    put.add(Bytes.toBytes("Info"), Bytes.toBytes("SPort"), Bytes.toBytes(flow.getsPort()));//Set SPort, value:flow.getsPort()
                    put.add(Bytes.toBytes("Info"), Bytes.toBytes("DIp"), Bytes.toBytes(flow.getdIp()));//Set DIp, value:flow.getdIp()
                    put.add(Bytes.toBytes("Info"), Bytes.toBytes("DPort"),  Bytes.toBytes(flow.getdPort()));//Set DPort, value:flow.getdPort()
                    table.put(put);
                }
            }

            table.close();
            L.info("NetFlowHistoryProcess Done.");
            L.info("END.."+dt.toString("yyyy-MM-dd HH:mm:ss:mmm"));
        } catch (Exception e) {
            L.error("HTable fail.", e);
            //e.printStackTrace();
        }

    }

    /**
     * 讀取txt檔案，以一個檔案為單位
     */
    private static List<NetFlowHistory> LoadFile(File file){
        List<NetFlowHistory> results = new ArrayList<NetFlowHistory>();

        try {
            FileReader fr = new FileReader(file.getAbsoluteFile());
            BufferedReader br = new BufferedReader(fr);
            String strRow = ""; // 讀第一行
            while ((strRow = br.readLine()) != null) {
                L.info("strRow:" + strRow);
                String[] row = strRow.split("\\|");
                String guid = UUID.randomUUID().toString();
                try {
                    NetFlowHistory domain = new NetFlowHistory(guid, row[0],
                            row[1], row[2], row[3], row[4], row[5], row[6]);
                    results.add(domain);
                } catch (Exception e) {
                    L.error("Error Data:" + Arrays.toString(row));
                    L.error("FileReader Data fail.", e);
                }
            }
            br.close();
            fr.close();
        } catch (IOException e) {
            L.error("read *.txt fail.", e);
        }
        return results;
    }
}
