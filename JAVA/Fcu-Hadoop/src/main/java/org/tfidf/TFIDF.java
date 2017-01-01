package org.tfidf;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileStatus;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.InputSplit;
import org.apache.hadoop.mapreduce.lib.input.FileSplit;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.Partitioner;
import org.apache.hadoop.mapreduce.Reducer;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;

public class TFIDF {
    // part1------------------------------------------------------------------------
    public static class Mapper_Part1 extends Mapper<LongWritable, Text, Text, Text> {
        String File_name = ""; // 保存文件名，根據文件名區分所屬文件
        int all = 0; // 單詞總數統計
        static Text one = new Text("1");
        String word;
        public void map(LongWritable key, Text value, Context context) throws IOException, InterruptedException {
            InputSplit inputSplit = context.getInputSplit();
            String str =  ((FileSplit) inputSplit).getPath().toString();
            File_name = str.substring(str.lastIndexOf("/") + 1); // 獲取文件名
            StringTokenizer itr = new StringTokenizer(value.toString());//替換成IKAnalyzer
            while (itr.hasMoreTokens()) {
                word = File_name;
                word += " ";
                word += itr.nextToken(); // 將文件名加單詞作為key es: test1 hello 1
                all++;
                context.write(new Text(word), one);
            }
        }

        public void cleanup(Context context) throws IOException,
                InterruptedException {
            // Map的最後，我們將單詞的總數寫入。下面需要用總單詞數來計算。
            String str = "";
            str += all;
            context.write(new Text(File_name + " " + "!"), new Text(str));
            // 主要這裡值使用的 "!"是特別構造的。因為!的ascii比所有的字母都小。
        }
    }

    public static class Combiner_Part1 extends Reducer<Text, Text, Text, Text> {
        float all = 0;
        public void reduce(Text key, Iterable<Text> values, Context context) throws IOException, InterruptedException {
            int index = key.toString().indexOf(" ");
            // 因為!的ascii最小，所以在map階段的排序後，!會出現在第一個
            if (key.toString().substring(index + 1, index + 2).equals("!")) {
                for (Text val : values) {
                    // 獲取總的單詞數。
                    all = Integer.parseInt(val.toString());
                }
                // 這個key-value被拋棄
                return;
            }
            float sum = 0; // 統計某個單詞出現的次數
            for (Text val : values) {
                sum += Integer.parseInt(val.toString());
            }
            // 跳出循環後，某個單詞數出現的次數就統計完了，所有TF(詞頻) = sum / all
            float tmp = sum / all;
            String value = "";
            value += tmp; // 記錄詞頻

            // 將key中單詞和文件名進行互換。 es: test1 hello -> hello test1
            String p[] = key.toString().split(" ");
            String key_to = "";
            key_to += p[1];
            key_to += " ";
            key_to += p[0];
            context.write(new Text(key_to), new Text(value));
        }
    }

    public static class Reduce_Part1 extends Reducer<Text, Text, Text, Text> {
        public void reduce(Text key, Iterable<Text> values, Context context) throws IOException, InterruptedException {
            for (Text val : values) {
                context.write(key, val);
            }
        }
    }

    public static class MyPartitoner extends Partitioner<Text, Text> {
        //實現自定義的Partitioner
        public int getPartition(Text key, Text value, int numPartitions) {
            // 我們將一個文件中計算的結果作為一個文件保存
            // es： test1 test2
            String ip1 = key.toString();
            ip1 = ip1.substring(0, ip1.indexOf(" "));
            Text p1 = new Text(ip1);
            return Math.abs((p1.hashCode() * 127) % numPartitions);
        }
    }

