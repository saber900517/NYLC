package com.nylc.nylc.character.farmer;

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
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.nylc.nylc.BaseActivity;
import com.nylc.nylc.R;
import com.nylc.nylc.character.GoodsOrderDetailActivity;
import com.nylc.nylc.character.ProductOrderDetailActivity;
import com.nylc.nylc.model.BaseResult;
import com.nylc.nylc.model.GoodsOrder;
import com.nylc.nylc.model.ProductOrder;
import com.nylc.nylc.utils.CommonUtils;
import com.nylc.nylc.utils.Urls;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadmoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by kasim on 2018/3/28.
 */

public class FarmerOrderActivity extends BaseActivity implements View.OnClickListener, AdapterView.OnItemSelectedListener {
    private ListView list;
    private Spinner sp_year, sp_month, sp_type;
    private ImageView iv_back;
    private TextView tv_goods, tv_products;
    private ArrayList<String> years, months;
    private String[] typesArray = new String[]{"全部", "待确认", "被选中", "已发布", "待发货", "已发货", "交易完成"};
    private String[] productsTypesArray = new String[]{"全部", "待确认", "已预订", "已发布", "待企业交易", "交易完成", "失效"};
    private List<GoodsOrder> goodsOrders;
    private FarmerGoodsOrderAdapter farmerGoodsOrderAdapter;
    private FarmerProductsOrderAdapter farmerProductsOrderAdapter;
    private View v_goods, v_products;

    private List<ProductOrder> productOrders;
    private SmartRefreshLayout mSmartRefreshLayout;

    private int state = STATE_GOODS;
    private static final int STATE_GOODS = 1;
    private static final int STATE_PRODUCTS = 2;

    private int productsPageSize = 1;
    private int goodsPageSize = 1;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_farmer_order);
        init();
    }

