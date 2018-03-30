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
import com.nylc.nylc.model.HistoryOrder;
import com.nylc.nylc.model.MeansOfProduction;

import java.util.ArrayList;
import java.util.List;

/**
 * 历史记录
 * Created by 吴曰阳 on 2018/3/3.
 */

public class HistoryOrderActivity extends BaseActivity implements View.OnClickListener {
    private ImageView iv_back;
    private Spinner sp_county, sp_town;
    private ListView list_type, list_products;
    private String[] counties = {"全部", "滨海县"};
    private String[] towns = {"全部", "滨海港镇"};
    private String[] types = {"化肥", "种子", "农药"};


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history_order);
        init();
    }

    private void init() {
        iv_back = findViewById(R.id.iv_back);
        sp_county = findViewById(R.id.sp_county);
        sp_town = findViewById(R.id.sp_town);
        list_type = findViewById(R.id.list_type);
        list_products = findViewById(R.id.list_products);

        iv_back.setOnClickListener(this);

        defaultData();
    }

    private void defaultData() {
        sp_county.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, counties));
        sp_town.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, towns));
        List<HistoryOrder> list = new ArrayList<>();
        for (int i = 0; i < 4; i++) {
            HistoryOrder order = new HistoryOrder();
            list.add(order);
        }
        list_type.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, types));
        list_products.setAdapter(new HistoryOrderAdapter(this, list));
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.iv_back:
                finish();
                break;

        }
    }
}
