package com.fcu.gtml.service;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.dbcp.BasicDataSource;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fcu.gtml.edx.domain.UserInfo;

@Service
public class EdXAppService {
    private static final Logger L = LogManager.getLogger();
    @Resource
    private BasicDataSource basicDataSource;

    @Transactional(readOnly = true)
    public List<UserInfo> listAPPUser(String courseId) {
        String sql = " SELECT A.username, G.user_id FROM edxapp.certificates_generatedcertificate AS G " +
                     " INNER JOIN edxapp.auth_user AS A " +
                     " ON G.user_id = A.id " +
                     " where course_id = ? ";
        List<UserInfo> userInfos = new ArrayList<>();
        try (Connection conn = basicDataSource.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, courseId);
            try (ResultSet rs = ps.executeQuery();) {
                while (rs.next()) {
                    UserInfo user = new UserInfo();
                    user.setUserName(rs.getString("username"));
                    user.setUserId(rs.getInt("user_id"));
                    userInfos.add(user);
                }
            }
        } catch (Exception e) {
            L.error("listAPPUser:{}", e);
        }
        return userInfos;
    }

    @Transactional(readOnly = true)
    public List<UserInfo> listNoCertificateUser(String courseId) {
        String sql = " SELECT A.username, C.user_id FROM edxapp.student_courseenrollment AS C " +
                     " INNER JOIN edxapp.auth_user AS A " +
                     " ON C.user_id = A.id " +
                     " where C.course_Id = ? and C.is_active = 1 " +
                     " AND user_id not in ( " +
                     " SELECT G.user_id FROM edxapp.certificates_generatedcertificate AS G " +
                     " INNER JOIN edxapp.auth_user AS A " +
                     " ON G.user_id = A.id " +
                     " where course_id = ?)";
        List<UserInfo> userInfos = new ArrayList<>();
        try (Connection conn = basicDataSource.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, courseId);
            ps.setString(2, courseId);
            try (ResultSet rs = ps.executeQuery();) {
                while (rs.next()) {
                    UserInfo user = new UserInfo();
                    user.setUserName(rs.getString("username"));
                    user.setUserId(rs.getInt("user_id"));
                    userInfos.add(user);
                }
            }
        } catch (Exception e) {
            L.error("listNoCertificateUser:{}", e);
        }
        return userInfos;
    }
}
