package com.fcu.gtml.service;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.dbcp.BasicDataSource;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;

import com.fcu.gtml.edx.domain.EventTypeSum;

@Service
public class EdXInfoService {
    private static final Logger L = LogManager.getLogger();
    @Resource
    private BasicDataSource basicDataSource;
    
    public List<EventTypeSum> listEventType(String userName, String courseId) {
        String sql = " SELECT EventType, count(EventType) AS CNT FROM EdXInfo.TrackingLog " +
                     " where UserName = ? AND courseId = ? " +
                     " group by EventType ";
        List<EventTypeSum> list = new ArrayList<>();
        try (Connection conn = basicDataSource.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, userName);
            ps.setString(2, courseId);
            try (ResultSet rs = ps.executeQuery();) {
                while (rs.next()) {
                    EventTypeSum type = new EventTypeSum();
                    type.setEventType(rs.getString("EventType"));
                    type.setCount(rs.getInt("CNT"));
                    list.add(type);
                }
            }
        } catch (Exception e) {
            L.error("listEventType:{}", e);
        }
        return list;
    }
}
