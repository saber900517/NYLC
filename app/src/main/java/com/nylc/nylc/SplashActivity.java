package com.nylc.nylc;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;

import com.nylc.nylc.login.LoginActivity;

import java.util.Timer;
import java.util.TimerTask;

/**
 * 启动页
 * Created by kasim on 2018/5/17.
 */

public class SplashActivity extends BaseActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        new Timer().schedule(new TimerTask() {
            @Override
            public void run() {
                startActivity(new Intent(SplashActivity.this, LoginActivity.class));
                finish();
            }
        }, 2000);
    }
}
