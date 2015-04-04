package com.fcu.gtml.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.junit.Test;

import com.fcu.gtml.SupportSpringTest;
import com.fcu.gtml.domain.UserVisitorAccess;

public class UserVisitorAccessServiceTest extends SupportSpringTest {

    @Resource
    private UserVisitorAccessService service;
    @Test
    public void batchInsert() {
        List<UserVisitorAccess> visitorList = new ArrayList<>();
        UserVisitorAccess visitor = new UserVisitorAccess();
        visitor.setResponseTime(33);
        visitor.setTimesTemp(new Date());
        visitor.setUri("www.google.com.tw");
        visitor.setClientIdentity("M0217973");
        visitorList.add(visitor);
        service.batchInsert(visitorList);
    }
}
