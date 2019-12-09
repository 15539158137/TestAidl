package com.xlkx.testaidl;

import android.util.Log;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class TimeUtil {
   // 获取今天的data
    public static String getNowData1() {
        return new SimpleDateFormat("yyyy-MM-dd").format(new Date());
    }
    //判断后面的日期是否比前面大
    public static boolean afterIsBig(Date date_start,Date date_end) {

        Log.e("比较的两个时间",date_end.getTime()+"="+date_start.getTime());
        if(date_end.getTime()>=date_start.getTime()){
            return true;
        }

      return  false;
    }
    //判断这个日期是不是今天
    public static boolean isNowData(Date date) {
        Date nowDate = Calendar.getInstance().getTime();
        if (nowDate.getYear() == date.getYear() && nowDate.getMonth() == date.getMonth() && nowDate.getDay() == date.getDay()) {
            return true;
        } else {
            return false;
        }
    }
    //时间戳返回date
    public static Date time2Date(String _data) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        try {
            Date date = format.parse(_data);
            Log.e("data2ms的结果", date.toString());
            return date;
        } catch (Exception e) {
            return new Date();
        }
    }
    public static String getNowData() {
        return new SimpleDateFormat("yyyy-MM-dd HH:mm").format(new Date());
    }

    public static String getData(String longs) {
        Calendar c = Calendar.getInstance();
        c.setTimeInMillis(Long.parseLong(longs));
        Date date = c.getTime();
        return new SimpleDateFormat("yyyy-MM-dd HH:mm").format(date);
    }

    public static String getData1(Date date1) {
        Calendar c = Calendar.getInstance();
        c.setTimeInMillis(date1.getTime());
        Date date = c.getTime();
        return new SimpleDateFormat("yyyy-MM-dd").format(date);
    }

    public static long Date2ms(String _data) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try {
            Date date = format.parse(_data);
            Log.e("data2ms的结果", date.toString());
            return date.getTime();
        } catch (Exception e) {
            return 0;
        }
    }

    public static long Date2ms_yeay_month_day(String _data) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        try {
            Date date = format.parse(_data);
            Log.e("Date2ms_yea的结果", date.toString());
            return date.getTime();
        } catch (Exception e) {
            return 0;
        }
    }

    //计算毫秒数与当前时间的时间差(当前时间到目标还有多久)
    public static String distanceNowWithDate(long _ms) {
        SimpleDateFormat DateF = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try {
            Long time = new Long(_ms);
            String d = DateF.format(time);
            Date endDate = DateF.parse(d);
            Date nowDate = Calendar.getInstance().getTime();
            return DateDistance(nowDate, endDate);
        } catch (Exception e) {
        }
        return null;
    }


    //计算毫秒数与当前时间的时间差(多久到当前时间)
    public static String DateDistanceWithNow(long _ms) {
        SimpleDateFormat DateF = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try {
            Long time = new Long(_ms);
            String d = DateF.format(time);
            Date startDate = DateF.parse(d);
            Date nowDate = Calendar.getInstance().getTime();
            return DateDistance(startDate, nowDate);
        } catch (Exception e) {
        }
        return null;
    }

    public static String DateDistance(Date startDate, Date endDate) {
        if (startDate == null || endDate == null) {
            return null;
        }
        long timeLong = endDate.getTime() - startDate.getTime();
        if (timeLong < 0) {
            timeLong = 0;
        }

        String hour = "00";
        long hour_int = 0;
        Long time_mill = timeLong;
        hour_int = time_mill / 1000 / 60 / 60;
        if (hour_int < 10) {
            hour = "0" + hour_int;
        } else {
            hour = "" + hour_int;
        }


        long minute_last = time_mill % (1000 * 60 * 60);
        String minute = "00";
        long minute_int = 0;
        minute_int = minute_last / 1000 / 60;
        if (minute_int < 10) {
            minute = "0" + minute_int;
        } else {
            minute = "" + minute_int;
        }

        long second_last = time_mill % (1000 * 60);
        String second = "00";
        long second_int = 0;
        second_int = second_last / 1000;
        if (second_int < 10) {
            second = "0" + second_int;
        } else {
            second = "" + second_int;
        }

        return hour + ":" + minute + ":" + second;

//        if (timeLong < 60 * 1000)
//            return timeLong / 1000 + "秒前";
//        else if (timeLong < 60 * 60 * 1000) {
//            timeLong = timeLong / 1000 / 60;
//            return timeLong + "分钟前";
//        } else if (timeLong < 60 * 60 * 24 * 1000) {
//            timeLong = timeLong / 60 / 60 / 1000;
//            return timeLong + "小时前";
//        } else if ((timeLong / 1000 / 60 / 60 / 24) < 7) {
//            timeLong = timeLong / 1000 / 60 / 60 / 24;
//            return timeLong + "天前";
//        } else {
//            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
//            return formatter.format(startDate);
//        }
    }
}
