package com.susin.icalendar.common;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.view.Window;



public abstract class MyFragmentActivity extends FragmentActivity {
	protected String ACTIVITY_NAME = this.getClass().getSimpleName() + ":";;
	protected MyFragmentActivity mContext = this;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContenView();
		initView();
	}

	protected abstract void setContenView();
	protected abstract void initView();


	/**
	 * 说明：UI绑定
	 *
	 * @作者 Susin
	 * @创建时间 2016/12/1 20:40
	 * @版本
	 * @------修改记录-------
	 * @修改人
	 * @版本
	 * @修改内容
	 */

    public <T extends View> T findView(int id) {
        T view = null;
        View genericView = findViewById(id);
        try {
            view = (T) (genericView);
        } catch (Exception e) {
        	e.printStackTrace();
        }
        return view;
    }
    
    /**
     * 说明：通过Class跳转界面
	 * @param cls 跳转到的class
     *
     * @作者 Susin
     * @创建时间 2016/12/1 20:40
     * @版本
     * @------修改记录-------
     * @修改人
     * @版本
     * @修改内容
     */

	protected void startActivity(Class<?> cls) {
		startActivity(cls, null);
	}

	/**
	 * 说明：含有Bundle通过Class跳转界面
	 *
	 * @作者 Susin
	 * @创建时间 2016/12/1 20:41
	 * @版本
	 * @------修改记录-------
	 * @修改人
	 * @版本
	 * @修改内容
	 */

	protected void startActivity(Class<?> cls, Bundle bundle) {
		Intent intent = new Intent();
		intent.setClass(this, cls);
		if (bundle != null) {
			intent.putExtras(bundle);
		}
		startActivity(intent);
	}

}
