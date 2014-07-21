package org.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.joda.time.DateTime;

public class NetFlowProxyLog {
    private String row;
    private String timesTemp;
    private String responseTime;
    private String clientAddress;
    private String statusCode;
    private String transportSize;
    private String requestMethod;
    private String uri;
    private String clientIdentity;
    private String rightSideCoding;
    private String contentType;

    public NetFlowProxyLog(String row, String timesTemp, String responseTime,
            String clientAddress, String statusCode, String transportSize,
            String requestMethod, String uri, String clientIdentity,
            String rightSideCoding, String contentType) {
        this.row = row;
        this.timesTemp = timesTemp;
        this.responseTime = responseTime;
        this.clientAddress = clientAddress;
        this.statusCode = statusCode;
        this.transportSize = transportSize;
        this.requestMethod = requestMethod;
        this.uri = uri;
        this.clientIdentity = clientIdentity;
        this.rightSideCoding = rightSideCoding;
        this.contentType = contentType;
    }

    public String getRow() {
        return row;
    }

    public void setRow(String row) {
        this.row = row;
    }

    public String getTimesTemp() {
        DateTime dt = new DateTime(Long.parseLong(timesTemp.replace(".", "")));
        return dt.toString("yyyy-MM-dd HH:mm:ss.mmm");
    }

    public void setTimesTemp(String timesTemp) {
        this.timesTemp = timesTemp;
    }

    public String getResponseTime() {
        return responseTime;
    }

    public void setResponseTime(String responseTime) {
        this.responseTime = responseTime;
    }

    public String getClientAddress() {
        return clientAddress;
    }

    public void setClientAddress(String clientAddress) {
        this.clientAddress = clientAddress;
    }

    public String getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(String statusCode) {
        this.statusCode = statusCode;
    }

    public String getTransportSize() {
        return transportSize;
    }

    public void setTransportSize(String transportSize) {
        this.transportSize = transportSize;
    }

    public String getRequestMethod() {
        return requestMethod;
    }

    public void setRequestMethod(String requestMethod) {
        this.requestMethod = requestMethod;
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

    public String getRightSideCoding() {
        return rightSideCoding;
    }

    public void setRightSideCoding(String rightSideCoding) {
        this.rightSideCoding = rightSideCoding;
    }

    public String getContentType() {
        return contentType;
    }

    public void setContentType(String contentType) {
        this.contentType = contentType;
    }
    
    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }
}
