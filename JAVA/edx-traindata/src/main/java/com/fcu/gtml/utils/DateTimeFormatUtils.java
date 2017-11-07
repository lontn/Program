package com.fcu.gtml.utils;

import java.text.DateFormat; 
import java.text.ParseException; 
import java.text.SimpleDateFormat; 
import java.util.Calendar; 
import java.util.Date; 
import java.util.GregorianCalendar; 
import java.util.Locale; 

import org.apache.commons.lang3.time.DateUtils; 
import org.joda.time.DateTime; 
import org.joda.time.Days; 
import org.joda.time.Months; 
import org.joda.time.Weeks; 
import org.joda.time.Years; 

public class DateTimeFormatUtils {
    public static double LOCAL_TIME_ZONES = 8/24d;
    
    public final static String[] parsePatterns = new String[] { 
            "yyyy-M-d", 
            "yyyy-MM-dd", 
            "yyyy-M-dd", 
            "yyyy-MM-d", 
            "yyyy-M-d H:m", 
            "yyyy-M-d H:mm", 
            "yyyy-M-d HH:m", 
            "yyyy-M-d HH:mm", 
            "yyyy-M-d H:m:s", 
            "yyyy-MM-dd HH:mm:ss", 
            "yyyy-M-d H:m:s.S", 
            "yyyy-MM-dd H:mm", 
            "yyyy-MM-dd HH:m", 
            "yyyy-MM-dd HH:mm", 
            "yyyy-MM-dd HH:mm:ss", 
            "yyyy-MM-dd HH:mm:ss.S", 
            "yyyy-MM-dd HH:mm:ss.SS", 
            "yyyy-MM-dd HH:mm:ss.SSS" 
    };

    private final static String[] weekNames = new String[] { "", "一", "二", "三", "四", "五", "六", "日" };
    public final static String datePattern = "yyyy-MM-dd";
    public final static String timePattern = "yyyy-MM-dd HH:mm:ss";

//  public final static DateFormat dateFormat = new SimpleDateFormat(datePattern); 
//  public final static DateFormat timeFormat = new SimpleDateFormat(timePattern); 

    /**
     * 获取当前系统日历
     * 
     * @return
     * @throws IllegalArgumentException
     * @see [类、类#方法、类#成员]
     */
    public static Calendar getCalendar() {
        return Calendar.getInstance();
    }

    /**
     * 获取当前系统时间，应统一调用，可根据用户的具体需求进行修改
     * 
     * @return
     * @throws IllegalArgumentException
     * @see [类、类#方法、类#成员]
     */
    public static Date getDate() {
        return getCalendar().getTime();
    }

    /**
     * 获取当前系统时间，返回java.sql.Date
     * 
     * @return
     * @throws IllegalArgumentException
     * @see [类、类#方法、类#成员]
     */
    public static java.sql.Date getSQLDate() {
        return toSqlDate(getCalendar().getTime());
    }

    /**
     * 获取当前系统日期
     * 
     * @return
     * @throws DataInvalidException 
     * @throws IllegalArgumentException
     * @see [类、类#方法、类#成员]
     */
    public static String getToDateString() throws DataInvalidException {
        return toDateString(getDate());
    }

    /**
     * 将Date转Calendar
     * 
     * @param date
     * @return
     * @see [类、类#方法、类#成员]
     */
    public static Calendar toCalendar(Date date) {
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        return c;
    }

    /**
     * 按默认样式转换字符串到日期类型
     * 
     * @param text
     * @return
     * @throws IllegalArgumentException
     * @throws DataInvalidException 
     * @see [类、类#方法、类#成员]
     */
    public static Date parseDate(final String text) throws IllegalArgumentException, DataInvalidException {
        return parseDate(text, parsePatterns);
    }

    /**
     * 按自定义样式转换字符串到日期类型
     * 
     * @param text
     * @param patterns
     * @return
     * @throws IllegalArgumentException
     * @see [类、类#方法、类#成员]
     */
    public static Date parseDate(final String text, final String... patterns) throws DataInvalidException {
        try {
            boolean parseAble = false;
            for (int i = 0; (i < patterns.length) && !parseAble; i++) {
                parseAble = (text.length() >= patterns[i].length());
            }
            if (parseAble) {
                return DateUtils.parseDate(text, parsePatterns);
            } else {
                throw new DataInvalidException("0101010004", text + ",日期");
            }
        } catch (ParseException ex) {
            throw new DataInvalidException("0101010005", text + ",日期");
        }
    }

