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

import com.fcu.gtml.domain.StudentVideoHideTranscript;
import com.fcu.gtml.domain.StudentVideoLoadVideo;
import com.fcu.gtml.domain.StudentVideoPauseVideo;
import com.fcu.gtml.domain.StudentVideoPlayVideo;
import com.fcu.gtml.domain.StudentVideoSeekVideo;
import com.fcu.gtml.domain.StudentVideoShowTranscript;
import com.fcu.gtml.domain.StudentVideoSpeedChangeVideo;
import com.fcu.gtml.domain.StudentVideoStopVideo;
import com.fcu.gtml.persistence.StudentEventMapper;
import com.fcu.gtml.persistence.StudentVideoEventMapper;

@Service
public class StudentEventService {
    private static final Logger L = LoggerFactory.getLogger(StudentEventService.class);
    @Resource(name = "sqlSessionFactory01")
    private SqlSessionFactory sqlSessionFactory;
    
    @SuppressWarnings("resource")
    public <T> void batchInsert(List<T> student) {
        L.info("student Size:{}", student.size());
        SqlSessionTemplate sqlSession = new SqlSessionTemplate(sqlSessionFactory, ExecutorType.BATCH);
        StudentEventMapper mapper = sqlSession.getMapper(StudentEventMapper.class);
        for (T t : student) {
            try {
                mapper.batchInsert(t);
            } catch (Exception e) {
                L.error("studentLog warring:{}", t);
                L.error("batchInsert fail.", e);
            }
        }
    }
    
    /**
     * Student Event Hide Transcript
     * @param student
     */
    @Transactional
    @SuppressWarnings("resource")
    public void batchInsertByHideTranscript(List<StudentVideoHideTranscript> student) {
        L.info("student Size:{}", student.size());
        for (StudentVideoHideTranscript st : student) {
            try {
                doInsert().batchInsertByHideTranscript(st);
            } catch (Exception e) {
                L.error("studentLog warring:{}", st);
                L.error("batchInsert fail.", e);
            }
        }
    }
    
    /**
     * Student Event Load Video
     * @param student
     */
    @Transactional
    public void batchInsertByLoadVideo(List<StudentVideoLoadVideo> student){
        L.info("student Size:{}", student.size());
        for (StudentVideoLoadVideo sv : student) {
            try{
                doInsert().batchInsertByLoadVideo(sv);
            } catch (Exception e) {
                L.error("studentLog warring:{}", sv);
                L.error("batchInsert fail.", e);
            }
        }
    }
    
    /**
     * Student Event Load Pause Video
     * @param student
     */
    @Transactional
    public void batchInsertByPauseVideo(List<StudentVideoPauseVideo> student) {
        L.info("student Size:{}", student.size());
        for (StudentVideoPauseVideo sp : student) {
            try{
                doInsert().batchInsertByPauseVideo(sp);
            } catch (Exception e) {
                L.error("studentLog warring:{}", sp);
                L.error("batchInsert fail.", e);
            }
        }
    }
    
    /**
     * Student Event Load Play Video
     * @param student
     */
    @Transactional
    public void batchInsertByPlayVideo(List<StudentVideoPlayVideo> student) {
        L.info("student Size:{}", student.size());
        for (StudentVideoPlayVideo sp : student) {
            try{
                doInsert().batchInsertByPlayVideo(sp);
            } catch (Exception e) {
                L.error("StudentVideoPlayVideo warring:{}", sp);
                L.error("batchInsert fail.", e);
            }
        }
    }
    
    /**
     * Student Event Load Play Video
     * @param student
     */
    @Transactional
    public void batchInsertByShowTranscript(List<StudentVideoShowTranscript> student) {
        L.info("student Size:{}", student.size());
        for (StudentVideoShowTranscript sp : student) {
            try{
                doInsert().batchInsertByShowTranscript(sp);
            } catch (Exception e) {
                L.error("StudentVideoShowTranscript warring:{}", sp);
                L.error("batchInsert fail.", e);
            }
        }
    }
    
    /**
     * Student Event Load Play Video
     * @param student
     */
    @Transactional
    public void batchInsertBySeekVideo(List<StudentVideoSeekVideo> student) {
        L.info("student Size:{}", student.size());
        for (StudentVideoSeekVideo sp : student) {
            try{
                doInsert().batchInsertBySeekVideo(sp);
            } catch (Exception e) {
                L.error("StudentVideoSeekVideo warring:{}", sp);
                L.error("batchInsert fail.", e);
            }
        }
    }
    
    /**
     * Student Event Speed Change Video
     * @param student
     */
    @Transactional
    public void batchInsertBySpeedChangeVideo(List<StudentVideoSpeedChangeVideo> student) {
        L.info("student Size:{}", student.size());
        for (StudentVideoSpeedChangeVideo sv : student) {
            try{
                doInsert().batchInsertBySpeedChangeVideo(sv);
            } catch (Exception e) {
                L.error("StudentVideoSpeedChangeVideo warring:{}", sv);
                L.error("batchInsert fail.", e);
            }
        }
    }
    
    /**
     * Student Event Speed Change Video
     * @param student
     */
    @Transactional
    public void batchInsertByStopVideo(List<StudentVideoStopVideo> student) {
        L.info("student Size:{}", student.size());
        for (StudentVideoStopVideo sv : student) {
            try{
                doInsert().batchInsertByStopVideo(sv);
            } catch (Exception e) {
                L.error("StudentVideoSpeedChangeVideo warring:{}", sv);
                L.error("batchInsert fail.", e);
            }
        }
    }
    
    private StudentVideoEventMapper doInsert() {
        SqlSessionTemplate sqlSession = new SqlSessionTemplate(sqlSessionFactory, ExecutorType.BATCH);
        StudentVideoEventMapper mapper = sqlSession.getMapper(StudentVideoEventMapper.class);
        return mapper;
    }
}
