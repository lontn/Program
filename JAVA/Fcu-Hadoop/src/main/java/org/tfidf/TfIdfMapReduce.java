package org.tfidf;

import java.io.IOException;
import java.util.Collection;
import java.util.Comparator;
import java.util.Map.Entry;
import java.util.Set;
import java.util.StringTokenizer;
import java.util.TreeMap;
 
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.input.FileSplit;
import org.apache.hadoop.mapreduce.lib.input.KeyValueTextInputFormat;
import org.apache.hadoop.mapreduce.lib.input.TextInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
import org.apache.hadoop.mapreduce.lib.output.MultipleOutputs;
import org.apache.hadoop.mapreduce.lib.output.TextOutputFormat;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
/**
 * <pre>
 * TF-IDF 算法MapReduce實現
 * 分3job
 * job 1: 統計文檔中單詞在該文檔中出現的次數(n)及該文檔所有單詞的總數(N)
 * job 2: 統計單詞所包含的文檔數(d),根據所有文檔的總數(D)，計算tf-idf值
 * job 3: 對job2進行排序，輸出tf-idf值最大的前top N個詞
 * 數學公式:
 * tf = n / N
 * idf = Math.log(D / d); 
 * tf-idf = tf * idf 
 * </pre>
 * @author deyin
 *
 */
public class TfIdfMapReduce {
 
  private static Configuration conf;
 
  public static void main(String[] args) throws Exception {
    conf = new Configuration();
    if (args.length < 3) {
      System.err.println("arguments invalid, usgae: hadoop jar tfidf.jar com.jumei.robot.mapreduce.tfidf.TfIdfMapReduce <hdfs input folder> <hdfs output folder> <number of documents> <topN>");
      return;
    }
 
    String pathin = args[0];
    String pathout = args[1];
    int nrOfDocuments = Integer.parseInt(args[2]);
    int topN = Integer.parseInt(args[3]);
 
    System.out.println("==========================================");
    System.out.println("pathin: " + pathin);
    System.out.println("pathout: " + pathout);
    System.out.println("nrOfDocuments: " + nrOfDocuments);
    System.out.println("topN: " + topN);
    System.out.println("==========================================");
 
    FileSystem fs = FileSystem.get(conf);
 
    if (!fs.exists(new Path(pathout))) {
      fs.mkdirs(new Path(pathout));
    }
 
    Path firstJobOut  = new Path(pathout, "job1_output");
    Path secondJobOut = new Path(pathout, "job2_output");
    Path thirdJobOut = new Path(pathout, "job3_output");
    // empty if exists output
    fs.delete(firstJobOut, true);
    fs.delete(secondJobOut, true);
    fs.delete(thirdJobOut, true);
     
    // Run job 1
    runFirstJob(new Path(pathin), firstJobOut, nrOfDocuments);
 
    // Run job 2
    runSecondJob(firstJobOut, secondJobOut, nrOfDocuments); // job1的输出作为job2的输入+
     
    // Run job 3
    runThirdJob(secondJobOut, thirdJobOut, topN); // job1的输出作为job2的输入+
     
  }
   
  private static int runFirstJob(Path pathin, Path pathout, final int reduceTaskSize) throws Exception {
    String jobName = "tfidf_first_job";
    System.out.println("==================" + jobName + "=======================");
 
    Job job = new Job(conf, jobName); 
    job.setJarByClass(TfIdfMapReduce.class);
 
    job.setMapperClass(FirstMapReduce.Mapper.class);
    job.setCombinerClass(FirstMapReduce.Combiner.class);
    job.setReducerClass(FirstMapReduce.Reducer.class);
     
    job.setNumReduceTasks(reduceTaskSize);
    // 自定义分区器
    job.setPartitionerClass(FirstMapReduce.Partitioner.class);
 
    job.setInputFormatClass(TextInputFormat.class);
    job.setOutputFormatClass(TextOutputFormat.class);
     
    job.setMapOutputKeyClass(Text.class);
    job.setMapOutputValueClass(Text.class);
     
    job.setOutputKeyClass(Text.class);
    job.setOutputValueClass(Text.class);
 
    FileInputFormat.addInputPath(job, pathin);
    FileOutputFormat.setOutputPath(job, pathout);
 
    boolean success = job.waitForCompletion(true);
    return success ? 0 : -1;
  }
 
