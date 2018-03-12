package com.nylc.nylc.login;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.Editable;
import android.text.TextUtils;

import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;


import com.alibaba.fastjson.JSON;
import com.nylc.nylc.BaseActivity;


import com.nylc.nylc.R;

import com.nylc.nylc.character.supplier.SupplierIndexActivity;
import com.nylc.nylc.model.BaseResult;
import com.nylc.nylc.model.Login;
import com.nylc.nylc.utils.CommonUtils;
import com.nylc.nylc.utils.PhoneFormatCheckUtils;
import com.nylc.nylc.utils.SharedPreferencesUtil;
import com.nylc.nylc.utils.Urls;


import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;


/**
 * 登录界面
 * Created by 吴曰阳 on 2017/11/30.
 */

public class LoginActivity extends BaseActivity implements View.OnClickListener {
    private EditText et_userName;

    private EditText et_password;

    private TextView tv_gotoActive;

    private Button bt_login;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        init();
    }

    private void init() {
        et_userName = findViewById(R.id.et_username);
        et_password = findViewById(R.id.et_password);
        tv_gotoActive = findViewById(R.id.tv_gotoActive);
        bt_login = findViewById(R.id.bt_login);

        bt_login.setOnClickListener(this);
        tv_gotoActive.setOnClickListener(this);

        String token = (String) SharedPreferencesUtil.getParam(this, "token", "");
        if (!TextUtils.isEmpty(token)) {
            String empType = (String) SharedPreferencesUtil.getParam(this, "empType", "");
            judgeType(empType);
        }
    }

    private void login() {
        Editable phoneText = et_userName.getText();
        String identifier = "", credential = "";
        if (phoneText != null) {
            identifier = phoneText.toString();
            // 校验手机号是否合法
            if (TextUtils.isEmpty(identifier) || !PhoneFormatCheckUtils.isPhone(identifier)) {
                Toast.makeText(this, "请输入用户名", Toast.LENGTH_LONG).show();
                return;
            }
        }

        Editable passwordtext = et_password.getText();
        if (passwordtext != null) {
            credential = passwordtext.toString();
            if (TextUtils.isEmpty(credential)) {
                Toast.makeText(this, "请输入密码", Toast.LENGTH_LONG).show();
                return;
            }
            if (credential.length() > 6) {
                Toast.makeText(this, "密码要小于六位", Toast.LENGTH_LONG).show();
                return;
            }
        }

        RequestParams params = new RequestParams(Urls.LOGIN);
        params.addBodyParameter("accountName", identifier);
        params.addBodyParameter("accountPwd", credential);
        x.http().post(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                BaseResult baseResult = JSON.parseObject(result, BaseResult.class);
                String code = baseResult.getCode();
                String msg = baseResult.getMsg();
                String level = baseResult.getLevel();
                CommonUtils.judgeCode(LoginActivity.this, code);
                if (level.equals("success")) {
                    //登录成功
                    Login login = JSON.parseObject(baseResult.getData(), Login.class);
                    SharedPreferencesUtil.setParam(LoginActivity.this, "token", login.getTokenKey());
                    SharedPreferencesUtil.setParam(LoginActivity.this, "empType", login.getEmpTypeName());
                    SharedPreferencesUtil.setParam(LoginActivity.this, "empTypeId", login.getEmpTypeId());
                    String empTypeName = login.getEmpTypeName();
                    judgeType(empTypeName);
                } else {
                    //登录失败
                    //{"level":"error","data":null,"method":"/user/loginAction","code":"001","msg":"用户未激活，请先激活"}
                    Toast.makeText(LoginActivity.this, msg, Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                Log.e("error", ex.getMessage());
            }

            @Override
            public void onCancelled(CancelledException cex) {
                Log.i("", "");
            }

            @Override
            public void onFinished() {
                Log.i("", "");
            }
        });


    }

    /**
     * 判断身份，跳转
     *
     * @param empTypeName
     */
    private void judgeType(String empTypeName) {
        switch (empTypeName) {
            case "农民":
                Toast.makeText(LoginActivity.this, "农民", Toast.LENGTH_LONG).show();
                break;
            case "供应商":
                Toast.makeText(LoginActivity.this, "供应商", Toast.LENGTH_LONG).show();
                //跳转到供应商首页
                startActivity(new Intent(LoginActivity.this, SupplierIndexActivity.class));
                break;
            case "小组长":
                Toast.makeText(LoginActivity.this, "小组长", Toast.LENGTH_LONG).show();
                break;
            case "企业":
                Toast.makeText(LoginActivity.this, "企业", Toast.LENGTH_LONG).show();
                break;
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.bt_login:
                login();
                break;
            case R.id.tv_gotoActive:
                Intent intent = new Intent(LoginActivity.this, AuthCodeActivity.class);
                startActivity(intent);
                break;
        }
    }
}