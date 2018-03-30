package com.nylc.nylc.character.supplier;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.nylc.nylc.R;
import com.nylc.nylc.model.MyOrder;
import com.nylc.nylc.model.SupplierOrder;
import com.nylc.nylc.utils.ViewHolder;

import java.util.List;

/**
 * Created by kasim on 2018/3/27.
 */

public class SupplierOrderAdapter extends BaseAdapter {
    private Context mContext;
    private List<SupplierOrder> mList;

    public SupplierOrderAdapter(Context mContext, List<SupplierOrder> mList) {
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
        return view;
    }
}
