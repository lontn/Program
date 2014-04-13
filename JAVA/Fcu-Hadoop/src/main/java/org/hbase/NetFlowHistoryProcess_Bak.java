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

public class NetFlowHistoryProcess_Bak {
    
    private static final Logger L = LoggerFactory.getLogger(NetFlowHistoryProcess_Bak.class);
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
            L.info("Start.."+dt.toString("yyyy-MM-dd HH:mm:ss:mmm"));
            List<NetFlowHistory> results = LoadFile();
            HTable table = new HTable(conf, "NetFlowHistory");
            for(NetFlowHistory flow : results){
                Put put = new Put(Bytes.toBytes(flow.getRow())); //Set Row Key
                put.add(Bytes.toBytes("Info"), Bytes.toBytes("Time"),
                        Bytes.toBytes(flow.getTime()));//Set Time, value:flow.getTime()
                put.add(Bytes.toBytes("Info"), Bytes.toBytes("Length"),
                        Bytes.toBytes(flow.getLength()));//Set length, value:flow.getLength()
                put.add(Bytes.toBytes("Info"), Bytes.toBytes("Protocol"),
                        Bytes.toBytes(flow.getProtocol()));//Set Protocol, value:flow.getProtocol()
                put.add(Bytes.toBytes("Info"), Bytes.toBytes("SIp"),
                        Bytes.toBytes(flow.getsIp()));//Set SIp, value:flow.getsIp()
                put.add(Bytes.toBytes("Info"), Bytes.toBytes("SPort"),
                        Bytes.toBytes(flow.getsPort()));//Set SPort, value:flow.getsPort()
                put.add(Bytes.toBytes("Info"), Bytes.toBytes("DIp"),
                        Bytes.toBytes(flow.getdIp()));//Set DIp, value:flow.getdIp()
                put.add(Bytes.toBytes("Info"), Bytes.toBytes("DPort"),
                        Bytes.toBytes(flow.getdPort()));//Set DPort, value:flow.getdPort()
                table.put(put);
            }
            //Get
//            String rowKey = "R3";
//            Get get = new Get(rowKey.getBytes());
//            Result rs = table.get(get);
//
//            for (Cell kv : rs.rawCells()) {
//                  //此部分是0.96以前的API寫法
////                System.out.print(new String(kv.getRow()) + " ");
////                System.out.print(new String(kv.getFamily()) + ":");
////                System.out.print(new String(kv.getQualifier()) + " ");
////                System.out.print("TimeStamp:" + new DateTime(kv.getTimestamp()).toString("yyyy-MM-dd HH:mm:ss:mmm") + " ");
////                System.out.println(new String(kv.getValue()));
//                //0.96以後的API寫法
//                System.out.print(new String(CellUtil.cloneRow(kv)) + " ");
//                System.out.print(new String(CellUtil.cloneFamily(kv)) + ":");
//                System.out.print(new String(CellUtil.cloneQualifier(kv)) + " ");
//                System.out.print("TimeStamp:" + new DateTime(kv.getTimestamp()).toString("yyyy-MM-dd HH:mm:ss:mmm") + " ");
//                System.out.println(new String(CellUtil.cloneValue(kv)));
//            }
            table.close();
            L.info("NetFlowHistoryProcess Done.");
            L.info("END.."+dt.toString("yyyy-MM-dd HH:mm:ss:mmm"));
        } catch (Exception e) {
            L.error("HTable fail.", e);
            //e.printStackTrace();
        }

    }

    /**
     * 讀取txt檔案
     */
    private static List<NetFlowHistory> LoadFile(){
        String filePath = prop.getProperty("FilePath").toString();
        String[] extensions = prop.getProperty("extensions").toString().split(",");
        L.info("filePath:"+filePath);
        L.info("extensions:"+extensions[0]);
        Collection<File> files = FileUtils.listFiles(FileUtils.getFile(filePath), extensions, true);
        List<NetFlowHistory> results = new ArrayList<NetFlowHistory>();

        for (File file : files) {
            try {
                FileReader fr = new FileReader(file.getAbsoluteFile());
                BufferedReader br = new BufferedReader(fr);
                String strRow = ""; //讀第一行
                while ((strRow=br.readLine())!=null){
                    L.info("strRow:"+strRow);
                    String[] row = strRow.split("\\|");
                    String guid = UUID.randomUUID().toString().toUpperCase();
                    try{
                        NetFlowHistory domain = new NetFlowHistory(guid, row[0], 
                                row[1], 
                                row[2], 
                                row[3], 
                                row[4], 
                                row[5], 
                                row[6]);
                        results.add(domain);
                    }catch(Exception e){
                        L.error("Error Data:" + Arrays.toString(row));
                        L.error("FileReader Data fail.", e);
                    }
                }
                br.close();
                fr.close();
            } catch (IOException e) {
                L.error("read *.txt fail.", e);
            }
        }
        return results;
    }
}
