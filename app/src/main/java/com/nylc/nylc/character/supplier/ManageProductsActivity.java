package com.nylc.nylc.character.supplier;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.nylc.nylc.BaseActivity;
import com.nylc.nylc.R;
import com.nylc.nylc.eventbus.UpdateGoodsStateEvent;
import com.nylc.nylc.model.BaseResult;
import com.nylc.nylc.model.Product;
import com.nylc.nylc.model.ProductType;
import com.nylc.nylc.utils.CommonUtils;
import com.nylc.nylc.utils.Urls;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.util.ArrayList;
import java.util.List;

/**
 * 商品管理
 * Created by 吴曰阳 on 2018/3/2.
 */

public class ManageProductsActivity extends BaseActivity implements View.OnClickListener {
    private ImageView iv_back;//返回
    private ImageView iv_add;//添加
    private ListView list_type;//商品类型列表
    private ListView list_products;//商品列表

    private int ADD_OR_UPDATE_PRODUCT = 123;

    private int typeIndex = 0;//当前展示的类型的index

    private List<Product> products;
    private List<ProductType> productTypes;

    private ManageProductTypeAdapter productTypeAdapter;
    private ManageProductsAdapter productsAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage_products);
        EventBus.getDefault().register(this);
        init();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void UpdateGoodsStateEvent(UpdateGoodsStateEvent event) {

    }

    private void init() {
        iv_back = findViewById(R.id.iv_back);
        iv_add = findViewById(R.id.iv_add);
        list_products = findViewById(R.id.list_products);
        list_type = findViewById(R.id.list_type);

        iv_add.setOnClickListener(this);
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
                CommonUtils.judgeCode(ManageProductsActivity.this, code);
                String level = baseResult.getLevel();
                if ("success".equals(level)) {
                    //请求成功
                    productTypes = JSON.parseArray(baseResult.getData(), ProductType.class);
                    productTypeAdapter = new ManageProductTypeAdapter(productTypes, ManageProductsActivity.this);
                    list_type.setAdapter(productTypeAdapter);
                    list_type.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                            ProductType productType = productTypes.get(i);
                            String display_name_zh = productType.getDISPLAY_NAME_ZH();
                            getProducts(display_name_zh);
                            typeIndex = i;
                        }
                    });
                    productTypeAdapter.notifyDataSetChanged();
                    if (productTypes != null && productTypes.size() > 0) {
                        ProductType productType = productTypes.get(typeIndex);
                        String display_name_zh = productType.getDISPLAY_NAME_ZH();
                        getProducts(display_name_zh);
                    }
                } else {
                    String msg = baseResult.getMsg();
                    Toast.makeText(ManageProductsActivity.this, msg, Toast.LENGTH_SHORT).show();
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

    private void getProducts(final String display_name_zh) {
        RequestParams params = new RequestParams(Urls.queryGoodsListAction);
        params.addBodyParameter("tokenKey", CommonUtils.getToken(this));
        params.addBodyParameter("goodsType", display_name_zh);
        x.http().post(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                Log.i("", "");
                BaseResult baseResult = JSON.parseObject(result, BaseResult.class);
                CommonUtils.judgeCode(ManageProductsActivity.this, baseResult.getCode());
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
                        productsAdapter = new ManageProductsAdapter(products, ManageProductsActivity.this);
                        list_products.setAdapter(productsAdapter);
                        list_products.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                            @Override
                            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                                ManageProductsAdapter adapter = (ManageProductsAdapter) adapterView.getAdapter();
                                List<Product> list = adapter.getList();
                                Product product = list.get(i);
                                int status = product.getSTATUS();
                                if (status == 0) {
                                    //商品无效，已经下架了
                                    Intent intent = new Intent(ManageProductsActivity.this, AddProductActivity.class);
                                    intent.putExtra("product", product);
                                    intent.putExtra("typeIndex", typeIndex);
                                    intent.putParcelableArrayListExtra("types", (ArrayList<? extends Parcelable>) productTypes);
                                    startActivityForResult(intent, ADD_OR_UPDATE_PRODUCT);
                                } else {
                                    Toast.makeText(ManageProductsActivity.this, "上架商品不能修改", Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
                    } else {
                        //没有数据
                        products.clear();
                        productsAdapter.notifyDataSetChanged();
                        Toast.makeText(ManageProductsActivity.this, "没有" + display_name_zh + "类型的商品", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    //请求失败
                    Toast.makeText(ManageProductsActivity.this, baseResult.getMsg(), Toast.LENGTH_SHORT).show();
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
            case R.id.iv_add:
                Intent intent = new Intent(this, AddProductActivity.class);
                intent.putParcelableArrayListExtra("types", (ArrayList<? extends Parcelable>) productTypes);
                intent.putExtra("typeIndex", typeIndex);
                startActivityForResult(intent, ADD_OR_UPDATE_PRODUCT);
                break;
            case R.id.iv_back:
                finish();
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == ADD_OR_UPDATE_PRODUCT && resultCode == RESULT_OK) {
            //添加商品成功，刷新页面
            if (products != null && products.size() > 0) products.clear();

            getProducts(productTypes.get(typeIndex).getDISPLAY_NAME_ZH());
        }
    }
}
