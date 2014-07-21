package org.library;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.math.NumberUtils;
import org.joda.time.DateTime;

public class KPI {

    private String timesTemp; //時間戳
    private String responseTime; //響應時間
    private String clientAddress; //客戶端地址
    private String result; //結果
    private String statusCode; //結果/狀態碼
    private String transportSize; //傳輸size
    private String requestMethod; //請求方式
    private String uri; //URI
    private String clientIdentity; //客戶端身份
    private String rightSideCoding; //對端編碼/對端主機
    private String host;
    private String contentType; //內容類型
    private String requestHeaders; //HTTP請求頭部
    private String responseHeader; //HTTP響應頭部

    private boolean valid = true;// 判断數據是否合法
    
    public String getTimesTemp() {
        DateTime dt = new DateTime(Long.parseLong(timesTemp));
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

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
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

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public String getContentType() {
        return contentType;
    }

    public void setContentType(String contentType) {
        this.contentType = contentType;
    }

    public String getRequestHeaders() {
        return requestHeaders;
    }

    public void setRequestHeaders(String requestHeaders) {
        this.requestHeaders = requestHeaders;
    }

    public String getResponseHeader() {
        return responseHeader;
    }

    public void setResponseHeader(String responseHeader) {
        this.responseHeader = responseHeader;
    }

    public boolean isValid() {
        return valid;
    }

    public void setValid(boolean valid) {
        this.valid = valid;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }

    private static KPI parser(String line) {
        KPI kpi = new KPI();
        String[] arr = line.split(" +");
        if (arr.length != 0) {
            kpi.setTimesTemp(arr[0]);
            kpi.setResponseTime(arr[1]);
            kpi.setClientAddress(arr[2]);
            kpi.setStatusCode(arr[3]);
            kpi.setTransportSize(arr[4]);
            kpi.setRequestMethod(arr[5]);
            kpi.setUri(arr[6]);
            kpi.setClientIdentity(arr[7]);
            kpi.setRightSideCoding(arr[8]);
            kpi.setContentType(arr[9]);
            if(arr.length > 10){
                kpi.setRequestHeaders(arr[10]);
            }
        } else {
            kpi.setValid(false);
        }
        return kpi;
    }

    /**
     * 按page的pv分类
     */
    public static KPI filterPVs(String line) {
        KPI kpi = parser(line);
        Set<String> pages = new HashSet<String>();
        pages.add("/about");
        pages.add("/black-ip-list/");
        pages.add("/cassandra-clustor/");
        pages.add("/finance-rhive-repurchase/");
        pages.add("/hadoop-family-roadmap/");
        pages.add("/hadoop-hive-intro/");
        pages.add("/hadoop-zookeeper-intro/");
        pages.add("/hadoop-mahout-roadmap/");

//        if (!pages.contains(kpi.getRequest())) {
//            kpi.setValid(false);
//        }
        return kpi;
    }

    /**
     * 按page的独立ip分类
     */
    public static KPI filterIPs(String line) {
        KPI kpi = parser(line);
        Set<String> pages = new HashSet<String>();
        pages.add("/about");
        pages.add("/black-ip-list/");
        pages.add("/cassandra-clustor/");
        pages.add("/finance-rhive-repurchase/");
        pages.add("/hadoop-family-roadmap/");
        pages.add("/hadoop-hive-intro/");
        pages.add("/hadoop-zookeeper-intro/");
        pages.add("/hadoop-mahout-roadmap/");

//        if (!pages.contains(kpi.getRequest())) {
//            kpi.setValid(false);
//        }
        
        return kpi;
    }
    /**
     * PV按浏览器分类
     */
    public static KPI filterBroswer(String line) {
        return parser(line);
    }
    
    /**
     * PV按小时分类
     */
    public static KPI filterTime(String line) {
        return parser(line);
    }
    
    /**
     * PV按访问域名分类
     */
    public static KPI filterDomain(String line){
        return parser(line);
    }

    public static void main(String[] args) {
        // TODO Auto-generated method stub
        String line = "1401566623.522    5730 59.120.225.171 TCP_MISS/200 590 GET http://www.imgclck.com/health_check.js? d0273611 DIRECT/23.23.179.160 text/javascript";
        System.out.println(line);
        
        String[] arr = line.split(" +");
        //String arr11 = arr.toString();
        //System.out.println("arr11:"+arr11);
        System.out.println("BBB:"+arr[0]);
        System.out.println("BBB:"+arr[1]);
        System.out.println("BBB:"+arr[2]);
        System.out.println("BBB:"+arr[4]);
        System.out.println(arr[0].replace(".", ""));
        System.out.println("CCC:"+arr[2].indexOf("59.120.225.171"));
        System.out.println(StringUtils.indexOf(arr[2], "59.120"));
        //System.out.println("BBB:"+bb);
        DateTime dt = new DateTime(Long.parseLong(arr[0].replace(".", "")));
        System.out.println(arr[1]);
        System.out.println(dt.toString("yyyy-MM-dd HH:mm:ss.mmm"));
    }

}
