package com.csp.formwork.ui.main;

import android.app.Activity;
import android.os.Bundle;

import com.csp.formwork.R;
import com.csp.formwork.base.BaseActivity;

public class MainActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    protected void init() {
        super.init();


    }
}
