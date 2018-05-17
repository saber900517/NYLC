package com.nylc.nylc.character.supplier;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.nylc.nylc.BaseActivity;
import com.nylc.nylc.R;
import com.nylc.nylc.character.farmer.FarmerGoodsOrderAdapter;
import com.nylc.nylc.model.BaseResult;
import com.nylc.nylc.model.GoodsOrder;
import com.nylc.nylc.model.SupplierOrder;
import com.nylc.nylc.utils.CommonUtils;
import com.nylc.nylc.utils.Urls;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadmoreListener;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by kasim on 2018/3/28.
 */

public class SupplierOrderActivity extends BaseActivity implements View.OnClickListener, AdapterView.OnItemSelectedListener {
    private ListView list;
    private Spinner sp_year, sp_type, sp_month;
    private ImageView iv_back;
    private List<String> years, months;
    private String[] types = new String[]{"全部", "待确认", "被选中", "已发布", "待发货", "已发货", "交易完成"};
    private int pageIndex = 1;
    private List<GoodsOrder> goodsOrders;
    private FarmerGoodsOrderAdapter farmerGoodsOrderAdapter;
    private boolean isFirstLoaded = false;//第一次加载完成

    private SmartRefreshLayout mSmartRefreshLayout;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_supplier_order);
        init();
    }

    private void init() {
        list = findViewById(R.id.listView);
        sp_type = findViewById(R.id.sp_type);
        sp_year = findViewById(R.id.sp_year);
        sp_month = findViewById(R.id.sp_month);
        iv_back = findViewById(R.id.iv_back);
        iv_back.setOnClickListener(this);
        years = CommonUtils.getYears();
        months = new ArrayList<>();
        months.add("全部");
        sp_year.setAdapter(new ArrayAdapter<String>(this, R.layout.item_one_text, years));
        sp_month.setAdapter(new ArrayAdapter<String>(this, R.layout.item_one_text, months));
        sp_type.setAdapter(new ArrayAdapter<String>(this, R.layout.item_one_text, types));
        sp_year.setOnItemSelectedListener(this);
        sp_month.setOnItemSelectedListener(this);
        sp_type.setOnItemSelectedListener(this);
        mSmartRefreshLayout = findViewById(R.id.smartRefreshLayout);
        mSmartRefreshLayout.setOnLoadmoreListener(new OnLoadmoreListener() {
            @Override
            public void onLoadmore(RefreshLayout refreshlayout) {
                pageIndex++;
                getGoodsOrders();
            }
        });
        getGoodsOrders();
    }


    Callback.Cancelable post;

    private void getGoodsOrders() {
        if (post != null && !post.isCancelled()) post.cancel();
        String year = sp_year.getSelectedItem().toString();
        String month = sp_month.getSelectedItem().toString();
        RequestParams params = new RequestParams(Urls.queryGoodsOrderList);
        params.addBodyParameter("tokenKey", CommonUtils.getToken(this));
        params.addBodyParameter("index", String.valueOf(pageIndex));
        if (!TextUtils.isEmpty(year) && !"全部".equals(year)) params.addBodyParameter("year", year);
        if (!TextUtils.isEmpty(month) && !"全部".equals(month))
            params.addBodyParameter("month", month);
        if (sp_type.getSelectedItemPosition() != 0)
            params.addBodyParameter("type", String.valueOf(sp_type.getSelectedItemPosition()));
        post = x.http().post(params, new Callback.CommonCallback<BaseResult>() {
            @Override
            public void onSuccess(BaseResult result) {
                isFirstLoaded = true;
                CommonUtils.judgeCode(SupplierOrderActivity.this, result.getCode());
                String level = result.getLevel();
                if ("success".equals(level)) {
                    if (goodsOrders == null) {
                        goodsOrders = new ArrayList<>();
                    }
                    if (farmerGoodsOrderAdapter == null) {
                        farmerGoodsOrderAdapter = new FarmerGoodsOrderAdapter(SupplierOrderActivity.this, goodsOrders);
                    }
                    list.setAdapter(farmerGoodsOrderAdapter);
                    List<GoodsOrder> goodsOrderLists = JSON.parseArray(result.getData(), GoodsOrder.class);
                    goodsOrders.addAll(goodsOrderLists);
                    farmerGoodsOrderAdapter.notifyDataSetChanged();
                    if (goodsOrders == null || goodsOrders.size() <= 0) {

                        Toast.makeText(SupplierOrderActivity.this, "没有查询到相关数据", Toast.LENGTH_SHORT).show();
                        mSmartRefreshLayout.setLoadmoreFinished(true);
                    }

                } else {
                    Toast.makeText(SupplierOrderActivity.this, result.getMsg(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                Log.i("", "");
            }

            @Override
            public void onCancelled(CancelledException cex) {

            }

            @Override
            public void onFinished() {

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

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        switch (adapterView.getId()) {
            case R.id.sp_year:
                if (i == 0) {
                    months.clear();
                    months.add("全部");
                    sp_month.setAdapter(new ArrayAdapter<String>(this, R.layout.item_one_text, R.id.textView, months));
                } else {
                    months = CommonUtils.getMonths();
                    sp_month.setAdapter(new ArrayAdapter<String>(this, R.layout.item_one_text, R.id.textView, months));
                }
                sp_month.setSelection(0);

                if (goodsOrders != null && goodsOrders.size() > 0) goodsOrders.clear();
                if (!isFirstLoaded) return;
                getGoodsOrders();

                break;
            case R.id.sp_month:

                if (goodsOrders != null && goodsOrders.size() > 0) goodsOrders.clear();
                if (!isFirstLoaded) return;
                getGoodsOrders();


                break;
            case R.id.sp_type:

                if (goodsOrders != null && goodsOrders.size() > 0) goodsOrders.clear();
                if (!isFirstLoaded) return;
                getGoodsOrders();

                break;

        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }
}
