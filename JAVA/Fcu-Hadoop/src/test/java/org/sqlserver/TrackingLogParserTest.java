package org.sqlserver;

import static com.google.common.io.Resources.getResource;

import java.io.IOException;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;

import com.google.common.io.Resources;

import org.common.utils.JsonParser;
import org.common.utils.ParseException;
import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.junit.Test;
import org.mortbay.util.ajax.JSON;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fcu.gtml.SupportSpringTest;
import com.fcu.gtml.domain.TrackingLog;
import com.fcu.gtml.domain.VideoByLoadVideo;
import com.fcu.gtml.domain.VideoByPlayVideo;
import com.fcu.gtml.domain.VideoBySeekVideo;
import com.fcu.gtml.service.TrackingLogService;

public class TrackingLogParserTest extends SupportSpringTest {
    //private static final Logger L = LogManager.getLogger();
    private static final Logger L = LoggerFactory.getLogger(TrackingLogParserTest.class);
    @Resource
    private TrackingLogService service;

    @Test
    public void test() throws IOException, ParseException {
        URL url = getResource(TrackingLogParserTest.class, "trackingByLoadVideo.json");
        String json = Resources.toString(url, Charset.forName("UTF-8"));
        System.out.println(json);
        TrackingLog tl = JsonParser.parse(json, TrackingLog.class);
        //TrackingLog.Context tt = tl.getContext();
        System.out.println("tl:"+tl);
        //System.out.println("tt:"+tt);
        List<TrackingLog> list = new ArrayList<>();
        list.add(tl);
        //L.info("service:{}", service);
        //service.batchInsert(list);
        L.info("tl getEvent:{}", tl.getEvent());
        //L.info("event:{}", tl.getEventObj());
        //L.info("event:{}", tl.getTime());
    }

    @Test
    public void testJsonNode() throws IOException, ParseException {
        URL url = getResource(TrackingLogParserTest.class, "trackingByLoadVideo.json");
        String json = Resources.toString(url, Charset.forName("UTF-8"));
        L.info("json:{}", json);
        JsonNode jnode = JsonParser.parse(json);
        L.info("event1111:{}", jnode.path("context").toString());
        String userName = jnode.path("username").asText();
        String eventType = jnode.path("event_type").asText();
        L.info("eventType:{}", eventType);
        String ip = jnode.path("ip").asText();
        String agent = jnode.path("agent").asText();
        String host = jnode.path("host").asText();
        String session = jnode.path("session").asText();
        String event = jnode.path("event").toString();
        String eventSource = jnode.path("event_source").asText();
        String context = jnode.path("context").toString();
        String time = jnode.path("time").asText();
        String page = jnode.path("page").asText() == "null" ? null : jnode.path("page").asText();
        if (page == null) {
            System.out.println("AAAA");
        }
        L.info("jnode:{}", jnode);
        Iterator<String> list = jnode.fieldNames();
        while(list.hasNext()){
            System.out.println("Key:" + list.next());
        }
        System.out.println("findPath:" + jnode.findPath("username"));
        System.out.println("event:" + (Object)jnode.path("event"));
        JsonNode eventNode = JsonParser.parse(event);
        System.out.println("eventNode:" + eventNode.path("code"));
        for (JsonNode jsonNode : eventNode) {
            System.out.println("jsonNode:" + jsonNode);
        }
        
        TrackingLog tl = JsonParser.parse(jnode, TrackingLog.class);
        System.out.println("tl:"+tl);
        TrackingLog tlog = new TrackingLog();
        tlog.setUserName(userName);
        tlog.setEventType(eventType);
        tlog.setIp(ip);
        tlog.setAgent(agent);
        tlog.setHost(host);
        tlog.setSession(session);
        tlog.setEvent(event);
        tlog.setEventSource(eventSource);
        tlog.setContext(context);
        tlog.setTime(time);
        tlog.setPage(page);
        System.out.println("tlog:"+tlog);
    }

    @Test
    public void testJsonNode2() throws IOException, ParseException {
        URL url = getResource(TrackingLogParserTest.class, "tracking.json");
        String json = Resources.toString(url, Charset.forName("UTF-8"));
        L.info("json:{}", json);
        JsonNode jnode = JsonParser.parse(json);
        L.info("event1111:{}", jnode.path("context").toString());
        String eventType = jnode.path("event_type").asText();
        L.info("eventType:{}", eventType);
        L.info("jnode:{}", jnode);
        Iterator<String> list = jnode.fieldNames();
        while(list.hasNext()){
            System.out.println("Key:" + list.next());
        }
        System.out.println("findPath:" + jnode.findPath("session"));
        TrackingLog tl = JsonParser.parse(jnode, TrackingLog.class);
        System.out.println("tl:"+tl);
    }

