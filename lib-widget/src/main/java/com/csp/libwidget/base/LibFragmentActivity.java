package com.csp.libwidget.base;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;

import com.zqylapp.base.mvp.IPresenter;

import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * LibActivity
 * Created by csp on 2019/12/15.
 * Modified by csp on 2019/03/21.
 *
 * @version 1.0.4
 */
public abstract class LibFragmentActivity<T extends IPresenter> extends FragmentActivity {

    private boolean destroyed = false;
    Unbinder unbinder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        int layoutId = getLayoutId();
        setContentView(layoutId);
        unbinder = ButterKnife.bind(this);
        init();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        destroyed = true;
        unbinder.unbind();
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
    protected abstract int getLayoutId();

    /**
     * 初始化界面
     */
    protected abstract void init();
}
