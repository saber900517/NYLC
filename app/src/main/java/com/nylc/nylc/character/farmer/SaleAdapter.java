package com.nylc.nylc.character.farmer;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

import com.nylc.nylc.R;
import com.nylc.nylc.model.MySale;
import com.nylc.nylc.utils.ViewHolder;

import java.util.List;

/**
 * Created by kasim on 2018/3/27.
 */

public class SaleAdapter extends BaseAdapter {
    private Context mContext;
    private List<MySale> mList;

    public SaleAdapter(Context mContext, List<MySale> mList) {
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
        view = LayoutInflater.from(mContext).inflate(R.layout.item_my_sale, null);
//        }
//        TextView tv_state = ViewHolder.get(view, R.id.tv_state);
//        Button bt_cancel = ViewHolder.get(view,R.id.bt_cancel);
        TextView tv_state = view.findViewById(R.id.tv_state);
        Button bt_cancel = view.findViewById(R.id.bt_cancel);
        int state = mList.get(i).getState();
        if (state == 0) {
            bt_cancel.setVisibility(View.VISIBLE);
            tv_state.setVisibility(View.GONE);
        } else {
            bt_cancel.setVisibility(View.GONE);
            tv_state.setVisibility(View.VISIBLE);
            tv_state.setText(state == 1 ? "已完成" : "已预订\n定金" + mList.get(i).getEarnest() + "\n等收尾款");
        }
        return view;
    }
}
