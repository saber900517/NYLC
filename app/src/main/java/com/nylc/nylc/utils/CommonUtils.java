package com.nylc.nylc.utils;

import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

import com.nylc.nylc.BaseActivity;
import com.nylc.nylc.login.AuthCodeActivity;
import com.nylc.nylc.login.LoginActivity;
import com.nylc.nylc.model.BaseResult;

/**
 * Created by 吴曰阳 on 2018/3/10.
 */

public class CommonUtils {

    public static void judgeCode(Context context, String code) {
        if ("001".equals(code)) {
            //未激活
            Toast.makeText(context, "账户尚未激活，请激活后登录", Toast.LENGTH_SHORT).show();
            context.startActivity(new Intent(context, AuthCodeActivity.class));
            return;
        } else if ("002".equals(code)) {
            //token过期
            SharedPreferencesUtil.clearParam(context);
            Toast.makeText(context, "登录信息失效，请重新登录", Toast.LENGTH_SHORT).show();
            context.startActivity(new Intent(context, LoginActivity.class));
            if (context instanceof BaseActivity) {
                ((BaseActivity) context).finish();
            }
            return;
        } else {
            //正常
        }
    }

    public static String getToken(Context context) {
        String token = (String) SharedPreferencesUtil.getParam(context, "token", "");
        return token;
    }
}
