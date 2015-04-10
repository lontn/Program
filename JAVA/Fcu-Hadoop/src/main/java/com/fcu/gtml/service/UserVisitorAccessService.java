package com.fcu.gtml.service;

import java.util.List;

import javax.annotation.Resource;

import org.apache.ibatis.session.ExecutorType;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionTemplate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fcu.gtml.domain.UserVisitorAccess;
import com.fcu.gtml.persistence.UserVisitorAccessMapper;

@Service
public class UserVisitorAccessService {
    private static final Logger L = LoggerFactory.getLogger(UserVisitorAccessService.class);
    
    @Resource(name = "sqlSessionFactory01")
    private SqlSessionFactory sqlSessionFactory;
    
    @SuppressWarnings("resource")
    @Transactional
    public void batchInsert(List<UserVisitorAccess> visitorList) {
        L.info("visitorList Size:{}", visitorList.size());
        SqlSessionTemplate sqlSession = new SqlSessionTemplate(sqlSessionFactory, ExecutorType.BATCH);
        UserVisitorAccessMapper mapper = sqlSession.getMapper(UserVisitorAccessMapper.class);
        for (UserVisitorAccess visitor : visitorList) {
            try {
                mapper.batchInsert(visitor);
            } catch (Exception e) {
                L.error("visitor warring:{}", visitor);
                L.error("batchInsert fail.", e);
            }
        }
    }
}
