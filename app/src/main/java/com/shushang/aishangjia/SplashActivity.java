package com.shushang.aishangjia;

import android.content.Intent;
import android.os.Handler;

import com.shushang.aishangjia.activity.LoginActivity2;
import com.shushang.aishangjia.base.BaseActivity;

public class SplashActivity extends BaseActivity {


    @Override
    public int setLayout() {
        return R.layout.activity_splash;
    }

    @Override
    public void init() {
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    startActivity(new Intent(SplashActivity.this,LoginActivity2.class));
                    finish();
                }
            },2000);
    }

}
