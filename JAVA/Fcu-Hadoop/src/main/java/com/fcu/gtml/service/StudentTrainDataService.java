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
import com.fcu.gtml.domain.StudentPlayVideoTrainData;
import com.fcu.gtml.domain.StudentVideoPlayVideo;
import com.fcu.gtml.persistence.StudentTrainDataMapper;

@Service
public class StudentTrainDataService {
    private static final Logger L = LoggerFactory.getLogger(StudentTrainDataService.class);
    @Resource
    private StudentTrainDataMapper mapper;
    @Resource(name = "sqlSessionFactory01")
    private SqlSessionFactory sqlSessionFactory;
    
    @Transactional(readOnly=true)
    public List<StudentVideoPlayVideo> listStudentPlayVideoUser() {
        return mapper.listStudentPlayVideoUser();
    }
    
    @Transactional(readOnly=true)
    public List<StudentVideoPlayVideo> listStudentPlayVideoByKey(String userName, String code) {
        return mapper.listStudentPlayVideoByKey(userName, code);
    }
    
    @Transactional(readOnly=true)
    public int getOpenResponseCount(String userName) {
        return mapper.getOpenResponseCount(userName);
    }

    @Transactional(readOnly=true)
    public List<StudentPlayVideoTrainData> listStudentPlayVideoTrainData() {
        return mapper.listStudentPlayVideoTrainData();
    }
    @Transactional
    public void batchInsert(List<StudentPlayVideoTrainData> studentList) {
        SqlSessionTemplate sqlSession = new SqlSessionTemplate(sqlSessionFactory, ExecutorType.BATCH);
        StudentTrainDataMapper mapper = sqlSession.getMapper(StudentTrainDataMapper.class);
        for (StudentPlayVideoTrainData data : studentList) {
            try {
                mapper.batchInsertByPlayVideoTrainData(data);
            } catch (Exception e) {
                L.error("studentLog warring:{}", data);
                L.error("batchInsert fail.", e);
            }
        }
    }
}
