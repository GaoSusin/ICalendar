package com.susin.icalendar.fragment;

import android.content.Intent;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import com.susin.icalendar.R;
import com.susin.icalendar.TagsAddActivity;
import com.susin.icalendar.common.Setting;
import com.susin.icalendar.model.Record;
import com.susin.icalendar.util.UtilCalculate;

import org.litepal.crud.DataSupport;

import cn.aigestudio.datepicker.bizs.calendars.DPCManager;
import cn.aigestudio.datepicker.bizs.decors.DPDecor;
import cn.aigestudio.datepicker.cons.DPMode;
import cn.aigestudio.datepicker.views.DatePicker;

/**
 * 说明：日历Fragment
 *
 * @作者 Susin
 * @创建时间 2016/12/1 21:47
 * @版本
 * @------修改记录-------
 * @修改人
 * @版本
 * @修改内容
 */

public class CalendarFragment extends MyBaseFragment {

    private Calendar c;
    private DatePicker picker;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        //
        if (mView == null) {
            mView = inflater.inflate(R.layout.fragment_calendar, container, false);
            initView();
            initData();
        }
        return mView;
    }

    private void initView() {

        picker = findView(R.id.main_dp);
    }

    private void initData() {
        c = Calendar.getInstance();
        int year = c.get(java.util.Calendar.YEAR);
        int month =  c.get(java.util.Calendar.MONTH) + 1;
        int day = c.get(java.util.Calendar.DATE);
        String date = year + "-" + month + "-" + day;

        // 获取前一天
        List<Record> records = DataSupport.where("date = ?", UtilCalculate.getYesterday()).find(Record.class);
        if (records.size() > 0){
            Record recordYesterday = records.get(0);
            // 判断昨天是否来姨妈，如果来了今天也得来
            if ("1".equals(recordYesterday.getMenstruation())){

                // 查询数据库是否有今天的数据
                List<Record> recordToday = DataSupport.where("date = ?", date).find(Record.class);
                boolean isSave = recordToday.size() > 0 ? false : true;
                // 避免重复存操作
                if(isSave){
                    Record record = new Record();
                    record.setMenstruation("1");
                    record.setDate(date);
                    record.save();
                }
            }
        }

        picker.setDate(year, month);
        // 单选模式
        picker.setMode(DPMode.SINGLE);


        // DatePicked单选模式下的监听
        picker.setOnDatePickedListener(new DatePicker.OnDatePickedListener() {
            @Override
            public void onDatePicked(String date) {
                TagsAddActivity.actionStart(getActivity(), date);
            }
        });

//        picker.setDPDecor(new DPDecor() {
//            @Override
//            public void drawDecorBG(Canvas canvas, Rect rect, Paint paint) {
//                paint.setColor(Color.GRAY);
//                canvas.drawCircle(rect.centerX(), rect.centerY(), rect.width() / 2F, paint);
//            }
//        });

    }


    @Override
    public void onResume() {
        super.onResume();

        // 清空缓存
        DPCManager.getInstance().getDateCache().clear();
        DPCManager.getInstance().getDecorCacheBg().clear();
        DPCManager.getInstance().getDecorCacheTr().clear();

        List<Record> records = DataSupport.findAll(Record.class);
        List<String> menstruationdays = new ArrayList<>();
        List<String> habitdays = new ArrayList<>();
        if (records.size() > 0){
            for (int i = 0; i < records.size(); i++) {
                Record record = records.get(i);
                String date = record.getDate();
                if("1".equals(record.getMenstruation())){
                    menstruationdays.add(date);
                }
//                if(record.isHabit()){
//                    habitdays.add(date);
//                }
            }

            DPCManager.getInstance().setDecorBG(menstruationdays);
            DPCManager.getInstance().setDecorTR(habitdays);
            picker.setDPDecor(new DPDecor() {
                @Override
                public void drawDecorBG(Canvas canvas, Rect rect, Paint paint) {
                    paint.setColor(Color.RED);
                    canvas.drawCircle(rect.centerX(), rect.centerY(), rect.width() / 2F, paint);
                }

                public void drawDecorTR(Canvas canvas, Rect rect, Paint paint) {
                    paint.setColor(Color.DKGRAY);
                    canvas.drawCircle(rect.centerX(), rect.centerY(), rect.width() / 2F, paint);
                }
            });
        }

        // 刷新视图
        picker.getMonthView().invalidate();
    }
}