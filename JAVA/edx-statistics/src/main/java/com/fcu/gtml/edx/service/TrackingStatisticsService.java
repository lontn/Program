package com.fcu.gtml.edx.service;

import java.util.Date;
import java.util.List;

import com.fcu.gtml.edx.domain.TrackingStatistics;
import com.fcu.gtml.edx.domain.TrackingStatisticsByMonth;
import com.fcu.gtml.edx.domain.TrackingStatisticsByPie;

public interface TrackingStatisticsService {

    public List<TrackingStatistics> listTrackingStatistics(String eventType, String startDate, String endDate);
    
    public List<TrackingStatisticsByPie> listTrackingStatisticsByPie(int year, String[] eventTypes);
    
    public List<TrackingStatisticsByMonth> listTrackingStatisticsByMonth(int year, String eventType, String startDate, String endDate);
}
