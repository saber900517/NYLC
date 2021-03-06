package com.nylc.nylc.character.supplier.manage;

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
import com.nylc.nylc.character.TypeAdapter;
import com.nylc.nylc.model.BaseResult;
import com.nylc.nylc.model.Goods;
import com.nylc.nylc.model.GoodsType;
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
 * 商品管理
 * Created by 吴曰阳 on 2018/3/2.
 */

public class ManageGoodsActivity extends BaseActivity implements View.OnClickListener {
    private ImageView iv_back;//返回
    private ImageView iv_add;//添加
    private ListView list_type;//商品类型列表
    private ListView list_goods;//商品列表

    private int ADD_OR_UPDATE_GOODS = 123;

    private int typeIndex = 0;//当前展示的类型的index

    private List<Goods> goods;
    private List<GoodsType> goodsTypes;

    private TypeAdapter typeAdapter;
    private ManageGoodsAdapter goodsAdapter;

    private SmartRefreshLayout mSmartRefreshLayout;
    private int pageIndex = 1;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage_goods);
        init();
    }


    private void init() {
        iv_back = findViewById(R.id.iv_back);
        iv_add = findViewById(R.id.iv_add);
        list_goods = findViewById(R.id.list_goods);
        list_type = findViewById(R.id.list_type);

        iv_add.setOnClickListener(this);
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
        mSmartRefreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(RefreshLayout refreshlayout) {
                refreshlayout.finishRefresh(500);
                pageIndex = 1;
                if (goods != null && goods.size() > 0) {
                    goods.clear();
                }
                getGoods(goodsTypes.get(typeIndex).getDISPLAY_NAME_ZH());
                mSmartRefreshLayout.setLoadmoreFinished(false);
            }
        });

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
                CommonUtils.judgeCode(ManageGoodsActivity.this, code);
                String level = baseResult.getLevel();
                if ("success".equals(level)) {
                    //请求成功
                    goodsTypes = JSON.parseArray(baseResult.getData(), GoodsType.class);
                    typeAdapter = new TypeAdapter(goodsTypes, ManageGoodsActivity.this);
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
                    Toast.makeText(ManageGoodsActivity.this, msg, Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                Log.e("error", ex.getMessage());
                Toast.makeText(ManageGoodsActivity.this, "连接服务器失败", Toast.LENGTH_SHORT).show();
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
                CommonUtils.judgeCode(ManageGoodsActivity.this, baseResult.getCode());
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
                        if (goodsAdapter == null) {
                            goodsAdapter = new ManageGoodsAdapter(goods, ManageGoodsActivity.this);
                            list_goods.setAdapter(goodsAdapter);
                            list_goods.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                @Override
                                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                                    ManageGoodsAdapter adapter = (ManageGoodsAdapter) adapterView.getAdapter();
                                    List<Goods> list = adapter.getList();
                                    Goods goods = list.get(i);
                                    int status = goods.getSTATUS();
                                    if (status == 0) {
                                        //商品无效，已经下架了
                                        Intent intent = new Intent(ManageGoodsActivity.this, AddGoodsActivity.class);
                                        intent.putExtra("goods", goods);
                                        intent.putExtra("typeIndex", typeIndex);
                                        intent.putParcelableArrayListExtra("types", (ArrayList<? extends Parcelable>) goodsTypes);
                                        startActivityForResult(intent, ADD_OR_UPDATE_GOODS);
                                    } else {
                                        Toast.makeText(ManageGoodsActivity.this, "上架商品不能修改", Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });
                        } else {
                            if (goodsAdapter != null) goodsAdapter.notifyDataSetChanged();
                        }

                    } else {
                        //没有数据
                        if (goods != null) {
                            goods.clear();
                        }
                        if (goodsAdapter != null) goodsAdapter.notifyDataSetChanged();
                        Toast.makeText(ManageGoodsActivity.this, "没有" + display_name_zh + "类型的商品", Toast.LENGTH_SHORT).show();
                        mSmartRefreshLayout.setLoadmoreFinished(true);
                    }
                } else {
                    //请求失败
                    Toast.makeText(ManageGoodsActivity.this, baseResult.getMsg(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                Log.e("error", ex.getMessage());
                Toast.makeText(ManageGoodsActivity.this, "连接服务器失败", Toast.LENGTH_SHORT).show();
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
                Intent intent = new Intent(this, AddGoodsActivity.class);
                intent.putParcelableArrayListExtra("types", (ArrayList<? extends Parcelable>) goodsTypes);
                intent.putExtra("typeIndex", typeIndex);
                startActivityForResult(intent, ADD_OR_UPDATE_GOODS);
                break;
            case R.id.iv_back:
                finish();
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == ADD_OR_UPDATE_GOODS && resultCode == RESULT_OK) {
            //添加商品成功，刷新页面
            if (goods != null && goods.size() > 0) goods.clear();

            getGoods(goodsTypes.get(typeIndex).getDISPLAY_NAME_ZH());
        }
    }
}
