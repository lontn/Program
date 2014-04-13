package org.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;

public class NetFlowHistory {

    private String row;
    private String time;
    private String length;
    private String protocol;
    private String sIp;
    private String sPort;
    private String dIp;
    private String dPort;

    
    public NetFlowHistory(String row, String time, String length,
            String protocol, String sIp, String sPort, String dIp, String dPort) {
        this.row = row;
        this.time = time;
        this.length = length;
        this.protocol = protocol;
        this.sIp = sIp;
        this.sPort = sPort;
        this.dIp = dIp;
        this.dPort = dPort;
    }

    public String getRow() {
        return row;
    }
    public void setRow(String row) {
        this.row = row;
    }
    public String getTime() {
        return time;
    }
    public void setTime(String time) {
        this.time = time;
    }
    public String getLength() {
        return length;
    }
    public void setLength(String length) {
        this.length = length;
    }
    public String getProtocol() {
        return protocol;
    }
    public void setProtocol(String protocol) {
        this.protocol = protocol;
    }
    public String getsIp() {
        return sIp;
    }
    public void setsIp(String sIp) {
        this.sIp = sIp;
    }
    public String getsPort() {
        return sPort;
    }
    public void setsPort(String sPort) {
        this.sPort = sPort;
    }
    public String getdIp() {
        return dIp;
    }
    public void setdIp(String dIp) {
        this.dIp = dIp;
    }
    public String getdPort() {
        return dPort;
    }
    public void setdPort(String dPort) {
        this.dPort = dPort;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }
}
