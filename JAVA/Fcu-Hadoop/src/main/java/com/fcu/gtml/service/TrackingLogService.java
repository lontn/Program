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

import com.fcu.gtml.domain.EnrollmentEventsLog;
import com.fcu.gtml.domain.NavigationEventsLog;
import com.fcu.gtml.domain.PDFDisplayScaled;
import com.fcu.gtml.domain.PDFOutlineToggled;
import com.fcu.gtml.domain.PDFPageScrolled;
import com.fcu.gtml.domain.PDFThumbnailNavigated;
import com.fcu.gtml.domain.PDFThumbnailsToggled;
import com.fcu.gtml.domain.TextBookByBook;
import com.fcu.gtml.domain.TrackingLog;
import com.fcu.gtml.domain.TrackingLogContext;
import com.fcu.gtml.domain.VideoByHideTranscript;
import com.fcu.gtml.domain.VideoByLoadVideo;
import com.fcu.gtml.domain.VideoByPauseVideo;
import com.fcu.gtml.domain.VideoByPlayVideo;
import com.fcu.gtml.domain.VideoBySeekVideo;
import com.fcu.gtml.domain.VideoByShowTranscript;
import com.fcu.gtml.domain.VideoBySpeedChangeVideo;
import com.fcu.gtml.domain.VideoByStopVideo;
import com.fcu.gtml.persistence.StudentLogMapper;
import com.fcu.gtml.persistence.TrackingLogMapper;

@Service
public class TrackingLogService {
    private static final Logger L = LoggerFactory.getLogger(TrackingLogService.class);
    @Resource(name = "sqlSessionFactory01")
    private SqlSessionFactory sqlSessionFactory;
    @Resource
    private TrackingLogMapper mapper;

    @Transactional(readOnly=true)
    public List<TrackingLog> listTrackingLogByEventType(String eventType) {
        L.info("EventType:{}", eventType);
        return mapper.listTrackingLogByEventType(eventType);
    }

    @Transactional
    public void batchInsertBySeekVideo(List<VideoBySeekVideo> svList) {
        L.info("VideoBySeekVideo Size:{}", svList.size());
        SqlSessionTemplate sqlSession = new SqlSessionTemplate(sqlSessionFactory, ExecutorType.BATCH);
        TrackingLogMapper mapper = sqlSession.getMapper(TrackingLogMapper.class);
        for (VideoBySeekVideo sv : svList) {
            try {
                L.info("Insert VideoBySeekVideo:{}", sv);
                mapper.batchInsertBySeekVideo(sv);
            } catch (Exception e) {
                L.error("VideoBySeekVideo warring:{}", sv);
                L.error("batchInsertBySeekVideo fail.", e);
            }
        }
    }

    @Transactional
    public void batchInsertByLoadVideo(List<VideoByLoadVideo> lvList) {
        L.info("VideoByLoadVideo Size:{}", lvList.size());
        SqlSessionTemplate sqlSession = new SqlSessionTemplate(sqlSessionFactory, ExecutorType.BATCH);
        TrackingLogMapper mapper = sqlSession.getMapper(TrackingLogMapper.class);
        for (VideoByLoadVideo lv : lvList) {
            try {
                L.info("Insert VideoByLoadVideo:{}", lv);
                mapper.batchInsertByLoadVideo(lv);
            } catch (Exception e) {
                L.error("VideoByLoadVideo warring:{}", lv);
                L.error("batchVideoByLoadVideo fail.", e);
            }
        }
    }

    @Transactional
    public void batchInsertByPlayVideo(List<VideoByPlayVideo> pvList) {
        L.info("VideoByPlayVideo Size:{}", pvList.size());
        SqlSessionTemplate sqlSession = new SqlSessionTemplate(sqlSessionFactory, ExecutorType.BATCH);
        TrackingLogMapper mapper = sqlSession.getMapper(TrackingLogMapper.class);
        for (VideoByPlayVideo pv : pvList) {
            try {
                L.info("Insert VideoByPlayVideo:{}", pv);
                mapper.batchInsertByPlayVideo(pv);
            } catch (Exception e) {
                L.error("VideoByPlayVideo warring:{}", pv);
                L.error("batchVideoByPlayVideo fail.", e);
            }
        }
    }

