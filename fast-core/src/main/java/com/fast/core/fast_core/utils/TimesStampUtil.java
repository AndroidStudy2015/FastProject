package com.fast.core.fast_core.utils;


import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by Administrator on 2016/2/18 0018.
 */
public class TimesStampUtil {
    public static String getTime(long time) {

        return new SimpleDateFormat("yyyy-MM-dd").format(new Date(time));
    }

    public static String getTime2(long time) {

        return new SimpleDateFormat("yyyy/MM/dd HH:mm").format(new Date(time));
    }
    public static String getTime10(long time) {

        return new SimpleDateFormat("yyyy/MM/dd HH:mm").format(new Date(time));
    }

    public static String getTime8(long time) {

        return new SimpleDateFormat("MM/dd HH:mm").format(new Date(time));
    }

    public static String getTime4(long time) {

        return new SimpleDateFormat("MM/dd HH:mm").format(new Date(time));
    }

    public static String getTime3(long time) {

        return new SimpleDateFormat("mm:ss").format(new Date(time));
    }

    public static String getTime5(long time) {

        return new SimpleDateFormat("yyyy年MM月dd日").format(new Date(time));
    }

    public static String getTime6(long time) {

        return new SimpleDateFormat("MM/dd").format(new Date(time));
    }

    public static String getTime9(long time) {

        return new SimpleDateFormat("MM月dd").format(new Date(time));
    }

    public static String getTime7(long time) {

        return new SimpleDateFormat("HH:mm").format(new Date(time));
    }

    public static String getWeek(long time) {
        String[] weeks = {"周日", "周一", "周二", "周三", "周四", "周五", "周六"};
        Calendar cal = Calendar.getInstance();
        cal.setTime(new Date(time));
        int week_index = cal.get(Calendar.DAY_OF_WEEK) - 1;
        if (week_index < 0) {
            week_index = 0;
        }
        return weeks[week_index];
    }

    /**
     * 今天 10-19
     * @param time
     * @return
     */
    public static String getJintianMingtianHoutian(long time) {
        String result = "";
        Date newDate = new Date(time);
        SimpleDateFormat sdf = new SimpleDateFormat("MM-dd");
        String riqi = sdf.format(newDate);
        Calendar aCalendar = Calendar.getInstance();
        aCalendar.setTime(newDate);
        int day1 = aCalendar.get(Calendar.DAY_OF_YEAR);
        aCalendar.setTime(new Date());
        int day2 = aCalendar.get(Calendar.DAY_OF_YEAR);
        if (day1 - day2 == 0) {
            result += "今天 " + riqi;
        } else if (day1 - day2 == 1) {
            result += "明天 " + riqi;
        } else if (day1 - day2 == 2) {
            result += "后天 " + riqi;
        } else {
            result += getWeek(time) + " " + riqi;
        }
        return result;
    }

    /**
     * 今天 10月19日
     * @param time
     * @return
     */
    public static String getDayAndWeek(long time) {
        String result = "";
        Date newDate = new Date(time);
        SimpleDateFormat sdf = new SimpleDateFormat("MM月dd日");
        String riqi = sdf.format(newDate);
        Calendar aCalendar = Calendar.getInstance();
        aCalendar.setTime(newDate);
        int day1 = aCalendar.get(Calendar.DAY_OF_YEAR);
        aCalendar.setTime(new Date());
        int day2 = aCalendar.get(Calendar.DAY_OF_YEAR);
        if (day1 - day2 == 0) {
            result += "今天 " + riqi;
        } else if (day1 - day2 == 1) {
            result += "明天 " + riqi;
        } else if (day1 - day2 == 2) {
            result += "后天 " + riqi;
        } else {
            result += getWeek(time) + " " + riqi;
        }
        return result;
    }

    /**
     * 今天 10-19 15:44
     * @param time
     * @return
     */
    public static String getDayAndWeek2(long time) {
        String result = "";
        Date newDate = new Date(time);
        SimpleDateFormat sdf = new SimpleDateFormat("MM-dd HH:mm");
        String riqi = sdf.format(newDate);
        Calendar aCalendar = Calendar.getInstance();
        aCalendar.setTime(newDate);
        int day1 = aCalendar.get(Calendar.DAY_OF_YEAR);
        aCalendar.setTime(new Date());
        int day2 = aCalendar.get(Calendar.DAY_OF_YEAR);
        if (day1 - day2 == 0) {
            result += "今天 " + riqi;
        } else if (day1 - day2 == 1) {
            result += "明天 " + riqi;
        } else if (day1 - day2 == 2) {
            result += "后天 " + riqi;
        } else {
            result += getWeek(time) + " " + riqi;
        }
        return result;
    }
    private static final int seconds_of_1minute = 60;

    private static final int seconds_of_30minutes = 30 * 60;

    private static final int seconds_of_1hour = 60 * 60;

    private static final int seconds_of_1day = 24 * 60 * 60;

    private static final int seconds_of_2day = seconds_of_1day * 2;

    private static final int seconds_of_15days = seconds_of_1day * 15;

    private static final int seconds_of_30days = seconds_of_1day * 30;

    private static final int seconds_of_6months = seconds_of_30days * 6;

    private static final int seconds_of_1year = seconds_of_30days * 12;


