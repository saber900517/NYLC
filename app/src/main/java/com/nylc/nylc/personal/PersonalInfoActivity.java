package com.nylc.nylc.personal;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.nylc.nylc.BaseActivity;
import com.nylc.nylc.R;
import com.nylc.nylc.model.BaseResult;
import com.nylc.nylc.model.PersonalInfo;
import com.nylc.nylc.utils.CommonUtils;
import com.nylc.nylc.utils.Urls;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

/**
 * Created by 吴曰阳 on 2018/3/14.
 */

public class PersonalInfoActivity extends BaseActivity implements View.OnClickListener {
    private ImageView iv_back;
    private TextView tv_name, tv_phone, tv_character;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_personal_info);
        init();
    }

    private void init() {
        iv_back = findViewById(R.id.iv_back);
        iv_back.setOnClickListener(this);

        tv_name = findViewById(R.id.tv_name);
        tv_phone = findViewById(R.id.tv_phone);
        tv_character = findViewById(R.id.tv_character);
        getUserInfo();
    }

    private void getUserInfo() {
        RequestParams params = new RequestParams(Urls.queryUserAction);
        params.addBodyParameter("tokenKey", CommonUtils.getToken(this));
        x.http().post(params, new Callback.CommonCallback<BaseResult>() {
            @Override
            public void onSuccess(BaseResult result) {
                CommonUtils.judgeCode(PersonalInfoActivity.this, result.getCode());
                if ("success".equals(result.getLevel())) {
                    PersonalInfo personalInfo = JSON.parseObject(result.getData(), PersonalInfo.class);
                    tv_name.setText(personalInfo.getEMPNAME());
                    tv_phone.setText(personalInfo.getACCOUNT_NAME());
                    tv_character.setText(personalInfo.getEMPTYPENAME());
                }

            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                Log.i("", "");
            }

            @Override
            public void onCancelled(CancelledException cex) {

            }

            @Override
            public void onFinished() {

            }
        });
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_back:
                finish();
                break;
        }
    }
}
