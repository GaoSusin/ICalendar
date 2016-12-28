package com.susin.icalendar.util;

import com.susin.icalendar.model.Record;

import org.litepal.crud.DataSupport;

import java.text.ParseException;
import java.text.ParsePosition;
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
     * @param someday
     * @return
     */
    public static List<String> last6Days(String someday){
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

        Date today = new Date();
        // 第二天
        c.add(Calendar.DAY_OF_YEAR, 1);
        Date today_plus1 = c.getTime();
        if (today_plus1.before(today)){
            days.add(UtilCalculate.getStringDateShort(today_plus1));
        } else{
            return days;
        }


        c.add(Calendar.DAY_OF_YEAR, 1);
        Date today_plus2 = c.getTime();
        if (today_plus2.before(today))
            days.add(UtilCalculate.getStringDateShort(today_plus2));
        else{
            return days;
        }

        c.add(Calendar.DAY_OF_YEAR, 1);
        Date today_plus3 = c.getTime();
        if (today_plus3.before(today))
            days.add(UtilCalculate.getStringDateShort(today_plus3));
        else{
            return days;
        }

        c.add(Calendar.DAY_OF_YEAR, 1);
        Date today_plus4 = c.getTime();
        if (today_plus4.before(today))
            days.add(UtilCalculate.getStringDateShort(today_plus4));
        else{
            return days;
        }

        c.add(Calendar.DAY_OF_YEAR, 1);
        Date today_plus5 = c.getTime();
        if (today_plus5.before(today))
            days.add(UtilCalculate.getStringDateShort(today_plus5));
        else{
            return days;
        }

        c.add(Calendar.DAY_OF_YEAR, 1);
        Date today_plus6 = c.getTime();
        if (today_plus6.before(today))
            days.add(UtilCalculate.getStringDateShort(today_plus6));
        else{
            return days;
        }

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
     * 获取前一天时间
     *
     * @return 返回短时间字符串格式yyyy-M-d
     */
    public static String getYesterday() {
        Date dNow = new Date();   //当前时间
        Date dBefore;

        Calendar calendar = Calendar.getInstance(); //得到日历
        calendar.setTime(dNow);//把当前时间赋给日历
        calendar.add(Calendar.DAY_OF_MONTH, -1);  //设置为前一天
        dBefore = calendar.getTime();   //得到前一天的时间


        SimpleDateFormat sdf=new SimpleDateFormat("yyyy-M-d"); //设置时间格式
        String yesterday = sdf.format(dBefore);    //格式化前一天

        return yesterday;
    }

    public static int[] getLast6Months(){

        int[] last6Months = new int[6];

        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.MONTH, cal.get(Calendar.MONTH)+1); //要先+1,才能把本月的算进去</span>
        for(int i=0; i<6; i++){
            cal.set(Calendar.MONTH, cal.get(Calendar.MONTH)-1); //逐次往前推1个月
            last6Months[5-i] = cal.get(Calendar.MONTH)+ 1;
        }

        return last6Months;
    }


}
