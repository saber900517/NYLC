package com.nylc.nylc.character.farmer;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.nylc.nylc.BaseActivity;
import com.nylc.nylc.R;
import com.nylc.nylc.character.ProductTypeAdapter;
import com.nylc.nylc.model.BaseResult;
import com.nylc.nylc.model.Product;
import com.nylc.nylc.model.ProductType;
import com.nylc.nylc.utils.CommonUtils;
import com.nylc.nylc.utils.Urls;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by kasim on 2018/3/25.
 */

public class SaleActivity extends BaseActivity implements View.OnClickListener {
    private ImageView iv_back;//返回
    private TextView tv_reserve;//添加
    private ListView list_type;//商品类型列表
    private ListView list_products;//商品列表

    private int typeIndex = 0;//当前展示的类型的index

    private List<Product> products;
    private List<ProductType> productTypes;

    private ProductTypeAdapter productTypeAdapter;
    private FarmerProductsAdapter productsAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_buy);
        init();
    }

    private void init() {
        iv_back = findViewById(R.id.iv_back);
        tv_reserve = findViewById(R.id.tv_reserve);
        list_products = findViewById(R.id.list_products);
        list_type = findViewById(R.id.list_type);

        tv_reserve.setOnClickListener(this);
        iv_back.setOnClickListener(this);

        getProductsType();
    }

    private void getProductsType() {
        RequestParams params = new RequestParams(Urls.queryGoodsTypeAction);
        params.addBodyParameter("tokenKey", CommonUtils.getToken(this));
        x.http().post(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                BaseResult baseResult = JSON.parseObject(result, BaseResult.class);
                String code = baseResult.getCode();
                CommonUtils.judgeCode(SaleActivity.this, code);
                String level = baseResult.getLevel();
                if ("success".equals(level)) {
                    //请求成功
                    productTypes = JSON.parseArray(baseResult.getData(), ProductType.class);
                    productTypeAdapter = new ProductTypeAdapter(productTypes, SaleActivity.this);
                    list_type.setAdapter(productTypeAdapter);
                    list_type.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                            ProductType productType = productTypes.get(i);
                            String display_name_zh = productType.getDISPLAY_NAME_ZH();
                            getProducts();
                            typeIndex = i;
                        }
                    });
                    productTypeAdapter.notifyDataSetChanged();
                    if (productTypes != null && productTypes.size() > 0) {
                        ProductType productType = productTypes.get(typeIndex);
                        String display_name_zh = productType.getDISPLAY_NAME_ZH();
                        getProducts();
                    }
                } else {
                    String msg = baseResult.getMsg();
                    Toast.makeText(SaleActivity.this, msg, Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                Log.e("error", ex.getMessage());
                Toast.makeText(SaleActivity.this, "连接服务器失败", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onCancelled(CancelledException cex) {

            }

            @Override
            public void onFinished() {

            }
        });

    }

    private void getProducts() {
        RequestParams params = new RequestParams(Urls.queryGoodsListAction);
        params.addBodyParameter("tokenKey", CommonUtils.getToken(this));
        x.http().post(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                Log.i("", "");
                BaseResult baseResult = JSON.parseObject(result, BaseResult.class);
                CommonUtils.judgeCode(SaleActivity.this, baseResult.getCode());
                String level = baseResult.getLevel();
                if ("success".equals(level)) {
                    //请求成功
                    String data = baseResult.getData();
                    List<Product> list = JSON.parseArray(data, Product.class);
                    if (list != null && list.size() > 0) {
                        //有数据
                        if (products == null) {
                            products = new ArrayList<>();
                        } else {
                            products.clear();
                        }
                        products.addAll(list);
                        productsAdapter = new FarmerProductsAdapter(products, SaleActivity.this);
                        list_products.setAdapter(productsAdapter);
                    } else {
                        //没有数据
                        products.clear();
                        productsAdapter.notifyDataSetChanged();
                        Toast.makeText(SaleActivity.this, "没有商品", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    //请求失败
                    Toast.makeText(SaleActivity.this, baseResult.getMsg(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                Log.e("error", ex.getMessage());
                Toast.makeText(SaleActivity.this, "连接服务器失败", Toast.LENGTH_SHORT).show();
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
            case R.id.tv_reserve:
                //预定
                sendPushMessageToLeader();
                break;
            case R.id.iv_back:
                finish();
                break;
        }
    }

    private void sendPushMessageToLeader() {
        RequestParams params = new RequestParams(Urls.buyPublishAction);
        params.addBodyParameter("tokenKey",CommonUtils.getToken(this));
        x.http().post(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                BaseResult baseResult = JSON.parseObject(result, BaseResult.class);

            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {

            }

            @Override
            public void onCancelled(CancelledException cex) {

            }

            @Override
            public void onFinished() {

            }
        });
    }
}
