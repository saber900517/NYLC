package com.nylc.nylc.character;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.nylc.nylc.BaseActivity;
import com.nylc.nylc.R;
import com.nylc.nylc.model.ApproveBuy;
import com.nylc.nylc.model.GoodsOrder;

import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import java.math.BigDecimal;

/**
 * 商品订单详情界面
 * Created by kasim on 2018/5/22.
 */

public class GoodsOrderDetailActivity extends BaseActivity {
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

    @ViewInject(R.id.tv_time)
    TextView tv_time;

    @ViewInject(R.id.ll)
    LinearLayout ll;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_goods_order_detail);
        x.view().inject(this);
        init();
    }

    private void init() {
        Parcelable order = getIntent().getParcelableExtra("order");
        GoodsOrder goodsOrder = null;
        ApproveBuy approveBuy = null;
        if (order instanceof GoodsOrder) {
            goodsOrder = (GoodsOrder) order;
//            ll.setVisibility(View.VISIBLE);
        } else {
            approveBuy = (ApproveBuy) order;
//            ll.setVisibility(View.GONE);
        }
        String name = goodsOrder == null ? approveBuy.getFARMER_NAME() : goodsOrder.getFARMER_NAME();
        if (TextUtils.isEmpty(name)) {
            name = goodsOrder == null ? approveBuy.getVILLAGE() : goodsOrder.getVILLAGE();
        }
        tv_name.setText(name);
        tv_productType.setText(goodsOrder == null ? approveBuy.getPRODUCT_TYPE() : goodsOrder.getPRODUCT_TYPE());
        tv_count.setText(goodsOrder == null ? approveBuy.getQUANTITY() + "亩" : goodsOrder.getQUANTITY() + "亩");
        tv_earnest.setText(goodsOrder == null ? approveBuy.getSUBSCRIPTION() + "元" : goodsOrder.getSUBSCRIPTION() + "元");
        tv_amount.setText(goodsOrder == null ? approveBuy.getAMOUNT() + "元" : goodsOrder.getAMOUNT() + "元");
        try {
            BigDecimal needPay = new BigDecimal(goodsOrder == null ? approveBuy.getAMOUNT() : goodsOrder.getAMOUNT())
                    .subtract(new BigDecimal(goodsOrder == null ? approveBuy.getSUBSCRIPTION() : goodsOrder.getSUBSCRIPTION()));
            tv_needPay.setText(needPay.toString());
        } catch (Exception e) {
            tv_needPay.setText("");
        }
        tv_state.setText(getStateText(goodsOrder == null ? approveBuy.getSTATUS() : goodsOrder.getSTATUS()));
        tv_time.setText(goodsOrder == null ? approveBuy.getCREATED_DATE() : goodsOrder.getCREATED_DATE());
    }

    public static void newInstance(BaseActivity start, Parcelable goodsOrder) {
        Intent intent = new Intent(start, GoodsOrderDetailActivity.class);
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
