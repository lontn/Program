package com.fcu.gtml;

import java.util.Date;

import org.joda.time.DateTime;
import org.junit.Test;

import com.fcu.gtml.utils.DateFormatUtils;

public class TrainProcessTest {

    @Test
    public void test() {
        DateTime dt = new DateTime();
        //90天後那個星期的星期一日期
        System.out.println("90天後那個星期的星期一日期");
        System.out.println(dt.dayOfWeek().withMinimumValue());
        System.out.println(dt.dayOfWeek().withMaximumValue());
        System.out.println(dt.minusDays(14).dayOfWeek().withMinimumValue());
        System.out.println(dt.minusDays(14).dayOfWeek().withMaximumValue());
    }

    @Test
    public void test2() {
        Date date = new Date();
        System.out.println(date);
        String tString = DateFormatUtils.YMDHMS.format(date);
        System.out.println(tString);
        String tString2 = DateFormatUtils.YMDHMS24.format(date);
        System.out.println(tString2);
    }
}
