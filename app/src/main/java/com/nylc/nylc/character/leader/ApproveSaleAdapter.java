package com.nylc.nylc.character.leader;

import android.content.Context;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

import com.nylc.nylc.R;
import com.nylc.nylc.model.ApproveBuy;
import com.nylc.nylc.model.ApproveSale;

import java.util.List;

/**
 * Created by kasim on 2018/3/28.
 */

public class ApproveSaleAdapter extends BaseAdapter {
    private Context mContext;
    private List<ApproveSale> mList;
    private FragmentManager mManager;

    public ApproveSaleAdapter(Context mContext, List<ApproveSale> mList, FragmentManager mManager) {
        this.mContext = mContext;
        this.mList = mList;
        this.mManager = mManager;
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
        view = LayoutInflater.from(mContext).inflate(R.layout.item_approve_sale, null);
        TextView tv_right1 = view.findViewById(R.id.tv_right1);
        TextView tv_right2 = view.findViewById(R.id.tv_right2);
        Button btn = view.findViewById(R.id.btn);
        int i1 = i % 4;
        tv_right1.setVisibility(i1 == 0 ? View.INVISIBLE : View.VISIBLE);
        tv_right1.setText(i1 == 0 ? "" : i1 == 1 ? "定金300" : i1 == 2 ? "待收购商交易" : "总价11200");

        tv_right2.setVisibility(i1 == 2 ? View.GONE : View.VISIBLE);
        tv_right2.setText(i1 == 0 ? "已发布" : i1 == 1 ? "已预订" : i1 == 2 ? "" : "已完成");

        btn.setVisibility(i1 == 1 || i1 == 2 ? View.VISIBLE : View.GONE);
        btn.setText(i1 == 1 ? "提货" : "确认");
        if (i1 == 1) {
            btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    TakeDeliveryOfGoodsFragmentDialog takeDialog = new TakeDeliveryOfGoodsFragmentDialog();
                    takeDialog.show(mManager, "takeDeliverOfGoods");
                }
            });
        } else {
            btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    ConfirmFragmentDialog confirmFragmentDialog = new ConfirmFragmentDialog();
                    confirmFragmentDialog.show(mManager, "confirmDialog");
                }
            });
        }
        return view;
    }
}
