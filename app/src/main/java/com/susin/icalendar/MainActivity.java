package com.susin.icalendar;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.widget.ImageView;

import com.susin.icalendar.common.MyFragmentActivity;
import com.susin.icalendar.fragment.CalendarFragment;
import com.susin.icalendar.fragment.ChartFragment;
import com.susin.icalendar.fragment.RecordCalendarFragment;

import java.util.ArrayList;

public class MainActivity extends MyFragmentActivity {

    private ImageView ivFirst, ivSecond,ivThird;
    private ViewPager mPager;
    private CalendarFragment calendarFragment;
    private ChartFragment chartFragment;
    private RecordCalendarFragment typeListFragment;
    private MyFragmentPagerAdapter adapter;
    private ArrayList<Fragment> fragmentsList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    protected void setContenView() {
        setContentView(R.layout.activity_main);
    }

    @Override
    protected void initView() {
        addFragmentList();
        ivFirst = findView(R.id.iv_first);
        ivSecond = findView(R.id.iv_second);
        ivThird = findView(R.id.iv_third);
        mPager = findView(R.id.meal_viewpager);
        initViewPager();


    }

    private void addFragmentList() {
        fragmentsList = new ArrayList<Fragment>();
        calendarFragment = new CalendarFragment();
        fragmentsList.add(calendarFragment);
        chartFragment = new ChartFragment();
        fragmentsList.add(chartFragment);
        typeListFragment = new RecordCalendarFragment();
        fragmentsList.add(typeListFragment);
    }

    private void initViewPager() {
        adapter = new MyFragmentPagerAdapter(getSupportFragmentManager(), fragmentsList);

        mPager.setAdapter(adapter);
        mPager.setOnPageChangeListener(new MyOnPageChangeListener());
    }

    public class MyOnPageChangeListener implements ViewPager.OnPageChangeListener {

        @Override
        public void onPageSelected(int arg0) {
            switch (arg0) {
                // 日历
                case 0:
                    ivFirst.setBackgroundResource(R.drawable.point_banner_sel);
                    ivSecond.setBackgroundResource(R.drawable.point_banner_nor);
                    ivThird.setBackgroundResource(R.drawable.point_banner_nor);
                    break;
                // 倒数日
                case 1:
                    ivFirst.setBackgroundResource(R.drawable.point_banner_nor);
                    ivSecond.setBackgroundResource(R.drawable.point_banner_sel);
                    ivThird.setBackgroundResource(R.drawable.point_banner_nor);
                    break;
                //
                case 2:
                    ivFirst.setBackgroundResource(R.drawable.point_banner_nor);
                    ivSecond.setBackgroundResource(R.drawable.point_banner_nor);
                    ivThird.setBackgroundResource(R.drawable.point_banner_sel);
                    break;
            }
        }

        @Override
        public void onPageScrolled(int arg0, float arg1, int arg2) {
        }

        @Override
        public void onPageScrollStateChanged(int arg0) {
        }
    }

    private class MyFragmentPagerAdapter extends FragmentPagerAdapter {
        private ArrayList<Fragment> fragmentsList;

        public MyFragmentPagerAdapter(FragmentManager fm, ArrayList<Fragment> fragments) {
            super(fm);
            this.fragmentsList = fragments;
        }

        @Override
        public int getCount() {
            return fragmentsList.size();
        }

        @Override
        public Fragment getItem(int arg0) {
            return fragmentsList.get(arg0);
        }

        @Override
        public int getItemPosition(Object object) {
            return super.getItemPosition(object);
        }

    }
}
