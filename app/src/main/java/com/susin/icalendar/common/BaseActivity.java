package com.susin.icalendar.common;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Toast;

import com.susin.icalendar.widget.AnimBitmapDrawable;

import net.qiujuer.genius.graphics.Blur;


/**
 * 说明：
 *
 * @作者 Susin
 * @创建时间 2016/6/27 21:11
 * @版本
 * @------修改记录-------
 * @修改人
 * @版本
 * @修改内容
 */
public abstract class BaseActivity extends Activity {
    protected BaseActivity mContext = this;

    private static Bitmap mBlurBitmap;
    private static final Object mBlurBitmapLock = new Object();
    private static final int BLUR_LEVEL = 4;
    private static final boolean NEED_CUT = Build.VERSION.SDK_INT < Build.VERSION_CODES.KITKAT;
    private static final int SCALE_FACTOR = 10;
    /**
     * set layout of this activity
     *
     * @return the id of layout
     */

    protected abstract void initView();

    protected abstract int getLayout();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayout());
        initView();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }


    /**
     * UI绑定
     *
     * @作者 huangssh
     * @创建时间 2015-8-6 上午11:20:48
     * @param id
     * @return
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
     * UI绑定(通过父级view)
     *
     * @作者 huangssh
     * @创建时间 2015-8-6 上午11:23:21
     * @param parentView 父级view
     * @param id
     * @return
     */
    public <T extends View> T findView(View parentView, int id) {
        T view = null;
        View genericView = parentView.findViewById(id);
        try {
            view = (T) (genericView);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return view;
    }

    /**
     * 长时提示
     *
     * @作者 huangssh
     * @创建时间 2015-8-6 上午9:05:26
     * @param text 显示的文本
     */
    public void showToastLong(String text){
        Toast.makeText(mContext, text, Toast.LENGTH_LONG).show();
    }


    /**
     * 短时提示
     *
     * @作者 huangssh
     * @创建时间 2015-8-6 上午9:05:26
     * @param text 显示的文本
     */
    public void showToastShort(String text){
        Toast.makeText(mContext, text, Toast.LENGTH_SHORT).show();
    }

    protected Drawable getBlur() {
        if (mBlurBitmap == null) {
            synchronized (mBlurBitmapLock) {
                if (mBlurBitmap == null) {
                    try {
                        mBlurBitmapLock.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
        AnimBitmapDrawable drawable = new AnimBitmapDrawable(mBlurBitmap);
        mBlurBitmap = null;
        return drawable;
    }

    public static void setBlur(Activity activity) {
        mBlurBitmap = null;
        synchronized (mBlurBitmapLock) {
            Bitmap bitmap = formatBlurBitmap(activity, true);
            mBlurBitmap = Blur.onStackBlurPixels(bitmap, BLUR_LEVEL, true);
            mBlurBitmapLock.notifyAll();
        }
    }
    private static Bitmap formatBlurBitmap(Activity activity, boolean isMatrix) {
        View view = activity.getWindow().getDecorView();
        view.setDrawingCacheEnabled(true);

        Bitmap bitmap = view.getDrawingCache();

        int statusBarHeight = 0;

        // If need cut statusBar on sdk < KITKAT
        if (NEED_CUT) {
            Rect frame = new Rect();
            view.getWindowVisibleDisplayFrame(frame);
            statusBarHeight = frame.top;
        }

        if (isMatrix) {
            // Compress
            Matrix matrix = new Matrix();
            matrix.postScale(1.0f / SCALE_FACTOR, 1.0f / SCALE_FACTOR);
            // New Compress bitmap
            bitmap = Bitmap.createBitmap(bitmap, 0, statusBarHeight,
                    bitmap.getWidth(), bitmap.getHeight() - statusBarHeight, matrix, true);
        } else {
            bitmap = Bitmap.createBitmap(bitmap, 0, statusBarHeight, bitmap.getWidth(),
                    bitmap.getHeight() - statusBarHeight);
        }

        view.destroyDrawingCache();
        return bitmap;
    }

    /**
     * 透明化状态栏
     */
    private void initBar(){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {//5.0及以上
            View decorView = getWindow().getDecorView();
            int option = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                    | View.SYSTEM_UI_FLAG_LAYOUT_STABLE;
            decorView.setSystemUiVisibility(option);
            getWindow().setStatusBarColor(Color.TRANSPARENT);
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {//4.4到5.0
            WindowManager.LayoutParams localLayoutParams = getWindow().getAttributes();
            localLayoutParams.flags = (WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS | localLayoutParams.flags);
        }
    }

}
