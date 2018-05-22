package com.nylc.nylc.character.farmer;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Spinner;

import com.nylc.nylc.BaseActivity;
import com.nylc.nylc.R;
import com.nylc.nylc.model.BaseResult;
import com.nylc.nylc.utils.CommonUtils;
import com.nylc.nylc.utils.Urls;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadmoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

/**
 * 我的预定
 * Created by 吴曰阳 on 2018/3/4.
 */

public class MyReserveActivity extends BaseActivity implements View.OnClickListener {


    private ImageView iv_back;
    private Spinner sp_year, sp_month, sp_type;
    private SmartRefreshLayout mSmartRefreshLayout;

    private ListView list;

    private String[] types = new String[]{"全部", "待确认", "已预定", "已发布", "待企业交易", "交易完成", "失效"};


    private int pageIndex = 1;

    private Button loadMore;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_order);
        init();
    }

    private void init() {
        iv_back = findViewById(R.id.iv_back);
        iv_back.setOnClickListener(this);

        sp_year = findViewById(R.id.sp_year);
        sp_month = findViewById(R.id.sp_month);
        sp_type = findViewById(R.id.sp_type);

        setSpinnerData();


        list = findViewById(R.id.list);
        mSmartRefreshLayout = findViewById(R.id.smartRefreshLayout);
        mSmartRefreshLayout.setOnLoadmoreListener(new OnLoadmoreListener() {
            @Override
            public void onLoadmore(RefreshLayout refreshlayout) {
                refreshlayout.finishLoadmore(500);
                pageIndex++;
                getOrderList();
            }
        });
        mSmartRefreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(RefreshLayout refreshlayout) {
                refreshlayout.finishRefresh(500);
                pageIndex=1;
            }
        });

    }


    /**
     * 获取订单列表
     */
    private void getOrderList() {

        RequestParams params = new RequestParams(Urls.queryProductOrderList);
        params.addBodyParameter("tokenKey", CommonUtils.getToken(this));
        params.addBodyParameter("index", String.valueOf(pageIndex));
        if (sp_year.getSelectedItemPosition() != 0) {
            params.addBodyParameter("year", sp_year.getSelectedItem().toString());
        }
        if (sp_month.getSelectedItemPosition() != 0) {
            params.addBodyParameter("month", sp_month.getSelectedItem().toString());
        }
        params.addBodyParameter("type", sp_type.getSelectedItemPosition() == 0 ? null : sp_type.getSelectedItem().toString());

        x.http().post(params, new Callback.CommonCallback<BaseResult>() {
            @Override
            public void onSuccess(BaseResult result) {
                //TODO
                Log.i("","");
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                Log.i("","");
            }

            @Override
            public void onCancelled(CancelledException cex) {
                Log.i("","");
            }

            @Override
            public void onFinished() {
                Log.i("","");
            }
        });
    }

    private void setSpinnerData() {
        sp_year.setAdapter(new ArrayAdapter<String>(this, R.layout.item_one_text, CommonUtils.getYears()));
        sp_month.setAdapter(new ArrayAdapter<String>(this, R.layout.item_one_text, new String[]{"全部"}));
        sp_type.setAdapter(new ArrayAdapter<String>(this, R.layout.item_one_text, types));

        sp_year.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if (i == 0) {
                    sp_month.setAdapter(new ArrayAdapter<String>(MyReserveActivity.this, R.layout.item_one_text, new String[]{"全部"}));
                } else {
                    sp_month.setAdapter(new ArrayAdapter<String>(MyReserveActivity.this, R.layout.item_one_text, CommonUtils.getMonths()));
                }
                sp_month.setSelection(0);
                getOrderList();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });


        sp_month.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                getOrderList();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        sp_type.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                getOrderList();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

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
