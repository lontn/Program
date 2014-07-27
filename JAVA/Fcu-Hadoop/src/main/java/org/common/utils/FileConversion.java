package org.common.utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Properties;
import java.util.UUID;

import org.apache.commons.io.FileUtils;
import org.hbase.HBaseDataProcess;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class FileConversion {

    private static final Logger L = LoggerFactory.getLogger(FileConversion.class);
    private static Properties prop = new Properties();
    static{
        try {
            prop.load(HBaseDataProcess.class.getResourceAsStream("FilePath.properties"));
        } catch (IOException e) {
            // log exception
            L.error("read *.properties fail.", e);
            throw new RuntimeException("read properties Fail.", e);
        }
    }
    public static void main(String[] args) {
        String filePath = prop.getProperty("FilePath").toString();
        String[] extensions = prop.getProperty("extensions").toString().split(",");
        L.info("filePath:"+filePath);
        L.info("extensions:{} ,{}", new Object[]{filePath, extensions});
//        Collection<File> files = FileUtils.listFiles(FileUtils.getFile(filePath), extensions, true);
//        for(File file : files){
//            List<String[]> results = LoadFile(file);
//            for(String[] val : results){
//                
//            }
//        }
    }

    /**
     * 讀取檔案，以一個檔案為單位
     */
    private static List<String[]> LoadFile(File file){
        List<String[]> results = new ArrayList<>();
        try {
            FileReader fr = new FileReader(file.getAbsoluteFile());
            BufferedReader br = new BufferedReader(fr);
            String strRow = ""; // 讀第一行
            while ((strRow = br.readLine()) != null) {
                L.info("strRow:" + strRow);
                //String[] row = strRow.replace(oldChar, newChar);
                String[] row = strRow.split(" +");
                try {
                    L.info(row[0]);
                    L.info(row[1]);
                    L.info(row[2]);
                    L.info(row[3]);
                    L.info(row[4]);
                    L.info(row[5]);
                    L.info(row[6]);
                } catch (Exception e) {
                    L.error("Error Data:" + Arrays.toString(row));
                    L.error("FileReader Data fail.", e);
                }
            }
            br.close();
            fr.close();
        } catch (IOException e) {
            L.error("read *.log fail.", e);
        }
        return results;
    }

}