    /**
     * 转换为日期字符串
     * 
     * @param date
     * @return
     * @throws DataInvalidException 
     * @see [类、类#方法、类#成员]
     */
    public static String toDateString(final Date date) throws DataInvalidException {
        return parseString(date, "yyyy-MM-dd");
    }

    /**
     * 转换为时间字符串
     * 
     * @param date
     * @return
     * @throws DataInvalidException 
     * @see [类、类#方法、类#成员]
     */
    public static String toTimeString(final Date date) throws DataInvalidException {
        return parseString(date, "HH:mm:ss");
    }

    /**
     * 转换为日期时间字符串
     * 
     * @param date
     * @return
     * @throws DataInvalidException 
     * @see [类、类#方法、类#成员]
     */
    public static String toDateTimeString(final Date date) throws DataInvalidException {
        return parseString(date, "yyyy-MM-dd HH:mm:ss");
    }

    /**
     * 转换为指定格式字符串
     * 
     * @param date
     * @param pattern
     * @return
     * @throws DataInvalidException 
     * @see [类、类#方法、类#成员]
     */
    public static String parseString(final Date date, final String pattern) throws DataInvalidException {
        if (date == null) {
            throw new DataInvalidException("0101010001, 日期date");
        }
        if (pattern == null) {
            throw new DataInvalidException("0101010001, 日期格式pattern");
        }
        DateFormat dateFormat = new SimpleDateFormat(pattern, Locale.CHINESE);
        return dateFormat.format(date);
    }

    /**
     * 得到星期几（中文）
     * 
     * @param date
     * @return
     * @throws DataInvalidException 
     */
    public static String cnWeekDay(Date date) throws DataInvalidException {
        return weekNames[getWeekDay(date)];
    }

    /**
     * 得到星期几
     * 
     * @param date
     * @return
     * @throws DataInvalidException 
     */
    public static int getWeekDay(Date date) throws DataInvalidException {
        if (date == null) {
            throw new DataInvalidException("0101010001", "日期date");
        }
        Calendar c = toCalendar(date);
        int dayOfWeek = c.get(Calendar.DAY_OF_WEEK) - 1;

        dayOfWeek = dayOfWeek == 0 ? 7 : dayOfWeek;
        return dayOfWeek;
    }

    /**
     * 计算begin和end之间的天数
     * 
     * @param begin
     * @param end
     * @return
     * @see [类、类#方法、类#成员]
     */
    public static int diffDays(final Date begin, final Date end) {
        DateTime jStart = new DateTime(begin);
        DateTime jEnd = new DateTime(end);
        return Days.daysBetween(jStart, jEnd).getDays();
    }

    /**
     * 计算begin和end之间的周数
     * 
     * @param begin
     * @param end
     * @return
     * @see [类、类#方法、类#成员]
     */
    public static int diffWeeks(final Date begin, final Date end) {
        DateTime jStart = new DateTime(begin);
        DateTime jEnd = new DateTime(end);
        return Weeks.weeksBetween(jStart, jEnd).getWeeks();
    }

    /**
     * 计算begin和end之间的月数
     * 
     * @param begin
     * @param end
     * @return
     * @see [类、类#方法、类#成员]
     */
    public static int diffMonths(final Date begin, final Date end) {
        DateTime jStart = new DateTime(begin);
        DateTime jEnd = new DateTime(end);
        return Months.monthsBetween(jStart, jEnd).getMonths();
    }

    /**
     * 计算begin和end之间的年数
     * 
     * @param begin
     * @param end
     * @return
     * @see [类、类#方法、类#成员]
     */
    public static int diffYears(final Date begin, final Date end) {
        DateTime jStart = new DateTime(begin);
        DateTime jEnd = new DateTime(end);
        return Years.yearsBetween(jStart, jEnd).getYears();
    }

    /**
     * 对date，加上day天
     * 
     * @param date
     * @param day
     * @return String
     * @see [类、类#方法、类#成员]
     */
    public static String addDay(final Date date, final int day) {
        DateFormat dateFormat = new SimpleDateFormat(datePattern, Locale.CHINESE);
        return dateFormat.format(operateDay(date, day));
    }

