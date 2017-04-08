package com.fcu.gtml.edx.domain;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class TrackingStatistics extends Entity {
    private static final long serialVersionUID = 1L;
    private String evenType;
    private Date time;
    private int quantity;

    public String getEvenType() {
        return evenType;
    }
    public void setEvenType(String evenType) {
        this.evenType = evenType;
    }
    public Date getTime() {
        return time;
    }
    public void setTime(Date time) {
        this.time = time;
    }
    public int getQuantity() {
        return quantity;
    }
    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public static class TrackingLoadVideo extends Entity {
        private static final long serialVersionUID = 1L;
        private String eventType;
        private List<Integer> sums = new ArrayList<>();
        private List<String> dates = new ArrayList<>();
        
        public TrackingLoadVideo(String eventType, List<Integer> sums,
                List<String> dates) {
            this.eventType = eventType;
            this.sums = sums;
            this.dates = dates;
        }

        public String getEventType() {
            return eventType;
        }

        public void setEventType(String eventType) {
            this.eventType = eventType;
        }

        public List<Integer> getSums() {
            return sums;
        }

        public void setSums(List<Integer> sums) {
            this.sums = sums;
        }

        public List<String> getDates() {
            return dates;
        }

        public void setDates(List<String> dates) {
            this.dates = dates;
        }

    }
}
