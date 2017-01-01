package com.fcu.gtml.domain;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

public class EnrollmentEventsLog extends TheData {
    private int trackingLogId;
    private int userId;
    private String eventType;
    @JsonProperty("course_id")
    private String courseId;
    @JsonProperty("mode")
    private String mode;
    @JsonProperty("user_id")
    private int enrollUserId;
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
    public String getCourseId() {
        return courseId;
    }
    public void setCourseId(String courseId) {
        this.courseId = courseId;
    }
    public String getMode() {
        return mode;
    }
    public void setMode(String mode) {
        this.mode = mode;
    }
    public int getEnrollUserId() {
        return enrollUserId;
    }
    public void setEnrollUserId(int enrollUserId) {
        this.enrollUserId = enrollUserId;
    }
    public Date getTime() {
        return time;
    }
    public void setTime(Date time) {
        this.time = time;
    }
    
    
}