    /**
     * 对date，加上day天
     * 
     * @param date
     * @param day
     * @return Date
     * @see [类、类#方法、类#成员]
     */
    public static Date operateDay(final Date date, final int day) {
        // return DateUtils.addDays(date, amount);
        Calendar c = toCalendar(date);
        c.add(Calendar.DATE, day);
        return c.getTime();
    }

    /**
     * 对date，加上month月
     * 
     * @param date
     * @param month
     * @return String
     * @see [类、类#方法、类#成员]
     */
    public static String addMonth(final Date date, final int month) {
        DateFormat dateFormat = new SimpleDateFormat(datePattern, Locale.CHINESE);
        return dateFormat.format(operateDay(date, month));
    }

    /**
     * 对date，加上month月
     * 
     * @param date
     * @param month
     * @return Date
     * @see [类、类#方法、类#成员]
     */
    public static Date operateMonth(final Date date, final int month) {
        Calendar c = toCalendar(date);
        c.add(Calendar.MONTH, month);
        return c.getTime();
    }

    /**
     * 对date，加上year年
     * 
     * @param date
     * @param year
     * @return String
     * @see [类、类#方法、类#成员]
     */
    public static String addYear(final Date date, final int year) {
        DateFormat dateFormat = new SimpleDateFormat(datePattern, Locale.CHINESE);
        return dateFormat.format(operateDay(date, year));
    }

    /**
     * 对date，加上year年
     * 
     * @param date
     * @param year
     * @return Date
     * @see [类、类#方法、类#成员]
     */
    public static Date opearteYear(final Date date, final int year) {
        Calendar c = toCalendar(date);
        c.add(Calendar.YEAR, year);
        return c.getTime();
    }

    /**
     * 比较日期 ,得到较大的日期
     */
    public Date greatDate(Date date1, Date date2) {
        return date1.getTime() > date2.getTime() ? date1 : date2;
    }

    /**
     * 比较日期 ,得到较小的日期
     */
    public Date lessDate(Date date1, Date date2) {
        return date1.getTime() < date2.getTime() ? date1 : date2;
    }

    /**
     * 当前年
     * 
     * @return
     * @see [类、类#方法、类#成员]
     */
    public static int getToYear() {
        return getCalendar().get(Calendar.YEAR);
    }

    /**
     * 当前月
     * 
     * @return
     * @see [类、类#方法、类#成员]
     */
    public static int getToMonth() {
        return (getCalendar().get(Calendar.MONTH) + 1);
    }

    /**
     * 当前日（月中日期）
     * 
     * @return
     * @see [类、类#方法、类#成员]
     */
    public static int getToDay() {
        return getCalendar().get(Calendar.DAY_OF_MONTH);
    }

    /**
     * date的年
     * 
     * @return
     * @see [类、类#方法、类#成员]
     */
    public static int getYear(Date date) {
        return toCalendar(date).get(Calendar.YEAR);
    }

    /**
     * date的月
     * 
     * @return
     * @see [类、类#方法、类#成员]
     */
    public static int getMonth(Date date) {
        return (toCalendar(date).get(Calendar.MONTH) + 1);
    }

    /**
     * date的日（月中日期）
     * 
     * @return
     * @see [类、类#方法、类#成员]
     */
    public static int getDay(Date date) {
        return toCalendar(date).get(Calendar.DAY_OF_MONTH);
    }

    /**
     * 根据year,month,day获得Date
     * 
     * @param year
     * @param month
     * @param day
     * @return
     * @see [类、类#方法、类#成员]
     */
    public static Date getDate(int year, int month, int day) {
        Calendar c = new GregorianCalendar(year, (month - 1), day);
        return c.getTime();
    }

