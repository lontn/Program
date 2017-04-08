package com.fcu.gtml.edx.persistence;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.fcu.gtml.edx.domain.TrackingStatistics;
import com.fcu.gtml.edx.domain.TrackingStatisticsByMonth;
import com.fcu.gtml.edx.domain.TrackingStatisticsByPie;

public interface TrackingStatisticsMapper {
    public static final String BY_EVENTTYPES = "eventTypes";
    public static final String BY_EVENTTYPE = "eventType";
    public static final String BY_STARTDATE = "startDate";
    public static final String BY_ENDDATE = "endDate";
    public static final String BY_YEAR = "year";

    public List<TrackingStatistics> listTrackingStatistics(Map<String, Object> params);

    public List<TrackingStatisticsByPie> listTrackingStatisticsByPie(Map<String, Object> params);

    public List<TrackingStatisticsByMonth> listTrackingStatisticsByMonth(Map<String, Object> params);
}
