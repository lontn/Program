package com.fcu.gtml.traindata;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.dbcp.BasicDataSource;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.joda.time.DateTime;
import org.springframework.stereotype.Component;

import com.fcu.gtml.edx.domain.EventTypeSum;
import com.fcu.gtml.edx.domain.FeatureData;
import com.fcu.gtml.edx.domain.FeatureEnum;
import com.fcu.gtml.edx.domain.UserInfo;
import com.fcu.gtml.service.EdXAppService;
import com.fcu.gtml.service.EdXInfoService;
import com.opencsv.CSVWriter;

@Component
public class TrainProcess {
    private static final Logger L = LogManager.getLogger();
    @Resource
    private BasicDataSource basicDataSource;
    @Resource
    private EdXAppService edXAppService;
    @Resource
    private EdXInfoService edXInfoService;
    private static final String outputFile = "D:\\ResourceDataTest\\trainData" + new DateTime().toString("yyyy-MM-dd_HH_mm_ss") + ".csv";
    private String courseId;
    
    public void setCourseId(String courseId) {
        this.courseId = courseId;
    }

    public void build() {
        System.out.println(basicDataSource);
        System.out.println(edXAppService);
        System.out.println(FeatureEnum.SHOWTRANSCRIPT.getName());
        //拿到證書的
        List<UserInfo> userInfos = edXAppService.listAPPUser(courseId);
        List<FeatureData> featureDatas = new ArrayList<>();
        for (UserInfo userInfo : userInfos) {
            L.info("userInfo:{}", userInfo);
            //每個人的事件行為統計
            List<EventTypeSum> listEventType = edXInfoService.listEventType(userInfo.getUserName(), courseId);
            L.info("listEventType:{}", listEventType);
            Map<String, Integer> eventTypeMap = EventTypeSum.EventTypeToMap(listEventType);
//            L.info("eventTypeMap:{}", eventTypeMap);
//            System.out.println(eventTypeMap.get(FeatureEnum.SHOWTRANSCRIPT.getName()));
            FeatureData featureData = combineData(eventTypeMap, userInfo.getUserId(), 1);
            L.info("featureData:{}", featureData);
            featureDatas.add(featureData);
        }
        // 沒有拿到證書的
        List<UserInfo> noCertificateUsers = edXAppService.listNoCertificateUser(courseId);
        for (UserInfo userInfo : noCertificateUsers) {
            //每個人的事件行為統計
            List<EventTypeSum> listEventType = edXInfoService.listEventType(userInfo.getUserName(), courseId);
            L.info("listEventType:{}", listEventType);
            Map<String, Integer> eventTypeMap = EventTypeSum.EventTypeToMap(listEventType);
            FeatureData featureData = combineData(eventTypeMap, userInfo.getUserId(), 0);
            featureDatas.add(featureData);
        }
        // write CSV
        buildCSV(featureDatas);
        L.info("Done!");
    }

