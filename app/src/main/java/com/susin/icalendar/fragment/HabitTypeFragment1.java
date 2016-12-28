package com.susin.icalendar.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.susin.icalendar.Adapter.HabitTypeAdapter;
import com.susin.icalendar.R;

/**
 * 说明：
 *
 * @作者 Susin
 * @创建时间 2016/11/23 21:46
 */
public class HabitTypeFragment1 extends MyBaseFragment {

    private ListView lvType;
    private HabitTypeAdapter typeAdapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        //
        if (mView == null) {
            mView = inflater.inflate(R.layout.fragment_habit_type1, container, false);
            initView();
            initData();
        }
        return mView;
    }

    private void initData() {

    }

    private void initView() {
        lvType = findView(R.id.lv_type);
        typeAdapter = new HabitTypeAdapter();
        lvType.setAdapter(typeAdapter);
    }
}