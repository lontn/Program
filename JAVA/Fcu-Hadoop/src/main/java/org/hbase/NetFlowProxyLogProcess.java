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
import org.apache.hadoop.hbase.client.HConnection;
import org.apache.hadoop.hbase.client.HConnectionManager;
import org.apache.hadoop.hbase.client.HTable;
import org.apache.hadoop.hbase.client.HTableInterface;
import org.apache.hadoop.hbase.client.Put;
import org.apache.hadoop.hbase.client.Result;
import org.apache.hadoop.hbase.util.Bytes;
import org.domain.NetFlowHistory;
import org.domain.NetFlowProxyLog;
import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class NetFlowProxyLogProcess {
    
    private static final Logger L = LoggerFactory.getLogger(NetFlowProxyLogProcess.class);
    private static Properties prop = new Properties();
    private static Configuration conf = null;
    static{
        conf = HBaseConfiguration.create();
        try {
            prop.load(NetFlowProxyLogProcess.class.getResourceAsStream("FilePath.properties"));
        } catch (IOException e) {
            // log exception
            L.error("read *.properties fail.", e);
            throw new RuntimeException("read properties Fail.", e);
        }
    }

    /**
     * 兩種建立連線方式
     * 1.建立表格的實體(instance)方式，此方法比較佔網路資源，且
     * HTable這個類對於讀寫來說，都是線程(Thread)不安全的。所以，在多線程(multi thread)的環境下，要么使用連接池，
     * 要么把HTable變成一個線程局部變量。對於頻繁創建線程的情況下，意味需要頻繁的創建、關閉HTable，這種情況下使用連接池更好點。
     * 
     * 2.在多線程環境下，我們可以使用HTablePool這個連接池實現類，
     * 但同時，我們卻又發現，HTablePool被標記為@deprecated。這說明，現在已經不推薦使用這個類的實現了。
     * 官方推薦​​我們使用HConnectionManager的getTable方法，來代替HTablePool。
     * @param args
     */
    public static void main(String[] args) {
        try {
            DateTime dt = new DateTime();
            String filePath = prop.getProperty("FilePath").toString();
            String[] extensions = prop.getProperty("extensions").toString().split(",");
            L.info("filePath:"+filePath);
            L.info("extensions:"+extensions[0]);
            Collection<File> files = FileUtils.listFiles(FileUtils.getFile(filePath), extensions, true);
            L.info("Start.."+dt.toString("yyyy-MM-dd HH:mm:ss:mmm"));
            //連接管理
            HConnection connection = HConnectionManager.createConnection(conf);
            HTableInterface table = connection.getTable("NetFlowProxyLogTest");
            //建立表格的實體(instance)方式
            //HTable table = new HTable(conf, "NetFlowProxyLog");
            for(File file : files){
                List<NetFlowProxyLog> results = LoadFile(file);
                for(NetFlowProxyLog flow : results){
                    Put put = new Put(Bytes.toBytes(flow.getRow())); //Set Row Key
                    put.add(Bytes.toBytes("LibraryInfo"), Bytes.toBytes("Timestemp"), Bytes.toBytes(flow.getTimesTemp()));//Set Time, value:flow.getTime()
                    put.add(Bytes.toBytes("LibraryInfo"), Bytes.toBytes("ResponseTime"), Bytes.toBytes(flow.getResponseTime()));//Set length, value:flow.getLength()
                    put.add(Bytes.toBytes("LibraryInfo"), Bytes.toBytes("ClientAddress"), Bytes.toBytes(flow.getClientAddress()));//Set Protocol, value:flow.getProtocol()
                    put.add(Bytes.toBytes("LibraryInfo"), Bytes.toBytes("StatusCode"), Bytes.toBytes(flow.getStatusCode()));//Set SIp, value:flow.getsIp()
                    put.add(Bytes.toBytes("LibraryInfo"), Bytes.toBytes("TransportSize"), Bytes.toBytes(flow.getTransportSize()));//Set SPort, value:flow.getsPort()
                    put.add(Bytes.toBytes("LibraryInfo"), Bytes.toBytes("RequestMethod"), Bytes.toBytes(flow.getRequestMethod()));//Set DIp, value:flow.getdIp()
                    put.add(Bytes.toBytes("LibraryInfo"), Bytes.toBytes("URI"),  Bytes.toBytes(flow.getUri()));
                    put.add(Bytes.toBytes("LibraryInfo"), Bytes.toBytes("ClientIdentity"),  Bytes.toBytes(flow.getClientIdentity()));
                    put.add(Bytes.toBytes("LibraryInfo"), Bytes.toBytes("RightSideCoding"),  Bytes.toBytes(flow.getRightSideCoding()));
                    put.add(Bytes.toBytes("LibraryInfo"), Bytes.toBytes("ContentType"),  Bytes.toBytes(flow.getContentType()));
                    table.put(put);
                }
                //跑完一個刪一個
                file.delete();
            }

            table.close();
            connection.close();
            L.info("NetFlowProxyLogProcess Done.");
            L.info("END.."+dt.toString("yyyy-MM-dd HH:mm:ss:mmm"));
        } catch (Exception e) {
            L.error("HTable fail.", e);
            //e.printStackTrace();
        }

    }

    /**
     * 讀取txt檔案，以一個檔案為單位
     * @param file
     * @return
     */
    private static List<NetFlowProxyLog> LoadFile(File file){
        List<NetFlowProxyLog> results = new ArrayList<NetFlowProxyLog>();

        try {
            FileReader fr = new FileReader(file.getAbsoluteFile());
            BufferedReader br = new BufferedReader(fr);
            String strRow = ""; // 讀第一行
            while ((strRow = br.readLine()) != null) {
                //L.info("strRow:" + strRow);
                String[] row = strRow.split(" +");
                String guid = UUID.randomUUID().toString();
                try {
                    NetFlowProxyLog domain = new NetFlowProxyLog(guid, row[0],
                            row[1], row[2], row[3], row[4], row[5], row[6], row[7], row[8], row[9]);
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
