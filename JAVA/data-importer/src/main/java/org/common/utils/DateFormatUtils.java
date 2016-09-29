package org.common.utils;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

/**
 * 
 * @since $Id: 1b37dab74940780e69ce3eea6158f8a738a5fa1f $
 */
public class DateFormatUtils {

    public static class YMD {
        private static final DateFormat DF = new SimpleDateFormat("yyyy-MM-dd",
                Locale.US);

        public static final String format() {
            return DF.format(new Date());
        }

        public static final String format(long time) {
            return DF.format(new Date(time));
        }

        public static final String format(Date time) {
            return DF.format(time);
        }

        public static final String format(Calendar time) {
            return DF.format(time.getTime());
        }
    }

    public static class YMDHM {
        private static final DateFormat DF = new SimpleDateFormat(
                "yyyy-MM-dd hh:mm", Locale.US);

        public static final String format() {
            return DF.format(new Date());
        }

        public static final String format(long time) {
            return DF.format(new Date(time));
        }

        public static final String format(Date time) {
            return DF.format(time);
        }

        public static final String format(Calendar time) {
            return DF.format(time.getTime());
        }
    }

    public static class YMDHMS {
        private static final DateFormat DF = new SimpleDateFormat(
                "yyyy-MM-dd hh:mm:ss", Locale.US);

        public static final String format() {
            return DF.format(new Date());
        }

        public static final String format(long time) {
            return DF.format(new Date(time));
        }

        public static final String format(Date time) {
            return DF.format(time);
        }

        public static final String format(Calendar time) {
            return DF.format(time.getTime());
        }
    }

}
