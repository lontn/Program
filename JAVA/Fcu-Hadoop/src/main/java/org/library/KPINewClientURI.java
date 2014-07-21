package org.library;

import java.io.IOException;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.Reducer;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.input.TextInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;

public class KPINewClientURI {

    public static class KPINewClientURIMapper extends Mapper<Object, Text, Text, IntWritable> {
        private Text word = new Text();
        private IntWritable one = new IntWritable(1);

        public void map(Object key, Text value, Context context) throws IOException, InterruptedException {
            KPI kpi = KPI.filterDomain(value.toString());
            if(kpi.getClientAddress().indexOf("140.134") != -1){
                word.set(kpi.getClientAddress()+" --- "+kpi.getUri()+ " --- " +kpi.getClientIdentity());
                context.write(word, one);
            }
        }
    }

    public static class KPINewClientURIReducer extends Reducer<Text, IntWritable, Text, IntWritable> {
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
    public static void main(String[] args) throws Exception {
        // Step 1. Set configuration
        Configuration conf = new Configuration();
//        conf.set("fs.default.name", "hdfs://fcudm22:8020");
//        conf.set("mapred.job.tracker", "fcudm22:8021");
        conf.addResource("classpath:/hadoop/core-site.xml");
        conf.addResource("classpath:/hadoop/hdfs-site.xml");
        conf.addResource("classpath:/hadoop/mapred-site.xml");

        FileSystem hdfs = FileSystem.get(conf);
        Path inputPath = new Path(hdfs.getWorkingDirectory().toString() + "/ResourceDataTest");
        //Path inputHDFS = new Path(hdfs.getWorkingDirectory().toString() + "/KPINewClientURI/input");
        Path outputPath = new Path(hdfs.getWorkingDirectory().toString() + "/KPINewClientURI/output");

        //Step 2. Create a Job
        Job job = new Job(conf, "KPINewClientURI");
        job.setJarByClass(KPINewClientURI.class);

        //Step 3. Set Input format
        job.setInputFormatClass(TextInputFormat.class);

        //Step 4. Set Mappered
        job.setMapperClass(KPINewClientURIMapper.class);
        //Step 4.1 Set Combiner (Local Reducer)
        job.setCombinerClass(KPINewClientURIReducer.class);
        //Step 5. Set Reducer
        job.setReducerClass(KPINewClientURIReducer.class);

        //Step 6. Set Input
        FileInputFormat.addInputPath(job, inputPath);

        //Step 7. Set Output
        job.setOutputKeyClass(Text.class);
        job.setOutputValueClass(IntWritable.class);
        
        //判斷目錄是否存在
        if (hdfs.exists(outputPath)){
            System.out.println("DDDDD");
            hdfs.delete(outputPath, true);
        }
        
        FileOutputFormat.setOutputPath(job, outputPath);
        //Step 8. Submit Job
        job.submit();

        if (job.waitForCompletion(true)) {
            System.out.println("Job Done!");
            System.exit(0);
        } else {
            System.out.println("Job Failed!");
            System.exit(1);
        }
    }

}
