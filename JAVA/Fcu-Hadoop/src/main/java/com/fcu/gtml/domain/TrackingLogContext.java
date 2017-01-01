package com.fcu.gtml.domain;

public class TrackingLogContext extends TheData {
    private int trackingLogBAKId;
    private int userId;
    private String orgId;
    private String courseId;
    private String path;

    public int getTrackingLogBAKId() {
        return trackingLogBAKId;
    }
    public void setTrackingLogBAKId(int trackingLogBAKId) {
        this.trackingLogBAKId = trackingLogBAKId;
    }
    public int getUserId() {
        return userId;
    }
    public void setUserId(int userId) {
        this.userId = userId;
    }
    public String getOrgId() {
        return orgId;
    }
    public void setOrgId(String orgId) {
        this.orgId = orgId;
    }
    public String getCourseId() {
        return courseId;
    }
    public void setCourseId(String courseId) {
        this.courseId = courseId;
    }
    public String getPath() {
        return path;
    }
    public void setPath(String path) {
        this.path = path;
    }

    
}
