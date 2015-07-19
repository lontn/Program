package com.fcu.gtml.domain;

import java.util.Date;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import com.fasterxml.jackson.annotation.JsonProperty;

public class StudentNavigational {
    private String userName;
    private String eventType;
    @JsonProperty("id")
    private String edXId;
    @JsonProperty("new")
    private Integer newId;
    @JsonProperty("old")
    private Integer old;
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
    public Integer getNewId() {
        return newId;
    }
    public void setNewId(Integer newId) {
        this.newId = newId;
    }
    public Integer getOld() {
        return old;
    }
    public void setOld(Integer old) {
        this.old = old;
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
