package com.nylc.nylc.character.company;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.nylc.nylc.R;
import com.nylc.nylc.model.CompanyOrder;
import com.nylc.nylc.utils.ViewHolder;

import java.util.List;

/**
 * Created by kasim on 2018/3/27.
 */

public class CompanyOrderAdapter extends BaseAdapter {
    private Context mContext;
    private List<CompanyOrder> mList;

    public CompanyOrderAdapter(Context mContext, List<CompanyOrder> mList) {
        this.mContext = mContext;
        this.mList = mList;
    }

    @Override
    public int getCount() {
        return mList == null ? 0 : mList.size();
    }

    @Override
    public Object getItem(int i) {
        return mList == null ? null : mList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        if (view == null) {
            view = LayoutInflater.from(mContext).inflate(R.layout.item_supplier_order, null);
        }
        TextView tv_productType = ViewHolder.get(view, R.id.tv_name);
        TextView tv_count = ViewHolder.get(view, R.id.tv_count);
        TextView tv_price = ViewHolder.get(view, R.id.tv_price);
        TextView tv_state = ViewHolder.get(view, R.id.tv_state);
        TextView tv_village = ViewHolder.get(view, R.id.tv_village);
        TextView tv_sellType = ViewHolder.get(view, R.id.tv_sellType);
        CompanyOrder companyOrder = mList.get(i);
        tv_productType.setText(companyOrder.getPRODUCT_TYPE());
        tv_count.setText(companyOrder.getQUANTITY() + "亩");
        tv_price.setText(companyOrder.getQUANTITY_JIN() + "元");
        tv_state.setText(getStateText(companyOrder.getSTATUS()));
        tv_village.setText(companyOrder.getVILLAGE());
        tv_sellType.setText(companyOrder.getSELL_TYPE());
        return view;
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
