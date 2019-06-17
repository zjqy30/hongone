package com.hone.system.utils;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 *  日期工具类
 *
 * Created by panzhangbao on 2018/5/12 13:00
 * Copyright ©2018  panzhangbao All rights reserved.
 **/
public class DateUtils {

    /**
     *  日期转字符串
     *
     * @param date
     * @param format
     * @return
     */
    public static  String formatDateToString(Date date ,String format){
        if (format == null || format.isEmpty()) {
            format = new String("yyyy-MM-dd HH:mm:ss");
        }
        return new SimpleDateFormat(format).format(date);
    }


    /**
     * 获取当前时间 格式为 yyyy-MM-dd
     * @return
     */
    public static  String YYYYMMDD(){
        return new SimpleDateFormat("yyyyMMdd").format(new Date());
    }

    /**
     * 获取当前时间 格式为 yyyy-MM
     * @return
     */
    public static  String YYYYMM(){
        return new SimpleDateFormat("yyyyMM").format(new Date());
    }

    /**
     *  日期转字符串
     *
     * @param date
     * @return
     */
    public static  String formatDateToString(Date date){
        return formatDateToString(date, null);
    }

    /**
     * 字符串转日期
     *
     * @param date
     * @param format
     * @return
     * @throws Exception
     */
    public static  Date formatStringToDate(String date ,String format) throws  Exception{
        if (format == null || format.isEmpty()) {
            format = new String("yyyy-MM-dd HH:mm:ss");
        }

        return new SimpleDateFormat(format).parse(date);
    }

    /**
     * 字符串转日期
     *
     * @param date
     * @return
     */
    public static  Date formatStringToDate(String date){
        try {
            return formatStringToDate(date, null);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }


    /**
     *  获取当前时间
     * @return
     */
    public static String getNowDateTime() {
        Date today = new Date();
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return format.format(today);
    }

    public static String getToday() {
        Date today = new Date();
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        return format.format(today);
    }

    public static String getTodayYM() {
        Date today = new Date();
        SimpleDateFormat format = new SimpleDateFormat("yyMM");
        return format.format(today);
    }

    /**
     * 获取年月日
     *  格式：181010
     *
     * @author Jason Pan
     * @generatedDate: 2018/10/23 10:07
     * @param
     * @return
     */
    public static String getTodayYMD() {
        Date today = new Date();
        SimpleDateFormat format = new SimpleDateFormat("yyMMdd");
        return format.format(today);
    }

    /**
     *  获取过去或未来 任意天内的日期
     *
     * @param intervalDay   间隔天数（可正负）
     * @return
     */
    public static String getOneDaysDate(Integer intervalDay) {
        if (intervalDay == null) {
            return getOneDaysDate(0);
        }

        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.DAY_OF_YEAR, calendar.get(Calendar.DAY_OF_YEAR) + intervalDay);

        Date today = calendar.getTime();
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        return format.format(today);
    }

    /**
     *  获取未来几个小时后的日期
     *
     * @param delayHours   小时
     * @return
     */
    public static String getDelayHoursDate(Integer delayHours) {
        if (delayHours == null) {
            delayHours=0;
        }

        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.HOUR,delayHours);
        Date today = calendar.getTime();
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return format.format(today);
    }

    /**
     * 设置超时截至时间
     * @param expireTime    截至时间（单位：秒）
     * @return
     * @throws Exception
     */
    public static String setExpireTime(Integer expireTime) throws Exception{

        Calendar rightNow = Calendar.getInstance();
        rightNow.setTime(formatStringToDate(getNowDateTime(), null));

        rightNow.add(Calendar.SECOND, expireTime);

        return formatDateToString(rightNow.getTime(), null);
    }

    /**
     * 超时时间 和 当前时间比较
     * @param expireTime
     * @return
     */
    public static Boolean compare(String expireTime) {

        String nowDateTime = getNowDateTime();
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try {
            Date dt1 = df.parse(nowDateTime);
            Date dt2 = df.parse(expireTime);
            if (dt1.getTime() > dt2.getTime()) {
                return true;
            } else {
                return false;
            }
        } catch (Exception exception) {
            exception.printStackTrace();
        }
        return false;
    }

    /**
     *  获取超时时间 和 现在时间的时间差：秒
     *
     * @param expireTime
     * @return
     */
    public static int getIntervals(String expireTime) {
        int intervals = 0;

        String nowDateTime = getNowDateTime();
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try {
            Date dt1 = df.parse(nowDateTime);
            Date dt2 = df.parse(expireTime);
            intervals = (int) (dt2.getTime() - dt1.getTime()) / 1000;
        } catch (Exception exception) {
            exception.printStackTrace();
        }
        return intervals;
    }

    /**
     *  获取当前时间 和 创建时间的时间差：秒
     *
     * @param createdTime 创建时间
     * @return
     */
    public static int getNowToCreatedTimeIntervals(String createdTime) {
        int intervals = 0;

        String nowDateTime = getNowDateTime();
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date dt1 = null;
        try {
            dt1 = df.parse(nowDateTime);
            Date dt2 = df.parse(createdTime);
            intervals = (int) (dt1.getTime() - dt2.getTime()) / 1000;
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return intervals;
    }

    /**
     * 获取 时分秒(10:10:10) 格式的倒计时
     *
     * @author Jason Pan
     * @generatedDate: 2018/10/9 10:32
     * @param time 剩余时间
     * @return java.lang.String 时分秒格式的倒计时
     */
    public static String getCountDownTimeFormatHMS(Integer time) {
        // 时分秒
        Integer hh = time / 60 / 60 % 60;
        Integer mm = time / 60 % 60;
        Integer ss = time % 60;

        String H = hh < 10 ? "0" + String.valueOf(hh) : String.valueOf(hh);
        String M = mm < 10 ? "0" + String.valueOf(mm) : String.valueOf(mm);
        String S = ss < 10 ? "0" + String.valueOf(ss) : String.valueOf(ss);

        return H + ":" + M + ":" + S;
    }
}
