package com.nylc.nylc.character;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.TextView;

import com.nylc.nylc.BaseActivity;
import com.nylc.nylc.R;
import com.nylc.nylc.model.ApproveSale;
import com.nylc.nylc.model.CompanyOrder;
import com.nylc.nylc.model.ProductOrder;

import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import java.math.BigDecimal;

/**
 * 农产品订单详情界面
 * Created by kasim on 2018/5/22.
 */

public class CompanyProductOrderDetailActivity extends BaseActivity {
    @ViewInject(R.id.tv_name)
    TextView tv_name;

    @ViewInject(R.id.tv_productType)
    TextView tv_productType;

    @ViewInject(R.id.tv_count)
    TextView tv_count;

    @ViewInject(R.id.tv_earnest)
    TextView tv_earnest;

    @ViewInject(R.id.tv_amount)
    TextView tv_amount;

    @ViewInject(R.id.tv_needPay)
    TextView tv_needPay;

    @ViewInject(R.id.tv_state)
    TextView tv_state;

    @ViewInject(R.id.tv_quantityJin)
    TextView tv_quantityJin;

    @ViewInject(R.id.tv_time)
    TextView tv_time;

    @ViewInject(R.id.tv_water)
    TextView tv_water;

    @ViewInject(R.id.tv_price)
    TextView tv_price;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_company_products_order_detail);
        x.view().inject(this);
        init();
    }

    private void init() {
        CompanyOrder order = getIntent().getParcelableExtra("order");
        tv_name.setText(order.getVILLAGE());
        tv_productType.setText(order.getPRODUCT_TYPE());
        tv_count.setText(order.getQUANTITY() + "亩");
        tv_earnest.setText(order.getSUBSCRIPTION() + "元");
        tv_amount.setText(order.getAMOUNT() + "元");
        try {
            BigDecimal needPay = new BigDecimal(order.getAMOUNT())
                    .subtract(new BigDecimal(order.getSUBSCRIPTION()));
            tv_needPay.setText(needPay.toString());
        } catch (Exception e) {
            tv_needPay.setText("");
        }
        tv_state.setText(getStateText(order.getSTATUS()));
        tv_time.setText(order.getCREATED_DATE());

        tv_price.setText(order.getPRICE() + "元/斤");
        tv_water.setText(order.getWATER() + "%");
        tv_quantityJin.setText(order.getQUANTITY_JIN() + "斤");
    }

    public static void newInstance(BaseActivity start, CompanyOrder goodsOrder) {
        Intent intent = new Intent(start, CompanyProductOrderDetailActivity.class);
        intent.putExtra("order", goodsOrder);
        start.startActivity(intent);
    }

    @Event(R.id.iv_back)
    private void onClick(View v) {
        finish();
    }

    private String getStateText(int status) {
        if (status == 0) {
            return "待确认";
        } else if (status == 10) {
            return "被选中";
        } else if (status == 20) {
            return "已发布";
        } else if (status == 30) {
            return "待发货";
        } else if (status == 40) {
            return "已发货";
        } else if (status == 50) {
            return "交易完成";
        } else {
            return "";
        }
    }
}
