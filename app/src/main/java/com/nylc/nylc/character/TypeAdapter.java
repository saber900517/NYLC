package com.nylc.nylc.character;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.nylc.nylc.R;
import com.nylc.nylc.model.GoodsType;
import com.nylc.nylc.utils.ViewHolder;

import java.util.List;

/**
 * 商品列表适配器
 * Created by 吴曰阳 on 2018/3/3.
 */

public class TypeAdapter extends BaseAdapter {

    private List<GoodsType> mList;
    private Context mContext;

    public TypeAdapter(List<GoodsType> mList, Context mContext) {
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
            v = LayoutInflater.from(mContext).inflate(R.layout.item_one_text, null);
        }
        textView = ViewHolder.get(v, R.id.textView);
        textView.setText(mList.get(i).getDISPLAY_NAME_ZH());
        return v;
    }
}
