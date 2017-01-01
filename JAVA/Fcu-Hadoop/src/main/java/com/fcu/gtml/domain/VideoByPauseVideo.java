package com.fcu.gtml.domain;

public class VideoByPauseVideo extends TheData {
    private int trackingLogId;
    private int userId;
    private String id;
    private double currentTime;
    private String code;

    public int getTrackingLogId() {
        return trackingLogId;
    }
    public void setTrackingLogId(int trackingLogId) {
        this.trackingLogId = trackingLogId;
    }
    public int getUserId() {
        return userId;
    }
    public void setUserId(int userId) {
        this.userId = userId;
    }
    public String getId() {
        return id;
    }
    public void setId(String id) {
        this.id = id;
    }
    public double getCurrentTime() {
        return currentTime;
    }
    public void setCurrentTime(double currentTime) {
        this.currentTime = currentTime;
    }
    public String getCode() {
        return code;
    }
    public void setCode(String code) {
        this.code = code;
    }
    
    
}
