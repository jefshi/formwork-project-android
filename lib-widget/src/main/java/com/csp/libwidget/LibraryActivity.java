package com.csp.libwidget;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;

import com.csp.libwidget.mvp.IPresenter;
import com.csp.libwidget.mvp.IView;

/**
 * LibraryActivity
 * Created by csp on 2017/06/23.
 * Modified by csp on 2017/06/23.
 *
 * @version 1.0.0
 */
public abstract class LibraryActivity<T extends IPresenter> extends Activity implements IView<T> {
    private boolean destroyed = false;
    protected T mPresenter;

    public T getPresenter() {
        if (mPresenter == null)
            mPresenter = createPresent();

        return mPresenter;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        beforeSetContentView();

        int layoutId = getLayoutId();
        if (layoutId > 0)
            setContentView(layoutId);
        else
            setContentView();

        afterSetContentView();
        init();

        getPresenter().onCreate();
    }

    @Override
    protected void onDestroy() {
        getPresenter().onDestroy();

        destroyed = true;
        super.onDestroy();
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

    protected void setContentView() {
    }

    protected void beforeSetContentView() {
    }

    protected void afterSetContentView() {
    }

    protected abstract int getLayoutId();

    protected abstract void init();
}
