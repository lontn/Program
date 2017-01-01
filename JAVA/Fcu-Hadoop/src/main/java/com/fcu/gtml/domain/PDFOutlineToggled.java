package com.fcu.gtml.domain;

public class PDFOutlineToggled extends TheData {
    private int trackingLogId;
    private Integer page;
    private String chapter;
    private String name;

    public int getTrackingLogId() {
        return trackingLogId;
    }
    public void setTrackingLogId(int trackingLogId) {
        this.trackingLogId = trackingLogId;
    }
    public Integer getPage() {
        return page;
    }
    public void setPage(Integer page) {
        this.page = page;
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

}
