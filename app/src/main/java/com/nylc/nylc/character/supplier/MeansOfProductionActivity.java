package com.nylc.nylc.character.supplier;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.nylc.nylc.BaseActivity;
import com.nylc.nylc.R;
import com.nylc.nylc.eventbus.RefreshEvent;
import com.nylc.nylc.model.BaseResult;
import com.nylc.nylc.model.MeansOfProduction;
import com.nylc.nylc.model.NodeMsg;
import com.nylc.nylc.model.ProductType;
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
 * 生产资料供货
 * Created by 吴曰阳 on 2018/3/3.
 */

public class MeansOfProductionActivity extends BaseActivity implements View.OnClickListener {
    private ImageView iv_back;
    private TextView tv_history;
    private Spinner sp_county, sp_town;
    private ListView list_type, list_products;
    //    private List<ProductType> types;
    private NodeMsg townNodeMsg;
    private int pageIndex = 1;
    private SmartRefreshLayout mSmartRefreshLayout;
    private List<MeansOfProduction> meansOfProductions;
    private MeansOfProductionAdapter meansOfProductionAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_means_of_production);
        init();
    }

    private void init() {
        iv_back = findViewById(R.id.iv_back);
        tv_history = findViewById(R.id.tv_history);
        sp_county = findViewById(R.id.sp_county);
        sp_town = findViewById(R.id.sp_town);
        list_type = findViewById(R.id.list_type);
        list_products = findViewById(R.id.list_goods);

        iv_back.setOnClickListener(this);
        tv_history.setOnClickListener(this);
        tv_history.setVisibility(View.GONE);
        sp_county.setVisibility(View.GONE);
        sp_town.setVisibility(View.GONE);

        mSmartRefreshLayout = findViewById(R.id.smartRefreshLayout);
        mSmartRefreshLayout.setOnLoadmoreListener(new OnLoadmoreListener() {
            @Override
            public void onLoadmore(RefreshLayout refreshlayout) {
                refreshlayout.finishLoadmore(500);
                pageIndex++;
                getQuoteOrderAction();
            }
        });

        mSmartRefreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(RefreshLayout refreshlayout) {
                refreshlayout.finishRefresh(500);
                pageIndex = 1;
                if (meansOfProductions != null && meansOfProductions.size() > 0) {
                    meansOfProductions.clear();
                }
                getQuoteOrderAction();
            }
        });
        getVillageMessage();

    }

    @Override
    @Subscribe(threadMode = ThreadMode.MAIN)
    public <RefreshEvent> void onEvent(RefreshEvent event) {
        pageIndex = 1;
        if (meansOfProductions != null) meansOfProductions.clear();
        getQuoteOrderAction();
    }

    /**
     * 获取县镇信息
     */
    private void getVillageMessage() {
        RequestParams params = new RequestParams(Urls.queryNodeList);
        params.addBodyParameter("tokenKey", CommonUtils.getToken(this));
        x.http().post(params, new Callback.CommonCallback<BaseResult>() {
            @Override
            public void onSuccess(BaseResult result) {
                CommonUtils.judgeCode(MeansOfProductionActivity.this, result.getCode());
                String level = result.getLevel();
                if (level.equals("success")) {
                    List<NodeMsg> list = JSON.parseArray(result.getData(), NodeMsg.class);
                    for (NodeMsg msg : list) {
                        int node_level = msg.getNODE_LEVEL();
                        if (node_level == 3) {
                            //县
                            townNodeMsg = msg;
                            getQuoteOrderAction();
                        }

                    }
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

    private void getQuoteOrderAction() {
        RequestParams params = new RequestParams(Urls.queryQuoteOrderAction);
        params.addBodyParameter("tokenKey", CommonUtils.getToken(this));
        params.addBodyParameter("townId", townNodeMsg.getNODE_ID());
        params.addBodyParameter("index", String.valueOf(pageIndex));
        x.http().post(params, new Callback.CommonCallback<BaseResult>() {
            @Override
            public void onSuccess(BaseResult result) {
                CommonUtils.judgeCode(MeansOfProductionActivity.this, result.getCode());
                String level = result.getLevel();
                if ("success".equals(level)) {
                    if (meansOfProductions == null) {
                        meansOfProductions = new ArrayList<>();
                    }
                    if (meansOfProductionAdapter == null) {
                        meansOfProductionAdapter = new MeansOfProductionAdapter(MeansOfProductionActivity.this, meansOfProductions);
                        list_products.setAdapter(meansOfProductionAdapter);
                    }
                    List<MeansOfProduction> list = JSON.parseArray(result.getData(), MeansOfProduction.class);
                    if (list != null && list.size() > 0) {
                        meansOfProductions.addAll(list);
                        meansOfProductionAdapter.notifyDataSetChanged();
                    } else {
                        Toast.makeText(MeansOfProductionActivity.this, "没有查询到相关数据", Toast.LENGTH_SHORT).show();
                    }
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
            case R.id.tv_history:
                startActivity(new Intent(this, HistoryOrderActivity.class));
                break;
        }
    }
}
