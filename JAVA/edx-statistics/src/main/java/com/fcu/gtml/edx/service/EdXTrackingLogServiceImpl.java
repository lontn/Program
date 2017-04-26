package com.fcu.gtml.edx.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fcu.gtml.edx.domain.EdXTrackingLog;
import com.fcu.gtml.edx.persistence.EdXTrackingLogMapper;

@Service
public class EdXTrackingLogServiceImpl implements EdXTrackingLogService {
    private static final Logger L = LogManager.getLogger();
    @Resource
    private EdXTrackingLogMapper edXTrackingLogMapper;

    @Override
    @Transactional(readOnly = true)
    public List<EdXTrackingLog> listEdXTrackingLog(String courseId, String datetimeString, String datetimeEnd,
            String[] roles, int pageIndex) {
        Map<String, Object> params = new HashMap<>();
        params.put(EdXTrackingLogMapper.BY_COURSEID, courseId);
        params.put(EdXTrackingLogMapper.BY_DATETIMESTART, datetimeString);
        params.put(EdXTrackingLogMapper.BY_DATETIMEEND, datetimeEnd);
        params.put(EdXTrackingLogMapper.BY_ROLES, roles);
        params.put(EdXTrackingLogMapper.BY_PAGEINDEX, pageIndex);
        List<EdXTrackingLog> list = edXTrackingLogMapper.listEdXTrackingLog(params);
        return list;
    }

    @Override
    @Transactional(readOnly = true)
    public int countEdXTrackingLog(String courseId, String datetimeString, String datetimeEnd, String[] roles) {
        Map<String, Object> params = new HashMap<>();
        params.put(EdXTrackingLogMapper.BY_COURSEID, courseId);
        params.put(EdXTrackingLogMapper.BY_DATETIMESTART, datetimeString);
        params.put(EdXTrackingLogMapper.BY_DATETIMEEND, datetimeEnd);
        params.put(EdXTrackingLogMapper.BY_ROLES, roles);
        return edXTrackingLogMapper.countEdXTrackingLog(params);
    }

}
