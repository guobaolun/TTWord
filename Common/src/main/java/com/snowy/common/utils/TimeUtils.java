package com.snowy.common.utils;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class TimeUtils {


    public final static String DATE_FORMAT = "yyyy-MM-dd";
    public final static String DATE_FORMAT_CN = "yyyy年MM月dd日";
    public final static String TIME_FORMAT = "yyyy-MM-dd HH:mm:ss";
    public final static String TIME_FORMAT_CN = "yyyy年MM月dd日 HH:mm:ss";
    public final static String MONTH_FORMAT = "yyyy-MM";
    private final static String DAY_FORMAT = "yyyyMMdd";



    /**
     * 得到当前的时间，时间格式yyyy-MM-dd
     *
     * @return data
     */
    public static String getCurrentFormatDate(String pattern) {
        SimpleDateFormat format = new SimpleDateFormat(pattern, Locale.getDefault());
        return format.format(new Date());
    }

    public static String getTime(long time) {

        String commitDate = sdf1.format(new Date(time));
        // 在主页面中设置当天时间
        Date nowTime = new Date();
        String currDate = sdf1.format(nowTime);
        Date date = null;
        try {
            // 将给定的字符串中的日期提取出来
            date = sdf1.parse(commitDate);
        } catch (Exception e) {
            e.printStackTrace();
        }
        int nowDate = Integer.valueOf(currDate.substring(8, 10));
        int commit = Integer.valueOf(commitDate.substring(8, 10));

        String monthDay = sdf2.format(date).substring(5, 12);
        String yearMonthDay = sdf2.format(date).substring(0, 12);
        int month = Integer.valueOf(monthDay.substring(0, 2));
        int day = Integer.valueOf(monthDay.substring(3, 5));
        if (month < 10 && day < 10) {
            monthDay = monthDay.substring(1, 3) + monthDay.substring(4);
        } else if (month < 10) {
            monthDay = monthDay.substring(1);
        } else if (day < 10) {
            monthDay = monthDay.substring(0, 3) + monthDay.substring(4);
        }
        int yearMonth = Integer.valueOf(yearMonthDay.substring(5, 7));
        int yearDay = Integer.valueOf(yearMonthDay.substring(8, 10));
        if (yearMonth < 10 && yearDay < 10) {
            yearMonthDay = yearMonthDay.substring(0, 5) + yearMonthDay.substring(6, 8) + yearMonthDay.substring(9);
        } else if (yearMonth < 10) {
            yearMonthDay = yearMonthDay.substring(0, 5) + yearMonthDay.substring(6);
        } else if (yearDay < 10) {
            yearMonthDay = yearMonthDay.substring(0, 8) + yearMonthDay.substring(9);
        }
        String str = " 00:00:00";
        float currDay = farmatTime(currDate.substring(0, 10) + str);
        float commitDay = farmatTime(commitDate.substring(0, 10) + str);
        int currYear = Integer.valueOf(currDate.substring(0, 4));
        int commitYear = Integer.valueOf(commitDate.substring(0, 4));
        int flag = (int) (farmatTime(currDate) / 1000 - farmatTime(commitDate) / 1000);
        String des;
        String hourMin = commitDate.substring(11, 16);
        if (flag < 60) {
            if (commitDay < currDay) {
                des = "昨天  " + hourMin;
            } else {
                des = "刚刚";
            }
        } else if (flag < 60 * 60) {
            if (commitDay < currDay) {
                des = "昨天  " + hourMin;
            } else {
                des = flag / 60 + "分钟前";
            }
        } else if (flag < 60 * 60 * 24) {
            int hour = flag / (60 * 60);
            if (commitDay < currDay) {
                des = "昨天  " + hourMin;
            } else {
                if (hour < 6) {
                    des = hour + "小时前";
                } else {
                    des = "今天  " +hourMin;
                }
            }
        } else if (flag < (60 * 60 * 24 * 2)) {
            if (nowDate - commit == 1) {
                des = "昨天  " + hourMin;
            } else {
                des = "前天  " + hourMin;
            }
        } else if (flag < 60 * 60 * 60 * 3) {
            if (nowDate - commit == 2) {
                des = "前天  " + hourMin;
            } else {
                if (commitYear < currYear) {
                    des = yearMonthDay + hourMin;
                } else {
                    des = monthDay + hourMin;
                }
            }
        } else {
            if (commitYear < currYear) {
                des = yearMonthDay + hourMin;
            } else {
                des = monthDay + hourMin;
            }
        }
        return des;
    }


    /**
     * 准备第一个模板，从字符串中提取出日期数字
     */
    private static String pat1 = "yyyy-MM-dd HH:mm:ss";
    /**
     * 准备第二个模板，将提取后的日期数字变为指定的格式
     */
    private static String pat2 = "yyyy年MM月dd日 HH:mm:ss";
    /**
     * 实例化模板对象
     */
    private static SimpleDateFormat sdf1 = new SimpleDateFormat(pat1, Locale.getDefault());
    private static SimpleDateFormat sdf2 = new SimpleDateFormat(pat2, Locale.getDefault());


    private static Long farmatTime(String string) {
        Long time = 0L;
        try {
            SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());
            Date date = new Date(sf.parse(string).getTime());
            time = date.getTime();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return time;
    }



    public static String getMusicTime(int time) {
        int second = time / 1000;
        return String.format(Locale.getDefault(),"%02d", second / 60) + ":" + String.format(Locale.getDefault(),"%02d", second % 60);
    }



    private static DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");


    public static Date getTime(int year,int month,int day){
        Calendar cal = Calendar.getInstance();
        cal.set(year,month,day);
        return cal.getTime();
    }


    public static Date getTime(int year, int month, int date, int hourOfDay, int minute, int second){
        Calendar cal = Calendar.getInstance();
        cal.set(year,month-1,date,hourOfDay,minute,second);
        return cal.getTime();
    }



}