    @Test
    public void testHashMap() throws IOException, ParseException {
        URL url = getResource(TrackingLogParserTest.class, "tracking.json");
        String json = Resources.toString(url, Charset.forName("UTF-8"));
        L.info("json:{}", json);
        Map<String, Object> mapJson = JsonParser.parse(json, new TypeReference<HashMap<String, Object>>(){ });
        System.out.println("context:"+mapJson.get("event"));
        Set<String> keySet = mapJson.keySet();
        for (String key : keySet) {
            System.out.println("KEY:" + key);
        }
    }

    @Test
    public void testVideoBySeekVideo() throws IOException, ParseException {
        URL url = getResource(TrackingLogParserTest.class, "tracking.json");
        String json = Resources.toString(url, Charset.forName("UTF-8"));
        System.out.println(json);
        TrackingLog tl = JsonParser.parse(json, TrackingLog.class);
        System.out.println("tl:"+tl);
        L.info("tl:{}", tl.getEvent());
        String event = tl.getEvent();
        VideoBySeekVideo sv = JsonParser.parse(event, VideoBySeekVideo.class);
        L.info("VideoBySeekVideo:{}", sv);
    }

    @Test
    public void testLoadVideo() throws IOException, ParseException {
        URL url = getResource(TrackingLogParserTest.class, "trackingByLoadVideo.json");
        String json = Resources.toString(url, Charset.forName("UTF-8"));
        System.out.println("json:" + json);
        TrackingLog tl = JsonParser.parse(json, TrackingLog.class);
        System.out.println("tl:"+tl);
        L.info("tl:{}", tl.getEvent());
        L.info("UserId:{}", tl.getUserId());
        String event = tl.getEvent();
        VideoByLoadVideo lv = JsonParser.parse(event, VideoByLoadVideo.class);
        L.info("VideoByLoadVideo:{}", lv);
        String context = tl.getContext();
        System.out.println("context:"+context);
        String[] array = context.replace("{", "").replace("}", "").split(", ");
        L.info("Context:{}", array[0]);
        String[] newArray = new String[1];
        for (String word : array) {
            if (word.contains("user_id")) {
                L.info("word:{}", word);
                newArray[0] = word;
            }
        }
        L.info("newArray:{}", newArray[0]);
        //Context ct = JsonParser.parse(context, Context.class);
        //TypeReference<HashMap<String,Object>> typeRef = new TypeReference<HashMap<String,Object>>() {};
        //Map<String, Object> ct = JsonParser.parse(context, typeRef);
        //L.info("Context:{}", ct);
    }

    @Test
    public void testlist() {
        List<TrackingLog> list = service.listTrackingLogByEventType("seek_video");
        L.info("list:{}", list.size());
    }

    @Test
    public void test2() {
        DateTime dateTime_Utc = new DateTime( "2014-09-12T06:59:20.049365+00:00", DateTimeZone.UTC);
        System.out.println(dateTime_Utc.toDate());
        L.info("compare:{}", compare(VideoByLoadVideo.class));
    }

    @Test
    public void dateTest() throws ParseException {
        DateTime dateTime = new DateTime();
        int monthDay = dateTime.dayOfMonth().getMaximumValue();
        L.info("monthDay:{}", monthDay);
//        List<TrackingLog> listLogs = service.listTrackingLogByEventType("load_video");
//        for (TrackingLog tLog : listLogs) {
//            System.out.println("TrackingLog:"+tLog);
//            String event = tLog.getEvent();
//            System.out.println("event:"+event);
//            System.out.println("tLog:"+JSON.parse(event));
//            int tLogId = tLog.getId();
//            int userId = tLog.getUserId();
//            VideoByLoadVideo lv = JsonParser.parse(JSON.parse(event).toString(), VideoByLoadVideo.class);
//            L.info("lv:{}", lv);
//        }
    }

    @Test
    public void parserTest() throws ParseException{
        String json = "{\"id\":\"i4x-ORG9784-C7351-video-5b2074c3beb84b7993f6f2dc6f402052\",\"code\":\"t3pp-MQkgug\"}";
        //JsonNode jsonNode = JsonParser.parse(json);
        VideoByLoadVideo lv = JsonParser.parse(json, VideoByLoadVideo.class);
        L.info("lv:{}", lv);
    }

    private <T> boolean compare(Class<T> clazz) {
        boolean flag = false;
        if (clazz.equals(VideoByPlayVideo.class)) {
            flag = true;
        }
        return flag;
    }
}
