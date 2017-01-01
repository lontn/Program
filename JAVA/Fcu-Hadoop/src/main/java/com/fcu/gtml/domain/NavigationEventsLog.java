package com.fcu.gtml.domain;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonProperty;

public class NavigationEventsLog extends TheData {
    private int trackingLogId;
    private int userId;
    private String eventType;
    @JsonProperty("id")
    private String eventId;
    @JsonProperty("old")
    private Integer eventOld;
    @JsonProperty("new")
    private Integer eventNew;
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
    public String getEventId() {
        return eventId;
    }
    public void setEventId(String eventId) {
        this.eventId = eventId;
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
    public Date getTime() {
        return time;
    }
    public void setTime(Date time) {
        this.time = time;
    }

}