    /**
     * 根据Date获得当天的起始时间，即0时
     * 
     * @param year
     * @param month
     * @param day
     * @return
     * @see [类、类#方法、类#成员]
     */
    public static Calendar toBeginOfCalendar(Date date) {
        Calendar c = toCalendar(date);
        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH);
        int day = c.get(Calendar.DAY_OF_MONTH);
        return new GregorianCalendar(year, month, day);
    }

    /**
     * 根据Date获得当天的起始时间，即0时
     * 
     * @param year
     * @param month
     * @param day
     * @return
     * @see [类、类#方法、类#成员]
     */
    public static Date toBeginOfDate(Date date) {
        return (toBeginOfCalendar(date)).getTime();
    }

    /**
     * 得到本月最后一天
     * 
     * @param date
     * @return
     * @see [类、类#方法、类#成员]
     */
    public static Date getLastDayOfMonth(Date date) {
        Calendar c = new GregorianCalendar(getYear(date), getMonth(date), 1);
        c.add(Calendar.DATE, -1);
        return c.getTime();
    }

    /**
     * 将java.util.Date转换为java.sql.Date
     * 
     * @param date
     * @return
     * @see [类、类#方法、类#成员]
     */
    public static java.sql.Date toSqlDate(Date date) {
        return new java.sql.Date(date.getTime());
    }

    /**
     * 比较targetDate是不是在beginDate和endDate之间，按天算，也就是yyyy-MM-dd； 三个条件必须同时满足：
     * 1、beginDate必须小于等于endDate 2、targetDate大于等于beginDate
     * 3、targetDate小于等于endDate
     * 
     * @param targetDate
     * @param beginDate
     * @param endDate
     * @return
     */
    public static boolean isDayBetween(Date targetDate, Date beginDate, Date endDate) {
        Calendar targetCalendar = toBeginOfCalendar(targetDate);
        Calendar beginCalendar = toBeginOfCalendar(beginDate);
        Calendar endCalendar = toBeginOfCalendar(endDate);
        // beginDate必须小于等于endDate
        boolean isRightRange = beginCalendar.before(endCalendar) || beginCalendar.equals(endCalendar);
        // targetDate大于等于beginDate
        boolean bigOrEquals = targetCalendar.after(beginCalendar) || targetCalendar.equals(beginCalendar);
        // targetDate小于等于endDate
        boolean smallOrEquals = targetCalendar.before(endCalendar) || targetCalendar.equals(endCalendar);
        // 三个条件必须同时满足
        return isRightRange && bigOrEquals && smallOrEquals;
    }

    public static void main(String[] args) throws IllegalArgumentException, DataInvalidException {
        /* 测试日期间差距 */

        Date testDate = parseDate("2001-05-03 23:52:23.32");

        System.out.println(testDate);
        System.out.println("周" + cnWeekDay(testDate));
        System.out.println("test Year:\t" + getYear(testDate));
        System.out.println("test Month:\t" + getMonth(testDate));
        System.out.println("test Day:\t" + getDay(testDate));

        Date toDate = new Date();
        System.out.println(toDate);
        System.out.println("周" + cnWeekDay(toDate));
        System.out.println("To Year:\t" + getToYear());
        System.out.println("To Month:\t" + getToMonth());
        System.out.println("To Day:\t\t" + getToDay());

        System.out.println("getDate():\t" + getDate(2011, 1, 9));
        System.out.println("getLastDayOfMonth():\t" + getLastDayOfMonth(getDate(2012, 2, 9)));

        System.out.println("diffYears()" + diffYears(getDate(2001, 2, 9), getDate(2011, 1, 21)));

        /* 测试日期间差距 */
        Date d1 = new Date();
        System.out.println(getWeekDay(d1));

        Date d2 = parseDate("2011-12-29");
        System.out.println(toBeginOfDate(d1));
        System.out.println(d2);
        System.out.println(diffDays(d1, d2));
        System.out.println(diffDays(toBeginOfDate(d1), toBeginOfDate(d2)));

        /* 按天比较 */
        Date targetDate = parseDate("2012-02-28 08:52:23.32"), beginDate = parseDate("2012-02-27 00:00:00.00"),
                endDate = parseDate("2012-02-28 00:00:00.00");

        System.out.println(isDayBetween(targetDate, beginDate, endDate));
        /*
         * Calendar targetCalendar = toBeginOfCalendar(targetDate); Calendar
         * beginCalendar = toBeginOfCalendar(beginDate); Calendar endCalendar =
         * toBeginOfCalendar(endDate);
         * System.out.println(targetCalendar.after(beginCalendar));
         * System.out.println(targetCalendar.equals(beginCalendar));
         * 
         * System.out.println(targetCalendar.before(endCalendar));
         * System.out.println(targetCalendar.equals(endCalendar)); //
         */

    }
}