    // part2-----------------------------------------------------
    public static class Mapper_Part2 extends Mapper<LongWritable, Text, Text, Text> {
        public void map(LongWritable key, Text value, Context context)
                throws IOException, InterruptedException {
            String val = value.toString().replaceAll("    ", " "); // 将vlaue中的TAB分割符换成空格
                                                                // es: Bank
                                                                // test1
                                                                // 0.11764706 ->
                                                                // Bank test1
                                                                // 0.11764706
            int index = val.indexOf(" ");
            String s1 = val.substring(0, index); // 獲取單詞 作為key es: hello
            String s2 = val.substring(index + 1); // 其餘部分 作為values​​: test1
                                                    // 0.11764706
            s2 += " ";
            s2 += "1"; // 統計單詞在所有文章中出現的次數, “1” 表示出現一次。 es: test1 0.11764706 1
            context.write(new Text(s1), new Text(s2));
        }
    }

    public static class Reduce_Part2 extends Reducer<Text, Text, Text, Text> {
        int file_count;

        public void reduce(Text key, Iterable<Text> values, Context context)
                throws IOException, InterruptedException {
            // 同一個單詞會被分成同一個group
            file_count = context.getNumReduceTasks(); // 獲取總文件數
            float sum = 0;
            List<String> vals = new ArrayList<String>();
            for (Text str : values) {
                int index = str.toString().lastIndexOf(" ");
                sum += Integer.parseInt(str.toString().substring(index + 1)); // 統計此單詞在所有文件中出現的次數
                vals.add(str.toString().substring(0, index)); // 保存
            }
            double tmp = Math.log10( file_count*1.0 /(sum*1.0)); // 單詞在所有文件中出現的次數除以總文件數= IDF
            for (int j = 0; j < vals.size(); j++) {
                String val = vals.get(j);
                System.out.println("val:"+val);
                //String end = val.substring(val.lastIndexOf(" "));
                String end = val;
                float f_end = Float.parseFloat(end); // 讀取TF
                val += " ";
                val += f_end * tmp; //  tf-idf值
                context.write(key, new Text(val));
            }
        }
    }

    public static void main(String[] args) throws Exception {
        // part1----------------------------------------------------
        Configuration conf1 = new Configuration();
        // 設置文件個數,在計算DF(文件頻率)時會使用
        FileSystem hdfs = FileSystem.get(conf1);
        FileStatus p[] = hdfs.listStatus(new Path(args[0]));

        // 獲取輸入文件夾內文件的個數，然後來設置NumReduceTasks
        Job job1 = Job.getInstance(conf1, "My_tdif_part1");
        job1.setJarByClass(TFIDF.class);
        job1.setMapperClass(Mapper_Part1.class);
        job1.setCombinerClass(Combiner_Part1.class); // combiner在本地執行，效率要高點。
        job1.setReducerClass(Reduce_Part1.class);
        job1.setMapOutputKeyClass(Text.class);
        job1.setMapOutputValueClass(Text.class);
        job1.setOutputKeyClass(Text.class);
        job1.setOutputValueClass(Text.class);
        job1.setNumReduceTasks(p.length);
        job1.setPartitionerClass(MyPartitoner.class); // 使用自定義Partitioner

        FileInputFormat.addInputPath(job1, new Path(args[0]));
        FileOutputFormat.setOutputPath(job1, new Path(args[1]));

        job1.waitForCompletion(true);
        // part2----------------------------------------
        Configuration conf2 = new Configuration();

        Job job2 = Job.getInstance(conf2, "My_tdif_part2");
        job2.setJarByClass(TFIDF.class);
        job2.setMapOutputKeyClass(Text.class);
        job2.setMapOutputValueClass(Text.class);
        job2.setOutputKeyClass(Text.class);
        job2.setOutputValueClass(Text.class);
        job2.setMapperClass(Mapper_Part2.class);
        job2.setReducerClass(Reduce_Part2.class);
        //需要提醒下，我這裡沒有使用自定義Partitioner,默認的Partitioner會根據key來劃分，而我們正
        //好需要這種方式來將所有文件中同一個單詞化為同一個組，方便我們統計一個單詞在所以文件中出現的次數。
        job2.setNumReduceTasks(p.length);

        FileInputFormat.setInputPaths(job2, new Path(args[1]));
        FileOutputFormat.setOutputPath(job2, new Path(args[2]));

        job2.waitForCompletion(true);

//        hdfs.delete(new Path(args[1]), true);
    }
}
