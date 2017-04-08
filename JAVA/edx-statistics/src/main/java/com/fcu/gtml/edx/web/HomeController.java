package com.fcu.gtml.edx.web;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.joda.time.DateTime;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fcu.gtml.edx.domain.TrackingStatistics;
import com.fcu.gtml.edx.domain.TrackingStatisticsByMonth;
import com.fcu.gtml.edx.domain.TrackingStatistics.TrackingLoadVideo;
import com.fcu.gtml.edx.domain.TrackingStatisticsByMonth.TrackingByMonth;
import com.fcu.gtml.edx.domain.TrackingStatisticsByPie;
import com.fcu.gtml.edx.domain.TrackingStatisticsByPie.ComBineDataByPie;
import com.fcu.gtml.edx.service.TrackingStatisticsService;

@Controller
@RequestMapping(value="/home")
public class HomeController {
    private static final Logger L = LogManager.getLogger();
    @Resource
    private TrackingStatisticsService service;

    /**
     * 進入首頁
     * @param model
     * @return
     */
    @RequestMapping(method = {RequestMethod.GET})
    public String View(Model model){
        return "home";
    }

    @RequestMapping(value="/searchType", method = {RequestMethod.POST})
    public @ResponseBody TrackingLoadVideo search(@RequestParam(value="eventType") String eventType, @RequestParam(value="year") int year, @RequestParam(value="month") int month) {
        L.info("eventType:{}, year:{}, month:{}", month);
        DateTime startDate = new DateTime(year, month, 1, 0 ,0);
        int monthMAX = startDate.dayOfMonth().getMaximumValue();
        DateTime endDate = new DateTime(year, month, monthMAX, 0 ,0);
        List<TrackingStatistics> listLogs = service.listTrackingStatistics(eventType, startDate.toString("yyyy-MM-dd"), endDate.toString("yyyy-MM-dd"));
        List<Integer> quantitys = new ArrayList<>();
        List<String> times = new ArrayList<>();
        for (TrackingStatistics tracking : listLogs) {
            int count = tracking.getQuantity();
            quantitys.add(count);
            String time = new DateTime(tracking.getTime()).toString("yyyy-MM-dd");
            times.add(time);
        }
        TrackingLoadVideo trackingLog = new TrackingLoadVideo(eventType, quantitys, times);
        L.info("trackingLog:{}", trackingLog);
        return trackingLog;
    }

    @RequestMapping(value="/combinePie", method = {RequestMethod.POST})
    public @ResponseBody List<ComBineDataByPie> combinePie(@RequestParam(value="year") int year) {
        String[] eventTypes = {"hide_transcript",
                "show_transcript",
                "edx.video.closed_captions.hidden",
                "edx.video.closed_captions.shown",
                "load_video",
                "pause_video",
                "play_video",
                "seek_video",
                "speed_change_video",
                "stop_video"}; 
        List<TrackingStatisticsByPie> listPies = service.listTrackingStatisticsByPie(year, eventTypes);
        List<ComBineDataByPie> listComBinePie = new ArrayList<>();
        for (TrackingStatisticsByPie dataPie : listPies) {
            String name = dataPie.getEvenType();
            int y = dataPie.getQuantity();
            ComBineDataByPie pie = new ComBineDataByPie(name, y);
            listComBinePie.add(pie);
        }
        L.info("listComBinePie:{}", listComBinePie);
        return listComBinePie;
        
    }

    @RequestMapping(value="/searchMonth", method = {RequestMethod.POST})
    public @ResponseBody TrackingByMonth searchMonth(@RequestParam(value="year") int year, @RequestParam(value="eventType") String eventType) {
        L.info("year:{}, eventType:{}", year, eventType);
        DateTime startDate = new DateTime(year, 1, 1, 0 ,0);
        DateTime endDate = new DateTime(year, 12, 31, 0 ,0);
        List<TrackingStatisticsByMonth> listMonth = service.listTrackingStatisticsByMonth(year, eventType, startDate.toString("yyyy-MM-dd"), endDate.toString("yyyy-MM-dd"));
        List<Integer> quantitys = new ArrayList<>();
        List<String> months = new ArrayList<>();
        for (TrackingStatisticsByMonth monthLog : listMonth) {
            int count = monthLog.getQuantity();
            quantitys.add(count);
            String month = getMonth(monthLog.getMonth());
            months.add(month);
        }
        TrackingByMonth tMonth = new TrackingByMonth(eventType, quantitys, months);
        return tMonth;
    }

    private String getMonth(int month) {
        String word = "";
        switch (month) {
        case 1:
            word = "一月";
            break;
        case 2:
            word = "二月";
            break;
        case 3:
            word = "三月";
            break;
        case 4:
            word = "四月";
            break;
        case 5:
            word = "五月";
            break;
        case 6:
            word = "六月";
            break;
        case 7:
            word = "七月";
            break;
        case 8:
            word = "八月";
            break;
        case 9:
            word = "九月";
            break;
        case 10:
            word = "十月";
            break;
        case 11:
            word = "十一月";
            break;
        case 12:
            word = "十二月";
            break;
        default:
            word = "None";
            break;
        }
        return word;
    }
}
