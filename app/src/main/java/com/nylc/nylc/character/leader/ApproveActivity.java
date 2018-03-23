package com.nylc.nylc.character.leader;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

import com.nylc.nylc.BaseActivity;
import com.nylc.nylc.R;
import com.nylc.nylc.utils.CommonUtils;

import java.util.ArrayList;

/**
 * 审批首页
 * Created by 吴曰阳 on 2018/3/22.
 */

public class ApproveActivity extends BaseActivity implements View.OnClickListener {

    private ImageView iv_back;
    private TextView tv_products, tv_foods;
    private Spinner sp_year, sp_month, sp_day, sp_type;
    private ArrayList<String> years, months, days, types;
    private ListView list;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_approve);
        init();
    }

    private void init() {
        iv_back = findViewById(R.id.iv_back);
        iv_back.setOnClickListener(this);

        tv_products = findViewById(R.id.tv_products);
        tv_products.setOnClickListener(this);

        tv_foods = findViewById(R.id.tv_foods);
        tv_foods.setOnClickListener(this);

        list = findViewById(R.id.list);

        sp_year = findViewById(R.id.sp_year);
        sp_month = findViewById(R.id.sp_month);
        sp_day = findViewById(R.id.sp_day);
        sp_type = findViewById(R.id.sp_type);

        years = CommonUtils.getYears();
        months = CommonUtils.getMonths();
        days = CommonUtils.getDays(years.get(1), months.get(1));
        sp_year.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, years));
        sp_month.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, months));
        sp_day.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, days));

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
