package com.fcu.gtml.domain;

import java.util.Date;
import java.util.List;

import org.joda.time.DateTime;
import org.joda.time.Minutes;

public class StudentPlayVideoTrainData extends Entity {

    private static final long serialVersionUID = 1L;

    private String userName;
    private String code;
    private int a1;
    private int a2;
    private int a3;
    private int a4;
    private int a5;
    private Date createTime;

    public String getUserName() {
        return userName;
    }
    public void setUserName(String userName) {
        this.userName = userName;
    }
    public String getCode() {
        return code;
    }
    public void setCode(String code) {
        this.code = code;
    }
    public int getA1() {
        return a1;
    }
    public void setA1(int a1) {
        this.a1 = a1;
    }
    public int getA2() {
        return a2;
    }
    public void setA2(int a2) {
        this.a2 = a2;
    }
    public int getA3() {
        return a3;
    }
    public void setA3(int a3) {
        this.a3 = a3;
    }
    public int getA4() {
        return a4;
    }
    public void setA4(int a4) {
        this.a4 = a4;
    }
    public int getA5() {
        return a5;
    }
    public void setA5(int a5) {
        this.a5 = a5;
    }
    public Date getCreateTime() {
        return createTime;
    }
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public static StudentPlayVideoTrainData bindData(StudentVideoPlayVideo video, DateTime dtStart, DateTime dtEnd, List<StudentVideoPlayVideo> userVideo, List<StudentVideoPlayVideo> a3list, int codeCount, int openCount) {
        StudentPlayVideoTrainData trainData = new StudentPlayVideoTrainData();
        trainData.setUserName(video.getUserName());
        trainData.setCode(video.getCode());
        //A1
        DateTime dt3 = new DateTime(userVideo.get(0).getTime());
        if (dt3.isAfter(dtStart) || dt3.isBefore(dtEnd)){
            trainData.setA1(1);
        } else {
            trainData.setA1(0);
        }
        //A2
        DateTime vidoStart = new DateTime(userVideo.get(0).getTime());
        DateTime videoEnd = new DateTime(userVideo.get(userVideo.size()-1).getTime());
        int totalMinutes = Minutes.minutesBetween(vidoStart, videoEnd).getMinutes();
        if (totalMinutes >=50 ){
            trainData.setA2(3);
        } else if (totalMinutes < 30 && totalMinutes >= 10) {
            trainData.setA2(2);
        } else if (totalMinutes < 10 && totalMinutes >= 2) {
            trainData.setA2(1);
        } else {
            trainData.setA2(0);
        }
        // A3
        DateTime a3Start = new DateTime(a3list.get(0).getTime());
        DateTime a3End = new DateTime(a3list.get(a3list.size()-1).getTime());
        int a3Minutes = Minutes.minutesBetween(a3Start, a3End).getMinutes();
        if (a3Minutes < 30 && a3Minutes >= 10) {
            trainData.setA3(2);
        } else if (a3Minutes < 10 && a3Minutes >= 2) {
            trainData.setA3(1);
        } else {
            trainData.setA3(0);
        }
        // A4
        if (codeCount >= 5) {
            trainData.setA4(2);
        } else if (codeCount < 5 && codeCount > 2) {
            trainData.setA4(1);
        } else {
            trainData.setA4(0);
        }
        // A5
        if (openCount > 0) {
            trainData.setA5(1);
        } else {
            trainData.setA5(0);
        }
        return trainData;
    }
}
