package com.csp.libwidget.base;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.LayoutRes;


/**
 * LibActivity
 * Created by csp on 2017/06/23.
 * Modified by csp on 2019/03/21.
 *
 * @version 1.0.4
 */
public abstract class LibActivity extends Activity {

    private boolean destroyed = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        int layoutId = getLayoutId();
        setContentView(layoutId);
        beforeInit();
        init();
    }

    protected void beforeInit() {
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        destroyed = true;
    }

    @Override
    @SuppressLint("ObsoleteSdkInt")
    public boolean isDestroyed() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            return super.isDestroyed();
        } else {
            return destroyed || isFinishing();
        }
    }

    public Context getContext() {
        return this;
    }

    /**
     * @return 布局资源 ID
     */
    @LayoutRes
    protected abstract int getLayoutId();

    /**
     * 初始化界面
     */
    protected abstract void init();
}
