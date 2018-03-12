package com.nylc.nylc.personal;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.LinearLayout;

import com.nylc.nylc.BaseActivity;
import com.nylc.nylc.R;

/**
 * 个人中心
 * Created by 吴曰阳 on 2018/3/2.
 */

public class PersonalActivity extends BaseActivity implements View.OnClickListener {
    private LinearLayout ll_personalInfo;//个人信息
    private LinearLayout ll_changePassword;//修改密码

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_personal);
        init();
    }

    private void init() {
        ll_changePassword = findViewById(R.id.ll_changePassword);
        ll_personalInfo = findViewById(R.id.ll_personalInfo);

        ll_changePassword.setOnClickListener(this);
        ll_personalInfo.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ll_personalInfo:
                //个人信息
                break;
            case R.id.ll_changePassword:
                //修改密码
                break;
        }
    }
}
