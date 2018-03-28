package com.nylc.nylc.character.farmer;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Spinner;

import com.nylc.nylc.BaseActivity;
import com.nylc.nylc.R;
import com.nylc.nylc.model.MySale;
import com.nylc.nylc.utils.CommonUtils;
import com.nylc.nylc.utils.Urls;

import org.xutils.http.RequestParams;

import java.util.ArrayList;
import java.util.List;

/**
 * 我的预定
 * Created by 吴曰阳 on 2018/3/4.
 */

public class MySaleActivity extends BaseActivity implements View.OnClickListener {


    private ImageView iv_back;
    private Spinner sp_year, sp_month, sp_other;

    private ListView list;

    private ArrayList<String> years, months;
    private String[] types = new String[]{"全部", "待确认", "被选中", "已发布", "待发货", "已发货", "交易完成"};

    private String year = "全部", month = "全部", type = "全部";

    private int index = 1;

    private Button loadMore;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_reserve);
        init();
    }

    private void init() {
        iv_back = findViewById(R.id.iv_back);
        iv_back.setOnClickListener(this);

        sp_year = findViewById(R.id.sp_year);
        sp_month = findViewById(R.id.sp_month);
        sp_other = findViewById(R.id.sp_other);
//        sp_month.setVisibility(View.GONE);
//        setSpinnerData();

        list = findViewById(R.id.list);
//        View footerView = CommonUtils.getFooterView(this);
//        loadMore = footerView.findViewById(R.id.load);
//        loadMore.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//
//            }
//        });
//        list.addFooterView(footerView);

//        getOrderList();

        defaultData();
    }

    private void defaultData() {
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

    /**
     * 获取订单列表
     */
    private void getOrderList() {
        //TODO
        RequestParams params = new RequestParams(Urls.queryGoodsOrderList);
        if (!"全部".equals(year)) {
            params.addBodyParameter("year", year);
            if (!"全部".equals(month)) {
                params.addBodyParameter("month", month);
            }
        }
        if (!"全部".equals(type)) {
            params.addBodyParameter("type", type);
        }
        params.addBodyParameter("tokenKey", CommonUtils.getToken(this));
        params.addBodyParameter("index", String.valueOf(index));
    }

    private void setSpinnerData() {
        years = CommonUtils.getYears();
        months = CommonUtils.getMonths();
        sp_year.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, years));
        sp_month.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, months));
        sp_other.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, types));

        sp_year.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                sp_month.setVisibility(i == 0 ? View.GONE : View.VISIBLE);

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });


        sp_month.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        sp_other.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

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
