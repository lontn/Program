package com.fcu.gtml.edx.service;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fcu.gtml.edx.domain.TrackingCourse;
import com.fcu.gtml.edx.persistence.TrackingCourseMapper;

@Service
public class TrackingCourseServiceImpl implements TrackingCourseService {
    @Resource
    private TrackingCourseMapper trackingCourseMapper;

    @Override
    @Transactional(readOnly = true)
    public List<TrackingCourse> listTrackingCourse() {
        return trackingCourseMapper.listTrackingCourse();
    }

    
}
