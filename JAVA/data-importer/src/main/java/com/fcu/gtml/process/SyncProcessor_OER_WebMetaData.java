package com.fcu.gtml.process;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.hadoop.conf.Configuration;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.wltea.analyzer.lucene.IKAnalyzer;

import com.fcu.gtml.process.Syncer_ProductData.SyncProcessor;
import com.fcu.gtml.process.oer.domain.WebMetadata;
import com.fcu.gtml.process.utils.HDFSUtilTools;
import com.fcu.gtml.process.utils.ListUtilTools;
import com.fcu.gtml.process.utils.WordNetTools;
import com.fcu.gtml.service.WebMetadataService;
import com.fcu.gtml.service.WordNetService;

public class SyncProcessor_OER_WebMetaData implements SyncProcessor {
    private static final Logger L = LogManager.getLogger();
    private WebMetadataService webMetadataService;
    private WordNetService wordNetService;
    
    @Override
    public void process(Syncer_ProductData syncer) {
        L.info("SEQ:{}", ConfigurationTool.WebMetaDataInfo.getSeq());
        Configuration conf = syncer.getConfiguration();
        webMetadataService = syncer.getMetadataService();
        wordNetService = syncer.getWordNetService();
        List<WebMetadata> listWebData = webMetadataService.listWebMetadata(ConfigurationTool.WebMetaDataInfo.getSeq());
        IKAnalyzer analyzer = new IKAnalyzer();
        for (WebMetadata webMetadata : listWebData) {
            int seq = webMetadata.getSeq();
            String title = webMetadata.getTitle();
            String text = webMetadata.getText();
            // 斷字斷詞與去除停用字
            List<String> iklist = new ArrayList<>();
            try {
                iklist = analyzer.split(webMetadata.getText());
            } catch (IOException e) {
                L.error("IKAnalyzer IOException:{}", e);
            }
            // 將處理過的斷字斷詞去停用字轉成MAP
            Map<String, String> ikMap = ListUtilTools.listToMap(new HashMap<String, String>(), iklist);
            //人文類 Humanities 1
            List<String> listWordNetHumanities = listHumanities();
            boolean human = isClassification(listWordNetHumanities, ikMap);
            if (human) {
                L.info("human:{}", seq);
                ConfigurationTool.WebMetaDataInfo.setConfig(seq);
                ConfigurationTool.WebMetaDataInfo.save();
                //Syne HDFS
                HDFSUtilTools.UploadHDFS(conf, "/tmp/mahout-oer/OER-DATA/Humanities/" + seq, text);
                continue;
            }
            //應用科學類 Application sciences 2
            List<String> listWordNetApplication = listApplication();
            boolean application = isClassification(listWordNetApplication, ikMap);
            if (application) {
                //Syne HDFS
                L.info("application:{}", webMetadata.getSeq());
                ConfigurationTool.WebMetaDataInfo.setConfig(webMetadata.getSeq());
                ConfigurationTool.WebMetaDataInfo.save();
                //Syne HDFS
                HDFSUtilTools.UploadHDFS(conf, "/tmp/mahout-oer/OER-DATA/Application/" + seq, text);
                continue;
            }
            //歷史地理類 History and geography 3
            List<String> listWordNetHistory = listHistoryGeography();
            boolean history = isClassification(listWordNetHistory, ikMap);
            if (history) {
                //Syne HDFS
                L.info("history:{}", webMetadata.getSeq());
                ConfigurationTool.WebMetaDataInfo.setConfig(webMetadata.getSeq());
                ConfigurationTool.WebMetaDataInfo.save();
                //Syne HDFS
                HDFSUtilTools.UploadHDFS(conf, "/tmp/mahout-oer/OER-DATA/History-geography/" + seq, text);
                continue;
            }
            //社會科學類 Social science 4
            List<String> listWordNetSocial = listSocial();
            boolean social = isClassification(listWordNetSocial, ikMap);
            if (social) {
                //Syne HDFS
                L.info("social:{}", webMetadata.getSeq());
                ConfigurationTool.WebMetaDataInfo.setConfig(webMetadata.getSeq());
                ConfigurationTool.WebMetaDataInfo.save();
                //Syne HDFS
                HDFSUtilTools.UploadHDFS(conf, "/tmp/mahout-oer/OER-DATA/Social/" + seq, text);
                continue;
            }
            //自然科學類 Natural science 5
            List<String> listWordNetNatural = listNatural();
            boolean natural = isClassification(listWordNetNatural, ikMap);
            if (natural) {
                //Syne HDFS
                L.info("natural:{}", webMetadata.getSeq());
                ConfigurationTool.WebMetaDataInfo.setConfig(webMetadata.getSeq());
                ConfigurationTool.WebMetaDataInfo.save();
                //Syne HDFS
                HDFSUtilTools.UploadHDFS(conf, "/tmp/mahout-oer/OER-DATA/Natural/" + seq, text);
                continue;
            }
            //醫學類 Medical 6
            List<String> listWordNetMedical = listMedical();
            boolean medical = isClassification(listWordNetMedical, ikMap);
            if (medical) {
                //Syne HDFS
                L.info("medical:{}", webMetadata.getSeq());
                ConfigurationTool.WebMetaDataInfo.setConfig(webMetadata.getSeq());
                ConfigurationTool.WebMetaDataInfo.save();
                //Syne HDFS
                HDFSUtilTools.UploadHDFS(conf, "/tmp/mahout-oer/OER-DATA/Medical/" + seq, text);
                continue;
            }
            //其他 Other 7
            ConfigurationTool.WebMetaDataInfo.setConfig(webMetadata.getSeq());
            ConfigurationTool.WebMetaDataInfo.save();
            //Syne HDFS
            //HDFSUtilTools.UploadHDFS(conf, "/tmp/mahout-oer/OER-DATA/Humanities/" + seq, text);
            //Sync HBase
        }
    }

