package com.fcu.gtml.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

public class VideoBySeekVideo extends TheData {
    private int trackingLogId;
    private int userId;
    private String id;
    @JsonProperty("old_time")
    private double oldTime;
    @JsonProperty("new_time")
    private double newTime;
    private String type;
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
    public double getOldTime() {
        return oldTime;
    }
    public void setOldTime(double oldTime) {
        this.oldTime = oldTime;
    }
    public double getNewTime() {
        return newTime;
    }
    public void setNewTime(double newTime) {
        this.newTime = newTime;
    }
    public String getType() {
        return type;
    }
    public void setType(String type) {
        this.type = type;
    }
    public String getCode() {
        return code;
    }
    public void setCode(String code) {
        this.code = code;
    }

}
