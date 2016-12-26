package com.fcu.gtml.hadoop;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Properties;
import java.util.StringTokenizer;

import org.apache.commons.io.FileUtils;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.hbase.client.Put;
import org.apache.hadoop.hbase.io.ImmutableBytesWritable;
import org.apache.hadoop.hbase.mapreduce.TableOutputFormat;
import org.apache.hadoop.hbase.util.Bytes;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.Reducer;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.input.TextInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.joda.time.DateTime;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;

import com.fcu.gtml.process.edx.domain.TrackingLog;
import com.fcu.gtml.utils.JsonParser;


public class TrackingLogImport {
    //private static final Logger L = LoggerFactory.getLogger(TrackingLogImport.class);
    private static final Logger L = LogManager.getLogger();
    private static Properties prop = new Properties();
    private static Configuration conf = null;
    private static String tableName = null;
    private static String filePath = null;
    private static String dfsPath = null;
    static {
        conf = HBaseConfiguration.create();
        try {
            prop.load(TrackingLogImport.class.getResourceAsStream("HadoopPath.properties"));
            tableName = prop.getProperty("TableName").toString();
            filePath = prop.getProperty("FilePath").toString();
            dfsPath = prop.getProperty("DFSPath").toString();
        } catch (IOException e) {
            // log exception
            L.error("read *.properties fail.", e);
            throw new RuntimeException("read properties Fail.", e);
        }
    }

    public static class ImportTrackingMapper extends Mapper<LongWritable, Text, ImmutableBytesWritable, Put> {
        private final static IntWritable one = new IntWritable(1);
        private Text word = new Text();
        private Collection<File> files = null;

        @Override
        protected void setup(Context context) throws IOException, InterruptedException {
            System.out.println("filePath:"+filePath);
            //files = FileUtils.listFiles(FileUtils.getFile(filePath), null, true);
        }

        @Override
        public void map(LongWritable key, Text value, Context context) throws IOException, InterruptedException {
            String strRow = value.toString();
            L.info("strRow:" + strRow);
            System.out.println("strRow:" + strRow);
            TrackingLog tLog = LoadFile(strRow);
            byte[] rowkey = Bytes.toBytes(tLog.getEventType() + tLog.getTime().getTime());
            context.write(new ImmutableBytesWritable(rowkey), putHBaseData(rowkey, tLog));
        }
    }

    public static class ImportTrackingReducer extends Reducer<Text,IntWritable,Text,IntWritable> {
        @Override
        public void reduce(Text key, Iterable<IntWritable> values, Context context) throws IOException, InterruptedException {
            // TODO Auto-generated method stub
            
        }
    }

    private static Put putHBaseData(byte[] rowkey, TrackingLog trackingLog) {
        Put put = new Put(rowkey); //Set Row Key
        put.addColumn(Bytes.toBytes("Info"), Bytes.toBytes("username"), Bytes.toBytes(trackingLog.getUserName()));
        put.addColumn(Bytes.toBytes("Info"), Bytes.toBytes("event_type"), Bytes.toBytes(trackingLog.getEventType()));
        put.addColumn(Bytes.toBytes("Info"), Bytes.toBytes("ip"), Bytes.toBytes(trackingLog.getIp()));
        put.addColumn(Bytes.toBytes("Info"), Bytes.toBytes("agent"), Bytes.toBytes(trackingLog.getAgent()));
        put.addColumn(Bytes.toBytes("Info"), Bytes.toBytes("host"), Bytes.toBytes(trackingLog.getHost()));
        put.addColumn(Bytes.toBytes("Info"), Bytes.toBytes("session"), Bytes.toBytes(trackingLog.getSession() != null ? trackingLog.getSession() : ""));
        put.addColumn(Bytes.toBytes("Info"), Bytes.toBytes("event"), Bytes.toBytes(trackingLog.getEvent()));
        put.addColumn(Bytes.toBytes("Info"), Bytes.toBytes("event_source"), Bytes.toBytes(trackingLog.getEventSource()));
        put.addColumn(Bytes.toBytes("Info"), Bytes.toBytes("context"), Bytes.toBytes(trackingLog.getContext()));
        put.addColumn(Bytes.toBytes("Info"), Bytes.toBytes("time"), Bytes.toBytes(new DateTime(trackingLog.getTime()).toString("yyyy-MM-dd HH:mm:ss.SSS")));
        put.addColumn(Bytes.toBytes("Info"), Bytes.toBytes("page"), Bytes.toBytes(trackingLog.getPage() != null ? trackingLog.getPage() : ""));
        return put;
    }

    private static TrackingLog LoadFile(String value) {
        TrackingLog tLog = null;
        try {
            tLog = JsonParser.parse(value, TrackingLog.class);
        } catch (Exception e) {
            L.error("Error Data:{}", value);
            L.error("TrackingLog Data fail.", e);
        }
        return tLog;
    }

    public static void main(String[] args) throws Exception {
        FileSystem hdfs = FileSystem.get(conf);
        Path inputPath = new Path(dfsPath + "/input");
        Path outputPath = new Path(dfsPath + "/output");

        // Step 2. Create a Job
        Job job = Job.getInstance(conf, "Tracking Log Import");
        job.setJarByClass(TrackingLogImport.class);

        // Step 3. Set Input format
        job.setInputFormatClass(TextInputFormat.class);

        // Step 4. Set Mapper
        job.setMapperClass(ImportTrackingMapper.class);

        // Step 4.1 Set Combiner (Local Reducer)
        //job.setCombinerClass(ImportTrackingReducer.class);

        // Step 5. Set Reducer
        //job.setReducerClass(ImportTrackingReducer.class);
        
        // Step 6. Set Input
        FileInputFormat.addInputPath(job, inputPath);

        // Step 7.1. Set Output format
        job.getConfiguration().set(TableOutputFormat.OUTPUT_TABLE, tableName);
        job.setOutputFormatClass(TableOutputFormat.class);

        // Step 7. Set Output
        job.setOutputKeyClass(ImmutableBytesWritable.class);
        job.setOutputValueClass(Put.class);

        job.setNumReduceTasks(0);

        if (hdfs.exists(outputPath))
            hdfs.delete(outputPath, true);
        FileOutputFormat.setOutputPath(job, outputPath);

        if (job.waitForCompletion(true)) {
            System.out.println("Job Done!!!");
            System.exit(0);
        } else {
            System.out.println("Job Failed!!!!!!!!!!!!!!!!");
            System.exit(1);
        }
    }
}
