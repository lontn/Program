package org.common.utils;

import java.util.ArrayList;
import java.util.List;

import org.apache.hadoop.hbase.filter.Filter;
import org.apache.hadoop.hbase.filter.SingleColumnValueFilter;
import org.apache.hadoop.hbase.filter.CompareFilter.CompareOp;
import org.apache.hadoop.hbase.util.Bytes;

public class HBaseQuaryTools {

    public static void main(String[] args) {
        // TODO Auto-generated method stub

    }

    /**
     * 過濾範圍的區間
     * @param columnFamily
     * @param qualifier
     * @param timeRange
     * @return
     */
    public static List<Filter> columnFilterList(String columnFamily, String qualifier, String[] timeRange){
        List<Filter> scvFilter = new ArrayList<>();
        int count = 0;
        for(String time : timeRange){
            if(count == 0){
                SingleColumnValueFilter filter 
                = new SingleColumnValueFilter(Bytes.toBytes(columnFamily), Bytes.toBytes(qualifier), 
                        CompareOp.GREATER_OR_EQUAL, Bytes.toBytes(time));
                scvFilter.add(filter);
                count++;
                continue;
            }
            SingleColumnValueFilter filter
               = new SingleColumnValueFilter(Bytes.toBytes(columnFamily), Bytes.toBytes(qualifier), 
                  CompareOp.LESS_OR_EQUAL, Bytes.toBytes(time));
            scvFilter.add(filter);
        }
        return scvFilter;
    }
}
