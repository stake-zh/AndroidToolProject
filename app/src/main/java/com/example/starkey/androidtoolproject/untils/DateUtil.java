package com.example.starkey.androidtoolproject.untils;

import android.text.TextUtils;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class DateUtil {

    private static String formatTimeMMddHHmm(long timestamp) {
        if (timestamp == 0) {
            return null;
        }

        String template = "MM-dd HH:mm";
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(template, Locale.getDefault());
        return simpleDateFormat.format(timestamp);
    }

    /**
     * 转化时间
     *
     * @param timestamp 时间戳
     * @return 刚刚|分钟前|小时前|天前|MM月dd日
     */
    public static String formatTime(long timestamp) {
        if (timestamp == 0) {
            return null;
        }
        long nowTimestamp = System.currentTimeMillis();
        // 如果时间差 <= 0秒，则修正为1秒钟前
        int deltaSeconds = Math.max(1, (int) ((nowTimestamp - timestamp) / 1000));

        if (deltaSeconds <= 5 * 60) {
            return "刚刚";
        }
        // 规则2: 小于1小时的，显示xx分钟前
        if (deltaSeconds <= 3600 && deltaSeconds > 5 * 60) {
            return (int) Math.max(1, Math.floor(deltaSeconds / 60)) + "分钟前";
        }
        if (deltaSeconds < 86400) {
            return (int) Math.floor(deltaSeconds / 3600) + "小时前";
        }
        if (deltaSeconds > 86400 && deltaSeconds < 3 * 86400) {
            return (int) Math.floor(deltaSeconds / 86400) + "天前";
        }
        String template = "MM月dd日";
      /*  if (includeHour) {
            template = "MM月dd日 HH:mm";
        } else {
            template = "MM月dd日";
        }*/
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(template, Locale.getDefault());
        return simpleDateFormat.format(timestamp);
    }

    public static long parseFormatTime(String timeString) {
        long timestamp = 0;
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());
        if (TextUtils.isEmpty(timeString))
            return timestamp;
        try {
            Date date = dateFormat.parse(timeString);
            timestamp = date.getTime();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return timestamp;
    }


    public static String getLiveDate(long startTime, long nowTime) {
        Calendar startCalendar = Calendar.getInstance();
        startCalendar.setTimeInMillis(startTime);
        Calendar nowCalendar = Calendar.getInstance();
        nowCalendar.setTimeInMillis(nowTime);

        String format = "MM月dd日HH:mm";
        if (startCalendar.get(Calendar.YEAR) == nowCalendar.get(Calendar.YEAR)
                && startCalendar.get(Calendar.MONTH) == nowCalendar.get(Calendar.MONTH)) {
            int delta = startCalendar.get(Calendar.DAY_OF_YEAR) - nowCalendar.get(Calendar.DAY_OF_YEAR);
            if (delta == 0) {
                format = "今天" + "HH:mm";
            } else if (delta == 1) {
                format = "明天" + "HH:mm";
            }
        }

        DateFormat df = new SimpleDateFormat(format, Locale.getDefault());
        return df.format(startTime);
    }
}
