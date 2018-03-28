package com.nylc.nylc.character.farmer;

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
import com.nylc.nylc.model.MyOrder;
import com.nylc.nylc.model.MySale;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by kasim on 2018/3/28.
 */

public class FarmerOrderActivity extends BaseActivity implements View.OnClickListener {
    private ListView list;
    private Spinner sp_year, sp_other;
    private ImageView iv_back;
    private TextView tv_goods, tv_products;
    private String[] types = new String[]{"全部", "待确认", "被选中", "已发布", "待发货", "已发货", "交易完成"};

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_farmer_order);
        init();
    }

    private void init() {
        list = findViewById(R.id.listView);
        sp_other = findViewById(R.id.sp_other);
        sp_year = findViewById(R.id.sp_year);
        iv_back = findViewById(R.id.iv_back);
        tv_goods = findViewById(R.id.tv_goods);
        tv_products = findViewById(R.id.tv_products);
        iv_back.setOnClickListener(this);
        tv_goods.setOnClickListener(this);
        tv_products.setOnClickListener(this);
        goodsDefaultData();
    }

    private void goodsDefaultData() {
        List<String> years = new ArrayList<>();
        years.add("全部");
        years.add("2017");
        years.add("2018");
        sp_year.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, years));
        sp_other.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, types));

        List<MyOrder> orders = new ArrayList<>();
        for (int i = 0; i < 4; i++) {
            MyOrder order = new MyOrder();
            order.setName("史丹利复合肥");
            order.setPrice("188");
            order.setCount("40");
            order.setState(i % 2);
            orders.add(order);
        }
        list.setAdapter(new ReserveAdapter(this, orders));
    }

    private void productsDefaultData() {
        List<String> years = new ArrayList<>();
        years.add("全部");
        years.add("2017");
        years.add("2018");
        sp_year.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, years));
        sp_other.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, types));

        List<MySale> sales = new ArrayList<>();
        MySale sale = new MySale();
        sale.setName("水稻");
        sale.setPrice("1.6");
        sale.setCount("40");
        sale.setState(0);
        sale.setEarnest("300");
        sales.add(sale);

        MySale sale1 = new MySale();
        sale1.setName("小麦");
        sale1.setPrice("1.4");
        sale1.setCount("60");
        sale1.setEarnest("300");
        sale1.setState(1);
        sales.add(sale1);

        MySale sale2 = new MySale();
        sale2.setName("小麦");
        sale2.setPrice("1.4");
        sale2.setCount("70");
        sale2.setState(2);
        sale2.setEarnest("300");
        sales.add(sale2);
        list.setAdapter(new SaleAdapter(this, sales));
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_back:
                finish();
                break;
            case R.id.tv_goods:
                goodsDefaultData();
                break;
            case R.id.tv_products:
                productsDefaultData();
                break;
        }
    }
}
