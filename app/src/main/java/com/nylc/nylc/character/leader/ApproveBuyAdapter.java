package com.nylc.nylc.character.leader;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.nylc.nylc.R;
import com.nylc.nylc.model.ApproveBuy;

import java.util.List;

/**
 * Created by kasim on 2018/3/28.
 */

public class ApproveBuyAdapter extends BaseAdapter {
    private Context mContext;
    private List<ApproveBuy> mList;

    public ApproveBuyAdapter(Context mContext, List<ApproveBuy> mList) {
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
        view = LayoutInflater.from(mContext).inflate(R.layout.item_approve_buy, null);
        return view;
    }
}
