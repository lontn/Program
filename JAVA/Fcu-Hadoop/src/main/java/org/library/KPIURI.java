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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class KPIURI {
    
    private static final Logger L = LoggerFactory.getLogger(KPIURI.class);

    public static class KPIURIMapper extends MapReduceBase implements Mapper<Object, Text, Text, IntWritable> {
        private Text word = new Text();
        private IntWritable one = new IntWritable(1);

        public void map(Object key, Text value, OutputCollector<Text, IntWritable> output, Reporter reporter) throws IOException {
            KPI kpi = KPI.filterDomain(value.toString());
            if (kpi.isValid()) {
                word.set(kpi.getUri());
                output.collect(word, one);
            }
        }
    }

    public static class KPIURIReducer extends MapReduceBase implements Reducer<Text, IntWritable, Text, IntWritable> {
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
    public static void main(String[] args) throws IOException {
        JobConf conf = new JobConf(KPIURI.class);
        conf.setJobName("KPIURI");
        conf.addResource("classpath:/hadoop/core-site.xml");
        conf.addResource("classpath:/hadoop/hdfs-site.xml");
        conf.addResource("classpath:/hadoop/mapred-site.xml");
        
        conf.setMapOutputKeyClass(Text.class);
        conf.setMapOutputValueClass(IntWritable.class);
        
        conf.setOutputKeyClass(Text.class);
        conf.setOutputValueClass(IntWritable.class);
        
        conf.setMapperClass(KPIURIMapper.class);
        conf.setCombinerClass(KPIURIReducer.class);
        conf.setReducerClass(KPIURIReducer.class);

        conf.setInputFormat(TextInputFormat.class);
        conf.setOutputFormat(TextOutputFormat.class);

        FileSystem hdfs = FileSystem.get(conf);
        Path inputPath = new Path(hdfs.getWorkingDirectory().toString() + "/ResourceData");
        Path outputPath = new Path(hdfs.getWorkingDirectory().toString() + "/KPIURI/output");

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
