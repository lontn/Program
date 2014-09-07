package org.proxylog;

import java.io.IOException;

import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.PropertiesConfiguration;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.hbase.client.Result;
import org.apache.hadoop.hbase.io.ImmutableBytesWritable;
import org.apache.hadoop.hbase.mapreduce.TableMapper;
import org.apache.hadoop.hbase.util.Bytes;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;
import org.domain.ProxyLog;
import org.hadoop.ProxyLogStatistics;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 該域包含2個token，以斜槓分隔。第一個token叫結果碼，它把協議和事務結果（例如TCP_HIT或UDP_DENIED）進行歸類。
 * 以TCP_開頭的編碼指HTTP請求，以UDP_開頭的編碼指ICP查詢。
 * @author freelon
 *
 */
public class CacheCodeStatistics {
    private static final Logger L = LoggerFactory.getLogger(ProxyLogStatistics.class);
    private static PropertiesConfiguration config = null;
    private static Configuration conf = null;
    private static final String COLUMN_FAMILY = "LibraryInfo";
    private static final String QUALIFIER_STATUSCODE = "StatusCode";
    private static final String RANGE = "TimeRange.properties";
    private static final String QUALIFIER_TIMESTEMP = "Timestemp";
    private static String[] timeRange = {};
    static {
        conf = HBaseConfiguration.create();
        try {
            config = new PropertiesConfiguration(RANGE);
            timeRange = config.getStringArray("TimeRange");
        } catch (ConfigurationException e) {
            L.error("ConfigurationException:"+ e);
        }
    }
    
    public static void main(String[] args) {
        // TODO Auto-generated method stub

    }

    public static class CacheCodeStatisticsMapper extends TableMapper<Text, IntWritable>{
        private IntWritable value = new IntWritable(1);
        private Text word = new Text();
        String statusCode = "";
        String timeStemp = "";
        @Override
        public void map(ImmutableBytesWritable row, Result columns, Context context) throws IOException {
            timeStemp = new String(columns.getValue(Bytes.toBytes(COLUMN_FAMILY), Bytes.toBytes(QUALIFIER_TIMESTEMP)));
            statusCode = new String(columns.getValue(Bytes.toBytes(COLUMN_FAMILY), Bytes.toBytes(QUALIFIER_STATUSCODE)));
            String[] code = statusCode.split("/");
            String[] time = timeStemp.split(" ");
            word.set(time[0]+ "---" + code[0]);
            try {
                context.write(word, value);
            } catch (InterruptedException e) {
                L.error("ProxyLogStatisticsMapper fail.", e);
                L.error("Row:{}", row.get());
                L.info("statusCode:{}", statusCode);
                context.getCounter(ProxyLog.Counters.ERROR).increment(1);
            }
        }
    }
    
    public static class CacheCodeStatisticsReducer extends Reducer<Text, IntWritable, Text, IntWritable> {
        private IntWritable result = new IntWritable();
        public void reduce(Text key, Iterable<IntWritable> values, Context context) throws IOException, InterruptedException {
            int sum = 0;
            for (IntWritable val : values) {
                sum += val.get();
            }
            result.set(sum);
            context.write(key, result);
        }
    }
}
