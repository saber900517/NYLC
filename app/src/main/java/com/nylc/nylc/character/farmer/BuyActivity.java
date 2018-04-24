package com.nylc.nylc.character.farmer;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
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
import com.nylc.nylc.model.Goods;
import com.nylc.nylc.model.GoodsType;
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
 * 我要买
 * Created by 吴曰阳 on 2018/3/4.
 */

public class BuyActivity extends BaseActivity implements View.OnClickListener {
    private ImageView iv_back;//返回
    private TextView tv_reserve;//添加
    private ListView list_type;//商品类型列表
    private ListView list_goods;//商品列表

    private int typeIndex = 0;//当前展示的类型的index

    private List<Goods> goods;
    private List<GoodsType> goodsTypes;

    private TypeAdapter typeAdapter;
    private FarmerGoodsAdapter farmerGoodsAdapter;
    private int pageIndex = 1;
    private SmartRefreshLayout mSmartRefreshLayout;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_buy);
        init();
    }

    private void init() {
        iv_back = findViewById(R.id.iv_back);
        tv_reserve = findViewById(R.id.tv_reserve);
        list_goods = findViewById(R.id.list_goods);
        list_type = findViewById(R.id.list_type);
        View footerView = LayoutInflater.from(this).inflate(android.R.layout.simple_list_item_1, null);
        TextView tv_my = footerView.findViewById(android.R.id.text1);
        tv_my.setGravity(Gravity.CENTER);
        tv_my.setTextSize(14);
        tv_my.setText("我的");
        footerView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(BuyActivity.this, MyReserveActivity.class));
            }
        });
        list_type.addFooterView(footerView);
        tv_reserve.setOnClickListener(this);
        iv_back.setOnClickListener(this);
        mSmartRefreshLayout = findViewById(R.id.srl);
        mSmartRefreshLayout.setOnLoadmoreListener(new OnLoadmoreListener() {
            @Override
            public void onLoadmore(RefreshLayout refreshlayout) {
                refreshlayout.finishLoadmore(500);
                pageIndex++;
                getGoods(goodsTypes.get(typeIndex).getDISPLAY_NAME_ZH());
            }
        });
        mSmartRefreshLayout.setEnableRefresh(false);
        getGoodsType();
    }


    private void getGoodsType() {
        RequestParams params = new RequestParams(Urls.queryGoodsTypeAction);
        params.addBodyParameter("tokenKey", CommonUtils.getToken(this));
        x.http().post(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                BaseResult baseResult = JSON.parseObject(result, BaseResult.class);
                String code = baseResult.getCode();
                CommonUtils.judgeCode(BuyActivity.this, code);
                String level = baseResult.getLevel();
                if ("success".equals(level)) {
                    //请求成功
                    goodsTypes = JSON.parseArray(baseResult.getData(), GoodsType.class);
                    typeAdapter = new TypeAdapter(goodsTypes, BuyActivity.this);
                    list_type.setAdapter(typeAdapter);
                    list_type.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                            GoodsType goodsType = goodsTypes.get(i);
                            String display_name_zh = goodsType.getDISPLAY_NAME_ZH();
                            getGoods(display_name_zh);
                            typeIndex = i;
                        }
                    });
                    typeAdapter.notifyDataSetChanged();
                    if (goodsTypes != null && goodsTypes.size() > 0) {
                        GoodsType goodsType = goodsTypes.get(typeIndex);
                        String display_name_zh = goodsType.getDISPLAY_NAME_ZH();
                        getGoods(display_name_zh);
                    }
                } else {
                    String msg = baseResult.getMsg();
                    Toast.makeText(BuyActivity.this, msg, Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                Log.e("error", ex.getMessage());
                Toast.makeText(BuyActivity.this, "连接服务器失败", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onCancelled(CancelledException cex) {

            }

            @Override
            public void onFinished() {

            }
        });

    }

    private void getGoods(final String display_name_zh) {
        RequestParams params = new RequestParams(Urls.queryGoodsListAction);
        params.addBodyParameter("tokenKey", CommonUtils.getToken(this));
        params.addBodyParameter("goodsType", display_name_zh);
        params.addBodyParameter("index", pageIndex + "");
        x.http().post(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                Log.i("", "");
                BaseResult baseResult = JSON.parseObject(result, BaseResult.class);
                CommonUtils.judgeCode(BuyActivity.this, baseResult.getCode());
                String level = baseResult.getLevel();
                if ("success".equals(level)) {
                    //请求成功
                    String data = baseResult.getData();
                    List<Goods> list = JSON.parseArray(data, Goods.class);
                    if (list != null && list.size() > 0) {
                        //有数据
                        if (goods == null) {
                            goods = new ArrayList<>();
                        } else {
                            goods.clear();
                        }
                        goods.addAll(list);
                        if (farmerGoodsAdapter == null) {
                            farmerGoodsAdapter = new FarmerGoodsAdapter(goods, BuyActivity.this);
                            list_goods.setAdapter(farmerGoodsAdapter);
                        } else {
                            if (farmerGoodsAdapter != null)
                                farmerGoodsAdapter.notifyDataSetChanged();
                        }

                    } else {
                        //没有数据
                        if (goods != null) {
                            goods.clear();
                        }
                        if (farmerGoodsAdapter != null) farmerGoodsAdapter.notifyDataSetChanged();
                        Toast.makeText(BuyActivity.this, "没有" + display_name_zh + "类型的商品", Toast.LENGTH_SHORT).show();
                        mSmartRefreshLayout.setLoadmoreFinished(true);
                    }
                } else {
                    //请求失败
                    Toast.makeText(BuyActivity.this, baseResult.getMsg(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                Log.e("error", ex.getMessage());
                Toast.makeText(BuyActivity.this, "连接服务器失败", Toast.LENGTH_SHORT).show();
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
                Toast.makeText(this, "预定", Toast.LENGTH_SHORT).show();
                sendPushMessageToLeader();
                break;
            case R.id.iv_back:
                finish();
                break;
        }
    }

    private void sendPushMessageToLeader() {
        RequestParams params = new RequestParams(Urls.addPublishAction);
        params.addBodyParameter("tokenKey", CommonUtils.getToken(this));
        params.addBodyParameter("type", "1");
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
