package com.nylc.nylc.character.farmer;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.nylc.nylc.R;
import com.nylc.nylc.model.MyOrder;
import com.nylc.nylc.utils.ViewHolder;

import java.util.List;

/**
 * Created by kasim on 2018/3/27.
 */

public class ReserveAdapter extends BaseAdapter {
    private Context mContext;
    private List<MyOrder> mList;

    public ReserveAdapter(Context mContext, List<MyOrder> mList) {
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
//        if (view == null) {
            view = LayoutInflater.from(mContext).inflate(R.layout.item_my_reserve, null);
//        }
//        TextView tv_state = ViewHolder.get(view, R.id.tv_state);
        TextView tv_state = view.findViewById(R.id.tv_state);
        tv_state.setText(mList.get(i).getState() == 0 ? "待发货" : "已完成");
        return view;
    }
}
