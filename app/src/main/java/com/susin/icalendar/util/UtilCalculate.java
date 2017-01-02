package com.susin.icalendar.util;


import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * 说明：
 *
 * @作者 zhangyi
 * @创建时间 2016/12/14 20:43
 * @版本
 * @------修改记录-------
 * @修改人
 * @版本
 * @修改内容
 */

public class UtilCalculate {

    /**
     * 获取过去某天的后六天(不包括未来)
     *
     * @param someday
     * @return
     */
    public static List<String> last6Days(String someday) {
        List<String> days = new ArrayList<>();
        Calendar c = Calendar.getInstance();
        c.setTime(UtilCalculate.stringToDate(someday));

        Date today = new Date();
        // 第二天
        c.add(Calendar.DAY_OF_YEAR, 1);
        Date today_plus1 = c.getTime();
        if (today_plus1.before(today)) {
            days.add(UtilCalculate.getStringDateShort(today_plus1));
        } else {
            return days;
        }


        c.add(Calendar.DAY_OF_YEAR, 1);
        Date today_plus2 = c.getTime();
        if (today_plus2.before(today))
            days.add(UtilCalculate.getStringDateShort(today_plus2));
        else {
            return days;
        }

        c.add(Calendar.DAY_OF_YEAR, 1);
        Date today_plus3 = c.getTime();
        if (today_plus3.before(today))
            days.add(UtilCalculate.getStringDateShort(today_plus3));
        else {
            return days;
        }

        c.add(Calendar.DAY_OF_YEAR, 1);
        Date today_plus4 = c.getTime();
        if (today_plus4.before(today))
            days.add(UtilCalculate.getStringDateShort(today_plus4));
        else {
            return days;
        }

        c.add(Calendar.DAY_OF_YEAR, 1);
        Date today_plus5 = c.getTime();
        if (today_plus5.before(today))
            days.add(UtilCalculate.getStringDateShort(today_plus5));
        else {
            return days;
        }

        c.add(Calendar.DAY_OF_YEAR, 1);
        Date today_plus6 = c.getTime();
        if (today_plus6.before(today))
            days.add(UtilCalculate.getStringDateShort(today_plus6));
        else {
            return days;
        }

        return days;
    }

    /**
     * 获取过去某天的前4天(不包括某天)
     *
     * @param someday
     * @return
     */
    public static List<String> before2Days(String someday) {
        List<String> days = new ArrayList<>();
        String pattern = "yyyy-M-d";
        SimpleDateFormat sdf = new SimpleDateFormat(pattern);
        Date date = null;
        try {
            date = sdf.parse(someday);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        Calendar c = Calendar.getInstance();
        c.setTime(date);

        // 前一天
        c.add(Calendar.DAY_OF_YEAR, -1);
        Date today_plus1 = c.getTime();
        days.add(UtilCalculate.getStringDateShort(today_plus1));

        c.add(Calendar.DAY_OF_YEAR, -1);
        Date today_plus2 = c.getTime();
        days.add(UtilCalculate.getStringDateShort(today_plus2));

        return days;
    }

    /**
     * 获取现在时间
     *
     * @return 返回短时间字符串格式yyyy-M-d
     */
    public static String getStringDateShort(Date currentTime) {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-M-d");
        String dateString = formatter.format(currentTime);
        return dateString;
    }

    /**
     * 获取现在时间
     *
     * @param date
     * @return
     */
    public static Date stringToDate(String date) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-M-d");//小写的mm表示的是分钟
        Date currentDay = null;
        try {
            currentDay = sdf.parse(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return currentDay;
    }

    /**
     * 获取前某一天时间
     *
     * @return 返回短时间字符串格式yyyy-M-d
     */
    public static String getDaysago(Date date, int days) {
        Date daysBefore;

        Calendar calendar = Calendar.getInstance(); //得到日历
        calendar.setTime(date);//把当前时间赋给日历
        calendar.add(Calendar.DAY_OF_MONTH, -days);  //设置为前某一天
        daysBefore = calendar.getTime();   //得到前某一天的时间


        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-M-d"); //设置时间格式
        String Daysago = sdf.format(daysBefore);    //格式化前某一天的时间

        return Daysago;
    }


    /**
     * 获取过去六个月的年月信息，用于表格查询数据
     *
     * @return
     */
    public static String[] getLast6YearMonths() {
        String[] last6Months = new String[6];
        String[] last6YearMonths = new String[6];

        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.MONTH, cal.get(Calendar.MONTH) + 1); //要先+1,才能把本月的算进去</span>
        for (int i = 0; i < 6; i++) {
            cal.set(Calendar.MONTH, cal.get(Calendar.MONTH) - 1); //逐次往前推1个月
            last6Months[5 - i] = cal.get(Calendar.MONTH) + 1 + "";
            last6YearMonths[5 - i] = cal.get(Calendar.YEAR) + "-" + last6Months[5 - i];
        }

        return last6YearMonths;
    }

    /**
     * 获取过去六个月的月份信息，用于表格横坐标显示
     *
     * @return
     */
    public static int[] getLast6Months() {

        int[] last6Months = new int[6];

        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.MONTH, cal.get(Calendar.MONTH) + 1); //要先+1,才能把本月的算进去</span>
        for (int i = 0; i < 6; i++) {
            cal.set(Calendar.MONTH, cal.get(Calendar.MONTH) - 1); //逐次往前推1个月
            last6Months[5 - i] = cal.get(Calendar.MONTH) + 1;
        }

        return last6Months;
    }
}
