package com.nylc.nylc.character.supplier;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.nylc.nylc.BaseActivity;
import com.nylc.nylc.R;
import com.nylc.nylc.model.BaseResult;
import com.nylc.nylc.model.Product;
import com.nylc.nylc.model.ProductType;
import com.nylc.nylc.utils.CommonUtils;
import com.nylc.nylc.utils.Urls;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.util.ArrayList;

/**
 * 添加商品
 * Created by 吴曰阳 on 2018/3/3.
 */

public class AddProductActivity extends BaseActivity implements View.OnClickListener {

    private ImageView iv_back;
    private ImageView iv_img;
    private EditText et_productName;//商品名称
    private EditText et_price;//单价
    private Spinner sp_type;//规格
    private EditText et_description;//介绍
    private Button bt_confirm;//确定按钮
    private ArrayList<ProductType> types;
    private Product product;

    private boolean isAdd = true;//是否是新增商品，true新增，false修改
    //小麦种子
    private String wheatSeed = "https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1520875222192&di=f80b4ed26db00bcfd957e1818fe535d4&imgtype=0&src=http%3A%2F%2Fwww.cnjidan.com%2Fupload%2Fpictures%2F2015%2F10%2F0-HMM6UY.jpg";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_product);
        init();
    }

    private void init() {
        iv_back = findViewById(R.id.iv_back);
        iv_img = findViewById(R.id.iv_img);
        et_productName = findViewById(R.id.et_productName);
        et_price = findViewById(R.id.et_price);
        sp_type = findViewById(R.id.sp_type);
        et_description = findViewById(R.id.et_description);
        bt_confirm = findViewById(R.id.bt_confirm);

        iv_back.setOnClickListener(this);
        bt_confirm.setOnClickListener(this);
        Intent intent = getIntent();
        types = intent.getParcelableArrayListExtra("types");
        ArrayAdapter<ProductType> adapter = new ArrayAdapter<ProductType>(this, android.R.layout.simple_list_item_1, types);
        sp_type.setAdapter(adapter);

        int typeIndex = intent.getIntExtra("typeIndex", 0);
        sp_type.setSelection(typeIndex);
        if (intent.hasExtra("product")) {
            isAdd = false;
            product = intent.getParcelableExtra("product");
            et_productName.setText(product.getGOODS_NAME());
            et_price.setText(product.getGOODS_PRICE());
            et_description.setText(product.getGOODS_DESCRIPTION());
        }


    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_back:
                setResult(RESULT_CANCELED);
                finish();
                break;
            case R.id.bt_confirm:
                addGoods();
                break;
        }
    }

    /**
     * 添加商品
     */
    private void addGoods() {
        String name = "";
        String price = "";
        String type = "";
        String description = "";
        if (et_productName.getText() == null) {
            Toast.makeText(this, "请输入商品名称", Toast.LENGTH_SHORT).show();
            return;
        }
        if (et_price.getText() == null) {
            Toast.makeText(this, "请输入商品单价", Toast.LENGTH_SHORT).show();
            return;
        }
        if (et_description.getText() == null) {
            Toast.makeText(this, "请输入商品介绍", Toast.LENGTH_SHORT).show();
            return;
        }
        name = et_productName.getText().toString();
        price = et_price.getText().toString();
        type = sp_type.getSelectedItem().toString();
        description = et_description.getText().toString();
        RequestParams params = new RequestParams(isAdd ? Urls.addGoodsAction : Urls.updateGoodsAction);
        params.addBodyParameter("tokenKey", CommonUtils.getToken(this));
        params.addBodyParameter("goodsName", name);
        params.addBodyParameter("goodsPrice", price);
        params.addBodyParameter("goodsType", type);
        params.addBodyParameter("goodsDescription", description);
        params.addBodyParameter("goodsName", wheatSeed);
        if (!isAdd) {
            params.addBodyParameter("goodsId", product.getGOODS_ID());
        }
        x.http().post(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                BaseResult baseResult = JSON.parseObject(result, BaseResult.class);
                CommonUtils.judgeCode(AddProductActivity.this, baseResult.getCode());
                String level = baseResult.getLevel();
                if ("success".equals(level)) {
                    Toast.makeText(AddProductActivity.this, (isAdd ? "新增" : "修改") + "商品成功", Toast.LENGTH_SHORT).show();
                    setResult(RESULT_OK);
                    finish();
                } else {
                    Toast.makeText(AddProductActivity.this, (isAdd ? "新增" : "修改") + "商品失败", Toast.LENGTH_SHORT).show();
                }
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
