package com.nylc.nylc.character.farmer;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.Editable;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.nylc.nylc.BaseActivity;
import com.nylc.nylc.R;

/**
 * 想借钱
 * Created by 吴曰阳 on 2018/3/4.
 */

public class BorrowMoneyActivity extends BaseActivity implements View.OnClickListener {
    private ImageView iv_back;
    private TextView tv_manage;
    private EditText et_foodsCount, et_borrowMoney;
    private Button bt_apply;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_borrow_money);
        init();
    }

    private void init() {
        iv_back = findViewById(R.id.iv_back);
        tv_manage = findViewById(R.id.tv_manage);
        et_foodsCount = findViewById(R.id.et_foodsCount);
        et_borrowMoney = findViewById(R.id.et_borrowMoney);
        bt_apply = findViewById(R.id.bt_apply);

        iv_back.setOnClickListener(this);
        bt_apply.setOnClickListener(this);
        tv_manage.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_back:
                finish();
                break;
            case R.id.bt_apply:
                Toast.makeText(this, "提交成功", Toast.LENGTH_SHORT).show();
                break;
            case R.id.tv_manage:
                startActivity(new Intent(this, BorrowHistoryActivity.class));
                break;
        }
    }
}
