package com.fcu.gtml.domain;

import java.util.Date;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

public class UserVisitorAccess {
    private int Id;
    private Date timesTemp;
    private int responseTime;
    private String uri;
    private String clientIdentity;
    
    public UserVisitorAccess() {
    }
    public UserVisitorAccess(Date timesTemp, int responseTime, String uri,
            String clientIdentity) {
        this.timesTemp = timesTemp;
        this.responseTime = responseTime;
        this.uri = uri;
        this.clientIdentity = clientIdentity;
    }
    public int getId() {
        return Id;
    }
    public void setId(int id) {
        Id = id;
    }
    public Date getTimesTemp() {
        return timesTemp;
    }
    public void setTimesTemp(Date timesTemp) {
        this.timesTemp = timesTemp;
    }
    public int getResponseTime() {
        return responseTime;
    }
    public void setResponseTime(int responseTime) {
        this.responseTime = responseTime;
    }
    public String getUri() {
        return uri;
    }
    public void setUri(String uri) {
        this.uri = uri;
    }
    public String getClientIdentity() {
        return clientIdentity;
    }
    public void setClientIdentity(String clientIdentity) {
        this.clientIdentity = clientIdentity;
    }
    
    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.MULTI_LINE_STYLE);
    }
}
