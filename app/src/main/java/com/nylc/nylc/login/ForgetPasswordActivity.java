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
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.nylc.nylc.BaseActivity;
import com.nylc.nylc.R;
import com.nylc.nylc.model.BaseResult;
import com.nylc.nylc.utils.PhoneFormatCheckUtils;
import com.nylc.nylc.utils.Urls;
import com.nylc.nylc.widget.CountDownTextView;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

/**
 * 忘记密码
 * Created by kasim on 2018/3/25.
 */

public class ForgetPasswordActivity extends BaseActivity implements View.OnClickListener {
    private ImageView iv_back;
    private EditText et_accountName,et_password,et_authCode;
    private CountDownTextView tv_countDown;
    private Button bt_submit;


    private String accountName,authCode,password;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forget_password);
        init();
    }

    private void init() {
        iv_back=findViewById(R.id.iv_back);
        et_accountName=findViewById(R.id.et_accountName);
        et_password=findViewById(R.id.et_password);
        et_authCode=findViewById(R.id.et_authCode);
        tv_countDown=findViewById(R.id.tv_countDown);
        bt_submit=findViewById(R.id.bt_submit);

        tv_countDown.setOnClickListener(this);
        iv_back.setOnClickListener(this);
        bt_submit.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.iv_back:
                finish();
                break;
            case R.id.tv_countDown:
                requestAuthCode();
                tv_countDown.setCountDownMillis(60*1000);
                tv_countDown.start();
                break;
            case R.id.bt_submit:
                submit();
                break;
        }
    }

    private void submit() {
        Editable phoneText = et_accountName.getText();
        if (phoneText != null) {
            accountName = phoneText.toString();
            // 校验手机号是否合法
            if (TextUtils.isEmpty(accountName) || !PhoneFormatCheckUtils.isPhone(accountName)) {
                Toast.makeText(this, "请输入正确的手机号", Toast.LENGTH_LONG).show();
                return;
            }
        }
        Editable authCodeText = et_authCode.getText();
        if (authCodeText == null) {
            Toast.makeText(this, "请输入验证码", Toast.LENGTH_SHORT).show();
            return;
        }
        authCode = authCodeText.toString();
        Editable passwordText = et_password.getText();
        if(passwordText==null){
            Toast.makeText(this,"请输入密码",Toast.LENGTH_SHORT).show();
            return;
        }
        password=passwordText.toString();
        RequestParams params = new RequestParams(Urls.forgetPassAction);
        params.addBodyParameter("accountName", accountName);
        params.addBodyParameter("accountPwd", password);
        params.addBodyParameter("authCode", authCode);
        x.http().post(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                Log.i("", result);
                //{"level":"success","data":null,"method":"/user/activeAccount","code":"","msg":"激活账号成功"}

                BaseResult baseResult = JSON.parseObject(result, BaseResult.class);
                String level = baseResult.getLevel();
                if (level.equals("success")) {
                    Toast.makeText(ForgetPasswordActivity.this, "重置密码成功", Toast.LENGTH_SHORT).show();
                    finish();
                } else {
                    Toast.makeText(ForgetPasswordActivity.this, "重置密码失败，请稍后再试", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                Log.e("error", ex.getMessage());
                Toast.makeText(ForgetPasswordActivity.this, "连接服务器失败", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onCancelled(CancelledException cex) {

            }

            @Override
            public void onFinished() {

            }
        });
    }

    private void requestAuthCode() {
        Editable phoneText = et_accountName.getText();
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
                    Toast.makeText(ForgetPasswordActivity.this, "发送验证码成功", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(ForgetPasswordActivity.this, "发送验证码失败", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                Log.e("error", ex.getMessage());
                Toast.makeText(ForgetPasswordActivity.this, "连接服务器失败", Toast.LENGTH_SHORT).show();
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