    /**
     * @return timtPoint距离现在经过的时间，分为
     * 刚刚，1-29分钟前，半小时前，1-23小时前，1-14天前，半个月前，1-5个月前，半年前，1-xxx年前
     */
    public static String getTimeElapse(long createTime) {

        long nowTime = new Date().getTime() / 1000;

        //createTime是发表文章的时间

        long oldTime = createTime;

        //elapsedTime是发表和现在的间隔时间

        long elapsedTime = nowTime - oldTime;

        if (elapsedTime < seconds_of_1minute) {

            return "刚刚";
        }
        if (elapsedTime < seconds_of_1hour) {

            return elapsedTime / seconds_of_1minute + "分钟前";
        }
//        if (elapsedTime < seconds_of_1hour) {
//
//            return "半小时前";
//        }
        if (elapsedTime < seconds_of_1day) {

            return elapsedTime / seconds_of_1hour + "小时前";
        }
//        if (elapsedTime < seconds_of_2day) {
//
//            return "昨天";
//        }
        if (elapsedTime < seconds_of_15days) {

            return elapsedTime / seconds_of_1day + "天前";
        }

        if (elapsedTime < seconds_of_30days) {

            return "半个月前";
        }
        if (elapsedTime < seconds_of_6months) {

            return elapsedTime / seconds_of_30days + "个月前";
        }
        if (elapsedTime < seconds_of_1year) {

            return "半年前";
        }
        if (elapsedTime >= seconds_of_1year) {

            return elapsedTime / seconds_of_1year + "年前";
        }

        return "";
    }


//    // 将字符串转为时间戳
//
//
//    public static String getString2Time(String user_time) {
//        String re_time = null;
//        SimpleDateFormat sdf = new SimpleDateFormat("yyyy年MM月dd日HH时mm分ss秒");
//        Date d;
//        try {
//
//
//            d = sdf.parse(user_time);
//            long l = d.getTime();
//            String str = String.valueOf(l);
//            re_time = str.substring(0, 10);
//
//
//        } catch (Exception e) {
//// TODO Auto-generated catch block
//            e.printStackTrace();
//        }
//        return re_time;
//    }

//public static String getDayAndWeek(long time){
//        String result = "";
//        Date newDate = new Date(time);
//        SimpleDateFormat sdf = new SimpleDateFormat("MM-dd");
//        String riqi = sdf.format(newDate);
//        Calendar aCalendar = Calendar.getInstance();
//        aCalendar.setTime(newDate);
//        int day1 = aCalendar.get(Calendar.DAY_OF_YEAR);
//        aCalendar.setTime(new Date());
//        int day2 = aCalendar.get(Calendar.DAY_OF_YEAR);
//        if(day2 - day1 == 0){
//            result += "今天 " + riqi;
//        }else if(day2 - day1 == 1){
//            result += "明天 " + riqi;
//        }else if(day2 - day1 == 2){
//            result += "后天 " + riqi;
//        }else{
//            result += getWeek(time) + " " + riqi;
//        }
//        return result;
//    }


    //    public static String getTime2(long time) {
//
//        return new SimpleDateFormat("HH:mm").format(new Date(time));
//    }
    public static String getTime(String time) {
        long t = Long.parseLong(time);
        return getTime(t);
    }

    public static String getDate(String month, String day) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//24小时制
        Date d = new Date();
        String str = sdf.format(d);
        String nowmonth = str.substring(5, 7);
        String nowday = str.substring(8, 10);
        String result = null;

        int temp = Integer.parseInt(nowday) - Integer.parseInt(day);
        switch (temp) {
            case 0:
                result = "今天";
                break;
            case 1:
                result = "昨天";
                break;
            case 2:
                result = "前天";
                break;
            default:
                StringBuilder sb = new StringBuilder();
                sb.append(Integer.parseInt(month) + "月");
                sb.append(Integer.parseInt(day) + "日");
                result = sb.toString();
                break;
        }
        return result;
    }

    public static String getTimeQian(long timestamp) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String time = null;
        try {
            Date currentdate = new Date();//当前时间

            long i = (currentdate.getTime() / 1000 - timestamp) / (60);
            Timestamp now = new Timestamp(System.currentTimeMillis());//获取系统当前时间 //返回结果精确到毫秒。
            String str = sdf.format(new Timestamp(timestamp));
            time = str.substring(11, 16);
            String month = str.substring(5, 7);
            String day = str.substring(8, 10);
            time = getDate(month, day) + time;
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return time;
    }

    //java Timestamp构造函数需传入Long型
    public static long IntToLong(int i) {
        long result = (long) i;
        result *= 1000;
        return result;
    }

    //计算给定一个准确时间后经过多长时间够得到新的时间
    public static String addDateMinut(String time, int x)//返回的是字符串型的时间，输入的
//是String day, int x
    {
        SimpleDateFormat format = new SimpleDateFormat("HH:mm");// 24小时制
//引号里面个格式也可以是 HH:mm:ss或者HH:mm等等，很随意的，不过在主函数调用时，要和输入的变
//量day格式一致
        Date date = null;
        try {
            date = format.parse(time);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        if (date == null)
            return "";
        // System.out.println("front:" + format.format(date)); //显示输入的日期
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.add(Calendar.MINUTE, x);// 24小时制
        date = cal.getTime();
        //System.out.println("after:" + format.format(date));  //显示更新后的日期
        cal = null;
        return format.format(date);

    }

    public static long getLongTime(String time) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        Date date = null;
        try {
            date = sdf.parse(time);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date.getTime();
    }
    public static long getPaiQiLongTime(String time) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date date = null;
        try {
            date = sdf.parse(time);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date.getTime();
    }
}
