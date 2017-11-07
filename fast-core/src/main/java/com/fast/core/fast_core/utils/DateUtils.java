package com.fast.core.fast_core.utils;

import java.util.Calendar;

/**
 * Created by a on 2016/12/20.
 */

public class DateUtils {

    private static Calendar mCalendar = Calendar.getInstance();

    public static int getYear() {
        return mCalendar.get(Calendar.YEAR);

    }

    public static int getMonth() {
        return mCalendar.get(Calendar.MONTH);

    }

    public static int getDay() {
        return mCalendar.get(Calendar.DAY_OF_MONTH);

    }


    /**
     * 获取某年某月的最后一天
     *
     * @param year  年
     * @param month 月
     * @return 最后一天
     */
    public static int getLastDayOfMonth(int year, int month) {
        if (month == 1 || month == 3 || month == 5 || month == 7 || month == 8
                || month == 10 || month == 12) {
            return 31;
        }
        if (month == 4 || month == 6 || month == 9 || month == 11) {
            return 30;
        }
        if (month == 2) {
            if (isLeapYear(year)) {
                return 29;
            } else {
                return 28;
            }
        }
        return 0;
    }

    /**
     * 是否闰年
     *
     * @param year 年
     * @return
     */
    public static boolean isLeapYear(int year) {
        return (year % 4 == 0 && year % 100 != 0) || (year % 400 == 0);
    }
}
