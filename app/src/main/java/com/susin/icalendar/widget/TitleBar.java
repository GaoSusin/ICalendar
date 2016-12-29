package com.susin.icalendar.widget;

import android.app.Activity;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;


import com.susin.icalendar.R;

import java.lang.reflect.Method;

/**
 * 通用头部控件
 * @作者 
 * @创建时间  2015-8-7 下午5:03:34
 *
 * @修改人
 * @修改时间
 * @修改内容
 */
public class TitleBar extends RelativeLayout {
	private Context mContext;
	private TextView mTvTitle;

	public TitleBar(Context context, AttributeSet attrs) {
		super(context, attrs);
		mContext = context;
		if (isInEditMode())
			return;

		LayoutInflater inflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		inflater.inflate(R.layout.title_bar, this);
		mTvTitle = (TextView) findViewById(R.id.title);

		// 从布局文件中读出控件设置
		TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.MyCustomWidget);

		String myText = a.getString(R.styleable.MyCustomWidget_title);
		a.recycle();

		// 控件设置
		mTvTitle.setText(myText);
		mTvTitle.requestFocus();

	}

}
