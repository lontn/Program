package com.fcu.gtml.hadoop;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.Random;
import java.util.StringTokenizer;

import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.PropertiesConfiguration;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FSDataOutputStream;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.Reducer;
import org.apache.hadoop.mapreduce.Mapper.Context;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.input.FileSplit;
import org.apache.hadoop.mapreduce.lib.input.TextInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;

public class PresidentElection {
    private static Configuration conf = null;
    private static final String RANGE = "com/fcu/gtml/hadoop/HadoopPath.properties";
    private static PropertiesConfiguration config = null;
    private static String dfsPath = "";

    static {
        conf = new Configuration();
        try {
            config = new PropertiesConfiguration(RANGE);
            dfsPath = config.getString("DFSPath");
        } catch (ConfigurationException e) {
            System.out.println("ConfigurationException:" + e);
        }
    }
    public static class TokenizerMapper extends Mapper<Object, Text, Text, IntWritable> {
        private final static IntWritable one = new IntWritable(1);
        private Text word = new Text();
        protected void setup(Context context) {
            System.out.println("Mapper Setup: " + ((FileSplit) context.getInputSplit()).getPath().getName());
        }

        protected void cleanup(Context context) {
            System.out.println("Mapper Cleanup");
        }

        public void map(Object key, Text value, Context context) throws IOException, InterruptedException {
            StringTokenizer itr = new StringTokenizer(value.toString());
            while (itr.hasMoreTokens()) {
                word.set(itr.nextToken());
                context.write(word, one);
            }
        }
    }

    public static class IntSumReducer extends Reducer<Text, IntWritable, Text, IntWritable> {
        private IntWritable result = new IntWritable();

        protected void setup(Context context) {
            System.out.println("Reducer Setup");
        }

        protected void cleanup(Context context) {
            System.out.println("Reducer Cleanup");
        }

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
        String[] candidate = { "Kevin", "Mike", "Philip" };
        String[] location = { "Taichung", "Taipei", "Kaohsiung", "Tainan" };
        Random r;
//        boolean useJobTracker = true;

        // Step 0. Set configuration
//        Configuration conf = new Configuration();
//        conf.set("fs.default.name", "hdfs://fcudm22:8020");
//        if (useJobTracker)
//            conf.set("mapred.job.tracker", "fcudm22:8021");
//        else
//            conf.set("mapred.job.tracker", "local");
        FileSystem hdfs = FileSystem.get(conf);
        System.out.println("Working Directory -> " + hdfs.getWorkingDirectory().toString());
        Path inputPath = new Path(dfsPath + "/input");
        Path outputPath = new Path(dfsPath + "/output"); //當下的運行的目錄

        // Step 1. prepare data
        for (String city : location) {
            System.out.println("Creating Data: " + city);
            int people = 1000000;
            Path dataPath = new Path(dfsPath + "/input/" + city + ".txt");
            FSDataOutputStream oStream = hdfs.create(dataPath);
            BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(oStream));
            r = new Random(System.currentTimeMillis());
            while (people > 0) {
                bw.write(candidate[r.nextInt(candidate.length)]);
                bw.newLine();
                people--;
            }
            bw.flush();
            bw.close();
        }

        // Step 2. Create a Job
        Job job = Job.getInstance(conf, "President Election");
        job.setJarByClass(PresidentElection.class);

        // Step 3. Set Input format
        job.setInputFormatClass(TextInputFormat.class);

        // Step 4. Set Mapper
        job.setMapperClass(TokenizerMapper.class);

        // Step 4.1 Set Combiner (Local Reducer)
        job.setCombinerClass(IntSumReducer.class);

        // Step 5. Set Reducer
        job.setReducerClass(IntSumReducer.class);

        // Step 6. Set Input
        FileInputFormat.addInputPath(job, inputPath);

        // Step 7. Set Output
        job.setOutputKeyClass(Text.class);
        job.setOutputValueClass(IntWritable.class);

        if (hdfs.exists(outputPath))
            hdfs.delete(outputPath, true);
        FileOutputFormat.setOutputPath(job, outputPath);

        // Step 8. Submit Job
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
