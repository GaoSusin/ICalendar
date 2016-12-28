package com.susin.icalendar.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.susin.icalendar.R;
import com.susin.icalendar.model.Record;


/**
 * 说明：
 *
 * @作者 Susin
 * @创建时间 2016/6/11 14:16
 * @版本
 * @------修改记录-------
 * @修改人
 * @版本
 * @修改内容
 */
public class HabitTypeAdapter extends SimpleAdapter<Record> {

    public View getView(int position, View view, ViewGroup viewGroup) {
        ViewHolder holder1 = null;
        if (view == null) {
            Context context = viewGroup.getContext();
            view = LayoutInflater.from(context).inflate(
                    R.layout.item_habit_type, null);
            holder1 = new ViewHolder(view);
            view.setTag(holder1);
        } else {
            holder1 = (ViewHolder) view.getTag();
        }
        Record record = mDatas.get(position);
        holder1.bindViews(record);
        return view;
    }

    static class ViewHolder {

        ImageView ivHabit , ivedit;
        TextView tvhabit;

        public ViewHolder(View view) {
            tvhabit = (TextView) view.findViewById(R.id.tv_habit);
            ivHabit = (ImageView) view.findViewById(R.id.iv_habit);
            ivedit = (ImageView) view.findViewById(R.id.iv_edit);
        }

        private void bindViews(Record item) {
            tvhabit.setText(item.getHabit());
            if ("0".equals(item.getHabit())){
                ivHabit.setImageResource(R.drawable.btn_slip_half_bg);
            }
        }
    }
}
