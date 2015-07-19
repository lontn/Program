package com.fcu.gtml.domain;

import java.util.Date;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.supercsv.cellprocessor.ift.CellProcessor;

public class EdxLog {
    private String userName;
    private String eventType;
    private String ip;
    private String agent;
    private String host;
    private String event;
    private String eventSource;
    private String contextUserId;
    private String contextOrgId;
    private String contextSession;
    private String contextCourseid;
    private String contextPath;    
    private String contextModuleDisplayName;
    private Date time;
    private String page;
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
    public String getIp() {
        return ip;
    }
    public void setIp(String ip) {
        this.ip = ip;
    }
    public String getAgent() {
        return agent;
    }
    public void setAgent(String agent) {
        this.agent = agent;
    }
    public String getHost() {
        return host;
    }
    public void setHost(String host) {
        this.host = host;
    }
    public String getEvent() {
        return event;
    }
    public void setEvent(String event) {
        this.event = event;
    }
    public String getEventSource() {
        return eventSource;
    }
    public void setEventSource(String eventSource) {
        this.eventSource = eventSource;
    }
    public String getContextUserId() {
        return contextUserId;
    }
    public void setContextUserId(String contextUserId) {
        this.contextUserId = contextUserId;
    }
    public String getContextOrgId() {
        return contextOrgId;
    }
    public void setContextOrgId(String contextOrgId) {
        this.contextOrgId = contextOrgId;
    }
    public String getContextSession() {
        return contextSession;
    }
    public void setContextSession(String contextSession) {
        this.contextSession = contextSession;
    }
    public String getContextCourseid() {
        return contextCourseid;
    }
    public void setContextCourseid(String contextCourseid) {
        this.contextCourseid = contextCourseid;
    }
    public String getContextPath() {
        return contextPath;
    }
    public void setContextPath(String contextPath) {
        this.contextPath = contextPath;
    }
    public String getContextModuleDisplayName() {
        return contextModuleDisplayName;
    }
    public void setContextModuleDisplayName(String contextModuleDisplayName) {
        this.contextModuleDisplayName = contextModuleDisplayName;
    }
    public Date getTime() {
        return time;
    }
    public void setTime(Date time) {
        this.time = time;
    }
    public String getPage() {
        return page;
    }
    public void setPage(String page) {
        this.page = page;
    }
    
    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.MULTI_LINE_STYLE);
    }
}
