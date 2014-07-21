package org.hbase;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.PropertiesConfiguration;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.hbase.client.HConnection;
import org.apache.hadoop.hbase.client.HConnectionManager;
import org.apache.hadoop.hbase.client.HTableInterface;
import org.apache.hadoop.hbase.client.Result;
import org.apache.hadoop.hbase.client.ResultScanner;
import org.apache.hadoop.hbase.client.Scan;
import org.apache.hadoop.hbase.filter.CompareFilter.CompareOp;
import org.apache.hadoop.hbase.filter.Filter;
import org.apache.hadoop.hbase.filter.FilterList;
import org.apache.hadoop.hbase.filter.SingleColumnValueFilter;
import org.apache.hadoop.hbase.util.Bytes;
import org.domain.ProxyLog;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class NetFlowProxyLogQuery {
    private static final Logger L = LoggerFactory.getLogger(NetFlowProxyLogQuery.class);
    private static Configuration conf = null;
    private static PropertiesConfiguration config = null;
    private static final String RANGE = "TimeRange.properties";
    static{
        conf = HBaseConfiguration.create();
        try {
            config = new PropertiesConfiguration(RANGE);
        } catch (ConfigurationException e) {
            L.error("ConfigurationException:"+ e);
        }
        
    }
    public static void main(String[] args) {
        try {
            HConnection connection = HConnectionManager.createConnection(conf);
            HTableInterface table = connection.getTable("NetFlowProxyLogTest");
            String[] timeRange = config.getStringArray("TimeRange");
            L.info("size:{}", filterList(timeRange).size());
            FilterList filterList = new FilterList(filterList(timeRange));
            Scan scan = new Scan();
            scan.setFilter(filterList);

            ResultScanner resultScanner = table.getScanner(scan);
            L.info("NetFlowProxyLogQuery Start.....");
            for(Result rs : resultScanner){
                L.info("Row:"+new String(rs.getRow()));
                L.info("Timestemp:"+new String(rs.getValue(Bytes.toBytes("LibraryInfo"), Bytes.toBytes("Timestemp"))));
                L.info("ResponseTime:"+new String(rs.getValue(Bytes.toBytes("LibraryInfo"), Bytes.toBytes("ResponseTime"))));
                L.info("ClientAddress:"+new String(rs.getValue(Bytes.toBytes("LibraryInfo"), Bytes.toBytes("ClientAddress"))));
                L.info("ClientIdentity:"+new String(rs.getValue(Bytes.toBytes("LibraryInfo"), Bytes.toBytes("ClientIdentity"))));
            }
            L.info("NetFlowProxyLogQuery END.....");
        } catch (IOException e) {
            L.error("HTable fail.", e);
        }

    }

    /**
     * 過濾範圍的區間
     * @param timeRange
     * @return
     */
    private static List<Filter> filterList(String[] timeRange){
        List<Filter> scvFilter = new ArrayList<>();
        int count = 0;
        for(String time : timeRange){
            if(count == 0){
                SingleColumnValueFilter filter 
                = new SingleColumnValueFilter(Bytes.toBytes("LibraryInfo"), Bytes.toBytes("Timestemp"), 
                        CompareOp.GREATER_OR_EQUAL, Bytes.toBytes(time));
                scvFilter.add(filter);
                count++;
                continue;
            }
            SingleColumnValueFilter filter
               = new SingleColumnValueFilter(Bytes.toBytes("LibraryInfo"), Bytes.toBytes("Timestemp"), 
                  CompareOp.LESS_OR_EQUAL, Bytes.toBytes(time));
            scvFilter.add(filter);
        }
        return scvFilter;
    }
}
