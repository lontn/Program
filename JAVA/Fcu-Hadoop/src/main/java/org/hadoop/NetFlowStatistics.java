package org.hadoop;

import java.io.IOException;
import java.util.Properties;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.hbase.Cell;
import org.apache.hadoop.hbase.CellUtil;
import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.hbase.KeyValue;
import org.apache.hadoop.hbase.client.HTable;
import org.apache.hadoop.hbase.client.Result;
import org.apache.hadoop.hbase.client.ResultScanner;
import org.apache.hadoop.hbase.client.Scan;
import org.apache.hadoop.hbase.io.ImmutableBytesWritable;
import org.apache.hadoop.hbase.mapreduce.TableMapReduceUtil;
import org.apache.hadoop.hbase.mapreduce.TableMapper;
import org.apache.hadoop.hbase.util.Bytes;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.Mapper.Context;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
import org.apache.hadoop.mapreduce.Reducer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class NetFlowStatistics {
    private static final Logger L = LoggerFactory.getLogger(NetFlowStatistics.class);
    private static Properties prop = new Properties();
    private static Configuration conf = null;
    public static enum Counters { ROWS, COLS, ERROR, VALID }
    static {
        conf = HBaseConfiguration.create();
    }
    
    public static class NetFlowAnalyzeMapper extends TableMapper<Text, IntWritable>{
        private IntWritable value = new IntWritable();
        private Text word = new Text();
        @Override
        public void map(ImmutableBytesWritable row, Result columns, Context context) throws IOException {
            context.getCounter(Counters.ROWS).increment(1); //Row
            String qualifier = null;
            String sIpVale = null;
            Integer packageLength = null;
            try{
                for (Cell rowKV : columns.rawCells()) {
                    context.getCounter(Counters.COLS).increment(1); //Column
                    qualifier = new String(CellUtil.cloneQualifier(rowKV));
                    if(qualifier.equals("SIp")){
                        sIpVale = new String(CellUtil.cloneValue(rowKV));
                        word.set(sIpVale);
                    }
                    if(qualifier.equals("Length")){
                        packageLength = Integer.parseInt(new String(CellUtil.cloneValue(rowKV)));
                        value.set(packageLength);
                    }
                }
                // vv AnalyzeData
                context.write(word, value);
                context.getCounter(Counters.VALID).increment(1);
            }catch(Exception e){
                L.error("NetFlowAnalyzeMapper fail.", e);
                L.error("Row:{}, Value:{} ", row.get(), packageLength);
                context.getCounter(Counters.ERROR).increment(1);
            }
        }
    }

    public static class NetFlowAnalyzeReducer extends Reducer<Text, IntWritable, Text, IntWritable> {
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

    public static void main(String[] args) {
        try {
            Scan scan = new Scan();
            scan.addColumn(Bytes.toBytes("Info"), Bytes.toBytes("SIp"));
            scan.addColumn(Bytes.toBytes("Info"), Bytes.toBytes("Length"));

            FileSystem hdfs = FileSystem.get(conf);
            //Path inputPath = new Path(hdfs.getWorkingDirectory().toString() + "/input");
            Path outputPath = new Path(hdfs.getWorkingDirectory().toString() + "/output"); //當下的運行的目錄

            Job job = new Job(conf, "NetFlowStatistics - NetFlowHistoryLog");
            job.setJarByClass(NetFlowStatistics.class);
            TableMapReduceUtil.initTableMapperJob("NetFlowHistoryLog", scan, NetFlowAnalyzeMapper.class,
                    Text.class, IntWritable.class, job); 
            job.setReducerClass(NetFlowAnalyzeReducer.class);
            
            //FileInputFormat.addInputPath(job, inputPath);
            
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
            L.error("HTable Query fail.", e);
        }
    }
}
