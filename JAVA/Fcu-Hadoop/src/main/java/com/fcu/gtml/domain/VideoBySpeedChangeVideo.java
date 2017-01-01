package com.fcu.gtml.domain;

import com.fasterxml.jackson.annotation.JsonProperty;

public class VideoBySpeedChangeVideo extends TheData {
    private int trackingLogId;
    private int userId;
    private String id;
    @JsonProperty("current_time")
    private double currentTime;
    @JsonProperty("old_speed")
    private String oldSpeed;
    @JsonProperty("new_speed")
    private String newSpeed;
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
    public String getOldSpeed() {
        return oldSpeed;
    }
    public void setOldSpeed(String oldSpeed) {
        this.oldSpeed = oldSpeed;
    }
    public String getNewSpeed() {
        return newSpeed;
    }
    public void setNewSpeed(String newSpeed) {
        this.newSpeed = newSpeed;
    }
    public String getCode() {
        return code;
    }
    public void setCode(String code) {
        this.code = code;
    }
    
}
