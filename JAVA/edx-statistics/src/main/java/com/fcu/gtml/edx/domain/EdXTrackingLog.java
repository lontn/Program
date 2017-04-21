package com.fcu.gtml.edx.domain;

import java.util.Date;

import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;

public class EdXTrackingLog extends Entity {

    private static final long serialVersionUID = 1L;
    private int id;
    private String userName;
    private String eventType;
    private String event;
    private Date time;
    private String context;
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
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
    public String getEvent() {
        return event;
    }
    public void setEvent(String event) {
        this.event = event;
    }
    public Date getTime() {
        return time;
    }
    public void setTime(Date time) {
        this.time = time;
    }
    public String getDateTime() {
        DateTime dt = new DateTime(this.time, DateTimeZone.UTC);
        return dt.toString("yyyy-MM-dd HH:mm:ss");
    }
    public String getContext() {
        return context;
    }
    public void setContext(String context) {
        this.context = context;
    }

    
}