    @Transactional
    public <T> void batchInsertByEventType(List<T> tlist) {
        SqlSessionTemplate sqlSession = new SqlSessionTemplate(sqlSessionFactory, ExecutorType.BATCH);
        TrackingLogMapper mapper = sqlSession.getMapper(TrackingLogMapper.class);
        for (T t : tlist) {
            try {
                L.info("Insert " + t.getClass().getName() + ":{}", t);
                if (t.getClass().equals(VideoByPlayVideo.class)){
                    mapper.batchInsertByPlayVideo((VideoByPlayVideo) t);
                }
                if (t.getClass().equals(VideoByPauseVideo.class)) {
                    mapper.batchInsertByPauseVideo((VideoByPauseVideo) t);
                }
                if (t.getClass().equals(VideoByStopVideo.class)) {
                    mapper.batchInsertByStopVideo((VideoByStopVideo) t);
                }
                if (t.getClass().equals(VideoBySpeedChangeVideo.class)) {
                    mapper.batchInsertBySpeedChangeVideo((VideoBySpeedChangeVideo) t);
                }
                if (t.getClass().equals(VideoByHideTranscript.class)) {
                    mapper.batchInsertByHideTranscript((VideoByHideTranscript) t);
                }
                if (t.getClass().equals(VideoByShowTranscript.class)) {
                    mapper.batchInsertByShowTranscript((VideoByShowTranscript) t);
                }
                if (t.getClass().equals(EnrollmentEventsLog.class)) {
                    mapper.batchInsertByEnrollmentEventsLog((EnrollmentEventsLog) t);
                }
                if (t.getClass().equals(NavigationEventsLog.class)) {
                    mapper.batchInsertByNavigationEventsLog((NavigationEventsLog) t);
                }
                if (t.getClass().equals(TextBookByBook.class)) {
                    mapper.batchInsertByTextBookByBook((TextBookByBook) t);
                }
                if (t.getClass().equals(PDFThumbnailsToggled.class)) {
                    mapper.batchInsertByPDFThumbnailsToggled((PDFThumbnailsToggled) t);
                }
                if (t.getClass().equals(PDFThumbnailNavigated.class)) {
                    mapper.batchInsertByPDFThumbnailNavigated((PDFThumbnailNavigated) t);
                }
                if (t.getClass().equals(PDFPageScrolled.class)) {
                    mapper.batchInsertByPDFPageScrolled((PDFPageScrolled) t);
                }
                if (t.getClass().equals(PDFDisplayScaled.class)) {
                    mapper.batchInsertByPDFDisplayScaled((PDFDisplayScaled) t);
                }
                if (t.getClass().equals(PDFOutlineToggled.class)) {
                    mapper.batchInsertByPDFOutlineToggled((PDFOutlineToggled) t);
                }
            } catch (Exception e) {
                L.error("VideoBy" + t.getClass().getName() + "warring:{}", t);
                L.error("batch" + t.getClass().getName() + " fail.", e);
            }
        }
    }

    @Transactional
    public void batchInsert(List<TrackingLog> logList) {
        L.info("studentList Size:{}", logList.size());
        SqlSessionTemplate sqlSession = new SqlSessionTemplate(sqlSessionFactory, ExecutorType.BATCH);
        TrackingLogMapper mapper = sqlSession.getMapper(TrackingLogMapper.class);
        for (TrackingLog trackingLog : logList) {
            try {
                //L.info("trackingLog:{}", trackingLog);
                mapper.batchInsert(trackingLog);
            } catch (Exception e) {
                L.error("studentLog warring:{}", trackingLog);
                L.error("batchInsert fail.", e);
            }
        }
    }
    
    public void batchInsertBak(TrackingLog log) {
        mapper.batchInsertBak(log);
        int trackingLogBAKId = log.getId();
        L.info("trackingLogBAKId:{}", trackingLogBAKId);
        TrackingLogContext tLogContext = log.getTlogContext();
        tLogContext.setTrackingLogBAKId(trackingLogBAKId);
        mapper.batchInsertByTrackingLogContext(tLogContext);
    }
}
