package org.domain;

public class NetFlow {

    private String row;
    private String srcDateTime;
    private String srcDuration;
    private String srcProto;
    private String srcIP;
    private String srcPort;
    private String srcType;
    private String dstIP;
    private String dstPort;
    private String dstType;

    

    public NetFlow(String row, String srcDateTime, String srcDuration,
            String srcProto, String srcIP, String srcPort, String srcType,
            String dstIP, String dstPort, String dstType) {
        this.row = row;
        this.srcDateTime = srcDateTime;
        this.srcDuration = srcDuration;
        this.srcProto = srcProto;
        this.srcIP = srcIP;
        this.srcPort = srcPort;
        this.srcType = srcType;
        this.dstIP = dstIP;
        this.dstPort = dstPort;
        this.dstType = dstType;
    }

    public String getRow() {
        return row;
    }
    public void setRow(String row) {
        this.row = row;
    }
    public String getSrcDateTime() {
        return srcDateTime;
    }
    public void setSrcDateTime(String srcDateTime) {
        this.srcDateTime = srcDateTime;
    }
    public String getSrcDuration() {
        return srcDuration;
    }
    public void setSrcDuration(String srcDuration) {
        this.srcDuration = srcDuration;
    }
    public String getSrcProto() {
        return srcProto;
    }
    public void setSrcProto(String srcProto) {
        this.srcProto = srcProto;
    }
    public String getSrcIP() {
        return srcIP;
    }
    public void setSrcIP(String srcIP) {
        this.srcIP = srcIP;
    }
    public String getSrcPort() {
        return srcPort;
    }
    public void setSrcPort(String srcPort) {
        this.srcPort = srcPort;
    }
    public String getSrcType() {
        return srcType;
    }
    public void setSrcType(String srcType) {
        this.srcType = srcType;
    }
    public String getDstIP() {
        return dstIP;
    }
    public void setDstIP(String dstIP) {
        this.dstIP = dstIP;
    }
    public String getDstPort() {
        return dstPort;
    }
    public void setDstPort(String dstPort) {
        this.dstPort = dstPort;
    }
    public String getDstType() {
        return dstType;
    }
    public void setDstType(String dstType) {
        this.dstType = dstType;
    }

    @Override
    public String toString() {
        return "NetFlow [row=" + row + ", srcDateTime=" + srcDateTime
                + ", srcDuration=" + srcDuration + ", srcProto=" + srcProto
                + ", srcIP=" + srcIP + ", srcPort=" + srcPort + ", srcType="
                + srcType + ", dstIP=" + dstIP + ", dstPort=" + dstPort
                + ", dstType=" + dstType + "]";
    }
    
}
