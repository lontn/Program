package com.fcu.gtml.edx.persistence;

import java.util.List;
import java.util.Map;

import com.fcu.gtml.edx.domain.EdXTrackingLog;

public interface EdXTrackingLogMapper {
    public static final String BY_COURSEID = "courseId";
    public static final String BY_DATETIMESTART = "datetimeStart";
    public static final String BY_DATETIMEEND = "datetimeEnd";
    public static final String BY_ROLES = "roles";
    public static final String BY_PAGEINDEX = "pageIndex";

    public List<EdXTrackingLog> listEdXTrackingLog(Map<String, Object> params);

    public int countEdXTrackingLog(Map<String, Object> params);
}
