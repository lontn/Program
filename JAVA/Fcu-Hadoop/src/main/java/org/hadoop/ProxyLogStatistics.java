package org.hadoop;

import java.io.IOException;

import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.PropertiesConfiguration;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.hbase.client.Result;
import org.apache.hadoop.hbase.client.Scan;
import org.apache.hadoop.hbase.filter.FilterList;
import org.apache.hadoop.hbase.io.ImmutableBytesWritable;
import org.apache.hadoop.hbase.mapreduce.TableMapReduceUtil;
import org.apache.hadoop.hbase.mapreduce.TableMapper;
import org.apache.hadoop.hbase.util.Bytes;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.Reducer;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
import org.common.utils.HBaseQuaryTools;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ProxyLogStatistics {
    private static final Logger L = LoggerFactory.getLogger(ProxyLogStatistics.class);
    private static PropertiesConfiguration config = null;
    private static Configuration conf = null;
    private static final String COLUMN_FAMILY = "LibraryInfo";
    private static final String QUALIFIER_CLIENTADDRESS = "ClientAddress";
    private static final String QUALIFIER_URI = "URI";
    private static final String QUALIFIER_CLIENTIDENTITY = "ClientIdentity";
    private static final String QUALIFIER_TIMESTEMP = "Timestemp";
    private static final String QUALIFIER_CONTENTTYPE = "ContentType";
    private static final String QUALIFIER_STATUSCODE = "StatusCode";
    private static final String RANGE = "TimeRange.properties";
    private static String[] timeRange = {};
    public static enum Counters { ROWS, COLS, ERROR, VALID }
    static {
        conf = HBaseConfiguration.create();
        try {
            config = new PropertiesConfiguration(RANGE);
            timeRange = config.getStringArray("TimeRange");
        } catch (ConfigurationException e) {
            L.error("ConfigurationException:"+ e);
        }
    }
    
    public static class ProxyLogStatisticsMapper extends TableMapper<Text, IntWritable>{
        private IntWritable value = new IntWritable(1);
        private Text word = new Text();
        
        @Override
        public void map(ImmutableBytesWritable row, Result columns, Context context) throws IOException {
            //context.getCounter(Counters.ROWS).increment(1); //Row
            //String qualifier = null;
            String clientAddress = "";
            String uri = "";
            String clientIdentity = "";
            String statusCode = "";
            String timeStemp = "";
            //String contentType = "";
            try{
                //clientAddress = new String(columns.getValue(Bytes.toBytes(COLUMN_FAMILY), Bytes.toBytes(QUALIFIER_CLIENTADDRESS)));
                uri = new String(columns.getValue(Bytes.toBytes(COLUMN_FAMILY), Bytes.toBytes(QUALIFIER_URI)));
                clientIdentity = new String(columns.getValue(Bytes.toBytes(COLUMN_FAMILY), Bytes.toBytes(QUALIFIER_CLIENTIDENTITY)));
                timeStemp = new String(columns.getValue(Bytes.toBytes(COLUMN_FAMILY), Bytes.toBytes(QUALIFIER_TIMESTEMP)));
                //contentType = new String(columns.getValue(Bytes.toBytes(COLUMN_FAMILY), Bytes.toBytes(QUALIFIER_CONTENTTYPE)));
                statusCode = new String(columns.getValue(Bytes.toBytes(COLUMN_FAMILY), Bytes.toBytes(QUALIFIER_STATUSCODE)));
//                String resultURL = uri.replace("http://", "");
//                String[] result = resultURL.split("/");
//                word.set(result[0] + "---"+ clientIdentity);
                String[] code = statusCode.split("/");
                String[] time = timeStemp.split(" ");
                word.set(time[0]+ "---" + code[1]);
                context.write(word, value);
//                if(clientAddress.indexOf("140.134") != -1 && contentType.equals("text/html")){
//                    word.set(timeStemp+ "---"+ clientAddress+ "---" + uri + "---" + clientIdentity);
//                    // vv AnalyzeData
//                    
//                    //context.getCounter(Counters.VALID).increment(1);
//                }
            }catch(Exception e){
                L.error("ProxyLogStatisticsMapper fail.", e);
                L.error("Row:{}", row.get());
                L.info("clientIdentity:{}", clientIdentity);
                context.getCounter(Counters.ERROR).increment(1);
            }
        }
    }
    
    public static class ProxyLogStatisticsReducer extends Reducer<Text, IntWritable, Text, IntWritable> {
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
            scan.addColumn(Bytes.toBytes(COLUMN_FAMILY), Bytes.toBytes(QUALIFIER_CLIENTADDRESS));
            scan.addColumn(Bytes.toBytes(COLUMN_FAMILY), Bytes.toBytes(QUALIFIER_URI));
            scan.addColumn(Bytes.toBytes(COLUMN_FAMILY), Bytes.toBytes(QUALIFIER_CLIENTIDENTITY));
            scan.addColumn(Bytes.toBytes(COLUMN_FAMILY), Bytes.toBytes(QUALIFIER_TIMESTEMP));
            scan.addColumn(Bytes.toBytes(COLUMN_FAMILY), Bytes.toBytes(QUALIFIER_CONTENTTYPE));
            scan.addColumn(Bytes.toBytes(COLUMN_FAMILY), Bytes.toBytes(QUALIFIER_STATUSCODE));
            //選取時間區間
            FilterList filterList = new FilterList(HBaseQuaryTools.columnFilterList(COLUMN_FAMILY, QUALIFIER_TIMESTEMP, timeRange));
            scan.setFilter(filterList);

            FileSystem hdfs = FileSystem.get(conf);
            //Path inputPath = new Path(hdfs.getWorkingDirectory().toString() + "/input");
            Path outputPath = new Path(hdfs.getWorkingDirectory().toString() + "/output"); //當下的運行的目錄
            
            Job job = new Job(conf, "ProxyLogStatistics");
            job.setJarByClass(ProxyLogStatistics.class);
            TableMapReduceUtil.initTableMapperJob("NetFlowProxyLogTest", scan, ProxyLogStatisticsMapper.class, Text.class, IntWritable.class, job);
            job.setReducerClass(ProxyLogStatisticsReducer.class);
            
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
