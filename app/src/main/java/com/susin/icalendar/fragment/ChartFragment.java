package com.susin.icalendar.fragment;

import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.susin.icalendar.R;
import com.susin.icalendar.model.Record;
import com.susin.icalendar.util.UtilCalculate;
import com.susin.icalendar.util.UtilPhoneParam;
import com.susin.icalendar.util.UtilUnitConversion;
import com.susin.icalendar.widget.SplineChart03View;

import org.litepal.crud.DataSupport;
import org.xclcharts.chart.BarChart;
import org.xclcharts.chart.PointD;
import org.xclcharts.event.click.BarPosition;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import static org.litepal.crud.DataSupport.where;

/**
 * 说明：
 *
 * @作者 Susin
 * @创建时间 2016/11/23 21:46
 */
public class ChartFragment extends MyBaseFragment {

    private Calendar c;

    private SplineChart03View splineChart03View;

    private TextView tvTitle;
    private Double minNum = 0.00, maxNum = 0.00, avgNum;
    private boolean isUser;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        UtilPhoneParam.init(getActivity());
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        if (mView == null) {
            mView = inflater.inflate(R.layout.fragment_chart, container, false);

            initViews();
            getHisBillDate();
        }
        return mView;
    }

    private void initViews() {
        splineChart03View = findView(R.id.spChart);
        splineChart03View.setOnClickListener(new SplineChart03View.OnClickListener() {
            @Override
            public void onClickListener(float x, float y, BarChart barChart, List<Integer> months) {
                intentClick(x, y, barChart, months);
            }
        });
    }

    // 跳转
    private void intentClick(float x, float y, BarChart barChart, List<Integer> months) {
        BarPosition record = barChart.getPositionRecord(x, y);
        if (null == record)
            return;

        String month = months.get(record.getDataChildID()) + "";
        Toast.makeText(getActivity(),  month + "月", Toast.LENGTH_SHORT).show();
    }

    private void initViews(double arrBill[]) {
        // 瞄点值 BillAmount1代表最近的月份，应显示在曲线的最右边，BillAmount5代表最远的月份，应显示在曲线的最左边
        List<PointD> linePoint1 = new ArrayList<PointD>();
        linePoint1.add(new PointD(10d, arrBill[5]));
        linePoint1.add(new PointD(30d, arrBill[4]));
        linePoint1.add(new PointD(50d, arrBill[3]));
        linePoint1.add(new PointD(70d, arrBill[2]));
        linePoint1.add(new PointD(90d, arrBill[1]));
        linePoint1.add(new PointD(110d, arrBill[0]));

        Double sum = 0.00;
        minNum = arrBill[0];
        for (int i = 0; i < arrBill.length; i++) {
            sum += arrBill[i];
            if (arrBill[i] > maxNum) {
                maxNum = arrBill[i];
            }
            if (arrBill[i] < minNum) {
                minNum = arrBill[i];
            }
        }
        avgNum = sum / arrBill.length;
        splineChart03View.refreshChart(linePoint1, avgNum, maxNum);

    }

    private void getHisBillDate() {

        c = Calendar.getInstance();
        int year = c.get(java.util.Calendar.YEAR);
        int[] months = UtilCalculate.getLast6Months();
        String monthes;
        double[] arrBill = new double[6];
        for (int i = 0; i < 6; i++) {

            monthes = year + "-" + months[i];
//            List<Record> datelist = DataSupport.where("date like ? and menstruation = ?", monthes + "%", "true").find(Record.class);
            List<Record> datelist = DataSupport.where("date like ?", monthes + "%").find(Record.class);
            arrBill[5-i] = datelist.size();
        }

        initViews(arrBill);
    }
}