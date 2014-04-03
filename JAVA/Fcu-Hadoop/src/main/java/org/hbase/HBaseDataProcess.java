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

import org.apache.commons.io.FileUtils;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.Cell;
import org.apache.hadoop.hbase.CellUtil;
import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.hbase.KeyValue;
import org.apache.hadoop.hbase.client.Get;
import org.apache.hadoop.hbase.client.HTable;
import org.apache.hadoop.hbase.client.Put;
import org.apache.hadoop.hbase.client.Result;
import org.apache.hadoop.hbase.util.Bytes;
import org.domain.NetFlow;
import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class HBaseDataProcess {
    private static final Logger L = LoggerFactory.getLogger(HBaseDataProcess.class);
    private static Properties prop = new Properties();
    private static Configuration conf = null;
    static{
        conf = HBaseConfiguration.create();
//        conf.set("hbase.zookeeper.quorum", "fcudm01,fcudm02,fcudm03,fcudm04,fcudm05,fcudm06,fcudm07,fcudm08,fcudm09,fcudm10,fcudm11,fcudm12,fcudm13,fcudm14,fcudm15,fcudm16,fcudm17,fcudm18,fcudm19,fcudm20,fcudm21,fcudm23,fcudm24");
//        conf.set("hbase.zookeeper.property.clientPort", "2181");
        try {
            prop.load(HBaseDataProcess.class.getResourceAsStream("FilePath.properties"));
        } catch (IOException e) {
            // log exception
            L.error("read *.properties fail.", e);
            throw new RuntimeException("read properties Fail.", e);
        }
    }
    /**
     * @param args
     */
    public static void main(String[] args) {
        try {
            //List<NetFlow> results = LoadFile();
            HTable table = new HTable(conf, "NetFlowLog");
//            for(NetFlow flow : results){
//                Put put = new Put(Bytes.toBytes(flow.getRow())); //Set Row Key
//                put.add(Bytes.toBytes("Src"), Bytes.toBytes("DateTime"),
//                        Bytes.toBytes(flow.getSrcDateTime()));//Set DateTime, value:flow.getSrcDateTime()
//                put.add(Bytes.toBytes("Src"), Bytes.toBytes("Duration"),
//                        Bytes.toBytes(flow.getSrcDuration()));//Set DateTime, value:flow.getSrcDateTime()
//                put.add(Bytes.toBytes("Src"), Bytes.toBytes("Proto"),
//                        Bytes.toBytes(flow.getSrcProto()));//Set DateTime, value:flow.getSrcDateTime()
//                put.add(Bytes.toBytes("Src"), Bytes.toBytes("IP"),
//                        Bytes.toBytes(flow.getSrcIP()));//Set DateTime, value:flow.getSrcDateTime()
//                put.add(Bytes.toBytes("Src"), Bytes.toBytes("Port"),
//                        Bytes.toBytes(flow.getSrcPort()));//Set DateTime, value:flow.getSrcDateTime()
//                put.add(Bytes.toBytes("Src"), Bytes.toBytes("Type"),
//                        Bytes.toBytes(flow.getSrcType()));//Set DateTime, value:flow.getSrcDateTime()
//                
//                put.add(Bytes.toBytes("Dst"), Bytes.toBytes("IP"),
//                        Bytes.toBytes(flow.getDstIP()));//Set IP, value:flow.getDstIP()
//                put.add(Bytes.toBytes("Dst"), Bytes.toBytes("Port"),
//                        Bytes.toBytes(flow.getDstPort()));//Set IP, value:flow.getDstIP()
//                put.add(Bytes.toBytes("Dst"), Bytes.toBytes("Type"),
//                        Bytes.toBytes(flow.getDstType()));//Set IP, value:flow.getDstIP()
//                table.put(put);
//            }
            //Get
            String rowKey = "R3";
            Get get = new Get(rowKey.getBytes());
            Result rs = table.get(get);

            for (Cell kv : rs.rawCells()) {
                  //此部分是0.96以前的API寫法
//                System.out.print(new String(kv.getRow()) + " ");
//                System.out.print(new String(kv.getFamily()) + ":");
//                System.out.print(new String(kv.getQualifier()) + " ");
//                System.out.print("TimeStamp:" + new DateTime(kv.getTimestamp()).toString("yyyy-MM-dd HH:mm:ss:mmm") + " ");
//                System.out.println(new String(kv.getValue()));
                //0.96以後的API寫法
                System.out.print(new String(CellUtil.cloneRow(kv)) + " ");
                System.out.print(new String(CellUtil.cloneFamily(kv)) + ":");
                System.out.print(new String(CellUtil.cloneQualifier(kv)) + " ");
                System.out.print("TimeStamp:" + new DateTime(kv.getTimestamp()).toString("yyyy-MM-dd HH:mm:ss:mmm") + " ");
                System.out.println(new String(CellUtil.cloneValue(kv)));
            }
            table.close();
            L.info("HBaseDataProcess Done.");
        } catch (Exception e) {
            L.error("HTable fail.", e);
            //e.printStackTrace();
        }
    }

    /**
     * 讀取txt檔案
     */
    private static List<NetFlow> LoadFile(){
        String filePath = prop.getProperty("FilePath").toString();
        String[] extensions = prop.getProperty("extensions").toString().split(",");
        L.info("filePath:"+filePath);
        L.info("extensions:"+extensions[0]);
        Collection<File> files = FileUtils.listFiles(FileUtils.getFile(filePath), extensions, true);
        List<NetFlow> results = new ArrayList<NetFlow>();
        int sum = 0;
        for (File file : files) {
            try {
                int count = 1;
                FileReader fr = new FileReader(file.getAbsoluteFile());
                BufferedReader br = new BufferedReader(fr);
                String strRow = ""; //讀第一行

                while ((strRow=br.readLine())!=null){
                    L.info("strRow:"+strRow);
                    String[] row = strRow.split("\\|");
                    try{
                        NetFlow domain = new NetFlow("R"+(sum + count), row[0], 
                                row[1], 
                                row[2], 
                                row[3], 
                                row[4], 
                                row[5], 
                                row[6], 
                                row[7], row[8]);
                        results.add(domain);
                        count++;
                    }catch(Exception e){
                        L.error("Error Data:" + Arrays.toString(row));
                        L.error("FileReader Data fail.", e);
                    }
                }
                sum = sum + count;
                br.close();
                fr.close();
            } catch (IOException e) {
                L.error("read *.txt fail.", e);
            }
        }
        return results;
    }
}
