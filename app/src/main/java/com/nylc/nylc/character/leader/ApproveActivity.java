package com.nylc.nylc.character.leader;

import android.os.Bundle;
import android.support.annotation.Nullable;
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
import com.nylc.nylc.character.ProductTypeAdapter;
import com.nylc.nylc.character.supplier.ManageProductsActivity;
import com.nylc.nylc.model.BaseResult;
import com.nylc.nylc.model.ProductType;
import com.nylc.nylc.utils.CommonUtils;
import com.nylc.nylc.utils.Urls;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.util.ArrayList;
import java.util.List;

/**
 * 审批首页
 * Created by 吴曰阳 on 2018/3/22.
 */

public class ApproveActivity extends BaseActivity implements View.OnClickListener {

    private ImageView iv_back;
    private TextView tv_products, tv_foods;
    private Spinner sp_year, sp_month, sp_day, sp_type;
    private ArrayList<String> years, months, days, types;
    private ListView list;
    private List<ProductType> productTypes;

    private ProductTypeAdapter productTypeAdapter;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_approve);
        init();
    }

    private void init() {
        iv_back = findViewById(R.id.iv_back);
        iv_back.setOnClickListener(this);

        tv_products = findViewById(R.id.tv_products);
        tv_products.setOnClickListener(this);

        tv_foods = findViewById(R.id.tv_foods);
        tv_foods.setOnClickListener(this);

        list = findViewById(R.id.list);

        sp_year = findViewById(R.id.sp_year);
        sp_month = findViewById(R.id.sp_month);
        sp_day = findViewById(R.id.sp_day);
        sp_type = findViewById(R.id.sp_type);

        years = CommonUtils.getYears();
        months = CommonUtils.getMonths();
        days = CommonUtils.getDays(years.get(1), months.get(1));
        sp_year.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, years));
        sp_month.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, months));
        sp_day.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, days));
        getProductsType();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_back:
                finish();
                break;
        }
    }

    private void getProductsType() {
        RequestParams params = new RequestParams(Urls.queryGoodsTypeAction);
        params.addBodyParameter("tokenKey", CommonUtils.getToken(this));
        x.http().post(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                BaseResult baseResult = JSON.parseObject(result, BaseResult.class);
                String code = baseResult.getCode();
                CommonUtils.judgeCode(ApproveActivity.this, code);
                String level = baseResult.getLevel();
                if ("success".equals(level)) {
                    //请求成功
                    productTypes = JSON.parseArray(baseResult.getData(), ProductType.class);
                    productTypeAdapter = new ProductTypeAdapter(productTypes, ApproveActivity.this);
                    sp_type.setAdapter(productTypeAdapter);
                    sp_type.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                            ProductType productType = productTypes.get(i);

                        }
                    });
                    productTypeAdapter.notifyDataSetChanged();
                } else {
                    String msg = baseResult.getMsg();
                    Toast.makeText(ApproveActivity.this, msg, Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                Log.e("error", ex.getMessage());
                Toast.makeText(ApproveActivity.this, "连接服务器失败", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onCancelled(CancelledException cex) {

            }

            @Override
            public void onFinished() {

            }
        });

    }}