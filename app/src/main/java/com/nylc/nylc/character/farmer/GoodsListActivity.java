package com.nylc.nylc.character.farmer;

import android.os.Bundle;
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
 * Created by kasim on 2018/4/23.
 */

public class GoodsListActivity extends BaseActivity implements View.OnClickListener {
    private ListView list_type, list_goods;
    private ImageView iv_back;
    private SmartRefreshLayout mSmartRefreshLayout;

    private int pageIndex = 1;

    private List<GoodsType> goodsTypes;
    private TypeAdapter typeAdapter;

    private List<Goods> goods;
    private FarmerGoodsAdapter farmerGoodsAdapter;

    private int typeIndex;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_goods_list);
        init();
    }

    private void init() {
        iv_back = findViewById(R.id.iv_back);
        list_type = findViewById(R.id.list_type);
        list_goods = findViewById(R.id.list_goods);
        iv_back.setOnClickListener(this);

        mSmartRefreshLayout = findViewById(R.id.smartRefreshLayout);
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
                CommonUtils.judgeCode(GoodsListActivity.this, code);
                String level = baseResult.getLevel();
                if ("success".equals(level)) {
                    //请求成功
                    goodsTypes = JSON.parseArray(baseResult.getData(), GoodsType.class);
                    typeAdapter = new TypeAdapter(goodsTypes, GoodsListActivity.this);
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
                    Toast.makeText(GoodsListActivity.this, msg, Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                Log.e("error", ex.getMessage());
                Toast.makeText(GoodsListActivity.this, "连接服务器失败", Toast.LENGTH_SHORT).show();
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
                CommonUtils.judgeCode(GoodsListActivity.this, baseResult.getCode());
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
                            farmerGoodsAdapter = new FarmerGoodsAdapter(goods, GoodsListActivity.this);
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
                        Toast.makeText(GoodsListActivity.this, "没有" + display_name_zh + "类型的商品", Toast.LENGTH_SHORT).show();
                        mSmartRefreshLayout.setLoadmoreFinished(true);
                    }
                } else {
                    //请求失败
                    Toast.makeText(GoodsListActivity.this, baseResult.getMsg(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                Log.e("error", ex.getMessage());
                Toast.makeText(GoodsListActivity.this, "连接服务器失败", Toast.LENGTH_SHORT).show();
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
}
