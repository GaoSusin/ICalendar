package com.susin.icalendar;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.susin.icalendar.common.BaseActivity;
import com.susin.icalendar.model.Record;
import com.susin.icalendar.util.UtilCalculate;
import com.susin.icalendar.widget.SlipButton;

import org.litepal.crud.DataSupport;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 说明：
 *
 * @作者 Susin
 * @创建时间 2016/11/23 21:46
 */
public class TagsAddActivity extends BaseActivity {

    private SlipButton btnMenstruation;
    private TextView tvText;
    private Button btnSave;
    private String date;
    private Date currentDay;
    private TagsAddActivity tagsAddActivity = this;
    // flagMenstruation = 0, 为姨妈还未来时的状态；
    // flagMenstruation = 1, 延长行经期；
    // flagMenstruation = 2, 缩短行经期；
    private int flagMenstruation = 0;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        initData();

    }

    private void initData() {
        this.date = getIntent().getExtras().getString("date");
        List<Record> records = DataSupport.where("date = ?", date).find(Record.class);
        if(records.size() > 0){
            Record record = records.get(0);
            btnMenstruation.setCheck("1".equals(record.getMenstruation()));
        }

        currentDay = UtilCalculate.stringToDate(date);
        List<Record> threedayRecords = DataSupport.where("date = ? and menstruation = ?", UtilCalculate.getDaysago(currentDay, 3), "1").find(Record.class);
        List<Record> onedayRecords = DataSupport.where("date = ? and menstruation = ?", UtilCalculate.getDaysago(currentDay, -1), "1").find(Record.class);
       // 如果当前日的三天前及第二天有来经记录，置标志位为2（即缩短经期）
        if((threedayRecords.size() > 0 && onedayRecords.size() > 0)) {
            tvText.setText("姨妈是否走咯~~~");
            btnMenstruation.setCheck(false);
            flagMenstruation = 2;
        }
        // 如果只满足三天前有来经记录，置标志位为1（即延长经期）
        else if(threedayRecords.size() > 0 ){
            tvText.setText("姨妈是否走咯~~~");
            btnMenstruation.setCheck(false);
            flagMenstruation = 1;
        }
        // 标志位为0，正常记录经期
        else {
            tvText.setText("姨妈是否来了");
            flagMenstruation = 0;
        }

        Drawable drawable = getBlur();
        if (drawable == null)
            drawable = new ColorDrawable(0xc0ffffff);
        getWindow().getDecorView().setBackgroundDrawable(drawable);
    }

    public static void actionStart(Activity context, String date) {
        Intent intent = new Intent(context, TagsAddActivity.class);
        Bundle bundle = new Bundle();
        bundle.putString("date", date);
        intent.putExtras(bundle);
        context.startActivity(intent);
        setBlur(context);
    }

    @Override
    protected void initView() {

        btnMenstruation = findView(R.id.btn_menstruation);
        btnSave = findView(R.id.btn_save);
        tvText = findView(R.id.tv_text);

        requestListener();
    }

    private void requestListener() {

        final Record record = new Record();

        btnMenstruation.setOnChangedListener(new SlipButton.OnChangedListener() {
            @Override
            public void OnChanged(View v, boolean CheckState) {
                if(CheckState){
                    record.setMenstruation("1");

                }else{
                    if(flagMenstruation == 0){
                        record.setMenstruation("0");
                    }else {
                        record.setMenstruation("1");
                    }
                }
            }
        });
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ContentValues values = new ContentValues();
                List<Record> listRecord = new ArrayList<Record>();
                values.put("menstruation", record.getMenstruation());
                if (DataSupport.updateAll(Record.class, values, "date = ?", date) > 0){
                    if(flagMenstruation == 1){
                        List<String> days = UtilCalculate.before2Days(date);
                        for (int i = 0; i < days.size(); i++) {
                            values.put("menstruation", "1");
                            if(DataSupport.updateAll(Record.class, values, "date = ?", days.get(i)) <= 0){
                                Record record = new Record();
                                record.setDate(days.get(i));
                                record.setMenstruation("1");
                                listRecord.add(record);
                            }

                        }
                    }else if(flagMenstruation == 2){
                        List<String> days = UtilCalculate.last6Days(date);
                        for (int i = 0; i < days.size(); i++) {
                            values.put("menstruation", "0");
                            if(DataSupport.updateAll(Record.class, values, "date = ?", days.get(i)) <= 0){
                                Record record = new Record();
                                record.setDate(days.get(i));
                                record.setMenstruation("0");
                                listRecord.add(record);
                            }
                        }
                    }else if(flagMenstruation == 0){
                        List<String> days = UtilCalculate.last6Days(date);
                        for (int i = 0; i < days.size(); i++) {
                            values.put("menstruation", "1");
                            if(DataSupport.updateAll(Record.class, values, "date = ?", days.get(i)) <= 0){
                                Record record = new Record();
                                record.setDate(days.get(i));
                                record.setMenstruation("1");
                                listRecord.add(record);
                            }
                        }
                    }
                    if (listRecord.size() > 0){
                        DataSupport.saveAll(listRecord);
                    }
                    showToastShort("修改成功");
                }else{
                    record.setDate(date);
                    if (record.save()){
                            if(flagMenstruation == 0){
                                List<String> days = UtilCalculate.last6Days(date);
                                for (int i = 0; i < days.size(); i++) {
                                    values.put("menstruation", "1");
                                    if(DataSupport.updateAll(Record.class, values, "date = ?", days.get(i)) <= 0){
                                        Record record = new Record();
                                        record.setDate(days.get(i));
                                        record.setMenstruation("1");
                                        listRecord.add(record);
                                    }
                                }
                            }else if(flagMenstruation == 1){
                                List<String> days = UtilCalculate.before2Days(date);
                                for (int i = 0; i < days.size(); i++) {
                                    values.put("menstruation", "1");
                                    if(DataSupport.updateAll(Record.class, values, "date = ?", days.get(i)) <= 0){
                                        Record record = new Record();
                                        record.setDate(days.get(i));
                                        record.setMenstruation("1");
                                        listRecord.add(record);
                                    }
                                }
                            }
                        if (listRecord.size() > 0){
                            DataSupport.saveAll(listRecord);
                        }
                        showToastShort("保存成功");
                    }else{
                        showToastShort("保存失败");
                    }
                }

                Intent intent = new Intent();
                intent.setAction("action.refreshData");
                sendBroadcast(intent);

                finish();
            }
        });

    }


    @Override
    protected int getLayout() {
        return R.layout.activity_tags_add;
    }
}