package com.nylc.nylc.utils;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.nylc.nylc.BaseActivity;
import com.nylc.nylc.R;
import com.nylc.nylc.login.AuthCodeActivity;
import com.nylc.nylc.login.LoginActivity;
import com.nylc.nylc.model.BaseResult;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by 吴曰阳 on 2018/3/10.
 */

public class CommonUtils {

    private static ArrayList<String> thirtyOneDayMonth = new ArrayList<>(Arrays.asList("01", "03", "05", "07", "08", "10", "12"));

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

    public static String getAccountName(Context context) {
        String token = (String) SharedPreferencesUtil.getParam(context, "accountName", "");
        return token;
    }

    //获取年份列表
    public static ArrayList<String> getYears() {
        int currentYear = Calendar.getInstance().get(Calendar.YEAR);
        int distance = currentYear - 2018 + 1;
        ArrayList<String> years = new ArrayList<>();
        years.add("全部");
        for (int i = 0; i < distance; i++) {
            years.add(String.valueOf(2018 + i));
        }
        return years;
    }

    //获取月份列表
    public static ArrayList<String> getMonths() {
        ArrayList<String> months = new ArrayList<>();
        months.add("全部");
        for (int i = 1; i <= 12; i++) {
            months.add(i < 10 ? "0" + i : "" + i);
        }
        return months;
    }

    /**
     * 获取天列表
     *
     * @param year
     * @param month
     * @return
     */
    public static ArrayList<String> getDays(String year, String month) {
        ArrayList<String> days = new ArrayList<>();
        days.add("全部");
        for (int i = 1; i < 29; i++) {
            days.add(i < 10 ? "0" + i : "" + i);
        }
        if ("02".equals(month)) {
            int y = Integer.parseInt(year);
            if (y % 4 == 0 && y % 100 != 0 && y % 400 == 0) {
                days.add("29");
            }
        } else {
            days.add("29");
            days.add("30");
            if (thirtyOneDayMonth.contains(month)) {
                days.add("31");
            }
        }
        return days;
    }

    /**
     * 获取FooterView
     *
     * @param context
     * @return
     */
    public static View getFooterView(Context context) {
        return LayoutInflater.from(context).inflate(R.layout.bottom, null);
    }

}
