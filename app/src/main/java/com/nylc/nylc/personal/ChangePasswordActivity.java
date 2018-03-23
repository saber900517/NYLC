package com.nylc.nylc.personal;

import android.os.Bundle;
import android.support.annotation.Nullable;
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
import com.nylc.nylc.utils.CommonUtils;
import com.nylc.nylc.utils.Urls;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

/**
 * 修改密码
 * Created by 吴曰阳 on 2018/3/16.
 */

public class ChangePasswordActivity extends BaseActivity implements View.OnClickListener {

    private EditText et_oldPassword;
    private EditText et_newPassword;
    private ImageView iv_back;
    private Button bt_submit;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_password);
        init();
    }

    private void init() {
        et_oldPassword = findViewById(R.id.et_oldPassword);
        et_newPassword = findViewById(R.id.et_newPassword);
        iv_back = findViewById(R.id.iv_back);
        bt_submit = findViewById(R.id.bt_submit);

        iv_back.setOnClickListener(this);
        bt_submit.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_back:
                finish();
                break;
            case R.id.bt_submit:
                changePassword();
                break;
        }
    }

    private void changePassword() {
        String oldPassword = null;
        String newPassword = null;
        if (et_oldPassword.getText() != null) {
            oldPassword = et_oldPassword.getText().toString();
        } else {
            Toast.makeText(this, "请输入原密码", Toast.LENGTH_SHORT).show();
            return;
        }
        if (et_newPassword.getText() != null) {
            newPassword = et_newPassword.getText().toString();
        } else {
            Toast.makeText(this, "请输入新密码", Toast.LENGTH_SHORT).show();
            return;
        }
        RequestParams params = new RequestParams(Urls.modifyPassAction);
        params.addBodyParameter("tokenKey", CommonUtils.getToken(this));
        params.addBodyParameter("preAccountPwd", oldPassword);
        params.addBodyParameter("newAccountPwd", newPassword);
        x.http().post(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                BaseResult baseResult = JSON.parseObject(result, BaseResult.class);
                CommonUtils.judgeCode(ChangePasswordActivity.this, baseResult.getCode());
                String level = baseResult.getLevel();
                if ("success".equals(level)) {
                    Toast.makeText(ChangePasswordActivity.this, "密码修改成功", Toast.LENGTH_LONG).show();
                    finish();
                } else {
                    Toast.makeText(ChangePasswordActivity.this, baseResult.getMsg(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                Log.e("error", ex.getMessage());
                Toast.makeText(ChangePasswordActivity.this, "连接服务器失败", Toast.LENGTH_SHORT).show();
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
