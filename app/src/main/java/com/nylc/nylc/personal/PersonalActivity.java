package com.nylc.nylc.personal;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.nylc.nylc.BaseActivity;
import com.nylc.nylc.R;
import com.nylc.nylc.login.LoginActivity;
import com.nylc.nylc.utils.CommonUtils;
import com.nylc.nylc.utils.SharedPreferencesUtil;
import com.nylc.nylc.utils.Urls;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

/**
 * 个人中心
 * Created by 吴曰阳 on 2018/3/2.
 */

public class PersonalActivity extends BaseActivity implements View.OnClickListener {
    private LinearLayout ll_personalInfo;//个人信息
    private LinearLayout ll_changePassword;//修改密码
    private LinearLayout ll_logout;//退出

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_personal);
        init();
    }

    private void init() {
        ll_changePassword = findViewById(R.id.ll_changePassword);
        ll_personalInfo = findViewById(R.id.ll_personalInfo);
        ll_logout = findViewById(R.id.ll_logout);

        ll_changePassword.setOnClickListener(this);
        ll_personalInfo.setOnClickListener(this);
        ll_logout.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ll_personalInfo:
                //个人信息
                break;
            case R.id.ll_changePassword:
                //修改密码
                startActivity(new Intent(this, ChangePasswordActivity.class));
                break;
            case R.id.ll_logout:
                //退出
                logout();
                break;
        }
    }

    private void logout() {
        RequestParams params = new RequestParams(Urls.logOutAction);
        params.addBodyParameter("accountName", CommonUtils.getAccountName(this));
        x.http().post(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                SharedPreferencesUtil.clearParam(PersonalActivity.this);
                startActivity(new Intent(PersonalActivity.this, LoginActivity.class));
                finish();
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                Log.e("error", ex.getMessage());
                Toast.makeText(PersonalActivity.this, "连接服务器失败", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onCancelled(CancelledException cex) {

            }

            @Override
            public void onFinished() {

            }
        });
    }
}
