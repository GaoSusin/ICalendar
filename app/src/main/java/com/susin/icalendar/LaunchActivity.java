package com.susin.icalendar;

import android.app.Application;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Toast;

import com.susin.icalendar.common.BaseActivity;
import com.susin.icalendar.util.AnimationListener;


public class LaunchActivity extends BaseActivity {

    private final Handler mHandler = new Handler();

    @Override
    protected void initView() {
        iconIn();

        Thread thread = new Thread("GraveTips-Launch-InitThread") {
            @Override
            public void run() {
                mHandler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        skip();
                    }
                }, 3200);
            }
        };
        thread.setDaemon(true);
        thread.start();
    }

    @Override
    protected int getLayout() {
        return R.layout.activity_launch;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    private void iconIn() {
        Animation anim = AnimationUtils.loadAnimation(this,
                R.anim.anim_launch_item_fade_in);
        anim.setStartOffset(560);
        anim.setAnimationListener(new AnimationListener() {
            @Override
            public void onAnimationEnd(Animation animation) {
                super.onAnimationEnd(animation);

                Animation anim = AnimationUtils.loadAnimation(LaunchActivity.this,
                        R.anim.anim_launch_item_scale_in);
                View view = findViewById(R.id.launch_icon);
                view.setVisibility(View.VISIBLE);
                view.startAnimation(anim);
            }
        });
        findViewById(R.id.content).startAnimation(anim);
    }

    private void skip() {
        Intent intent;
        intent = new Intent(this, MainActivity.class);

        startActivity(intent);
        finish();
    }
}
