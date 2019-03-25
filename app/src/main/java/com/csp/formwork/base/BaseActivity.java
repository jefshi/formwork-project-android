package com.csp.formwork.base;


import android.os.Bundle;

import com.csp.libwidget.base.LibActivity;

import butterknife.ButterKnife;
import butterknife.Unbinder;

public abstract class BaseActivity extends LibActivity {

    Unbinder unbinder;

    @Override
    protected void beforeInit() {
        unbinder = ButterKnife.bind(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unbinder.unbind();
    }
}
