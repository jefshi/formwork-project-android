package com.csp.formwork.ui.main;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import com.csp.formwork.R;

public class SplashActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        startActivity(new Intent(this, MainActivity.class));
        finish();
    }
}