  private static int runSecondJob(Path pathin, Path pathout, final int nrOfDocuments) throws Exception {
    String jobName = "tfidf_second_job";
    System.out.println("==================" + jobName + "=======================");
     
    conf.setInt("nrOfDocuments", nrOfDocuments);
    Job job = new Job(conf, jobName);
     
    job.setJarByClass(TfIdfMapReduce.class);
 
    job.setMapperClass(SecondMapReduce.Mapper.class);
    job.setCombinerClass(SecondMapReduce.Combiner.class);
    job.setReducerClass(SecondMapReduce.Reducer.class);
 
    job.setInputFormatClass(KeyValueTextInputFormat.class);
    job.setOutputFormatClass(TextOutputFormat.class);
 
    job.setMapOutputKeyClass(Text.class);
    job.setMapOutputValueClass(Text.class);
 
    job.setOutputKeyClass(Text.class);
    job.setOutputValueClass(Text.class);
     
    FileInputFormat.addInputPath(job, pathin);
    FileOutputFormat.setOutputPath(job, pathout);
 
    boolean success = job.waitForCompletion(true);
    return success ? 0 : -1;
  }
   
  private static int runThirdJob(Path pathin, Path pathout, final int topN) throws Exception {
    String jobName = "tfidf_third_job";
    System.out.println("==================" + jobName + "=======================");
     
    conf.setInt("topN", topN);
    conf.set("topN_out", new Path(pathin.getParent(), "" + topN).getName());
    Job job = new Job(conf, jobName);
     
    job.setJarByClass(TfIdfMapReduce.class);
     
    job.setMapperClass(ThirdMapReduce.Mapper.class);
    job.setReducerClass(ThirdMapReduce.Reducer.class);
     
    job.setInputFormatClass(KeyValueTextInputFormat.class);
    job.setOutputFormatClass(TextOutputFormat.class);
     
    job.setMapOutputKeyClass(Text.class);
    job.setMapOutputValueClass(Text.class);
     
    job.setOutputKeyClass(Text.class);
    job.setOutputValueClass(Text.class);
     
    MultipleOutputs.addNamedOutput(job, "top" + topN, TextOutputFormat.class, Text.class, Text.class);
     
    FileInputFormat.addInputPath(job, pathin);
    FileOutputFormat.setOutputPath(job, pathout);
     
    boolean success = job.waitForCompletion(true);
    return success ? 0 : -1;
  }
 
  static class FirstMapReduce {
 
    // 分词接口
    //static IWordSegService wordSegService;
     
    //停用词过滤接口
    //static IFilterStopwordService filterStopwordService;
     
    static {
      init();
    }
     
    static void init() {
      //ApplicationContext ctx = new ClassPathXmlApplicationContext("classpath*:spring/robot-preprocess.xml");
      //wordSegService = (IWordSegService) ctx.getBean("wordSegService");
      //filterStopwordService = (IFilterStopwordService) ctx.getBean("filterStopwordService");
    }
 
    static class Mapper extends org.apache.hadoop.mapreduce.Mapper<LongWritable, Text, Text, Text> {
 
      static final Text one = new Text("1");
 
      String filename = "";
 
      long totalWordCount = 0; // 当前文档中单词总数
 
      @Override
      protected void setup(Context context) throws IOException, InterruptedException {
        System.out.println("=================" + context.getJobName() + " map================");
      }
       
      @Override
      protected void map(LongWritable key, Text value, Context context) throws IOException, InterruptedException {
        // Segment the line into words and output each word
        // Input (LineNr, Line in document)
        // Output (filename;word, 1)
        String line = value.toString();
        if (line.trim().isEmpty() || line.startsWith("#")) { // ignore empty or comment line
          return;
        }
        FileSplit split = (FileSplit) context.getInputSplit();
        filename = split.getPath().toString();
        // 分词
        //Collection<Word> words = wordSegService.segment(line);
        // 去掉停用词
        //filterStopwordService.filter(words);
        //for (Word word : words) {
          //String outputKey = filename + ";" + word.getName();
          //System.out.println("<" + outputKey + ", " + one.toString() + ">");
          //context.write(new Text(outputKey), one);
          //++totalWordCount;
        //} // end for
      } // end map
       
      @Override
      protected void cleanup(Context context) throws IOException, InterruptedException {
        context.write(new Text(filename + ";" + "!"), new Text("" + totalWordCount)); // 写入文件中词的总数目, '!'的ascii码比所有字母都小，sort后排在最前面
      }
       
    } // end class Mapper
     
    static class Partitioner extends org.apache.hadoop.mapreduce.Partitioner<Text, Text> {
      @Override
      public int getPartition(Text key, Text value, int numPartitions) {
        // partition by filename
        StringTokenizer tokenizer = new StringTokenizer(key.toString(), ";");
        String filename = tokenizer.nextToken();
        int hashCode = new Text(filename).hashCode();
        return Math.abs((hashCode * 127) % numPartitions);  
      }
    } // end class Partitioner
     
