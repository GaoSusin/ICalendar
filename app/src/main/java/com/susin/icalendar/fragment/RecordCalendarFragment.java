package com.susin.icalendar.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.susin.icalendar.Adapter.HabitTypeAdapter;
import com.susin.icalendar.CalendarRecordsActivity;
import com.susin.icalendar.R;

/**
 * 说明：
 *
 * @作者 Susin
 * @创建时间 2016/11/23 21:46
 */
public class RecordCalendarFragment extends MyBaseFragment {

    private ImageView ivShit, ivPimple, ivDiarrhea, ivSheep, ivInsomnia, ivHeadache, ivSoreThroat;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        //
        if (mView == null) {
            mView = inflater.inflate(R.layout.fragment_record_calendar, container, false);
            initView();
            initData();
        }
        return mView;
    }

    private void initData() {

    }

    private void initView() {
        ivShit = findView(R.id.iv_edit_shit);
        ivPimple = findView(R.id.iv_edit_pimple);
        ivDiarrhea = findView(R.id.iv_edit_diarrhea);
        ivSheep = findView(R.id.iv_edit_sheep);
        ivInsomnia = findView(R.id.iv_edit_insomnia);
        ivHeadache = findView(R.id.iv_edit_headache);
        ivSoreThroat = findView(R.id.iv_edit_sore_throat);

        requestListener();
    }

    private void requestListener() {

        ivShit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle bundle = new Bundle();
                bundle.putString("type", "0");
                startActivity(CalendarRecordsActivity.class, bundle);
            }
        });
        ivPimple.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle bundle = new Bundle();
                bundle.putString("type", "1");
                startActivity(CalendarRecordsActivity.class, bundle);
            }
        });
        ivDiarrhea.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle bundle = new Bundle();
                bundle.putString("type", "2");
                startActivity(CalendarRecordsActivity.class, bundle);
            }
        });
        ivSheep.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle bundle = new Bundle();
                bundle.putString("type", "3");
                startActivity(CalendarRecordsActivity.class, bundle);
            }
        });
        ivInsomnia.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle bundle = new Bundle();
                bundle.putString("type", "4");
                startActivity(CalendarRecordsActivity.class, bundle);
            }
        });
        ivHeadache.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle bundle = new Bundle();
                bundle.putString("type", "5");
                startActivity(CalendarRecordsActivity.class, bundle);
            }
        });
        ivSoreThroat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle bundle = new Bundle();
                bundle.putString("type", "6");
                startActivity(CalendarRecordsActivity.class, bundle);
            }
        });
    }
}