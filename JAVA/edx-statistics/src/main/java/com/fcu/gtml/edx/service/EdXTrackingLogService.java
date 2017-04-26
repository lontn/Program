package com.fcu.gtml.edx.service;

import java.util.List;

import com.fcu.gtml.edx.domain.EdXTrackingLog;

public interface EdXTrackingLogService {
    /**
     * 撈TrackingLog
     * @param courseId
     * @param datetimeString
     * @param datetimeEmd
     * @param roles
     * @param pageIndex
     * @return
     */
    public List<EdXTrackingLog> listEdXTrackingLog(String courseId, String datetimeString, String datetimeEnd, String[] roles, int pageIndex);

    /**
     * Count
     * @param courseId
     * @param datetimeString
     * @param datetimeEnd
     * @param roles
     * @return
     */
    public int countEdXTrackingLog(String courseId, String datetimeString, String datetimeEnd, String[] roles);
}
