package com.csp.libwidget.base;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.StringRes;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.zqylapp.lianxin.SaveDataApplication;

import butterknife.ButterKnife;
import butterknife.Unbinder;


/**
 * LibActivity
 * Created by csp on 2019/12/15.
 * Modified by csp on 2019/03/21.
 *
 * @version 1.0.4
 */
public abstract class LibFragment extends Fragment {

    private Context mContext;
    private Unbinder unbinder;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(getLayoutId(), container, false);
        unbinder = ButterKnife.bind(this, rootView);
        init(rootView);
        return rootView;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mContext = context;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        unbinder.unbind();
    }

    public Context getContext() {
        Context context = super.getContext();
        if (context != null)
            return context;

        context = getView() == null ? null : getView().getContext();
        if (context != null)
            return context;

        if (mContext != null)
            return mContext;

        return SaveDataApplication.context;
    }

    public Application getApplication() {
        if (getActivity() != null)
            return getActivity().getApplication();
        else if (mContext instanceof Activity)
            return ((Activity) mContext).getApplication();
        else if (SaveDataApplication.context instanceof Application)
            return (Application) SaveDataApplication.context;
        else
            return null;
    }

    public String getRString(@StringRes int resId) {
        try {
            return getString(resId);
        } catch (Exception e) {
            return getContext().getString(resId);
        }
    }

    /**
     * @return 布局资源 ID
     */
    protected abstract int getLayoutId();

    /**
     * 初始化界面
     *
     * @param rootView 根 View 对象
     */
    protected abstract void init(View rootView);
}
