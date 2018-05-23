package com.nylc.nylc.character;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.TextView;

import com.nylc.nylc.BaseActivity;
import com.nylc.nylc.R;
import com.nylc.nylc.model.ApproveBuy;
import com.nylc.nylc.model.ApproveSale;
import com.nylc.nylc.model.GoodsOrder;
import com.nylc.nylc.model.ProductOrder;

import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import java.math.BigDecimal;

/**
 * 农产品订单详情界面
 * Created by kasim on 2018/5/22.
 */

public class ProductOrderDetailActivity extends BaseActivity {
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
        setContentView(R.layout.activity_products_order_detail);
        x.view().inject(this);
        init();
    }

    private void init() {
        Parcelable order = getIntent().getParcelableExtra("order");
        ProductOrder productOrder = null;
        ApproveSale approveSale = null;
        if (order instanceof ProductOrder) {
            productOrder = (ProductOrder) order;
        } else {
            approveSale = (ApproveSale) order;
        }
        tv_name.setText(productOrder == null ? approveSale.getFARMER_NAME() : productOrder.getFARMER_NAME());
        tv_productType.setText(productOrder == null ? approveSale.getPRODUCT_TYPE() : productOrder.getPRODUCT_TYPE());
        tv_count.setText(productOrder == null ? approveSale.getQUANTITY() + "亩" : productOrder.getQUANTITY() + "亩");
        tv_earnest.setText(productOrder == null ? approveSale.getSUBSCRIPTION() + "元" : productOrder.getSUBSCRIPTION() + "元");
        tv_amount.setText(productOrder == null ? approveSale.getAMOUNT() + "元" : productOrder.getAMOUNT() + "元");
        try {
            BigDecimal needPay = new BigDecimal(productOrder == null ? approveSale.getAMOUNT() : productOrder.getAMOUNT())
                    .subtract(new BigDecimal(productOrder == null ? approveSale.getSUBSCRIPTION() : productOrder.getSUBSCRIPTION()));
            tv_needPay.setText(needPay.toString());
        } catch (Exception e) {
            tv_needPay.setText("");
        }
        tv_state.setText(getStateText(productOrder == null ? approveSale.getSTATUS() : productOrder.getSTATUS()));
        tv_time.setText(productOrder == null ? approveSale.getCREATED_DATE() : productOrder.getCREATED_DATE());

        tv_price.setText(productOrder == null ? approveSale.getPRICE() + "元/斤" : productOrder.getPRICE() + "元/斤");
        tv_water.setText(productOrder == null ? approveSale.getWARTER() + "%" : productOrder.getWARTER() + "%");
        tv_quantityJin.setText(productOrder == null ? approveSale.getQUANTITY_JIN() + "斤" : productOrder.getQUANTITY_JIN() + "斤");
    }

    public static void newInstance(BaseActivity start, Parcelable goodsOrder) {
        Intent intent = new Intent(start, ProductOrderDetailActivity.class);
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
