package com.fcu.gtml.edx.domain;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class EventTypeSum extends TheData {
    private String eventType;
    private int count;
    public String getEventType() {
        return eventType;
    }
    public void setEventType(String eventType) {
        this.eventType = eventType;
    }
    public int getCount() {
        return count;
    }
    public void setCount(int count) {
        this.count = count;
    }

    /**
     * 轉成Map Key:UserName, Value:UserId
     * @param listEventType
     * @return
     */
    public static Map<String, Integer> EventTypeToMap(List<EventTypeSum> listEventType) {
        Map<String, Integer> map = new HashMap<>();
        for (EventTypeSum eventType : listEventType) {
            map.put(eventType.getEventType(), eventType.getCount());
        }
        
        return map;
    }
}
