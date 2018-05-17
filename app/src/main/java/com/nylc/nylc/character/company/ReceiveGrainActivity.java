package com.nylc.nylc.character.company;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.nylc.nylc.BaseActivity;
import com.nylc.nylc.R;
import com.nylc.nylc.model.BaseResult;
import com.nylc.nylc.model.NodeMsg;
import com.nylc.nylc.model.ProductType;
import com.nylc.nylc.model.ReceiveGrain;
import com.nylc.nylc.utils.CommonUtils;
import com.nylc.nylc.utils.Urls;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadmoreListener;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.util.ArrayList;
import java.util.List;

/**
 * 收粮食
 * Created by kasim on 2018/3/28.
 */

public class ReceiveGrainActivity extends BaseActivity implements View.OnClickListener {
    private ImageView iv_back;
    private Spinner sp_county, sp_town;
    private ListView list_type, list_grain;
    private TextView tv_curveGraph;

    private String[] counties = {"全部", "滨海县"};
    private String[] towns = {"全部", "滨海港镇"};
    private String[] types = {"水稻", "玉米", "小麦"};
    private List<ProductType> productTypes;

    private List<ReceiveGrain> receiveGrainList;
    private ReceiverGrainAdapter receiverGrainAdapter;

    private SmartRefreshLayout mSmartRefreshLayout;
    private int pageIndex = 1;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_receive_grain);
        init();
    }

    private void init() {
        iv_back = findViewById(R.id.iv_back);
        sp_county = findViewById(R.id.sp_county);
        sp_town = findViewById(R.id.sp_town);
        list_type = findViewById(R.id.list_type);
        list_grain = findViewById(R.id.list_grain);
        tv_curveGraph = findViewById(R.id.tv_curveGraph);

        tv_curveGraph.setOnClickListener(this);
        iv_back.setOnClickListener(this);
        mSmartRefreshLayout = findViewById(R.id.smartRefreshLayout);
        mSmartRefreshLayout.setOnLoadmoreListener(new OnLoadmoreListener() {
            @Override
            public void onLoadmore(RefreshLayout refreshlayout) {
                refreshlayout.finishLoadmore(500);
                pageIndex++;
                getQuoteOrderList();
            }
        });
        mSmartRefreshLayout.setEnableRefresh(false);
        getQuoteOrderList();
        getVillageMessage();
    }
    @Override
    @Subscribe(threadMode = ThreadMode.MAIN)
    public <RefreshEvent> void onEvent(RefreshEvent event) {
        pageIndex = 1;
        if (receiveGrainList != null) receiveGrainList.clear();
        getQuoteOrderList();
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
                CommonUtils.judgeCode(ReceiveGrainActivity.this, result.getCode());
                String level = result.getLevel();
                if (level.equals("success")) {
                    List<NodeMsg> list = JSON.parseArray(result.getData(), NodeMsg.class);
                    for (NodeMsg msg : list) {
                        int node_level = msg.getNODE_LEVEL();
                        if (node_level == 3) {
                            //县
                            List<NodeMsg> counties = new ArrayList<>();
                            counties.add(msg);
                            sp_county.setAdapter(new ArrayAdapter<NodeMsg>(ReceiveGrainActivity.this, R.layout.item_one_text, counties));
                        }
                        if (node_level == 4) {
                            List<NodeMsg> towns = new ArrayList<>();
                            towns.add(msg);
                            sp_town.setAdapter(new ArrayAdapter<NodeMsg>(ReceiveGrainActivity.this, R.layout.item_one_text, towns));
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

    /**
     * 获取竞价列表
     */
    private void getQuoteOrderList() {
        RequestParams params = new RequestParams(Urls.queryQuoteOrderList);
        params.addBodyParameter("tokenKey", CommonUtils.getToken(this));
        params.addBodyParameter("index", String.valueOf(pageIndex));
        x.http().post(params, new Callback.CommonCallback<BaseResult>() {
            @Override
            public void onSuccess(BaseResult result) {
                CommonUtils.judgeCode(ReceiveGrainActivity.this, result.getCode());
                String level = result.getLevel();
                if ("success".equals(level)) {
                    if (receiveGrainList == null) {
                        receiveGrainList = new ArrayList<>();
                    }
                    if (receiverGrainAdapter == null) {
                        receiverGrainAdapter = new ReceiverGrainAdapter(ReceiveGrainActivity.this, receiveGrainList);
                        list_grain.setAdapter(receiverGrainAdapter);
                    }
                    List<ReceiveGrain> receiveGrains = JSON.parseArray(result.getData(), ReceiveGrain.class);
                    if (receiveGrains != null && receiveGrains.size() > 0) {
                        receiveGrainList.addAll(receiveGrains);
                        receiverGrainAdapter.notifyDataSetChanged();
                    }
                } else {
                    Toast.makeText(ReceiveGrainActivity.this, "没有查询到相关数据", Toast.LENGTH_SHORT).show();
                    mSmartRefreshLayout.setLoadmoreFinished(true);
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


    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_back:
                finish();
                break;
            case R.id.tv_curveGraph:
                startActivity(new Intent(this, CurveGraphActivity.class));
                break;
        }
    }

    /**
     * 获取农产品类型列表
     */
    private void getProductsType() {
        RequestParams params = new RequestParams(Urls.queryProductTypeAction);
        params.addBodyParameter("tokenKey", CommonUtils.getToken(this));
        x.http().post(params, new Callback.CommonCallback<BaseResult>() {
            @Override
            public void onSuccess(BaseResult result) {
                String code = result.getCode();
                CommonUtils.judgeCode(ReceiveGrainActivity.this, code);
                String level = result.getLevel();
                if ("success".equals(level)) {
                    //请求成功
                    productTypes = JSON.parseArray(result.getData(), ProductType.class);
                    list_type.setAdapter(new ArrayAdapter<ProductType>(ReceiveGrainActivity.this, R.layout.item_one_text, productTypes));
                } else {
                    String msg = result.getMsg();
                    Toast.makeText(ReceiveGrainActivity.this, msg, Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                Log.e("error", ex.getMessage());
                Toast.makeText(ReceiveGrainActivity.this, "连接服务器失败", Toast.LENGTH_SHORT).show();
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
