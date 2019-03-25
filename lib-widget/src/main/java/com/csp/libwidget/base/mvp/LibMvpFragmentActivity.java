package com.csp.libwidget.base.mvp;

import android.os.Bundle;

import com.csp.libwidget.base.LibFragmentActivity;
import com.zqylapp.base.BaseFragmentActivity;


/**
 * LibMvpFragmentActivity
 * Created by csp on 2019/3/21.
 * Modified by csp on 2019/03/21.
 *
 * @version 1.0.0
 */
public abstract class LibMvpFragmentActivity<T extends IPresenter> extends LibFragmentActivity {

    IPresenter mPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getPresenter().onCreate();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        getPresenter().onDestroy();
    }

    @SuppressWarnings("unchecked")
    public T getPresenter() {
        if (mPresenter == null) {
            mPresenter = createPresenter();
        }
        return (T) mPresenter;
    }

    /**
     * @return 追加 Presenter 对象
     */
    protected abstract IPresenter createPresenter();
}
