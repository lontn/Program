package com.fcu.gtml.persistence;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.joda.time.DateTime;
import org.joda.time.Days;
import org.joda.time.Hours;
import org.joda.time.Minutes;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fcu.gtml.SupportSpringTest;
import com.fcu.gtml.domain.StudentVideoPlayVideo;

public class StudentTrainDataMapperTest extends SupportSpringTest {
    private static final Logger L = LoggerFactory.getLogger(StudentTrainDataMapperTest.class);
    @Resource private StudentTrainDataMapper mapper;
    
    @Test
    public void listStudentPlayVideoUser() {
        List<StudentVideoPlayVideo> list = mapper.listStudentPlayVideoUser();
        for (StudentVideoPlayVideo data : list) {
            L.info("UserName:{}", data.getUserName());
            L.info("Code:{}", data.getCode());
        }
    }

    //A1
    @Test
    public void listStudentPlayVideoByKey() {
        List<StudentVideoPlayVideo> list = mapper.listStudentPlayVideoByKey("Rosberg", "9WcRpDwgNIY");
        DateTime dtStart = new DateTime(1970,1,1,17,0,0);
        DateTime dtEnd = new DateTime(1970,1,1,8,0,0);
        for (StudentVideoPlayVideo video : list) {
            DateTime dt3 = new DateTime(video.getTime());
            if (dt3.isAfter(dtStart) || dt3.isBefore(dtEnd)){
                L.info("boolean:{}", true);
            } else {
                L.info("boolean:{}", false);
            }
        }
    }
    
    //A2
    @Test
    public void listStudentPlayVideoByKeyA2() {
        List<StudentVideoPlayVideo> list = mapper.listStudentPlayVideoByKey("Rosberg", "9WcRpDwgNIY");
        DateTime dtStart = new DateTime(list.get(0).getTime());
        DateTime dtEnd = new DateTime(list.get(list.size()-1).getTime());
        L.info("boolean:{}", Minutes.minutesBetween(dtStart, dtEnd).getMinutes());
        L.info("boolean:{}", list.get(list.size()-1).getTime());
        
    }

    //A3
    @Test
    public void listStudentPlayVideoByKeyA3() {
        List<StudentVideoPlayVideo> list = mapper.listStudentPlayVideoByKey("525Foreval", null);
        DateTime dtStart = new DateTime(list.get(0).getTime());
        DateTime dtEnd = new DateTime(list.get(list.size()-1).getTime());
        L.info("boolean:{}", Minutes.minutesBetween(dtStart, dtEnd).getMinutes());
        L.info("list:{}", list.get(list.size()-1));
    }

    //A4
    @Test
    public void listStudentPlayVideoByKeyA4() {
        List<StudentVideoPlayVideo> list = mapper.listStudentPlayVideoUser();
        String userName = "";
        int count = 0;
        for (StudentVideoPlayVideo data : list) {
            if (count == 0) {
                userName = data.getUserName();
            }
            if (userName.equals(data.getUserName())){
                count++;
                continue;
            } else {
                L.info("userName:{}, count:{}", userName, count);
                count = 0;
                userName = data.getUserName();
                count++;
            }
        }
    }
    
    //A5
    @Test
    public void getOpenResponseCount() {
        int count = mapper.getOpenResponseCount("PeiLun");
        L.info("count:{}", count);
    }
    @Test
    public void datetime() {
        DateTime dtStart = new DateTime(1970,1,1,17,0,0);
        DateTime dtEnd = new DateTime(1970,1,1,8,0,0);
        DateTime dt3 = new DateTime(1970,1,1,4,0,0);
        L.info("time:{}", dtStart.toString("HH:mm"));
        L.info("time:{}", dtEnd.toString("HH:mm"));
        
        if (dt3.isAfter(dtStart) || dt3.isBefore(dtEnd)){
            L.info("boolean:{}", true);
        } else {
            L.info("boolean:{}", false);
        }
        Map<String, String> map = new HashMap<>();
        map.put("525Foreval", "2Sa_0CMEZiM");
        map.put("525Foreval", "3pm4kPscQSQ");
        map.put("525Foreval", "cDvAziR7DO0");
        L.info("map:{}", map.size());
    }
}
