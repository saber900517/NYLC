package com.nylc.nylc.character.company;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Spinner;

import com.nylc.nylc.BaseActivity;
import com.nylc.nylc.R;
import com.nylc.nylc.model.BaseResult;
import com.nylc.nylc.model.CompanyOrder;
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
 * Created by kasim on 2018/3/28.
 */

public class CompanyOrderActivity extends BaseActivity implements View.OnClickListener {
    private ListView list;
    private Spinner sp_year, sp_month, sp_type;
    private ImageView iv_back;
    private String[] types = new String[]{"全部", "待确认", "被选中", "已发布", "待发货", "已发货"};

    private SmartRefreshLayout mSmartRefreshLayout;

    private int pageIndex = 1;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_company_order);
        init();
    }

    private void init() {
        list = findViewById(R.id.listView);
        sp_type = findViewById(R.id.sp_type);
        sp_year = findViewById(R.id.sp_year);
        sp_month = findViewById(R.id.sp_month);
        iv_back = findViewById(R.id.iv_back);
        iv_back.setOnClickListener(this);

        sp_year.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, CommonUtils.getYears()));
        sp_month.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, new String[]{"全部"}));
        sp_type.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, types));


        mSmartRefreshLayout = findViewById(R.id.smartRefreshLayout);
        mSmartRefreshLayout.setOnLoadmoreListener(new OnLoadmoreListener() {
            @Override
            public void onLoadmore(RefreshLayout refreshlayout) {
                pageIndex++;
                getCompanyOrders();
            }
        });

        getCompanyOrders();

    }

    Callback.Cancelable post;

    private void getCompanyOrders() {
        if (post != null && !post.isCancelled()) post.cancel();
        RequestParams params = new RequestParams(Urls.queryCompanyOrderList);
        params.addBodyParameter("tokenKey", CommonUtils.getToken(this));
        params.addBodyParameter("index", String.valueOf(pageIndex));
        if (sp_year.getSelectedItemPosition() != 0)
            params.addBodyParameter("year", sp_year.getSelectedItem().toString());

        if (sp_month.getSelectedItemPosition() != 0)
            params.addBodyParameter("month", sp_month.getSelectedItem().toString());

        if (sp_type.getSelectedItemPosition() != 0)
            params.addBodyParameter("type", String.valueOf(sp_type.getSelectedItemPosition()));
        post = x.http().post(params, new Callback.CommonCallback<BaseResult>() {
            @Override
            public void onSuccess(BaseResult result) {
                CommonUtils.judgeCode(CompanyOrderActivity.this, result.getCode());
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

    private void goodsDefaultData() {
        List<String> years = new ArrayList<>();
        years.add("全部");
        years.add("2017");
        years.add("2018");
        sp_year.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, years));
        sp_type.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, types));

        List<CompanyOrder> orders = new ArrayList<>();
        for (int i = 0; i < 4; i++) {
            CompanyOrder order = new CompanyOrder();
            orders.add(order);
        }
        list.setAdapter(new CompanyOrderAdapter(this, orders));
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
