package com.fcu.gtml.edx.domain;

public class TrackingCourse extends Entity {

    private static final long serialVersionUID = 1L;
    private String courseId;
    private String displayName;

    public String getCourseId() {
        return courseId;
    }
    public void setCourseId(String courseId) {
        this.courseId = courseId;
    }
    public String getDisplayName() {
        return displayName;
    }
    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }
    

}
