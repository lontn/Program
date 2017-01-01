package com.fcu.gtml.domain;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonProperty;

public class TextBookByBook extends TheData {
    private int trackingLogId;
    private int userId;
    private String eventType;
    private String type;
    @JsonProperty("old")
    private Integer eventOld;
    @JsonProperty("new")
    private Integer eventNew;
    private String chapter;
    private String name;
    private Date time;

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
    public String getEventType() {
        return eventType;
    }
    public void setEventType(String eventType) {
        this.eventType = eventType;
    }
    public String getType() {
        return type;
    }
    public void setType(String type) {
        this.type = type;
    }
    public Integer getEventOld() {
        return eventOld;
    }
    public void setEventOld(Integer eventOld) {
        this.eventOld = eventOld;
    }
    public Integer getEventNew() {
        return eventNew;
    }
    public void setEventNew(Integer eventNew) {
        this.eventNew = eventNew;
    }
    public String getChapter() {
        return chapter;
    }
    public void setChapter(String chapter) {
        this.chapter = chapter;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public Date getTime() {
        return time;
    }
    public void setTime(Date time) {
        this.time = time;
    }
    
}
