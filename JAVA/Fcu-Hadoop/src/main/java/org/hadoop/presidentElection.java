package org.hadoop;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.Random;
import java.util.StringTokenizer;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.*;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.lib.input.*;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.Reducer;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;

public class presidentElection {

  public static class TokenizerMapper 
       extends Mapper<Object, Text, Text, IntWritable>{
    
    private final static IntWritable one = new IntWritable(1); 
    private Text word = new Text(); 
    protected void setup(Context context) 
    {
    	System.out.println("Mapper Setup: " + ((FileSplit)context.getInputSplit()).getPath().getName());
    }
    protected void cleanup(Context context) 
    {
    	System.out.println("Mapper Cleanup");
    }
    public void map(Object key, Text value, Context context
                    ) throws IOException, InterruptedException {
      StringTokenizer itr = new StringTokenizer(value.toString());
      while (itr.hasMoreTokens()) { 
        word.set(itr.nextToken());
        context.write(word, one);
      }
    }
  }
  
  public static class IntSumReducer 
       extends Reducer<Text,IntWritable,Text,IntWritable> {
    private IntWritable result = new IntWritable();
    protected void setup(Context context)
    {
    	System.out.println("Reducer Setup");
    }
    protected void cleanup(Context context)
    {
    	System.out.println("Reducer Cleanup");    	
    }
    public void reduce(Text key, Iterable<IntWritable> values, 
                       Context context
                       ) throws IOException, InterruptedException {
      int sum = 0;
      for (IntWritable val : values) {
        sum += val.get();
      }
      result.set(sum);
      context.write(key, result);
    }
  }

	public static void main(String[] args) throws Exception {
		String[] candidate = {"Kevin","Mike","Philip"};
		String[] location = {"Taichung", "Taipei", "Kaohsiung","Tainan"};
		Random r;
		boolean useJobTracker = true;
		
		//Step 0. Set configuration
		Configuration conf = new Configuration();
		conf.set("fs.default.name", "hdfs://fcudm22:8020");
		if(useJobTracker)
			conf.set("mapred.job.tracker", "fcudm22:8021");
		else
			conf.set("mapred.job.tracker", "local");

		FileSystem hdfs = FileSystem.get(conf);
		System.out.println("Working Directory -> " + hdfs.getWorkingDirectory().toString());
		Path inputPath = new Path(hdfs.getWorkingDirectory().toString() + "/input");
		Path outputPath = new Path(hdfs.getWorkingDirectory().toString() + "/output");
		
		//Step 1. prepare data		
		for(String city : location)
		{
			System.out.println("Creating Data: " + city);
			int people = 1000000;
			Path dataPath = new Path(hdfs.getWorkingDirectory().toString() + "/input/" + city + ".txt");
			FSDataOutputStream oStream = hdfs.create(dataPath);
			BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(oStream));
			r = new Random(System.currentTimeMillis());
			while(people>0)
			{				
				bw.write(candidate[r.nextInt(candidate.length)]);
				bw.newLine();
				people--;
			}
			bw.flush();
			bw.close();
		}

		//Step 2. Create a Job
		Job job = new Job(conf,"President Election");
		job.setJarByClass(presidentElection.class);
		
		//Step 3. Set Input format
		job.setInputFormatClass(TextInputFormat.class);
		
		//Step 4. Set Mapper
		job.setMapperClass(TokenizerMapper.class);
		
		//Step 4.1 Set Combiner (Local Reducer)
		job.setCombinerClass(IntSumReducer.class);
		
		//Step 5. Set Reducer
		job.setReducerClass(IntSumReducer.class);
		
		//Step 6. Set Input		
		FileInputFormat.addInputPath(job, inputPath);
		
		//Step 7. Set Output
		job.setOutputKeyClass(Text.class);
		job.setOutputValueClass(IntWritable.class);
		
		if(hdfs.exists(outputPath)) hdfs.delete(outputPath, true);
		FileOutputFormat.setOutputPath(job, outputPath);
		
		//Step 8. Submit Job
		job.submit();
		
		if(job.waitForCompletion(true))
		{
			System.out.println("Job Done!");
			System.exit(0);
		}
		else
		{
			System.out.println("Job Failed!");
			System.exit(1);
		}    
	}
}

