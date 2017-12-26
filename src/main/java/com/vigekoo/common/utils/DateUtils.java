package com.vigekoo.common.utils;

import java.security.InvalidParameterException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author oplus
 * @Description: TODO(日期处理)
 * @date 2017-6-23 15:07
 */
public class DateUtils {
	/** 时间格式(yyyy-MM-dd) */
	public final static String DATE_PATTERN = "yyyy-MM-dd";
	/** 时间格式(yyyy-MM-dd HH:mm:ss) */
	public final static String DATE_TIME_PATTERN = "yyyy-MM-dd HH:mm:ss";
	
	public static String format(Date date) {
        return format(date, DATE_PATTERN);
    }

    public static String format(Date date, String pattern) {
        if(date != null){
            SimpleDateFormat df = new SimpleDateFormat(pattern);
            return df.format(date);
        }
        return null;
    }

    /**
     * 统计两个日期之间包含的天数。
     *
     * @param date1
     * @param date2
     * @return
     */
    public static int getDayDiff(Date date1, Date date2) {
        if (date1 == null || date2 == null) {
            throw new InvalidParameterException("date1 and date2 cannot be null!");
        }
        long millSecondsInOneDay = 24 * 60 * 60 * 1000;
        return (int) ((date1.getTime() - date2.getTime()) / millSecondsInOneDay);
    }

}
