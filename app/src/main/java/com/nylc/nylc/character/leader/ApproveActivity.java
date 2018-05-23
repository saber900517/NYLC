package com.nylc.nylc.character.leader;

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
import com.nylc.nylc.character.TypeAdapter;
import com.nylc.nylc.model.ApproveBuy;
import com.nylc.nylc.model.ApproveSale;
import com.nylc.nylc.model.BaseResult;
import com.nylc.nylc.model.GoodsType;
import com.nylc.nylc.utils.CommonUtils;
import com.nylc.nylc.utils.Urls;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadmoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.util.ArrayList;
import java.util.List;

/**
 * 审批首页
 * Created by 吴曰阳 on 2018/3/22.
 */

public class ApproveActivity extends BaseActivity implements View.OnClickListener, AdapterView.OnItemSelectedListener {

    private ImageView iv_back;
    private TextView tv_goods, tv_products;
    private ImageView iv_order, iv_order1;
    private Spinner sp_year, sp_month, sp_type;
    //1：待确认2：被选中3：已发布 4：待发货 5：已发货 6：交易完成
    private ArrayList<String> years, months;
    private ListView list;
    private List<GoodsType> goodsTypes;
    private View v_goods, v_products;

    private TypeAdapter typeAdapter;

    private int state = STATE_GOODS;
    private static final int STATE_GOODS = 1;
    private static final int STATE_PRODUCTS = 2;

    private int goodsPageSize = 1;
    private int productsPageSize = 1;
    private List<ApproveBuy> approveBuyList;
    private List<ApproveSale> approveSaleList;
    private SmartRefreshLayout mSmartRefreshLayout;
    private ApproveBuyAdapter approveBuyAdapter;
    private ApproveSaleAdapter approveSaleAdapter;
    private boolean isFirstLoaded = false;


    //    private String[] years = new String[]{"全部", "2017", "2018"};
//    private String[] months = new String[]{"全部", "01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12"};
//    private String[] days = new String[]{"全部", "01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23", "24", "25", "26", "27", "28", "29", "30", "31"};
    private String[] typesArray = new String[]{"全部", "待确认", "被选中", "已发布", "待发货", "已发货", "交易完成"};
    private String[] productsTypesArray = new String[]{"全部", "待确认", "已预订", "已发布", "待企业交易", "交易完成", "失效"};

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_approve);
        init();
    }

    private void init() {
        iv_back = findViewById(R.id.iv_back);
        iv_back.setOnClickListener(this);

        tv_goods = findViewById(R.id.tv_goods);
        tv_goods.setOnClickListener(this);

        tv_products = findViewById(R.id.tv_products);
        tv_products.setOnClickListener(this);

        iv_order = findViewById(R.id.iv_order);
        iv_order.setOnClickListener(this);

        iv_order1 = findViewById(R.id.iv_order1);
        iv_order1.setOnClickListener(this);

        list = findViewById(R.id.list);

        v_goods = findViewById(R.id.v_goods);
        v_products = findViewById(R.id.v_products);

        sp_year = findViewById(R.id.sp_year);
        sp_month = findViewById(R.id.sp_month);
        sp_type = findViewById(R.id.sp_type);

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
                    getProductsOrders();
                }

            }
        });
//        mSmartRefreshLayout.setEnableRefresh(false);
        mSmartRefreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(RefreshLayout refreshlayout) {
                refreshlayout.finishRefresh(500);
                mSmartRefreshLayout.setLoadmoreFinished(false);
                if (state == STATE_GOODS) {
                    goodsPageSize = 1;
                    if (approveBuyList != null && approveBuyList.size() > 0) {
                        approveBuyList.clear();
                    }
                    getGoodsOrders();
                } else {
                    productsPageSize = 1;
                    if (approveSaleList != null && approveSaleList.size() > 0) {
                        approveSaleList.clear();
                    }
                    getProductsOrders();
                }
            }
        });
        initSpinner();
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                if (state == STATE_GOODS) {
                    GoodsOrderDetailActivity.newInstance(ApproveActivity.this, approveBuyList.get(i));
                } else {
                    ProductOrderDetailActivity.newInstance(ApproveActivity.this, approveSaleList.get(i));
                }
            }
        });
    }

    private void initSpinner() {
        years = CommonUtils.getYears();
        months = new ArrayList<>();
        months.add("全部");
        sp_year.setAdapter(new ArrayAdapter<String>(this, R.layout.item_one_text, R.id.textView, years));
        sp_month.setAdapter(new ArrayAdapter<String>(this, R.layout.item_one_text, R.id.textView, months));
        sp_type.setAdapter(new ArrayAdapter<String>(this, R.layout.item_one_text, R.id.textView, state == STATE_GOODS ? typesArray : productsTypesArray));

        sp_year.setOnItemSelectedListener(this);
        sp_month.setOnItemSelectedListener(this);
        sp_type.setOnItemSelectedListener(this);

        if (state == STATE_GOODS) {
            goodsPageSize = 1;
            if (approveBuyList != null && approveBuyList.size() > 0) approveBuyList.clear();
            getGoodsOrders();
        } else {
            productsPageSize = 1;
            if (approveSaleList != null && approveSaleList.size() > 0) approveSaleList.clear();
            getProductsOrders();
        }
    }

