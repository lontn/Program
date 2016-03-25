package com.fcu.gtml.process.utils;

import java.io.IOException;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FSDataOutputStream;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IOUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class HDFSUtilTools {
    private static final Logger L = LogManager.getLogger();

    /**
     * 寫入檔案到HDFS
     * @param conf HDFS路徑設定檔案
     * @param dist 目的地的HDFS位置Ex: /tmp/OER-DATA/Humanities/title 
     * @param word 要寫入檔案的內容
     */
    public static void UploadHDFS(Configuration conf, String dist, String word) {
        FSDataOutputStream fsOutput = null;
        try {
            FileSystem hdfs = FileSystem.get(conf);
            Path filePath = new Path(dist);
            fsOutput = hdfs.create(filePath);
            fsOutput.writeUTF(word);
        } catch (IOException e) {
            L.error("UploadHDFS Exception:{}", e);
        } finally {
            IOUtils.closeStream(fsOutput);
        }
    }
}
