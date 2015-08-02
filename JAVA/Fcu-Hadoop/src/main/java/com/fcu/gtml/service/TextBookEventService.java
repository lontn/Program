package com.fcu.gtml.service;

import java.util.List;

import javax.annotation.Resource;

import org.apache.ibatis.session.ExecutorType;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionTemplate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.fcu.gtml.domain.StudentTextBook;
import com.fcu.gtml.persistence.StudentTextBookEventMapper;

@Service
public class TextBookEventService {
    private static final Logger L = LoggerFactory.getLogger(TextBookEventService.class);
    @Resource(name = "sqlSessionFactory01")
    private SqlSessionFactory sqlSessionFactory;

    /**
     * Student TextBook Event textbook
     * @param textbooks
     */
    public void batchInsertByBook(List<StudentTextBook> textbooks){
        L.info("textbook Size:{}", textbooks.size());
        for (StudentTextBook text : textbooks) {
            try{
                doInsert().batchInsertByBook(text);
            } catch (Exception e) {
                L.error("batchInsertByBook warring:{}", text);
                L.error("batchInsert fail.", e);
            }
        }
    }
    
    private StudentTextBookEventMapper doInsert() {
        SqlSessionTemplate sqlSession = new SqlSessionTemplate(sqlSessionFactory, ExecutorType.BATCH);
        StudentTextBookEventMapper mapper = sqlSession.getMapper(StudentTextBookEventMapper.class);
        return mapper;
    }
}
