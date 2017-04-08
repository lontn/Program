package com.fcu.gtml.edx.domain;

import java.util.ArrayList;
import java.util.List;

public class TrackingStatisticsByPie extends Entity {

    private static final long serialVersionUID = 1L;
    private String evenType;
    private int year;
    private int quantity;
    private List<ComBineDataByPie> combineData = new ArrayList<>();

    public String getEvenType() {
        return evenType;
    }
    public void setEvenType(String evenType) {
        this.evenType = evenType;
    }
    public int getYear() {
        return year;
    }
    public void setYear(int year) {
        this.year = year;
    }
    public int getQuantity() {
        return quantity;
    }
    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public List<ComBineDataByPie> getCombineData() {
        return combineData;
    }

    public void setCombineData(ComBineDataByPie combineData) {
        this.combineData.add(combineData);
    }

    public static class ComBineDataByPie extends Entity {
        private static final long serialVersionUID = 1L;
        private String name;
        private int y;

        public ComBineDataByPie(String name, int y) {
            this.name = name;
            this.y = y;
        }

        public String getName() {
            return name;
        }
        public void setName(String name) {
            this.name = name;
        }
        public int getY() {
            return y;
        }
        public void setY(int y) {
            this.y = y;
        }
    }
}