    private boolean isClassification(List<String> listWordNet, Map<String, String> ikMap) {
        boolean isResult = false;
        for (String word : listWordNet) {
            if (ikMap.containsKey(word)) {
                isResult = true;
                break;
            }
        }
        return isResult;
    }
    private List<String> listHumanities() {
        List<String> wordNetHumanities = WordNetTools.listWordNet(wordNetService, "Humanities");
        //wordNetHumanities.add("Humanities");
        //wordNetHumanities.add("Human");
        return wordNetHumanities;
    }

    private List<String> listApplication() {
        List<String> wordNetApplication = WordNetTools.listWordNet(wordNetService, "Application");
        wordNetApplication.add("Application");
        return wordNetApplication;
    }

    private List<String> listHistoryGeography() {
        List<String> wordNetHistory = WordNetTools.listWordNet(wordNetService, "History");
        List<String> wordGeography = WordNetTools.listWordNet(wordNetService, "geography");
        wordNetHistory.addAll(wordGeography);
        return wordNetHistory;
    }

    private List<String> listSocial() {
        List<String> wordNetSocial = WordNetTools.listWordNet(wordNetService, "Social science");
        return wordNetSocial;
    }
    
    private List<String> listNatural() {
        List<String> wordNetNatural = WordNetTools.listWordNet(wordNetService, "Natural science");
        return wordNetNatural;
    }

    private List<String> listMedical() {
        List<String> wordNetMedical = WordNetTools.listWordNet(wordNetService, "Medical science");
        return wordNetMedical;
    }

//    private void UploadHDFS(Configuration conf, String dist, String word) {
//        FSDataOutputStream fsOutput = null;
//        try {
//            FileSystem hdfs = FileSystem.get(conf);
//            Path filePath = new Path(dist);
//            fsOutput = hdfs.create(filePath);
//            fsOutput.writeUTF(word);
//        } catch (IOException e) {
//            L.error("UploadHDFS Exception:{}", e);
//        } finally {
//            IOUtils.closeStream(fsOutput);
//        }
//    }
}
