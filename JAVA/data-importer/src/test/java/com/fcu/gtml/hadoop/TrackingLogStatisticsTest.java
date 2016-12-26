package com.fcu.gtml.hadoop;

import java.util.Date;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.junit.Test;

import com.fcu.gtml.SupportSpringTest;

public class TrackingLogStatisticsTest extends SupportSpringTest {
    private static final Logger L = LogManager.getLogger();
    private DateTimeFormatter dtf = DateTimeFormat.forPattern("yyyy-MM-dd'T'HH:mm:ss.xxxxxx+00:00");
    @Test
    public void dateTest() {
        String date = "2014-09-12 02:51:20.053";
        DateTime dateTime = DateTime.parse(date, DateTimeFormat.forPattern("yyyy-MM-dd HH:mm:ss.SSS"));
        L.info("dateTime:{}", dateTime.toString("yyyy-MM-dd"));
    }

    @Test
    public void dateTest2() {
        String time = "2014-09-12T06:59:20.252107+00:00";
        //Date dateTime = DateTime.parse(time, dtf).toDate();
        Date dateTime = new DateTime(time, DateTimeZone.UTC).toDate();
        L.info("dateTime:{}", dateTime);
    }
}
