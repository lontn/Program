package org.common.utils;

import org.joda.time.DateTime;

public final class DateTimeUtils {

    /**
     * 一天中最後的時間 yyyy-mm-dd 23:59:59:999
     * 
     * @param month
     *            1~12
     */
    public static long maxTimeOfDay(int year, int month, int day) {
        return new DateTime(year, month, day, 23, 59, 59, 999).toDate().getTime();
    }

    /**
     * 一天中最早的時間 yyyy-mm-dd 00:00:00.000
     * 
     * @param month
     *            1~12
     */
    public static long minTimeOfDay(int year, int month, int day) {
        return new DateTime(year, month, day, 0, 0, 0, 0).toDate().getTime();
    }

    /**
     * 回傳time是年當中的第幾周，採用ISO 8601
     */
    public static final int weekOfYear(long time) {
        return new DateTime(time).getWeekOfWeekyear();
    }

    /**
     * 回傳time是哪個周年，如2012/1/1的weekyear是2011，採用ISO 8601
     */
    public static final int weekyear(long time) {
        return new DateTime(time).getWeekyear();
    }

    /**
     * 回傳還差幾分鐘到達指定的分鐘 <br>
     * 例如 targetTime 是 15，circleTime是 30, 目前時間是 1:30，則差 15分到 1:45 <br>
     * 例如 targetTime 是 15，circleTime是 60, 目前時間是 1:30，則差 45分到 2:15 <br>
     */
    public static final int diffMinuteTime(int targetTime, int cycleTime) {
        int nowTime = new DateTime().getMinuteOfHour();
        int diffMins = targetTime - nowTime;
        while(diffMins < 0) {
            diffMins += cycleTime;
        }
        return diffMins;
    }
}
