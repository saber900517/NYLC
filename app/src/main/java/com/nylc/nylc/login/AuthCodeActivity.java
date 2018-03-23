package com.nylc.nylc.login;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.Editable;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.nylc.nylc.BaseActivity;
import com.nylc.nylc.R;
import com.nylc.nylc.model.BaseResult;
import com.nylc.nylc.utils.PhoneFormatCheckUtils;
import com.nylc.nylc.utils.Urls;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

/**
 * 输入验证码页面
 * Created by 吴曰阳 on 2018/3/2.
 */

public class AuthCodeActivity extends BaseActivity implements View.OnClickListener {
    private EditText et_userName;
    private EditText et_authCode;
    private Button bt_active;
    private TextView tv_getAuthCode;
    private ImageView iv_back;

    private String accountName;
    private String authCode;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_auth);
        init();
    }

    private void init() {
        et_authCode = findViewById(R.id.et_authCode);
        et_userName = findViewById(R.id.et_username);
        tv_getAuthCode = findViewById(R.id.tv_getAuthCode);
        bt_active = findViewById(R.id.bt_active);
        iv_back = findViewById(R.id.iv_back);
        bt_active.setOnClickListener(this);
        tv_getAuthCode.setOnClickListener(this);
        iv_back.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.bt_active:
                //激活
                activeAccount();
                break;
            case R.id.tv_getAuthCode:
                //获取验证码
                getAuthCode();
                break;
            case R.id.iv_back:
                //返回
                finish();
                break;
        }
    }

    /**
     * 激活
     */
    private void activeAccount() {
        Editable authCodeText = et_authCode.getText();
        if (authCodeText == null) {
            Toast.makeText(this, "请输入验证码", Toast.LENGTH_SHORT).show();
            return;
        }
        authCode = authCodeText.toString();
        RequestParams params = new RequestParams(Urls.activeAccount);
        params.addBodyParameter("accountName", accountName);
        params.addBodyParameter("authCode", authCode);
        x.http().post(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                Log.i("", result);
                //{"level":"success","data":null,"method":"/user/activeAccount","code":"","msg":"激活账号成功"}

                BaseResult baseResult = JSON.parseObject(result, BaseResult.class);
                String level = baseResult.getLevel();
                if (level.equals("success")) {
                    Toast.makeText(AuthCodeActivity.this, "激活账号成功", Toast.LENGTH_SHORT).show();
                    finish();
                } else {
                    Toast.makeText(AuthCodeActivity.this, "激活账号失败，请稍后再试", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                Log.e("error", ex.getMessage());
                Toast.makeText(AuthCodeActivity.this, "连接服务器失败", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onCancelled(CancelledException cex) {

            }

            @Override
            public void onFinished() {

            }
        });
    }

    /**
     * 获取验证码
     */
    private void getAuthCode() {
        Editable phoneText = et_userName.getText();
        accountName = "";
        if (phoneText != null) {
            accountName = phoneText.toString();
            // 校验手机号是否合法
            if (TextUtils.isEmpty(accountName) || !PhoneFormatCheckUtils.isPhone(accountName)) {
                Toast.makeText(this, "请输入正确的手机号", Toast.LENGTH_LONG).show();
                return;
            }
        }
        RequestParams params = new RequestParams(Urls.sendCodeAction);
        params.addBodyParameter("accountName", accountName);
        x.http().post(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                Log.i("", result);
                //{"level":"success","data":null,"method":"/auth/sendCodeAction","code":"","msg":"发送验证码成功"}
                BaseResult baseResult = JSON.parseObject(result, BaseResult.class);
                String level = baseResult.getLevel();
                if (level.equals("success")) {
                    Toast.makeText(AuthCodeActivity.this, "发送验证码成功", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(AuthCodeActivity.this, "发送验证码失败", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                Log.e("error", ex.getMessage());
                Toast.makeText(AuthCodeActivity.this, "连接服务器失败", Toast.LENGTH_SHORT).show();
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
