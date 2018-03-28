package com.nylc.nylc.character.farmer;

import android.content.Intent;
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
import com.nylc.nylc.character.TypeAdapter;
import com.nylc.nylc.model.BaseResult;
import com.nylc.nylc.model.Product;
import com.nylc.nylc.model.ProductType;
import com.nylc.nylc.model.SaleProduct;
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

    private List<SaleProduct> products;
    private List<ProductType> productTypes;

    private TypeAdapter productTypeAdapter;
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

//         getProductsType();
        defaultData();
    }

    private void defaultData() {
        productTypes = new ArrayList<>();
        ProductType type = new ProductType();
        type.setDISPLAY_NAME_ZH("粮食");
        productTypes.add(type);

        ProductType type3 = new ProductType();
        type3.setDISPLAY_NAME_ZH("我的");
        productTypes.add(type3);

        list_type.setAdapter(new TypeAdapter(productTypes, this));

        products = new ArrayList<>();
        SaleProduct product = new SaleProduct();
        product.setName("小麦");
        products.add(product);

        SaleProduct product1 = new SaleProduct();
        product1.setName("水稻");
        products.add(product1);

        SaleProduct product2 = new SaleProduct();
        product2.setName("玉米");
        products.add(product2);

        list_products.setAdapter(new SaleProductAdapter(this, products));
        list_type.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                startActivity(new Intent(SaleActivity.this, MySaleActivity.class));
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
        params.addBodyParameter("tokenKey", CommonUtils.getToken(this));
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