    static class Combiner extends org.apache.hadoop.mapreduce.Reducer<Text, Text, Text, Text> {
       
      @Override
      protected void setup(Context context) throws IOException, InterruptedException {
        System.out.println("=================" + context.getJobName() + " combiner================");
      }
       
      long totalWordCount = 0;
      @Override
      protected void reduce(Text key, Iterable<Text> values, Context context) throws IOException, InterruptedException {
        // Calculate word count of each document and total word count
        // Input  (filename;word, 1)
        // Output (word;filename, n;N)
        StringTokenizer tokenizer = new StringTokenizer(key.toString(), ";");
        String filename = tokenizer.nextToken();
        String word = tokenizer.nextToken();
        if(word.endsWith("!")) {
          for (Text value : values) {
            totalWordCount = Long.parseLong(value.toString());
            System.out.println("File " + filename + " total word count " + totalWordCount);
            return;
          }
        }
        long wordCount = 0;
        for(Text value: values) {
          wordCount += Integer.parseInt(value.toString());
        }
        String outputKey = word + ";" + filename;
        String outputValue = wordCount + ";" + totalWordCount;
        //System.out.println("<" + outputKey + ", " + outputValue + ">");
        context.write(new Text(outputKey), new Text(outputValue));
      } // end reduce
    } // end class Combiner
     
    static class Reducer extends org.apache.hadoop.mapreduce.Reducer<Text, Text, Text, Text> {
       
      @Override
      protected void setup(Context context)
          throws IOException, InterruptedException {
        System.out.println("=================" + context.getJobName() + " reducer================");
      }
       
      protected void reduce(Text key, Iterable<Text> values, Context context) throws IOException, InterruptedException {
        for (Text value : values) {
          //System.out.println("<" + key.toString() + ", " + value.toString() + ">");
          context.write(key, value);
        }
      }
    } // end reduce
  } // end class reducer
   
  static class SecondMapReduce {
     
    static class Mapper extends org.apache.hadoop.mapreduce.Mapper<Text, Text, Text, Text> {
 
      static Text one = new Text("1");
       
      @Override
      protected void setup(Context context)
          throws IOException, InterruptedException {
        System.out.println("=================" + context.getJobName() + " map================");
      }
       
      @Override
      protected void map(Text key, Text value, Context context) throws IOException, InterruptedException {
        // Word occur in document
        // Input  (word;filename, n;N)
        // Output (word;filename;n;N, 1)
        String outputKey = key.toString() + ";" + value.toString();
        String outputValue = one.toString();
        //System.out.println("<" + outputKey + ", " + outputValue  + ">");
        context.write(new Text(outputKey), one);
      }
    } // end map
   
    static class Combiner extends org.apache.hadoop.mapreduce.Reducer<Text, Text, Text, Text> {
       
      int D = 1;
       
      @Override
      protected void setup(Context context) throws IOException, InterruptedException {
        D = context.getConfiguration().getInt("nrOfDocuments", 0);
        System.out.println("=================" + context.getJobName() + " combiner================");
      }
       
      protected void reduce(Text key, Iterable<Text> values, Context context) throws IOException, InterruptedException {
        // Calculate word contains document count
        // Input  (word;filename;n;N, 1)
        // Output (word;filename;n;N, d;D)
        int d = 0; // 该单词包含的文档总数
        for (Text value : values) {
          d += Integer.parseInt(value.toString());
        }
        String outputKey = key.toString(); 
        String outputValue = d + ";" + D;
        //System.out.println("<" + outputKey + ", " + outputValue  + ">");
        context.write(key, new Text(outputValue));
      } // end reduce
    } // end class Combiner
     
    static class Reducer extends org.apache.hadoop.mapreduce.Reducer<Text, Text, Text, Text> {
       
      @Override
      protected void setup(Context context) throws IOException, InterruptedException {
        System.out.println("=================" + context.getJobName() + " reducer================");
      }
       
