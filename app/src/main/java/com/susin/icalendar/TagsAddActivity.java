package com.susin.icalendar;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.susin.icalendar.common.BaseActivity;
import com.susin.icalendar.common.Setting;
import com.susin.icalendar.model.Record;
import com.susin.icalendar.util.UtilCalculate;
import com.susin.icalendar.widget.SlipButton;

import org.litepal.crud.DataSupport;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * 说明：
 *
 * @作者 Susin
 * @创建时间 2016/11/23 21:46
 */
public class TagsAddActivity extends BaseActivity {

    private SlipButton btnMenstruation;
//    private  SlipButton btnHabit;
//    private EditText etHealth;
    private Button btnSave;
    private String date;
    private TagsAddActivity tagsAddActivity = this;

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
//            btnHabit.setCheck(record.isHabit());
//            etHealth.setText(record.getHealth());
        }

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

        Drawable drawable = getBlur();
        if (drawable == null)
            drawable = new ColorDrawable(0xc0ffffff);
        getWindow().getDecorView().setBackgroundDrawable(drawable);

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
                    record.setMenstruation("0");
                }
            }
        });
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ContentValues values = new ContentValues();
//                values.put("health", etHealth.getText().toString());
                values.put("menstruation", record.getMenstruation());
//                values.put("habit", record.isHabit());
                // DataSupport.updateAll的返回值为修改的数据量
                if (DataSupport.updateAll(Record.class, values, "date = ?", date) > 0){

                    showToastShort("修改成功");
                }else{
//                    record.setHealth(etHealth.getText().toString());
                    record.setDate(date);
                    // record.save()的返回值为true或false
                    if (record.save()){

                        List<Record> listRecord = new ArrayList<Record>();
                        if("1".equals(record.getMenstruation())) {
                            List<String> days = UtilCalculate.last6Days(date);
                            for (int i = 0; i < days.size(); i++) {
                                Record record = new Record();
                                record.setDate(days.get(i));
                                record.setMenstruation("1");
                                listRecord.add(record);
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

                finish();
            }
        });

    }


    @Override
    protected int getLayout() {
        return R.layout.activity_tags_add;
    }
}