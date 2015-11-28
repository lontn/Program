package org.sqlserver;

import java.util.ArrayList;
import java.util.List;

import org.common.utils.StudentEventTool;
import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fcu.gtml.domain.StudentPlayVideoTrainData;
import com.fcu.gtml.domain.StudentVideoPlayVideo;

public class StudentTrainDataTask extends StudentEventTool {
    private static final Logger L = LoggerFactory.getLogger(StudentTrainDataTask.class);
    
    public static void main(String[] args) {
        List<StudentVideoPlayVideo> userList = trainDataService.listStudentPlayVideoUser();
        List<StudentPlayVideoTrainData> result = new ArrayList<>();
        DateTime dtStart = new DateTime(1970,1,1,17,0,0);
        DateTime dtEnd = new DateTime(1970,1,1,8,0,0);
        for (StudentVideoPlayVideo video : userList) {
            //A1
            List<StudentVideoPlayVideo> userVideo = trainDataService.listStudentPlayVideoByKey(video.getUserName(), video.getCode());
            // A3
            List<StudentVideoPlayVideo> a3list = trainDataService.listStudentPlayVideoByKey(video.getUserName(), null);
            // A4
            int codeCount = getYoutubeCount(video.getUserName(), userList);
            //A5
            int openCount = trainDataService.getOpenResponseCount(video.getUserName());
            StudentPlayVideoTrainData trainData = StudentPlayVideoTrainData.bindData(video, dtStart, dtEnd, userVideo, a3list, codeCount, openCount);
            result.add(trainData);
        }
//        for (StudentPlayVideoTrainData train : result) {
//            L.info("train:{}", train);
//        }
        trainDataService.batchInsert(result);
        L.info("Done");
    }
    
    private static int getYoutubeCount(String userName, List<StudentVideoPlayVideo> userList) {
        int count = 0;
        boolean flag = false;
        for (StudentVideoPlayVideo data : userList) {
            if (userName.equals(data.getUserName())){
                count++;
                flag = true;
                continue;
            } else {
                if (flag == true) {
                    break;
                }
            }
        }
        return count;
    }
}
