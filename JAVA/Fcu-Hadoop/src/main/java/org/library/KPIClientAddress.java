package org.library;

import java.io.IOException;
import java.util.Iterator;

import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapred.FileInputFormat;
import org.apache.hadoop.mapred.FileOutputFormat;
import org.apache.hadoop.mapred.JobClient;
import org.apache.hadoop.mapred.JobConf;
import org.apache.hadoop.mapred.MapReduceBase;
import org.apache.hadoop.mapred.Mapper;
import org.apache.hadoop.mapred.OutputCollector;
import org.apache.hadoop.mapred.Reducer;
import org.apache.hadoop.mapred.Reporter;
import org.apache.hadoop.mapred.TextInputFormat;
import org.apache.hadoop.mapred.TextOutputFormat;

public class KPIClientAddress {

    public static class KPIClientAddressMapper extends MapReduceBase implements Mapper<Object, Text, Text, IntWritable> {
        private Text word = new Text();
        private IntWritable one = new IntWritable(1);

        public void map(Object key, Text value, OutputCollector<Text, IntWritable> output, Reporter reporter) throws IOException {
            KPI kpi = KPI.filterIPs(value.toString());
            if (kpi.isValid()) {
                word.set(kpi.getClientAddress());
                output.collect(word, one);
            }
        }
    }

    public static class KPIClientAddressReducer extends MapReduceBase implements Reducer<Text, IntWritable, Text, IntWritable> {
        private IntWritable result = new IntWritable();
        
        public void reduce(Text key, Iterator<IntWritable> values, OutputCollector<Text, IntWritable> output, Reporter reporter) throws IOException {
            int sum = 0;
            while (values.hasNext()) {
                sum += values.next().get();
            }
            result.set(sum);
            output.collect(key, result);
        }
    }

    public static void main(String[] args) throws Exception {
        //String input = "hdfs://fcudm22:8020/user/hdfs/log_kpi/";
        //String output = "hdfs://fcudm22:8020/user/hdfs/log_kpi/KPIClientAddress";

        JobConf conf = new JobConf(KPIClientAddress.class);
        conf.setJobName("KPIClientAddress");
        conf.addResource("classpath:/hadoop/core-site.xml");
        conf.addResource("classpath:/hadoop/hdfs-site.xml");
        conf.addResource("classpath:/hadoop/mapred-site.xml");
        
        conf.setMapOutputKeyClass(Text.class);
        conf.setMapOutputValueClass(IntWritable.class);
        
        conf.setOutputKeyClass(Text.class);
        conf.setOutputValueClass(IntWritable.class);
        
        conf.setMapperClass(KPIClientAddressMapper.class);
        conf.setCombinerClass(KPIClientAddressReducer.class);
        conf.setReducerClass(KPIClientAddressReducer.class);
        
        conf.setInputFormat(TextInputFormat.class);
        conf.setOutputFormat(TextOutputFormat.class);

        FileSystem hdfs = FileSystem.get(conf);
        Path inputPath = new Path(hdfs.getWorkingDirectory().toString() + "/KPIClientAddress/input");
        Path outputPath = new Path(hdfs.getWorkingDirectory().toString() + "/KPIClientAddress/output");
        System.out.println("AAA:"+hdfs.getHomeDirectory() + "/KPIClientAddress");

        //判斷目錄是否存在
        if (hdfs.exists(outputPath)){
            System.out.println("DDDDD");
            hdfs.delete(outputPath, true);
        }

        FileInputFormat.setInputPaths(conf, inputPath);
        FileOutputFormat.setOutputPath(conf, outputPath);

        JobClient.runJob(conf);
        System.exit(0);
    }

}
