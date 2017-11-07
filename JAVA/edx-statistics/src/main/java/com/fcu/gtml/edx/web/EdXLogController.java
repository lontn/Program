package com.fcu.gtml.edx.web;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fcu.gtml.edx.domain.EdXTrackingLog;
import com.fcu.gtml.edx.domain.TrackingCourse;
import com.fcu.gtml.edx.service.EdXTrackingLogService;
import com.fcu.gtml.edx.service.TrackingCourseService;

@Controller
@RequestMapping(value="/edxLog")
public class EdXLogController {
    private static final Logger L = LogManager.getLogger();
    @Resource
    private TrackingCourseService trackingCourseService;
    @Resource
    private EdXTrackingLogService edXTrackingLogService;

    @RequestMapping(method = {RequestMethod.GET})
    public String View(Model model){
        return "edxlog";
    }

    @RequestMapping(value="/listCourse", method = {RequestMethod.POST})
    public @ResponseBody List<TrackingCourse> listCourse() {
        List<TrackingCourse> listCourse = trackingCourseService.listTrackingCourse();
        return listCourse;
    }

    @RequestMapping(value="/searchEdX", method = {RequestMethod.POST})
    public @ResponseBody EdXTrackingLogResult searchEdX(
            @RequestParam(value="courseId") String courseId,
            @RequestParam(value="calenderStart") String calenderStart,
            @RequestParam(value="calenderEnd") String calenderEnd,
            @RequestParam(value="roles", required=false) String[] roles,
            @RequestParam(value="pageIndex", required = false, defaultValue = "0") int pageIndex) {
        L.info("courseId:{}, calenderStart:{}, calenderEnd:{}, roles:{}, pageIndex:{}", courseId, calenderStart, calenderEnd, roles, pageIndex);
        int totalCount = edXTrackingLogService.countEdXTrackingLog(courseId, calenderStart, calenderEnd, roles);
//        L.info("totalCount:{}", totalCount);
        if (totalCount == 0) {
            return null;
        }

        if (pageIndex == 0 || pageIndex == 1) {
            pageIndex = 0;
        } else {
            pageIndex = (pageIndex - 1) * 100;
        }
        List<EdXTrackingLog> list = edXTrackingLogService.listEdXTrackingLog(courseId, calenderStart, calenderEnd, roles, pageIndex);
        EdXTrackingLogResult result = new EdXTrackingLogResult();
        result.totalCount = totalCount;
        result.edxLogList = list;
        return result;
        
    }

    private static class EdXTrackingLogResult {
        private int totalCount;
        private List<EdXTrackingLog> edxLogList = new ArrayList<>();
        public int getTotalCount() {
            return totalCount;
        }
        public void setTotalCount(int totalCount) {
            this.totalCount = totalCount;
        }
        public List<EdXTrackingLog> getEdxLogList() {
            return edxLogList;
        }
        public void setEdxLogList(List<EdXTrackingLog> edxLogList) {
            this.edxLogList = edxLogList;
        }
    }
}
