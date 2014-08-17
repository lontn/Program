package org.hadoop;

import java.io.IOException;

import org.apache.hadoop.hbase.Cell;
import org.apache.hadoop.hbase.CellUtil;
import org.apache.hadoop.hbase.client.HTable;
import org.apache.hadoop.hbase.client.Result;
import org.apache.hadoop.hbase.client.ResultScanner;
import org.apache.hadoop.hbase.client.Scan;
import org.apache.hadoop.hbase.util.Bytes;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.unitTest.SupportHBaseTest;

public class NetFlowStatisticsTest extends SupportHBaseTest {
    private static final Logger L = LoggerFactory.getLogger(NetFlowStatisticsTest.class);

    public static void main(String[] args){
        try {
            HTable table = new HTable(conf, "NetFlowHistoryLog");
            Scan scan = new Scan();
            scan.addColumn(Bytes.toBytes("Info"), Bytes.toBytes("SIp"));
            scan.addColumn(Bytes.toBytes("Info"), Bytes.toBytes("Length"));
            ResultScanner results = table.getScanner(scan);
            for (Result result : results) {
                for (Cell rowKV : result.rawCells()) {
                    L.info("Row Name:{}, column Family:{}, Qualifier:{}, Value:{} ",
                            new String(CellUtil.cloneRow(rowKV)), new String(
                                    CellUtil.cloneFamily(rowKV)), new String(
                                    CellUtil.cloneQualifier(rowKV)),
                            new String(CellUtil.cloneValue(rowKV)));
                }
            }
            results.close();
            table.close();
        } catch (IOException e) {
            L.error("HTable Query fail.", e);
        }
        
    }

    @Test
    public void HBaseTest(){
        try {
            HTable table = new HTable(conf, "NetFlowHistoryLog");
            Scan scan = new Scan();
            scan.addColumn(Bytes.toBytes("Info"), Bytes.toBytes("SIp"));
            scan.addColumn(Bytes.toBytes("Info"), Bytes.toBytes("Length"));
            ResultScanner results = table.getScanner(scan);
            String qualifier = null;
            String sIpVale = null;
            Integer packageLength = null;
            for (Result result : results) {
                for (Cell rowKV : result.rawCells()) {
                    qualifier = new String(CellUtil.cloneQualifier(rowKV));
                    L.info("qualifier:"+ qualifier);
                    if(qualifier.equals("SIp")){
                        sIpVale = new String(CellUtil.cloneValue(rowKV));
                    }
                    if(qualifier.equals("Length")){
                        packageLength = Integer.parseInt(new String(CellUtil.cloneValue(rowKV)));
                    }
                }
                L.info("sIpVale:{}, packageLength:{}", sIpVale, packageLength);
            }
            results.close();
            table.close();
        } catch (Exception e) {
            L.error("HTable Query fail.", e);
        } finally{
            
        }
    }
    
    @Test
    public void test(){
        String word = "www.sciencedirect.com";
        word = word.replace("http://", "");
        String[] result = word.split("/");
        L.info("result:{}", result[0]);
    }
}
