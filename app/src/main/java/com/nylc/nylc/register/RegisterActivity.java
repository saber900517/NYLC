//package com.nylc.nylc.register;
//
//import android.os.Bundle;
//import android.support.annotation.Nullable;
//import android.text.Editable;
//import android.text.TextUtils;
//import android.util.Log;
//import android.view.View;
//import android.widget.Button;
//import android.widget.EditText;
//import android.widget.TextView;
//import android.widget.Toast;
//
//import com.alibaba.fastjson.JSON;
//import com.nylc.nylc.BaseActivity;
//import com.nylc.nylc.R;
//import com.nylc.nylc.model.Login;
//import com.nylc.nylc.utils.PhoneFormatCheckUtils;
//import com.nylc.nylc.utils.Urls;
//
//import org.xutils.common.Callback;
//import org.xutils.http.RequestParams;
//import org.xutils.view.annotation.ViewInject;
//import org.xutils.x;
//
///**
// * 注册界面
// * Created by 吴曰阳 on 2017/12/9.
// */
//
//public class RegisterActivity extends BaseActivity implements View.OnClickListener {
//    @ViewInject(R.id.et_username)
//    private EditText et_userName;
//
//    @ViewInject(R.id.et_password)
//    private EditText  et_password;
//
//    @ViewInject(R.id.bt_register)
//    private Button bt_register;
//
//    @ViewInject(R.id.tv_gotoLogin)
//    private TextView tv_gotoLogin;
//
//    @Override
//    protected void onCreate(@Nullable Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.item_one_text.activity_register);
//        init();
//    }
//    private void init() {
//        bt_register.setOnClickListener(this);
//        tv_gotoLogin.setOnClickListener(this);
//
//    }
//
//    private void register() {
//        Editable phoneText = et_userName.getText();
//        String identifier = "", credential = "";
//        if (phoneText != null) {
//            identifier = phoneText.toString();
//            // 校验手机号是否合法
//            if (TextUtils.isEmpty(identifier) || !PhoneFormatCheckUtils.isPhone(identifier)) {
//                Toast.makeText(this, "请输入用户名", Toast.LENGTH_LONG).show();
//                return;
//            }
//        }
//
//        Editable passwordtext = et_password.getText();
//        if (passwordtext != null) {
//            credential = passwordtext.toString();
//            if (TextUtils.isEmpty(credential)) {
//                Toast.makeText(this, "请输入密码", Toast.LENGTH_LONG).show();
//                return;
//            }
//            if (credential.length() > 6) {
//                Toast.makeText(this, "密码要小于六位", Toast.LENGTH_LONG).show();
//                return;
//            }
//        }
//
//        RequestParams params = new RequestParams(Urls.REGISTER);
//        params.addBodyParameter("identifier", identifier);
//        params.addBodyParameter("credential", credential);
////        params.setAsJsonContent(true);
//        x.http().post(params, new Callback.CommonCallback<String>() {
//            @Override
//            public void onSuccess(String result) {
//                Log.i("onSuccess", result);
//                Login loginResult = JSON.parseObject(result, Login.class);
//                String level = loginResult.getLevel();
//                if(level.equals("success")){
//                    Toast.makeText(RegisterActivity.this, "注册成功", Toast.LENGTH_LONG).show();
//                    finish();
//                }else{
//                    Toast.makeText(RegisterActivity.this, "注册失败", Toast.LENGTH_LONG).show();
//                }
//            }
//
//            @Override
//            public void onError(Throwable ex, boolean isOnCallback) {
//                Log.i("onError", ex.toString());
//            }
//
//            @Override
//            public void onCancelled(CancelledException cex) {
//                Log.i("onCancelled", "cancel");
//            }
//
//            @Override
//            public void onFinished() {
//                Log.i("onFinished", "finish");
//            }
//        });
//
//    }
//
//    @Override
//    public void onClick(View view) {
//        switch (view.getId()) {
//            case R.id.bt_register:
//                register();
//                break;
//            case R.id.tv_gotoLogin:
//                finish();
//                break;
//        }
//    }
//}
