package com.fcu.gtml.domain;

import java.util.Date;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import com.fasterxml.jackson.annotation.JsonProperty;

public class StudentVideoSpeedChangeVideo {
    private String userName;
    private String eventType;
    @JsonProperty("id")
    private String edXId;
    @JsonProperty("current_time")
    private Double currentTime;
    @JsonProperty("old_speed")
    private Double oldSpeed;
    @JsonProperty("new_speed")
    private Double newSpeed;
    @JsonProperty("code")
    private String code;
    private Date time;

    public String getUserName() {
        return userName;
    }
    public void setUserName(String userName) {
        this.userName = userName;
    }
    public String getEventType() {
        return eventType;
    }
    public void setEventType(String eventType) {
        this.eventType = eventType;
    }
    public String getEdXId() {
        return edXId;
    }
    public void setEdXId(String edXId) {
        this.edXId = edXId;
    }
    public Double getCurrentTime() {
        return currentTime;
    }
    public void setCurrentTime(Double currentTime) {
        this.currentTime = currentTime;
    }
    public Double getOldSpeed() {
        return oldSpeed;
    }
    public void setOldSpeed(Double oldSpeed) {
        this.oldSpeed = oldSpeed;
    }
    public Double getNewSpeed() {
        return newSpeed;
    }
    public void setNewSpeed(Double newSpeed) {
        this.newSpeed = newSpeed;
    }
    public String getCode() {
        return code;
    }
    public void setCode(String code) {
        this.code = code;
    }
    public Date getTime() {
        return time;
    }
    public void setTime(Date time) {
        this.time = time;
    }
    
    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.MULTI_LINE_STYLE);
    }
}
