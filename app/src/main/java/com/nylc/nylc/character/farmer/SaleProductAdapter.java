package com.nylc.nylc.character.farmer;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.nylc.nylc.R;
import com.nylc.nylc.model.SaleProduct;

import java.util.List;

/**
 * Created by kasim on 2018/3/26.
 */

public class SaleProductAdapter extends BaseAdapter {
    private Context mContext;
    private List<SaleProduct> mList;

    public SaleProductAdapter(Context mContext, List<SaleProduct> mList) {
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
            view = LayoutInflater.from(mContext).inflate(R.layout.item_sale_product, null);
        }
        TextView tv_name = view.findViewById(R.id.tv_name);
        tv_name.setText(mList.get(i).getName());
        return view;
    }
}