//    @Override
//    @Subscribe(threadMode = ThreadMode.MAIN)
//    public <RefreshEvent> void onEvent(RefreshEvent t) {
//        super.onEvent(t);
//        com.nylc.nylc.eventbus.RefreshEvent event = (com.nylc.nylc.eventbus.RefreshEvent) t;
//        String action = event.getAction();
//        if ("productRefresh".equals(action)) {
//            resetSpinner();
//            productsPageSize = 1;
//            getProductOrders();
//            mSmartRefreshLayout.setLoadmoreFinished(false);
//        }else{
//            resetSpinner();
//            goodsPageSize = 1;
//            getGoodsOrders();
//            mSmartRefreshLayout.setLoadmoreFinished(false);
//        }
//    }

    private void init() {
        list = findViewById(R.id.listView);
        sp_type = findViewById(R.id.sp_type);
        sp_year = findViewById(R.id.sp_year);
        sp_month = findViewById(R.id.sp_month);
        iv_back = findViewById(R.id.iv_back);
        tv_goods = findViewById(R.id.tv_goods);
        tv_products = findViewById(R.id.tv_products);

        v_goods = findViewById(R.id.v_goods);
        v_products = findViewById(R.id.v_products);
        iv_back.setOnClickListener(this);
        tv_goods.setOnClickListener(this);
        tv_products.setOnClickListener(this);

        mSmartRefreshLayout = findViewById(R.id.smartRefreshLayout);
        mSmartRefreshLayout.setOnLoadmoreListener(new OnLoadmoreListener() {
            @Override
            public void onLoadmore(RefreshLayout refreshlayout) {
                refreshlayout.finishLoadmore(500);
                if (state == STATE_GOODS) {
                    goodsPageSize++;
                    getGoodsOrders();
                } else {
                    productsPageSize++;
                    getProductOrders();
                }
            }
        });
        mSmartRefreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(RefreshLayout refreshlayout) {
                mSmartRefreshLayout.setLoadmoreFinished(false);
                refreshlayout.finishRefresh(500);
                if (state == STATE_GOODS) {
                    goodsPageSize = 1;
                    if (goodsOrders != null && goodsOrders.size() > 0) {
                        goodsOrders.clear();
                    }
                    getGoodsOrders();
                } else {
                    productsPageSize = 1;
                    if (productOrders != null && productOrders.size() > 0) {
                        productOrders.clear();
                    }
                    getProductOrders();
                }
            }
        });
        initSpinner();
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                if (state == STATE_GOODS) {
                    GoodsOrderDetailActivity.newInstance(FarmerOrderActivity.this, goodsOrders.get(i));
                } else {
                    ProductOrderDetailActivity.newInstance(FarmerOrderActivity.this, productOrders.get(i));
                }
            }
        });
    }

    private void initSpinner() {
        years = CommonUtils.getYears();
        months = new ArrayList<>();
        months.add("全部");
        sp_year.setAdapter(new ArrayAdapter<String>(this, R.layout.item_one_text, years));
        sp_month.setAdapter(new ArrayAdapter<String>(this, R.layout.item_one_text, months));
        sp_type.setAdapter(new ArrayAdapter<String>(this, R.layout.item_one_text, typesArray));

        sp_year.setOnItemSelectedListener(this);
        sp_month.setOnItemSelectedListener(this);
        sp_type.setOnItemSelectedListener(this);
        getGoodsOrders();
    }

    Callback.Cancelable post;

    private void getGoodsOrders() {
        if (post != null && !post.isCancelled()) post.cancel();
        String year = sp_year.getSelectedItem().toString();
        String month = sp_month.getSelectedItem().toString();
        RequestParams params = new RequestParams(Urls.queryGoodsOrderList);
        params.addBodyParameter("tokenKey", CommonUtils.getToken(this));
        params.addBodyParameter("index", state == STATE_PRODUCTS ? String.valueOf(productsPageSize) : String.valueOf(goodsPageSize));
        if (!TextUtils.isEmpty(year) && !"全部".equals(year)) params.addBodyParameter("year", year);
        if (!TextUtils.isEmpty(month) && !"全部".equals(month))
            params.addBodyParameter("month", month);
        if (sp_type.getSelectedItemPosition() != 0)
            params.addBodyParameter("type", String.valueOf(sp_type.getSelectedItemPosition() + 1));
        post = x.http().post(params, new Callback.CommonCallback<BaseResult>() {
            @Override
            public void onSuccess(BaseResult result) {
                CommonUtils.judgeCode(FarmerOrderActivity.this, result.getCode());
                String level = result.getLevel();
                if ("success".equals(level)) {
                    if (goodsOrders == null) {
                        goodsOrders = new ArrayList<>();
                    }
                    if (farmerGoodsOrderAdapter == null) {
                        farmerGoodsOrderAdapter = new FarmerGoodsOrderAdapter(FarmerOrderActivity.this, goodsOrders);
                    }
                    list.setAdapter(farmerGoodsOrderAdapter);
                    List<GoodsOrder> goodsOrderLists = JSON.parseArray(result.getData(), GoodsOrder.class);
                    goodsOrders.addAll(goodsOrderLists);
                    farmerGoodsOrderAdapter.notifyDataSetChanged();
                    if (goodsOrders == null || goodsOrders.size() <= 0) {

                        Toast.makeText(FarmerOrderActivity.this, "没有查询到买生产资料订单", Toast.LENGTH_SHORT).show();
                        mSmartRefreshLayout.setLoadmoreFinished(true);

                    }

                } else {
                    Toast.makeText(FarmerOrderActivity.this, result.getMsg(), Toast.LENGTH_SHORT).show();
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
            case R.id.tv_goods:
                state = STATE_GOODS;
                goodsPageSize = 1;
                mSmartRefreshLayout.setLoadmoreFinished(false);
                resetSpinner();
                getGoodsOrders();
                v_goods.setVisibility(View.VISIBLE);
                v_products.setVisibility(View.INVISIBLE);
                break;
            case R.id.tv_products:
                state = STATE_PRODUCTS;
                productsPageSize = 1;
                mSmartRefreshLayout.setLoadmoreFinished(false);
                resetSpinner();
                getProductOrders();
                v_goods.setVisibility(View.INVISIBLE);
                v_products.setVisibility(View.VISIBLE);
                break;
        }
    }

    private void resetSpinner() {
        if (sp_year.getSelectedItemPosition() != 0) {
            sp_year.setSelection(0);
        }
        months.clear();
        months.add("全部");
        sp_month.setAdapter(new ArrayAdapter<String>(this, R.layout.item_one_text, R.id.textView, months));
        sp_type.setAdapter(new ArrayAdapter<String>(this, R.layout.item_one_text, state == STATE_GOODS ? typesArray : productsTypesArray));
        sp_type.setOnItemSelectedListener(this);
    }

    private void getProductOrders() {
        if (post != null && !post.isCancelled()) {
            post.cancel();
        }
        RequestParams params = new RequestParams(Urls.queryProductOrderList);
        params.addBodyParameter("tokenKey", CommonUtils.getToken(this));
        if (sp_type.getSelectedItemPosition() != 0)
            params.addBodyParameter("type", sp_type.getSelectedItemPosition() + "");

        if (sp_year.getSelectedItemPosition() != 0)
            params.addBodyParameter("year", sp_year.getSelectedItem().toString());

        if (sp_month.getSelectedItemPosition() != 0)
            params.addBodyParameter("month", sp_month.getSelectedItem().toString());

        params.addBodyParameter("index", state == STATE_GOODS ? String.valueOf(goodsPageSize) : String.valueOf(productsPageSize));

        post = x.http().post(params, new Callback.CommonCallback<BaseResult>() {
            @Override
            public void onSuccess(BaseResult result) {
                CommonUtils.judgeCode(FarmerOrderActivity.this, result.getCode());
                String level = result.getLevel();
                if ("success".equals(level)) {
                    if (productOrders == null) {
                        productOrders = new ArrayList<>();
                    }
                    if (farmerProductsOrderAdapter == null) {
                        farmerProductsOrderAdapter = new FarmerProductsOrderAdapter(FarmerOrderActivity.this, productOrders);
                    }
                    list.setAdapter(farmerProductsOrderAdapter);
                    List<ProductOrder> productOrdersList = JSON.parseArray(result.getData(), ProductOrder.class);
                    productOrders.addAll(productOrdersList);
                    farmerProductsOrderAdapter.notifyDataSetChanged();
                    if (productOrders == null || productOrders.size() <= 0) {

                        Toast.makeText(FarmerOrderActivity.this, "没有查询到卖粮食订单", Toast.LENGTH_SHORT).show();
                        mSmartRefreshLayout.setLoadmoreFinished(true);
                    }

                } else {
                    Toast.makeText(FarmerOrderActivity.this, result.getMsg(), Toast.LENGTH_SHORT).show();
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
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        switch (adapterView.getId()) {
            case R.id.sp_year:
                if (i == 0) {
                    resetSpinner();
                } else {
                    months = CommonUtils.getMonths();
                    sp_month.setAdapter(new ArrayAdapter<String>(this, R.layout.item_one_text, R.id.textView, months));
                }
                sp_month.setSelection(0);
                sp_type.setSelection(0);
                if (state == STATE_GOODS) {
                    if (goodsOrders != null && goodsOrders.size() > 0) goodsOrders.clear();
                    getGoodsOrders();
                } else {
                    if (productOrders != null && productOrders.size() > 0) productOrders.clear();
                    getProductOrders();
                }
                break;
            case R.id.sp_month:
                if (i == 0) {
                    sp_type.setSelection(0);
                }
                if (state == STATE_GOODS) {
                    if (goodsOrders != null && goodsOrders.size() > 0) goodsOrders.clear();
                    getGoodsOrders();
                } else {
                    if (productOrders != null && productOrders.size() > 0) productOrders.clear();
                    getProductOrders();
                }
                break;
            case R.id.sp_type:
                if (state == STATE_GOODS) {
                    if (goodsOrders != null && goodsOrders.size() > 0) goodsOrders.clear();
                    getGoodsOrders();
                } else {
                    if (productOrders != null && productOrders.size() > 0) productOrders.clear();
                    getProductOrders();
                }

                break;

        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }
}
