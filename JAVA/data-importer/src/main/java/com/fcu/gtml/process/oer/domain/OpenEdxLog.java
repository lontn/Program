package com.fcu.gtml.process.oer.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

public class OpenEdxLog {
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
    private String contextCourseId;
    private String contextPath;
    private String contextModuleDisplayName;
    private String time;
    private String page;
    private long longTime;

    public OpenEdxLog() {}

    public OpenEdxLog(String userName, String eventType, String ip,
            String agent, String host, String event, String eventSource,
            String contextUserId, String contextOrgId, String contextSession,
            String contextCourseId, String contextPath,
            String contextModuleDisplayName, String time, String page, long longTime) {
        this.userName = userName;
        this.eventType = eventType;
        this.ip = ip;
        this.agent = agent;
        this.host = host;
        this.event = event;
        this.eventSource = eventSource;
        this.contextUserId = contextUserId;
        this.contextOrgId = contextOrgId;
        this.contextSession = contextSession;
        this.contextCourseId = contextCourseId;
        this.contextPath = contextPath;
        this.contextModuleDisplayName = contextModuleDisplayName;
        this.time = time;
        this.page = page;
        this.longTime = longTime;
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
    public String getContextCourseId() {
        return contextCourseId;
    }
    public void setContextCourseId(String contextCourseId) {
        this.contextCourseId = contextCourseId;
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
    public String getTime() {
        return time;
    }
    public void setTime(String time) {
        this.time = time;
    }
    public String getPage() {
        return page;
    }
    public void setPage(String page) {
        this.page = page;
    }
    public long getLongTime() {
        return longTime;
    }
    public void setLongTime(long longTime) {
        this.longTime = longTime;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE);
    }
}