//    private void approveBuyDefaultData() {
//        List<ApproveBuy> mList = new ArrayList<>();
//        for (int i = 0; i < 6; i++) {
//            ApproveBuy buy = new ApproveBuy();
//            mList.add(buy);
//        }
//        list.setAdapter(new ApproveBuyAdapter(this, mList));
//        getGoodsOrders("全部", "全部", "全部");
//    }

    @Override
    @Subscribe(threadMode = ThreadMode.MAIN)
    public <ApproveEvent> void onEvent(ApproveEvent t) {
        super.onEvent(t);
        com.nylc.nylc.eventbus.ApproveEvent event = (com.nylc.nylc.eventbus.ApproveEvent) t;
        int refreshType = event.getRefreshType();
        if (refreshType == STATE_GOODS) {
            //刷新商品列表
            initSpinner();
        }
        if (refreshType == STATE_PRODUCTS) {
            //刷新农产品列表
            initSpinner();
        }

    }

    private void approveSaleDefaultData() {
        List<ApproveSale> mList = new ArrayList<>();
        for (int i = 0; i < 4; i++) {
            ApproveSale sale = new ApproveSale();
            mList.add(sale);
        }
        list.setAdapter(new ApproveSaleAdapter(this, mList, getSupportFragmentManager()));
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_back:
                finish();
                break;
            case R.id.tv_products:
                state = STATE_PRODUCTS;
                mSmartRefreshLayout.setLoadmoreFinished(false);
                productsPageSize = 1;
                iv_order1.setVisibility(View.VISIBLE);
                if (approveSaleList != null && approveSaleList.size() > 0) approveSaleList.clear();
                getProductsOrders();
                v_goods.setVisibility(View.INVISIBLE);
                v_products.setVisibility(View.VISIBLE);
                initSpinner();
                break;
            case R.id.tv_goods:
                state = STATE_GOODS;
                goodsPageSize = 1;
                mSmartRefreshLayout.setLoadmoreFinished(false);
                iv_order1.setVisibility(View.GONE);
                if (approveBuyList != null && approveBuyList.size() > 0) approveBuyList.clear();
                getGoodsOrders();
                v_goods.setVisibility(View.VISIBLE);
                v_products.setVisibility(View.INVISIBLE);
                initSpinner();
                break;
            case R.id.iv_order:
                if (state == STATE_GOODS) {
                    BuyOrderFragmentDialog buyOrderFragmentDialog = new BuyOrderFragmentDialog();
                    buyOrderFragmentDialog.show(getSupportFragmentManager(), "buyOrderDialog");
                } else {
                    SaleOrderFragmentDialog saleOrderFragmentDialog = new SaleOrderFragmentDialog();
                    saleOrderFragmentDialog.show(getSupportFragmentManager(), "saleOrderDialog");
                }
                break;
            case R.id.iv_order1:
                SaleBatchOrderFragmentDialog saleBatchOrderFragmentDialog = new SaleBatchOrderFragmentDialog();
                saleBatchOrderFragmentDialog.show(getSupportFragmentManager(), "saleBatchOrderDialog");
                break;
        }
    }

    private void getProductsOrders() {
        if (post != null && !post.isCancelled()) post.cancel();
        String year = sp_year.getSelectedItem().toString();
        String month = sp_month.getSelectedItem().toString();
        String type = sp_type.getSelectedItem().toString();
        RequestParams params = new RequestParams(Urls.queryApprovalProductOrderList);
        params.addBodyParameter("tokenKey", CommonUtils.getToken(this));
        params.addBodyParameter("index", state == STATE_PRODUCTS ? String.valueOf(productsPageSize) : String.valueOf(goodsPageSize));
        if (!TextUtils.isEmpty(year) && !"全部".equals(year)) params.addBodyParameter("year", year);
        if (!TextUtils.isEmpty(month) && !"全部".equals(month))
            params.addBodyParameter("month", month);
        if (!TextUtils.isEmpty(type) && !"全部".equals(type))
            params.addBodyParameter("type", sp_type.getSelectedItemPosition() + "");

        post = x.http().post(params, new Callback.CommonCallback<BaseResult>() {
            @Override
            public void onSuccess(BaseResult result) {
                CommonUtils.judgeCode(ApproveActivity.this, result.getCode());
                String level = result.getLevel();
                if ("success".equals(level)) {
                    if (approveSaleList == null) {
                        approveSaleList = new ArrayList<>();
                    }
                    if (approveSaleAdapter == null) {
                        approveSaleAdapter = new ApproveSaleAdapter(ApproveActivity.this, approveSaleList, getSupportFragmentManager());
                    }
                    list.setAdapter(approveSaleAdapter);
                    List<ApproveSale> approveSales = JSON.parseArray(result.getData(), ApproveSale.class);
                    approveSaleList.addAll(approveSales);
                    approveSaleAdapter.notifyDataSetChanged();
                    if (approveSales == null || approveSales.size() <= 0) {
                        Toast.makeText(ApproveActivity.this, "没有查询到相关数据", Toast.LENGTH_SHORT).show();
                        mSmartRefreshLayout.setLoadmoreFinished(true);
                    }
                } else {
                    Toast.makeText(ApproveActivity.this, result.getMsg(), Toast.LENGTH_SHORT).show();
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


    Callback.Cancelable post;

    private void getGoodsOrders() {
        if (post != null && !post.isCancelled()) post.cancel();
        String year = sp_year.getSelectedItem().toString();
        String month = sp_month.getSelectedItem().toString();
        int type = sp_type.getSelectedItemPosition() + 1;
        RequestParams params = new RequestParams(Urls.queryApprovalGoodsOrderList);
        params.addBodyParameter("tokenKey", CommonUtils.getToken(this));
        params.addBodyParameter("index", state == STATE_PRODUCTS ? String.valueOf(productsPageSize) : String.valueOf(goodsPageSize));
        if (!TextUtils.isEmpty(year) && !"全部".equals(year)) params.addBodyParameter("year", year);
        if (!TextUtils.isEmpty(month) && !"全部".equals(month))
            params.addBodyParameter("month", month);
        params.addBodyParameter("type", String.valueOf(type));
        post = x.http().post(params, new Callback.CommonCallback<BaseResult>() {
            @Override
            public void onSuccess(BaseResult result) {
                isFirstLoaded = true;
                CommonUtils.judgeCode(ApproveActivity.this, result.getCode());
                String level = result.getLevel();
                if ("success".equals(level)) {
                    if (approveBuyList == null) {
                        approveBuyList = new ArrayList<>();
                    }
                    if (approveBuyAdapter == null) {
                        approveBuyAdapter = new ApproveBuyAdapter(ApproveActivity.this, approveBuyList);
                    }
                    list.setAdapter(approveBuyAdapter);
                    List<ApproveBuy> approveBuys = JSON.parseArray(result.getData(), ApproveBuy.class);
                    approveBuyList.addAll(approveBuys);
                    approveBuyAdapter.notifyDataSetChanged();
                    if (approveBuys == null || approveBuys.size() <= 0) {

                        Toast.makeText(ApproveActivity.this, "没有查询到相关数据", Toast.LENGTH_SHORT).show();
                        mSmartRefreshLayout.setLoadmoreFinished(true);
                    }

                } else {
                    Toast.makeText(ApproveActivity.this, result.getMsg(), Toast.LENGTH_SHORT).show();
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
                    months.clear();
                    months.add("全部");
                    sp_month.setAdapter(new ArrayAdapter<String>(this, R.layout.item_one_text, R.id.textView, months));
                } else {
                    months = CommonUtils.getMonths();
                    sp_month.setAdapter(new ArrayAdapter<String>(this, R.layout.item_one_text, R.id.textView, months));
                }
                sp_month.setSelection(0);
                if (state == STATE_GOODS) {
                    if (approveBuyList != null && approveBuyList.size() > 0) approveBuyList.clear();
                    if (!isFirstLoaded) return;
                    getGoodsOrders();
                } else {
                    if (approveSaleList != null && approveSaleList.size() > 0)
                        approveSaleList.clear();
                    getProductsOrders();
                }
                break;
            case R.id.sp_month:
                if (state == STATE_GOODS) {
                    if (approveBuyList != null && approveBuyList.size() > 0) approveBuyList.clear();
                    if (!isFirstLoaded) return;
                    getGoodsOrders();
                } else {
                    if (approveSaleList != null && approveSaleList.size() > 0)
                        approveSaleList.clear();
                    getProductsOrders();
                }

                break;
            case R.id.sp_type:
                if (state == STATE_GOODS) {
                    if (approveBuyList != null && approveBuyList.size() > 0) approveBuyList.clear();
                    if (!isFirstLoaded) return;
                    getGoodsOrders();
                } else {
                    if (approveSaleList != null && approveSaleList.size() > 0)
                        approveSaleList.clear();
                    getProductsOrders();
                }
                break;

        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }
}