      protected void reduce(Text key, Iterable<Text> values, Context context) throws IOException, InterruptedException {
        // Calculate tf-idf
        // Input  (word;filename;n;N, d;D)
        // Output (word;filename, tf-idf)
        StringTokenizer keyTokenizer = new StringTokenizer(key.toString(), ";");
        String word = keyTokenizer.nextToken();
        String filename = keyTokenizer.nextToken();
        long n = Long.parseLong(keyTokenizer.nextToken()); // 单词出现次数
        long N = Long.parseLong(keyTokenizer.nextToken()); // 单词总数
        StringTokenizer valueTokenizer = new StringTokenizer(values.iterator().next().toString(), ";");
        int d = Integer.parseInt(valueTokenizer.nextToken()); // 单词包含的文档数
        int D = Integer.parseInt(valueTokenizer.nextToken()); // 文档总数
        double tf = n / 1.0d / N;
        double idf = Math.log(D / 1.0d / d);
        double tfidf = tf * idf;
        String outputKey = word + ";" + filename;
        String outputValue = "" + tfidf;
        //System.out.println("<" + outputKey + ", " + outputValue  + ">");
        context.write(new Text(outputKey), new Text(outputValue));
      } // end reduce
       
      @Override
      protected void cleanup(Context context) throws IOException, InterruptedException {
        super.cleanup(context);
      }
    } // end Reducer
  } // end class SecondMapReduce
   
  static class ThirdMapReduce {
     
    static class Pair implements Comparable<Pair>{
      final String key; 
      final Double value;
      public Pair(String key, Double value) {
        this.key = key;
        this.value = value;
      }
 
      public int compareTo(Pair o) {
        int value = o.value.compareTo(this.value);
        if(value != 0) {
          return value;
        }
        return o.key.compareTo(this.key);
      }
       
      @Override
      public String toString() {
        return key;
      }
    }
 
    static class Mapper extends org.apache.hadoop.mapreduce.Mapper<Text, Text, Text, Text> {
       
      static TreeMap<Pair, String> treemap = new TreeMap<Pair, String>(new Comparator<Pair>() {
        public int compare(Pair o1, Pair o2) {
          return o1.compareTo(o2);
        }
      });
       
      int topN;
       
      @Override
      protected void setup(Context context) throws IOException, InterruptedException {
        topN = context.getConfiguration().getInt("topN", 100); // default 100
        System.out.println("=================" + context.getJobName() + " map================");
      }
       
      @Override
      protected void map(Text key, Text value, Context context) throws IOException, InterruptedException {
        // Input  (word;filename, tf-idf)
        treemap.put(new Pair(key.toString(), Double.parseDouble(value.toString())), value.toString());
        if(treemap.size() > topN) {
          treemap.remove(treemap.lastKey());
        }
      } // end map
       
      @Override
      protected void cleanup(Context context) throws IOException, InterruptedException {
        Set<Entry<Pair,String>> entrySet = treemap.entrySet();
        for (Entry<Pair, String> entry : entrySet) {
          String outputKey = entry.getKey().toString();
          String outputValue = entry.getValue();
          //System.out.println("<" + outputKey + ", " + outputValue  + ">");
          context.write(new Text(outputKey), new Text(outputValue));
        }
      }
    } // end class mapper
     
    static class Reducer extends org.apache.hadoop.mapreduce.Reducer<Text, Text, Text, Text> {
       
      int topN;
       
      static TreeMap<Pair, String> treemap = new TreeMap<Pair, String>(new Comparator<Pair>() {
        public int compare(Pair o1, Pair o2) {
          return o1.compareTo(o2);
        }
      });
       
      @Override
      protected void setup(Context context) throws IOException, InterruptedException {
        topN = context.getConfiguration().getInt("topN", 100); // default 100
        System.out.println("=================" + context.getJobName() + " reduce================");
      }
       
      @Override
      protected void reduce(Text key, Iterable<Text> values,Context context) throws IOException, InterruptedException {
        // Input (word;filename, tf-idf)
        Text value = values.iterator().next();
        treemap.put(new Pair(key.toString(), Double.parseDouble(value.toString())), value.toString());
        if(treemap.size() > topN) {
          treemap.remove(treemap.lastKey());
        }
      } // end reduce
       
      @Override
      protected void cleanup(Context context) throws IOException, InterruptedException {
        // 输出前topN个
        String path = context.getConfiguration().get("topN_out");
        MultipleOutputs<Text, Text> output = null;
        try {
          output = new MultipleOutputs<Text, Text>(context);
          Set<Entry<Pair, String>> entrySet = treemap.entrySet();
          System.out.println("================TF-IDF top " + topN + "==================");
          for (Entry<Pair, String> entry : entrySet) {
            String key = entry.getKey().toString();
            String value = entry.getValue();
            output.write("top" + topN, key, value, path);
            System.out.println("<" + key + ", " + value  + ">");
          }
        } catch (IOException e) {
          throw e;
        } catch (InterruptedException e) {
          throw e;
        } finally {
          if (output != null) {
            output.close();
          }
        }
      }
    } // end class Reducer
  }
   
}
