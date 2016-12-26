package com.fcu.gtml.hadoop;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.PropertiesConfiguration;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.hbase.client.Result;
import org.apache.hadoop.hbase.client.Scan;
import org.apache.hadoop.hbase.filter.Filter;
import org.apache.hadoop.hbase.filter.FilterList;
import org.apache.hadoop.hbase.filter.SingleColumnValueFilter;
import org.apache.hadoop.hbase.filter.CompareFilter.CompareOp;
import org.apache.hadoop.hbase.io.ImmutableBytesWritable;
import org.apache.hadoop.hbase.mapreduce.TableMapReduceUtil;
import org.apache.hadoop.hbase.mapreduce.TableMapper;
import org.apache.hadoop.hbase.util.Bytes;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.Reducer;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
//import org.apache.logging.log4j.LogManager;
//import org.apache.logging.log4j.Logger;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fcu.gtml.utils.HBaseQuaryTools;

public class TrackingLogStatistics {
    //private static final Logger L = LogManager.getLogger();
    private static final Logger L = LoggerFactory.getLogger(TrackingLogStatistics.class);
    private static PropertiesConfiguration config = null;
    private static Configuration conf = null;
    private static final String COLUMN_FAMILY = "Info";
    private static final String QUALIFIER_EVENT_TYPE = "event_type";
    private static final String QUALIFIER_TIME = "time";
    private static final String RANGE = "com/fcu/gtml/hadoop/HadoopPath.properties";
    private static String eventType = "";
    private static String dfsPath = "";
    private static String tableName = "";
    private static String[] timeRange = {};

    static {
        conf = HBaseConfiguration.create();
        try {
            config = new PropertiesConfiguration(RANGE);
            eventType = config.getString("EventType");
            dfsPath = config.getString("DFSPath");
            timeRange = config.getStringArray("TimeRange");
            tableName = config.getString("TableName");
        } catch (ConfigurationException e) {
            L.error("ConfigurationException:"+ e);
        }
    }


    public static class TrackingLogStatisticsMapper extends TableMapper<Text, IntWritable>{
        private IntWritable value = new IntWritable(1);
        private Text word = new Text();

        @Override
        public void map(ImmutableBytesWritable key, Result columns, Context context) throws IOException, InterruptedException {
            try {
                String eventType = new String(columns.getValue(Bytes.toBytes(COLUMN_FAMILY), Bytes.toBytes(QUALIFIER_EVENT_TYPE)));
                String time = new String(columns.getValue(Bytes.toBytes(COLUMN_FAMILY), Bytes.toBytes(QUALIFIER_TIME)));
                DateTime dateTime = DateTime.parse(time, DateTimeFormat.forPattern("yyyy-MM-dd"));
                String nowTime = dateTime.toString("yyyy-MM-dd");
                System.out.println("nowTime:" + nowTime);
                word.set(nowTime + "--" + eventType);
                context.write(word, value);
            } catch (Exception e) {
                L.error("TrackingLogStatisticsMapper fail.", e);
            }
        }
    }
    
    public static class TrackingLogStatisticsReducer extends Reducer<Text, IntWritable, Text, IntWritable> {
        private IntWritable result = new IntWritable();
        @Override
        public void reduce(Text key, Iterable<IntWritable> values, Context context) throws IOException, InterruptedException {
            int sum = 0;
            for (IntWritable val : values) {
                sum += val.get();
            }
            result.set(sum);
            context.write(key, result);
        }
    }

    public static void main(String[] args) {
        try {
            Scan scan = new Scan();
            scan.addColumn(Bytes.toBytes(COLUMN_FAMILY), Bytes.toBytes(QUALIFIER_EVENT_TYPE));
            scan.addColumn(Bytes.toBytes(COLUMN_FAMILY), Bytes.toBytes(QUALIFIER_TIME));
            //選取event_type
            SingleColumnValueFilter filter 
            = new SingleColumnValueFilter(Bytes.toBytes(COLUMN_FAMILY), Bytes.toBytes(QUALIFIER_EVENT_TYPE), 
                    CompareOp.EQUAL, Bytes.toBytes(eventType));
            //FilterList filterList = new FilterList(filter);
            List<Filter> scvFilter = new ArrayList<>();
            scvFilter.add(filter);
            scvFilter.addAll(HBaseQuaryTools.columnFilterList(COLUMN_FAMILY, QUALIFIER_TIME, timeRange));
            //FilterList filterList = new FilterList(HBaseQuaryTools.columnFilterList(COLUMN_FAMILY, QUALIFIER_TIME, timeRange));
            FilterList filterList = new FilterList(scvFilter);
            scan.setFilter(filterList);
            
            FileSystem hdfs = FileSystem.get(conf);
            //Path inputPath = new Path(hdfs.getWorkingDirectory().toString() + "/input");
            Path outputPath = new Path(dfsPath + "/output"); //當下的運行的目錄
            Job job = Job.getInstance(conf, "TrackingLog");
            job.setJarByClass(TrackingLogStatistics.class);
            TableMapReduceUtil.initTableMapperJob(tableName, scan, TrackingLogStatisticsMapper.class, Text.class, IntWritable.class, job);
            job.setReducerClass(TrackingLogStatisticsReducer.class);

            job.setOutputKeyClass(Text.class);
            job.setOutputValueClass(IntWritable.class);
            
            if (hdfs.exists(outputPath))
                hdfs.delete(outputPath, true);
            
            FileOutputFormat.setOutputPath(job, outputPath);
            if (job.waitForCompletion(true)) {
                System.out.println("Job Done!");
                System.out.println("tableName:" + tableName);
                System.out.println("eventType:" + eventType);
                System.out.println("dfsPath:" + dfsPath);
                //L.info("timeRange:{}", timeRange);
                System.exit(0);
            } else {
                System.out.println("Job Failed!");
                System.exit(1);
            }
        } catch (Exception e) {
            L.error("TrackingLog Query fail.", e);
        }
    }
}
