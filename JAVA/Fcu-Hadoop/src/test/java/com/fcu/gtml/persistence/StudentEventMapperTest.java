package com.fcu.gtml.persistence;

import java.util.Date;

import javax.annotation.Resource;

import org.junit.Test;

import com.fcu.gtml.SupportSpringTest;
import com.fcu.gtml.domain.StudentEnrollment;

public class StudentEventMapperTest extends SupportSpringTest {

    @Resource
    private StudentEventMapper mapper;

    @Test
    public void StudentEnrollment() {
        StudentEnrollment se = new StudentEnrollment();
        se.setUserName("1");
        se.setEventType("1");
        se.setUserId(1);
        se.setMode("1");
        se.setTime(new Date());
        mapper.batchInsert(se);
    }
}
