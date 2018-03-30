package com.nylc.nylc.character.supplier;

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
import com.nylc.nylc.character.farmer.ReserveAdapter;
import com.nylc.nylc.character.farmer.SaleAdapter;
import com.nylc.nylc.model.MyOrder;
import com.nylc.nylc.model.MySale;
import com.nylc.nylc.model.SupplierOrder;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by kasim on 2018/3/28.
 */

public class SupplierOrderActivity extends BaseActivity implements View.OnClickListener {
    private ListView list;
    private Spinner sp_year, sp_other;
    private ImageView iv_back;
    private String[] types = new String[]{"全部", "待确认", "被选中", "已发布", "待发货", "已发货", "交易完成"};

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_supplier_order);
        init();
    }

    private void init() {
        list = findViewById(R.id.listView);
        sp_other = findViewById(R.id.sp_other);
        sp_year = findViewById(R.id.sp_year);
        iv_back = findViewById(R.id.iv_back);
        iv_back.setOnClickListener(this);
        goodsDefaultData();
    }

    private void goodsDefaultData() {
        List<String> years = new ArrayList<>();
        years.add("全部");
        years.add("2017");
        years.add("2018");
        sp_year.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, years));
        sp_other.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, types));

        List<SupplierOrder> orders = new ArrayList<>();
        for (int i = 0; i < 4; i++) {
            SupplierOrder order = new SupplierOrder();
            orders.add(order);
        }
        list.setAdapter(new SupplierOrderAdapter(this, orders));
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
