package com.nylc.nylc.character.farmer;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

import com.nylc.nylc.R;
import com.nylc.nylc.model.BorrowHistory;
import com.nylc.nylc.utils.ViewHolder;

import java.util.List;

/**
 * Created by kasim on 2018/3/26.
 */

public class BorrowHistoryAdapter extends BaseAdapter {
    private Context mContext;
    private List<BorrowHistory> mList;

    public BorrowHistoryAdapter(Context mContext, List<BorrowHistory> mList) {
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
            view = LayoutInflater.from(mContext).inflate(R.layout.item_borrow_history, null);
        }
        TextView tv_borrowMoney = ViewHolder.get(view, R.id.tv_money);
        TextView tv_dateTitle = ViewHolder.get(view, R.id.tv_dateTitle);
        TextView tv_date = ViewHolder.get(view, R.id.tv_date);
        Button bt_cancel = ViewHolder.get(view, R.id.btn_cancel);
        BorrowHistory history = mList.get(i);
        String money = (i+1) + "想借" + history.getMoney() + "元";
        tv_borrowMoney.setText(money);
        int state = history.getState();
        if (state == 0) {//还没借到
            tv_dateTitle.setVisibility(View.GONE);
            tv_date.setVisibility(View.GONE);
            bt_cancel.setVisibility(View.VISIBLE);
        } else if (state == 1) {//借到了，还没还
            tv_dateTitle.setVisibility(View.VISIBLE);
            tv_date.setVisibility(View.VISIBLE);
            bt_cancel.setVisibility(View.GONE);
            tv_date.setText(history.getDate());
        } else if (state == 2) {//还完了
            tv_dateTitle.setVisibility(View.GONE);
            tv_date.setVisibility(View.VISIBLE);
            bt_cancel.setVisibility(View.GONE);
            tv_date.setText("已还清");
        }
        return view;
    }
}
