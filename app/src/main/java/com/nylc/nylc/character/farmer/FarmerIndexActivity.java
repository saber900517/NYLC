package com.nylc.nylc.character.farmer;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.nylc.nylc.BaseActivity;
import com.nylc.nylc.R;

/**
 * 农民首页
 * Created by 吴曰阳 on 2018/3/2.
 */

public class FarmerIndexActivity extends BaseActivity implements View.OnClickListener {
    private LinearLayout ll_buy;//买生产资料
    private LinearLayout ll_sale;//卖粮食
    private LinearLayout ll_borrow;//想借钱
    private LinearLayout ll_order;//订单
    private ImageView iv_person;//个人中心

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_farmer_index);
        init();
    }

    private void init() {
        ll_buy = findViewById(R.id.ll_buy);
        ll_sale = findViewById(R.id.ll_sale);
        ll_borrow = findViewById(R.id.ll_borrow);
        ll_order = findViewById(R.id.ll_order);
        iv_person = findViewById(R.id.iv_person);

        ll_buy.setOnClickListener(this);
        ll_sale.setOnClickListener(this);
        ll_borrow.setOnClickListener(this);
        ll_order.setOnClickListener(this);
        iv_person.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ll_buy:
                //买生产资料
                break;
            case R.id.ll_sale:
                //卖粮食
                break;
            case R.id.ll_borrow:
                //借钱
                break;
            case R.id.ll_order:
                //订单
                break;
            case R.id.iv_person:
                //个人中心
                break;
        }
    }
}
