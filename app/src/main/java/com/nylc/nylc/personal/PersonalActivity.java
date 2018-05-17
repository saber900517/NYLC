package com.nylc.nylc.personal;

import android.app.AlertDialog;
import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
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
    private ImageView iv_back;
    private AlertDialog logoutDialog;

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
        iv_back = findViewById(R.id.iv_back);

        ll_changePassword.setOnClickListener(this);
        ll_personalInfo.setOnClickListener(this);
        ll_logout.setOnClickListener(this);
        iv_back.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ll_personalInfo:
                //个人信息
                startActivity(new Intent(this, PersonalInfoActivity.class));
                break;
            case R.id.ll_changePassword:
                //修改密码
                startActivity(new Intent(this, ChangePasswordActivity.class));
                break;
            case R.id.ll_logout:
                //退出
                showLogoutDialog();
                break;
            case R.id.iv_back:
                //返回
                finish();
                break;
        }
    }

    private void showLogoutDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        View v = LayoutInflater.from(this).inflate(R.layout.dialog_title_content_towbtn, null);
        Button btn_confirm = v.findViewById(R.id.btn_confirm);
        Button btn_cancel = v.findViewById(R.id.btn_cancel);
        logoutDialog = builder.create();
        logoutDialog.getWindow().setBackgroundDrawable(new BitmapDrawable());
        logoutDialog.setCanceledOnTouchOutside(false);
        logoutDialog.show();
        logoutDialog.getWindow().setContentView(v);
        logoutDialog.getWindow().setGravity(Gravity.CENTER);
        logoutDialog.getWindow().setLayout(700, 410);
        btn_confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                logout();
                if (logoutDialog != null && logoutDialog.isShowing()) logoutDialog.dismiss();
            }
        });
        btn_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (logoutDialog != null && logoutDialog.isShowing()) logoutDialog.dismiss();
            }
        });
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
