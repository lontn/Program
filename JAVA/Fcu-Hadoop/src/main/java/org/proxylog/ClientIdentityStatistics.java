package org.proxylog;

import java.io.IOException;

import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.PropertiesConfiguration;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.hbase.client.Result;
import org.apache.hadoop.hbase.client.Scan;
import org.apache.hadoop.hbase.filter.FilterList;
import org.apache.hadoop.hbase.io.ImmutableBytesWritable;
import org.apache.hadoop.hbase.mapreduce.TableMapReduceUtil;
import org.apache.hadoop.hbase.mapreduce.TableMapper;
import org.apache.hadoop.hbase.util.Bytes;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.Reducer;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
import org.common.utils.HBaseQuaryTools;
import org.domain.ProxyLog;
import org.hadoop.ProxyLogStatistics;
import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ClientIdentityStatistics {
    private static final Logger L = LoggerFactory.getLogger(ClientIdentityStatistics.class);
    private static PropertiesConfiguration config = null;
    private static Configuration conf = null;
    private static final String COLUMN_FAMILY = "LibraryInfo";
    private static final String QUALIFIER_CLIENTIDENTITY = "ClientIdentity";
    private static final String QUALIFIER_URI = "URI";
    private static final String QUALIFIER_TIMESTEMP = "Timestemp";
    private static final String QUALIFIER_RESPONSETIME = "ResponseTime";
    private static final String RANGE = "TimeRange.properties";
    private static String[] timeRange = {};
    public static enum Counters { ROWS, COLS, ERROR, VALID }
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
        Scan scan = new Scan();
        scan.addColumn(Bytes.toBytes(COLUMN_FAMILY), Bytes.toBytes(QUALIFIER_TIMESTEMP));
        scan.addColumn(Bytes.toBytes(COLUMN_FAMILY), Bytes.toBytes(QUALIFIER_RESPONSETIME));
        scan.addColumn(Bytes.toBytes(COLUMN_FAMILY), Bytes.toBytes(QUALIFIER_URI));
        scan.addColumn(Bytes.toBytes(COLUMN_FAMILY), Bytes.toBytes(QUALIFIER_CLIENTIDENTITY));
        //選取時間區間
        FilterList filterList = new FilterList(HBaseQuaryTools.columnFilterList(COLUMN_FAMILY, QUALIFIER_TIMESTEMP, timeRange));
        scan.setFilter(filterList);
        try {
            FileSystem hdfs = FileSystem.get(conf);
            Path outputPath = new Path(hdfs.getWorkingDirectory().toString() + "/output/ClientIdentityStatistics-" + new DateTime().toString("yyyy-MM-dd HH:mm:ss.sss")); //當下的運行的目錄
            Job job = new Job(conf, "ClientIdentityStatistics");
            job.setJarByClass(ProxyLogStatistics.class);
            TableMapReduceUtil.initTableMapperJob("NetFlowProxyLogTest", scan, ClientIdentityStatisticsMapper.class, Text.class, IntWritable.class, job);
            job.setReducerClass(ClientIdentityStatisticsReducer.class);

            job.setOutputKeyClass(Text.class);
            job.setOutputValueClass(IntWritable.class);
            if (hdfs.exists(outputPath))
                hdfs.delete(outputPath, true);
            
            FileOutputFormat.setOutputPath(job, outputPath);
            if (job.waitForCompletion(true)) {
                System.out.println("Job Done!");
                System.exit(0);
            } else {
                System.out.println("Job Failed!");
                System.exit(1);
            }
        } catch (Exception e) {

        }
    }
    
    public static class ClientIdentityStatisticsMapper extends TableMapper<Text, IntWritable>{
        private IntWritable value = new IntWritable(1);
        private Text word = new Text();
        String timeStemp = "";
        String clientIdentity = "";
        String uri = "";
        String responseTime = "";
        @Override
        protected void map(ImmutableBytesWritable row, Result columns, Context context) throws IOException {
            timeStemp = new String(columns.getValue(Bytes.toBytes(COLUMN_FAMILY), Bytes.toBytes(QUALIFIER_TIMESTEMP)));
            clientIdentity = new String(columns.getValue(Bytes.toBytes(COLUMN_FAMILY), Bytes.toBytes(QUALIFIER_CLIENTIDENTITY)));
            uri = new String(columns.getValue(Bytes.toBytes(COLUMN_FAMILY), Bytes.toBytes(QUALIFIER_URI)));
            responseTime = new String(columns.getValue(Bytes.toBytes(COLUMN_FAMILY), Bytes.toBytes(QUALIFIER_RESPONSETIME)));
            word.set(timeStemp+ "-@-" + responseTime + "-@-" + uri + "-@-" + clientIdentity);
            try {
                context.write(word, value);
            } catch (InterruptedException e) {
                L.error("ClientIdentityStatisticsMapper fail.", e);
                L.error("Row:{}", row.get());
                L.info("clientIdentity:{}", clientIdentity);
                context.getCounter(ProxyLog.Counters.ERROR).increment(1);
            }
        }
    }

    public static class ClientIdentityStatisticsReducer extends Reducer<Text, IntWritable, Text, IntWritable> {
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
