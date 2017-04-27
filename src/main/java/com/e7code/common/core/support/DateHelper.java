package com.e7code.common.core.support;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by ssr on 2017/3/23.
 */
public class DateHelper {
    public static String DAY_FORMAT = "yyyy-MM-dd";
    public static String DAY_TIME_FORMAT = "yyyy-MM-dd HH:mm:ss";

    /***
     * 日期类型转换，默认支持两种格式：2017-03-23 或 2017-03-23 23:23:23
     * @param text 日期字符串
     * @return 日期对象
     */
    public static Date parse(String text) {
        if(text == null || text.length() == 0) {
            return null;
        }

        if(text.length() == DAY_FORMAT.length()) {
            return parse(text, DAY_FORMAT);
        }

        if(text.length() == DAY_TIME_FORMAT.length()) {
            return parse(text, DAY_TIME_FORMAT);
        }

        return null;
    }

    /***
     * 日期类型转换
     * @param text 日期字符串
     * @param partten 格式
     * @return 日期对象
     */
    public static Date parse(String text, String partten) {
        if(text == null || text.length() == 0) {
            return null;
        }

        SimpleDateFormat df = new SimpleDateFormat(partten);
        try {
            return df.parse(text);
        } catch (ParseException e) {
            return null;
        }
    }

    /***
     * 日期格式化
     *     如果时分秒都为0，格式化为：2017-03-23
     *     如果时分秒有值， 格式化为：2017-03-23 23:23:23
     * @param date  日期对象
     * @return  日期字符串
     */
    public static String format(Date date) {
        if(date == null)  return null;

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);

        if(calendar.get(Calendar.HOUR_OF_DAY) == 0 && calendar.get(Calendar.MINUTE) == 0 && calendar.get(Calendar.SECOND) == 0) {
            return format(date, DAY_FORMAT);
        } else {
            return format(date, DAY_TIME_FORMAT);
        }
    }
    /***
     * 日期格式化
     * @param date  日期对象
     * @param partten 格式
     * @return  日期字符串
     */
    public static String format(Date date, String partten) {
        if(date == null ||  partten == null)  return null;

        SimpleDateFormat df = new SimpleDateFormat(partten);
        return df.format(date);
    }
}
