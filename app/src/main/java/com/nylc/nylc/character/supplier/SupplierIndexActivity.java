package com.nylc.nylc.character.supplier;

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
import com.nylc.nylc.personal.PersonalActivity;

/**
 * 供货商首页
 * Created by 吴曰阳 on 2018/3/2.
 */

public class SupplierIndexActivity extends BaseActivity implements View.OnClickListener {

    private LinearLayout ll_manage;//商品管理
    private LinearLayout ll_supply;//供货中心
    private LinearLayout ll_order;//订单
    private ImageView iv_person;//个人中心


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_supplier_index);
        init();
    }

    private void init() {
        ll_manage = findViewById(R.id.ll_manage);
        ll_supply = findViewById(R.id.ll_supply);
        ll_order = findViewById(R.id.ll_order);
        iv_person = findViewById(R.id.iv_person);

        ll_manage.setOnClickListener(this);
        ll_supply.setOnClickListener(this);
        ll_order.setOnClickListener(this);
        iv_person.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ll_manage:
                //商品管理
                startActivity(new Intent(this, ManageProductsActivity.class));
                break;
            case R.id.ll_supply:
                //供货中心
                break;
            case R.id.ll_order:
                //订单
                break;
            case R.id.iv_person:
                //个人中心
                startActivity(new Intent(this, PersonalActivity.class));
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