    private FeatureData combineData(Map<String, Integer> eventTypeMap, int userId, int label) {
        int showTranscript = eventTypeMap.get(FeatureEnum.SHOWTRANSCRIPT.getName()) != null ? eventTypeMap.get(FeatureEnum.SHOWTRANSCRIPT.getName()) : 0;
        int hideTranscript = eventTypeMap.get(FeatureEnum.HIDETRANSCRIPT.getName()) != null ? eventTypeMap.get(FeatureEnum.HIDETRANSCRIPT.getName()) : 0;
        int loadVideo = eventTypeMap.get(FeatureEnum.LOADVIDEO.getName()) != null ? eventTypeMap.get(FeatureEnum.LOADVIDEO.getName()) : 0;
        int playVideo = eventTypeMap.get(FeatureEnum.PLAYVIDEO.getName()) != null ? eventTypeMap.get(FeatureEnum.PLAYVIDEO.getName()) : 0;
        int pauseVideo = eventTypeMap.get(FeatureEnum.PAUSEVIDEO.getName()) != null ? eventTypeMap.get(FeatureEnum.PAUSEVIDEO.getName()) : 0;
        int seekVideo = eventTypeMap.get(FeatureEnum.SEEKVIDEO.getName()) != null ? eventTypeMap.get(FeatureEnum.SEEKVIDEO.getName()) : 0;
        int stopVideo = eventTypeMap.get(FeatureEnum.STOPVIDEO.getName()) != null ? eventTypeMap.get(FeatureEnum.STOPVIDEO.getName()) : 0;
        int speedChangeVideo = eventTypeMap.get(FeatureEnum.SPEEDCHANGEVIDEO .getName()) != null ? eventTypeMap.get(FeatureEnum.SPEEDCHANGEVIDEO.getName()) : 0;
        int videoHideCCMenu = eventTypeMap.get(FeatureEnum.VIDEOHIDECCMENU .getName()) != null ? eventTypeMap.get(FeatureEnum.VIDEOHIDECCMENU.getName()) : 0;
        int videoShowCCMenu = eventTypeMap.get(FeatureEnum.VIDEOSHOWCCMENU .getName()) != null ? eventTypeMap.get(FeatureEnum.VIDEOSHOWCCMENU.getName()) : 0;
        int seqGoto = eventTypeMap.get(FeatureEnum.SEQGOTO.getName()) != null ? eventTypeMap.get(FeatureEnum.SEQGOTO.getName()) : 0;
        int seqNext = eventTypeMap.get(FeatureEnum.SEQNEXT.getName()) != null ? eventTypeMap.get(FeatureEnum.SEQNEXT.getName()) : 0;
        int seqPrev = eventTypeMap.get(FeatureEnum.SEQPREV.getName()) != null ? eventTypeMap.get(FeatureEnum.SEQPREV.getName()) : 0;
        int pageClose = eventTypeMap.get(FeatureEnum.PAGECLOSE.getName()) != null ? eventTypeMap.get(FeatureEnum.PAGECLOSE.getName()) : 0;
        FeatureData feature = new FeatureData(userId, showTranscript, hideTranscript, loadVideo, playVideo, pauseVideo, seekVideo, stopVideo, speedChangeVideo, videoHideCCMenu, videoShowCCMenu, seqGoto, seqNext, seqPrev, pageClose, label);
        return feature;
    }

    private void buildCSV(List<FeatureData> featureDatas) {
        try {
            boolean alreadyExists = new File(outputFile).exists();
            if (alreadyExists) {
                new File(outputFile).delete();
            }
            CSVWriter csvOutput = new CSVWriter(new FileWriter(outputFile, true), ',');
            List<String[]> data = toCSVStringArray(featureDatas);
            csvOutput.writeAll(data);
            csvOutput.close();
        } catch (IOException e) {
            L.error("buildCSV:{}", e);
            e.printStackTrace();
        }
    }
    
    private List<String[]> toCSVStringArray(List<FeatureData> featureDatas) {
        List<String[]> records = new ArrayList<>();
        String[] header = new String[16];
        int i = 0;
        // adding header record
        header[i] = "userid";
        for (FeatureEnum fEnum : FeatureEnum.values()) {
            i++;
            header[i] = fEnum.getName();
        }
        i++;
        header[i] = "label";
        records.add(header);
        // adding header record End

        // adding value record
        Iterator<FeatureData> it = featureDatas.iterator();
        while (it.hasNext()) {
            FeatureData fData = it.next();
            records.add(new String[] { String.valueOf(fData.getUserId()), 
                    String.valueOf(fData.getShowTranscript()),
                    String.valueOf(fData.getHideTranscript()),
                    String.valueOf(fData.getLoadVideo()),
                    String.valueOf(fData.getPlayVideo()),
                    String.valueOf(fData.getPauseVideo()),
                    String.valueOf(fData.getSeekVideo()),
                    String.valueOf(fData.getStopVideo()),
                    String.valueOf(fData.getSpeedChangeVideo()),
                    String.valueOf(fData.getVideoHideCCMenu()),
                    String.valueOf(fData.getVideoShowCCMenu()),
                    String.valueOf(fData.getSeqGoto()),
                    String.valueOf(fData.getSeqNext()),
                    String.valueOf(fData.getSeqPrev()),
                    String.valueOf(fData.getPageClose()),
                    String.valueOf(fData.getLabel())});
        }
        return records;
    }
}
