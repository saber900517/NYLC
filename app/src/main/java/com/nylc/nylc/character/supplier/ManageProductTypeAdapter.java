package com.nylc.nylc.character.supplier;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.nylc.nylc.R;
import com.nylc.nylc.model.Product;
import com.nylc.nylc.model.ProductType;
import com.nylc.nylc.utils.ViewHolder;

import java.util.ArrayList;
import java.util.List;

/**
 * 管理商品列表适配器
 * Created by 吴曰阳 on 2018/3/3.
 */

public class ManageProductTypeAdapter extends BaseAdapter {

    private List<ProductType> mList;
    private Context mContext;

    public ManageProductTypeAdapter(List<ProductType> mList, Context mContext) {
        this.mList = mList;
        this.mContext = mContext;
    }

    @Override
    public int getCount() {
        return mList != null ? mList.size() : 0;
    }

    @Override
    public Object getItem(int i) {
        return mList != null && mList.size() > i ? mList.get(i) : null;
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View v, ViewGroup viewGroup) {
        TextView textView;
        if (v == null) {
            v = LayoutInflater.from(mContext).inflate(android.R.layout.simple_list_item_1, null);
        }
        textView = ViewHolder.get(v, android.R.id.text1);
        textView.setText(mList.get(i).getDISPLAY_NAME_ZH());
        return v;
    }
}
