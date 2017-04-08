package com.fcu.gtml.edx.service;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fcu.gtml.edx.domain.TrackingStatistics;
import com.fcu.gtml.edx.domain.TrackingStatisticsByMonth;
import com.fcu.gtml.edx.domain.TrackingStatisticsByPie;
import com.fcu.gtml.edx.persistence.TrackingStatisticsMapper;

@Service
public class TrackingStatisticsServiceImpl implements TrackingStatisticsService {
    @Resource
    private TrackingStatisticsMapper mapper;

    @Override
    @Transactional(readOnly = true)
    public List<TrackingStatistics> listTrackingStatistics(String eventType, String startDate, String endDate) {
        Map<String, Object> params = new HashMap<>();
        params.put(TrackingStatisticsMapper.BY_EVENTTYPE, eventType);
        params.put(TrackingStatisticsMapper.BY_STARTDATE, startDate);
        params.put(TrackingStatisticsMapper.BY_ENDDATE, endDate);
        List<TrackingStatistics> lists = mapper.listTrackingStatistics(params);
        return lists;
    }

    @Override
    @Transactional(readOnly = true)
    public List<TrackingStatisticsByPie> listTrackingStatisticsByPie(int year, String[] eventTypes) {
        Map<String, Object> params = new HashMap<>();
        params.put(TrackingStatisticsMapper.BY_YEAR, year);
        params.put(TrackingStatisticsMapper.BY_EVENTTYPES, eventTypes);
        List<TrackingStatisticsByPie> lists = mapper.listTrackingStatisticsByPie(params);
        return lists;
    }

    @Override
    @Transactional(readOnly = true)
    public List<TrackingStatisticsByMonth> listTrackingStatisticsByMonth(int year, String eventType, String startDate, String endDate) {
        Map<String, Object> params = new HashMap<>();
        params.put(TrackingStatisticsMapper.BY_YEAR, year);
        params.put(TrackingStatisticsMapper.BY_EVENTTYPE, eventType);
        params.put(TrackingStatisticsMapper.BY_STARTDATE, startDate);
        params.put(TrackingStatisticsMapper.BY_ENDDATE, endDate);
        List<TrackingStatisticsByMonth> lists = mapper.listTrackingStatisticsByMonth(params);
        return lists;
    }
}
