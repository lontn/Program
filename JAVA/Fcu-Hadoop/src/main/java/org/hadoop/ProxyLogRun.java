package org.hadoop;

import java.io.IOException;

import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.PropertiesConfiguration;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.hbase.client.Scan;
import org.apache.hadoop.hbase.filter.FilterList;
import org.apache.hadoop.hbase.mapreduce.TableMapReduceUtil;
import org.apache.hadoop.hbase.util.Bytes;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
import org.common.utils.HBaseQuaryTools;
import org.joda.time.DateTime;
import org.proxylog.CacheCodeStatistics.CacheCodeStatisticsMapper;
import org.proxylog.CacheCodeStatistics.CacheCodeStatisticsReducer;
import org.proxylog.HttpStatusStatistics.HttpStatusStatisticsMapper;
import org.proxylog.HttpStatusStatistics.HttpStatusStatisticsReducer;
import org.proxylog.MethodStatistics.MethodStatisticsMapper;
import org.proxylog.MethodStatistics.MethodStatisticsReducer;
import org.proxylog.PeerStatusStatistics.PeerStatusStatisticsMapper;
import org.proxylog.PeerStatusStatistics.PeerStatusStatisticsReducer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ProxyLogRun {
    private static final Logger L = LoggerFactory.getLogger(ProxyLogRun.class);
    private static PropertiesConfiguration config = null;
    private static Configuration conf = null;
    private static final String COLUMN_FAMILY = "LibraryInfo";
    private static final String QUALIFIER_TIMESTEMP = "Timestemp";
    private static final String QUALIFIER_STATUSCODE = "StatusCode";
    private static final String QUALIFIER_CONTENTTYPE = "ContentType";
    private static final String QUALIFIER_REQUESTMETHOD = "RequestMethod";
    private static final String QUALIFIER_RIGHTSIDECODING = "RightSideCoding";
    private static final String RANGE = "TimeRange.properties";
    private static String[] timeRange = {};
    static {
        conf = HBaseConfiguration.create();
        try {
            config = new PropertiesConfiguration(RANGE);
            timeRange = config.getStringArray("TimeRange");
        } catch (ConfigurationException e) {
            L.error("ConfigurationException:"+ e);
        }
    }
    
    public static void main(String[] args) {
        Scan scan = getScanSetting(args[0]);
        Path outputPath = null;
        try {
            FileSystem hdfs = FileSystem.get(conf);
            Job job = getJob(args[0], hdfs, outputPath, scan);
            if (job.waitForCompletion(true)) {
                System.out.println("Job Done!");
                System.exit(0);
            } else {
                System.out.println("Job Failed!");
                System.exit(1);
            }
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    /**
     * 設定HBase的查詢Scan
     * @param set
     * @return
     */
    private static Scan getScanSetting(String set){
        Scan scan = new Scan();
        scan.addColumn(Bytes.toBytes(COLUMN_FAMILY), Bytes.toBytes(QUALIFIER_TIMESTEMP));
        if (set.equals("StatusCode")) {
            scan.addColumn(Bytes.toBytes(COLUMN_FAMILY), Bytes.toBytes(QUALIFIER_STATUSCODE));
        }
        if (set.equals("ContentType")) {
            scan.addColumn(Bytes.toBytes(COLUMN_FAMILY), Bytes.toBytes(QUALIFIER_CONTENTTYPE));
        }
        if (set.equals("RequestMethod")) {
            scan.addColumn(Bytes.toBytes(COLUMN_FAMILY), Bytes.toBytes(QUALIFIER_REQUESTMETHOD));
        }
        if (set.equals("PeerStatus")) {
            scan.addColumn(Bytes.toBytes(COLUMN_FAMILY), Bytes.toBytes(QUALIFIER_RIGHTSIDECODING));
        }
        //選取時間區間
        FilterList filterList = new FilterList(HBaseQuaryTools.columnFilterList(COLUMN_FAMILY, QUALIFIER_TIMESTEMP, timeRange));
        scan.setFilter(filterList);
        return scan;
    }

    /**
     * 設定Job運行的參數
     * @param set
     * @param hdfs
     * @param outputPath
     * @param scan
     * @return
     */
    private static Job getJob(String set, FileSystem hdfs, Path outputPath, Scan scan){
        Job job = null;
        try {
            job = new Job(conf, "ProxyLogRun");
        } catch (IOException e) {
            L.error("Job Error:{}", e);
        }
        job.setJarByClass(ProxyLogRun.class);
        try{
            if (set.equals("StatusCode")) {
                outputPath = new Path(hdfs.getWorkingDirectory().toString() + "/output/CacheCodeOutput-" + new DateTime().toString("yyyy-MM-dd HH:mm:ss.sss")); //當下的運行的目錄
                TableMapReduceUtil.initTableMapperJob("NetFlowProxyLogTest", scan, CacheCodeStatisticsMapper.class, Text.class, IntWritable.class, job);
                job.setReducerClass(CacheCodeStatisticsReducer.class);
            }
            if (set.equals("ContentType")) {
                outputPath = new Path(hdfs.getWorkingDirectory().toString() + "/output/HttpStatusOutput-" + new DateTime().toString("yyyy-MM-dd HH:mm:ss.sss")); //當下的運行的目錄
                TableMapReduceUtil.initTableMapperJob("NetFlowProxyLogTest", scan, HttpStatusStatisticsMapper.class, Text.class, IntWritable.class, job);
                job.setReducerClass(HttpStatusStatisticsReducer.class);
            }
            if (set.equals("RequestMethod")) {
                outputPath = new Path(hdfs.getWorkingDirectory().toString() + "/output/MethodStatisticsOutput-" + new DateTime().toString("yyyy-MM-dd HH:mm:ss.sss")); //當下的運行的目錄
                TableMapReduceUtil.initTableMapperJob("NetFlowProxyLogTest", scan, MethodStatisticsMapper.class, Text.class, IntWritable.class, job);
                job.setReducerClass(MethodStatisticsReducer.class);
            }
            if (set.equals("PeerStatus")) {
                outputPath = new Path(hdfs.getWorkingDirectory().toString() + "/output/PeerStatusOutput-" + new DateTime().toString("yyyy-MM-dd HH:mm:ss.sss")); //當下的運行的目錄
                TableMapReduceUtil.initTableMapperJob("NetFlowProxyLogTest", scan, PeerStatusStatisticsMapper.class, Text.class, IntWritable.class, job);
                job.setReducerClass(PeerStatusStatisticsReducer.class);
            }
            job.setOutputKeyClass(Text.class);
            job.setOutputValueClass(IntWritable.class);
            if (hdfs.exists(outputPath))
                hdfs.delete(outputPath, true);
            
            FileOutputFormat.setOutputPath(job, outputPath);
        } catch (IOException e) {
            L.error("MapperReduce Error:{}", e);
        }
        return job;
    }
}
