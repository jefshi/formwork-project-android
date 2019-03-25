package com.csp.libwidget.base.mvp;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.csp.libwidget.base.LibFragment;
import com.zqylapp.base.BaseFragment;


/**
 * LibMvpFragment
 * Created by csp on 2019/3/21.
 * Modified by csp on 2019/03/21.
 *
 * @version 1.0.0
 */
public abstract class LibMvpFragment<T extends IPresenter> extends LibFragment {

    private IPresenter mPresenter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = super.onCreateView(inflater, container, savedInstanceState);
        getPresenter().onCreate();
        return rootView;
    }

    @Override
    public void onDestroy() {
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
