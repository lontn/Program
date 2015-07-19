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

import com.fcu.gtml.domain.StudentLog;
import com.fcu.gtml.persistence.StudentLogMapper;

@Service
public class StudentLogService {
    private static final Logger L = LoggerFactory.getLogger(StudentLogService.class);
    @Resource(name = "sqlSessionFactory01")
    private SqlSessionFactory sqlSessionFactory;
    @Resource
    private StudentLogMapper mapper;
    
    @Transactional(readOnly=true)
    public List<StudentLog> listStudentLogByEventType(String eventType) {
        return mapper.listStudentLogByEventType(eventType);
    }

    @Transactional
    @SuppressWarnings("resource")
    public void batchInsert(List<StudentLog> studentList) {
        L.info("studentList Size:{}", studentList.size());
        SqlSessionTemplate sqlSession = new SqlSessionTemplate(sqlSessionFactory, ExecutorType.BATCH);
        StudentLogMapper mapper = sqlSession.getMapper(StudentLogMapper.class);
        for (StudentLog studentLog : studentList) {
            try {
                mapper.batchInsert(studentLog);
            } catch (Exception e) {
                L.error("studentLog warring:{}", studentLog);
                L.error("batchInsert fail.", e);
            }
        }
    }
}
