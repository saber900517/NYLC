package com.nylc.nylc.character.company;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.nylc.nylc.BaseActivity;
import com.nylc.nylc.R;

/**
 * 企业首页
 * Created by 吴曰阳 on 2018/3/2.
 */

public class CompanyIndexActivity extends BaseActivity implements View.OnClickListener {

    private LinearLayout ll_purchase;//商品管理
    private LinearLayout ll_order;//订单
    private ImageView iv_person;//个人中心


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_company_index);
        init();
    }

    private void init() {
        ll_purchase = findViewById(R.id.ll_manage);
        ll_order = findViewById(R.id.ll_order);
        iv_person = findViewById(R.id.iv_person);

        ll_purchase.setOnClickListener(this);
        ll_order.setOnClickListener(this);
        iv_person.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ll_purchase:
                //收购粮食
                startActivity(new Intent(this, ReceiveGrainActivity.class));
                break;
            case R.id.ll_order:
                //订单
                break;
            case R.id.iv_person:
                //个人中心
                break;
        }
    }

    long lastExit = 0;

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (event.getAction() == KeyEvent.ACTION_DOWN && keyCode == KeyEvent.KEYCODE_BACK) {
            if (System.currentTimeMillis() - lastExit >= 2000) {
                Toast.makeText(this, "再按一下返回退出应用", Toast.LENGTH_SHORT).show();
            } else {
                finish();
            }
            lastExit = System.currentTimeMillis();
        }
        return true;
    }
}
