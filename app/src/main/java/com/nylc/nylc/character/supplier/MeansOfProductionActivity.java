package com.nylc.nylc.character.supplier;

import android.content.Intent;
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
import com.nylc.nylc.model.MeansOfProduction;
import com.nylc.nylc.model.NodeMsg;
import com.nylc.nylc.model.ProductType;

import java.util.ArrayList;
import java.util.List;

/**
 * 生产资料供货
 * Created by 吴曰阳 on 2018/3/3.
 */

public class MeansOfProductionActivity extends BaseActivity implements View.OnClickListener {
    private ImageView iv_back;
    private TextView tv_history;
    private Spinner sp_county, sp_town;
    private ListView list_type, list_products;
    //    private String[] counties = {"全部", "滨海县"};
//    private String[] towns = {"全部", "滨海港镇"};
//    private String[] types = {"小麦", "玉米", "水稻"};
//    private List<NodeMsg> counties, towns;
    private List<ProductType> types;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_means_of_production);
        init();
    }

    private void init() {
        iv_back = findViewById(R.id.iv_back);
        tv_history = findViewById(R.id.tv_history);
        sp_county = findViewById(R.id.sp_county);
        sp_town = findViewById(R.id.sp_town);
        list_type = findViewById(R.id.list_type);
        list_products = findViewById(R.id.list_goods);

        iv_back.setOnClickListener(this);
        tv_history.setOnClickListener(this);

//        defaultData();
    }

//    private void defaultData() {
//        sp_county.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, counties));
//        sp_town.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, towns));
//        List<MeansOfProduction> list = new ArrayList<>();
//        for (int i = 0; i < 4; i++) {
//            MeansOfProduction production = new MeansOfProduction();
//            list.add(production);
//        }
//        list_type.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, types));
//        list_products.setAdapter(new MeansOfProductionAdapter(this, list));
//    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_back:
                finish();
                break;
            case R.id.tv_history:
                startActivity(new Intent(this, HistoryOrderActivity.class));
                break;
        }
    }
}
