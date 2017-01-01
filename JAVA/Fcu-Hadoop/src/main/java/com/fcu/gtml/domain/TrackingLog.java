package com.fcu.gtml.domain;

import java.util.Date;

import org.apache.commons.lang3.StringUtils;
import org.common.utils.JsonParser;
import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonSetter;

@JsonIgnoreProperties(ignoreUnknown = true)
public class TrackingLog extends TheData {
    //private DateTimeFormatter dtf = DateTimeFormat.forPattern("YYYY-MM-DD'T'hh:mm:ss.sTZD");
    private DateTimeFormatter dtf = DateTimeFormat.forPattern("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
    private DateTimeFormatter dtf2 = DateTimeFormat.forPattern("yyyy-MM-dd HH:mm:ss.SSS");
    private int id;
    @JsonProperty("username")
    private String userName;
    @JsonProperty("event_type")
    private String eventType;
    private String ip;
    private String agent;
    private String host;
    private String session;
    @JsonIgnore
    private Object event;
    //private String event;
    @JsonProperty("event_source")
    private String eventSource;
    @JsonIgnore
    private Object context;
    private String time;
    private String page;
    private int userId;
    private TrackingLogContext tlogContext;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public String getSession() {
        return session;
    }

    public void setSession(String session) {
        this.session = session;
    }

    public String getEvent() {
        return event.toString();
    }
    
//    public String getEvent() {
//        return event;
//    }

    @JsonSetter("event")
    public void setEvent(Object event) {
        this.event = event;
    }

//    public void setEvent(String event) {
//        this.event = event;
//    }

    public String getEventSource() {
        return eventSource;
    }

    public void setEventSource(String eventSource) {
        this.eventSource = eventSource;
    }

//    public Context getContext() {
//        return context;
//    }
//
//    public void setContext(Context context) {
//        this.context = context;
//    }

    public String getContext() {
        return context.toString();
    }

    @JsonSetter("context")
    public void setContext(Object context) {
        this.context = context;
    }

    public String getPage() {
        return page;
    }

    public void setPage(String page) {
        this.page = page;
    }

    public Date getTime() {
        //2014-09-12T06:59:20.049365+00:00
//        System.out.println("time-----------"+time);
        if (time.contains("T")) {
            return new DateTime(time, DateTimeZone.UTC).toDate();
            //.toString("yyyy-MM-dd HH:mm:ss.SSS");
        }
        //System.out.println("time::" + DateTime.parse(time, dtf).toDate());
        return DateTime.parse(time, dtf2).toDate();
        //return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
    

//    @JsonProperty("time")
//    public String getNowTime() {
//        return new DateTime(time).toString("yyyy-MM-dd HH:mm:ss.SSS");
//    }
//    public Event getEventObj() {
//        Event jNodes = null;
////        System.out.println("this.event:"+StringUtils.isNotEmpty(this.event));
//        try {
//            if (StringUtils.isNotEmpty(this.event)) {
////                System.out.println("this.event1111:"+event);
//                jNodes = JsonParser.parse(this.event, Event.class);
//            }
//        } catch (Exception e) {
//            throw new RuntimeException("parser error", e);
//        }
//        return jNodes;
//    }

    public int getUserId() {
//        String[] array = this.getContext().replace("{", "").replace("}", "").split(", ");
//        String user = "";
//        for (String word : array) {
//            if (word.contains("user_id")) {
//                if (word.split("=").length == 2) {
//                    user = word.split("=")[1];
//                } else {
//                    user = "";
//                }
//                userId = (user == null || user.equals("")) ? 0 : Integer.parseInt(user);
//            }
//        }
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public TrackingLogContext getTlogContext() {
        return tlogContext;
    }

    public void setTlogContext(TrackingLogContext tlogContext) {
        this.tlogContext = tlogContext;
    }



    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class Event extends TheData {
        private String id;
        private String code;

        public String getId() {
            return id;
        }
        public void setId(String id) {
            this.id = id;
        }
        public String getCode() {
            return code;
        }
        public void setCode(String code) {
            this.code = code;
        }
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class Context extends TheData {
        @JsonProperty("username")
        private String userName;
        @JsonProperty("user_id")
        private int userId;
        private String ip;
        @JsonProperty("org_id")
        private String orgId;
        private String agent;
        private String host;
        private String session;
        @JsonProperty("course_id")
        private String courseId;
        private String path;
        public String getUserName() {
            return userName;
        }
        public void setUserName(String userName) {
            this.userName = userName;
        }
        public int getUserId() {
            return userId;
        }
        public void setUserId(int userId) {
            this.userId = userId;
        }
        public String getIp() {
            return ip;
        }
        public void setIp(String ip) {
            this.ip = ip;
        }
        public String getOrgId() {
            return orgId;
        }
        public void setOrgId(String orgId) {
            this.orgId = orgId;
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
        public String getSession() {
            return session;
        }
        public void setSession(String session) {
            this.session = session;
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

//    @JsonIgnoreProperties(ignoreUnknown = true)
//    public static class VideoByPlayVideo extends TheData {
//        private int trackingLogId;
//        private String id;
//        private double currentTime;
//        private String code;
//        public int getTrackingLogId() {
//            return TrackingLog.class.id;
//        }
//        public void setTrackingLogId(int trackingLogId) {
//            this.trackingLogId = trackingLogId;
//        }
//        public String getId() {
//            return id;
//        }
//        public void setId(String id) {
//            this.id = id;
//        }
//        public double getCurrentTime() {
//            return currentTime;
//        }
//        public void setCurrentTime(double currentTime) {
//            this.currentTime = currentTime;
//        }
//        public String getCode() {
//            return code;
//        }
//        public void setCode(String code) {
//            this.code = code;
//        }
//
//    }
}
