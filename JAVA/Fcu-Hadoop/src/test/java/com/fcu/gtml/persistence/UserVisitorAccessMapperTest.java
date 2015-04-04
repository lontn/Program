package com.fcu.gtml.persistence;

import java.util.Date;

import javax.annotation.Resource;

import org.junit.Test;

import com.fcu.gtml.SupportSpringTest;
import com.fcu.gtml.domain.UserVisitorAccess;

public class UserVisitorAccessMapperTest extends SupportSpringTest {

    @Resource
    private UserVisitorAccessMapper mapper;
    @Test
    public void batchInster(){
        UserVisitorAccess visitor = new UserVisitorAccess();
        visitor.setResponseTime(20);
        visitor.setTimesTemp(new Date());
        visitor.setUri("www.google.com.tw");
        visitor.setClientIdentity("M0218235");
        mapper.batchInsert(visitor);
    }
}
