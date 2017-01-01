package org.edx;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Properties;
import java.util.UUID;

import org.apache.commons.io.FileUtils;
import org.apache.hadoop.conf.Configuration;
import org.domain.NetFlowProxyLog;
import org.domain.OpenEdxLog;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class OpenDataProcess {
    private static final Logger L = LoggerFactory.getLogger(OpenDataProcess.class);
    private static Properties prop = new Properties();
    private static Configuration conf = null;

    static {
        try {
            prop.load(OpenDataProcess.class.getResourceAsStream("FilePath.properties"));
        } catch (IOException e) {
            // log exception
            L.error("read *.properties fail.", e);
            throw new RuntimeException("read properties Fail.", e);
        }
    }

    public static void main(String[] args) {
        String filePath = prop.getProperty("FilePath").toString();
        //String[] extensions = prop.getProperty("extensions").toString().split(",");
        Collection<File> files = FileUtils.listFiles(FileUtils.getFile(filePath), null, true);
        for (File file : files) {
            LoadFile(file);
        }
    }

    private static void LoadFile(File file) {
        try {
            FileReader fr = new FileReader(file.getAbsoluteFile());
            BufferedReader br = new BufferedReader(fr);
            String strRow = ""; // 讀第一行
            while ((strRow = br.readLine()) != null) {
                L.info("strRow:" + strRow);
                String[] row = strRow.split(" +");
                try {
//                    L.info("row:{}", row);
                } catch (Exception e) {
                    L.error("Error Data:" + Arrays.toString(row));
                    L.error("FileReader Data fail.", e);
                }
            }
            br.close();
            fr.close();
        } catch (IOException e) {
            L.error("read *.txt fail.", e);
        }
    }
}
