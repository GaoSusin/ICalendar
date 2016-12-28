package com.susin.icalendar;

import android.app.Activity;
import android.content.ContentValues;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.os.Bundle;

import com.susin.icalendar.common.BaseActivity;
import com.susin.icalendar.model.Record;

import org.litepal.crud.DataSupport;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import cn.aigestudio.datepicker.bizs.calendars.DPCManager;
import cn.aigestudio.datepicker.bizs.decors.DPDecor;
import cn.aigestudio.datepicker.cons.DPMode;
import cn.aigestudio.datepicker.views.DatePicker;

/**
 * 说明：
 *
 * @作者 Susin
 * @创建时间 2016/11/23 21:46
 */
public class CalendarRecordsActivity extends BaseActivity {

    private DatePicker picker;
    private Record record;
    private String type;

    @Override
    protected void initView() {
        picker = findView(R.id.main_dp);
    }

    @Override
    protected int getLayout() {
        return R.layout.fragment_calendar;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initData();
    }

    private void initData() {
        Calendar c;
        c = Calendar.getInstance();
        int year = c.get(java.util.Calendar.YEAR);
        int month =  c.get(java.util.Calendar.MONTH) + 1;
        picker.setDate(year, month);
        // 单选模式
        picker.setMode(DPMode.SINGLE);
        type = getIntent().getExtras().getString("type");
        refresh();

        // DatePicked单选模式下的监听
        picker.setOnDatePickedListener(new DatePicker.OnDatePickedListener() {
            @Override
            public void onDatePicked(String date) {

                List<Record> recordes = DataSupport.where("date = ?", date).find(Record.class);
                if(recordes.size() > 0){
                    ContentValues values = new ContentValues();
                    values.put("habit", type);
                    DataSupport.updateAll(Record.class, values, "date = ?", date);
                }else{
                    record = new Record();
                    record.setHabit(type);
                    record.setDate(date);
                    record.save();
                }
                refresh();
            }
        });
    }

    private void refresh(){
        // 清空缓存
        DPCManager.getInstance().getDateCache().clear();
        DPCManager.getInstance().getDecorCacheBg().clear();
        DPCManager.getInstance().getDecorCacheTr().clear();
        List<Record> records = DataSupport.where("habit = ?", type).find(Record.class);
        List<String> recordDays = new ArrayList<>();
        if (records.size() > 0){
            for (int i = 0; i < records.size(); i++) {
                Record record = records.get(i);
                String recordDate = record.getDate();
                    recordDays.add(recordDate);
            }

            DPCManager.getInstance().setDecorBG(recordDays);
            picker.setDPDecor(new DPDecor() {
                @Override
                public void drawDecorBG(Canvas canvas, Rect rect, Paint paint) {
                    Bitmap bmp = null;
                    if("0".equals(type)){
                        bmp = BitmapFactory.decodeResource(getResources(),
                                R.drawable.ic_bianbian);
                    }else if("1".equals(type)){
                        bmp = BitmapFactory.decodeResource(getResources(),
                                R.drawable.ic_pimple);
                    }else if("2".equals(type)){
                        bmp = BitmapFactory.decodeResource(getResources(),
                                R.drawable.ic_diarrhea);
                    }else if("3".equals(type)){
                        bmp = BitmapFactory.decodeResource(getResources(),
                                R.drawable.ic_sheep);
                    }else if("4".equals(type)){
                        bmp = BitmapFactory.decodeResource(getResources(),
                                R.drawable.ic_insomnia);
                    }else if("5".equals(type)){
                        bmp = BitmapFactory.decodeResource(getResources(),
                                R.drawable.ic_headache);
                    }else if("6".equals(type)){
                        bmp = BitmapFactory.decodeResource(getResources(),
                                R.drawable.ic_sore_throat);
                    }
                    canvas.drawBitmap(bmp, rect.centerX(), rect.centerY(), paint);
                }
            });
        }

        // 刷新视图
        picker.getMonthView().invalidate();
    }
}