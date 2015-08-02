package com.fcu.gtml.domain;

import java.util.Date;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import com.fasterxml.jackson.annotation.JsonProperty;

public class StudentTextBook {
    private String userName;
    private String eventType;
    @JsonProperty("type")
    private String type;
    @JsonProperty("old")
    private Integer old;
    @JsonProperty("new")
    private Integer textBookNew;
    @JsonProperty("chapter")
    private String chapter;
    @JsonProperty("name")
    private String name;
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
    public String getType() {
        return type;
    }
    public void setType(String type) {
        this.type = type;
    }
    public Integer getOld() {
        return old;
    }
    public void setOld(Integer old) {
        this.old = old;
    }
    public Integer getTextBookNew() {
        return textBookNew;
    }
    public void setTextBookNew(Integer textBookNew) {
        this.textBookNew = textBookNew;
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
    
    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.MULTI_LINE_STYLE);
    }
}
