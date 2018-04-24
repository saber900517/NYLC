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

public class ApproveSaleAdapter extends BaseAdapter implements View.OnClickListener {
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
        Button btn_edit = view.findViewById(R.id.btn_edit);
        Button btn_delete = view.findViewById(R.id.btn_delete);
        ApproveSale approveSale = mList.get(i);
        int subscription = approveSale.getSUBSCRIPTION();//定金
        tv_right1.setVisibility(subscription > 0 ? View.VISIBLE : View.INVISIBLE);
        tv_right1.setText("定金" + subscription);
        int i1 = i % 4;
        int status = approveSale.getSTATUS();

        if (status == 0) {
            btn_edit.setVisibility(View.VISIBLE);
            btn_delete.setVisibility(View.VISIBLE);
            btn_edit.setOnClickListener(this);
            btn_delete.setOnClickListener(this);
        } else {
            btn_edit.setVisibility(View.GONE);
            btn_delete.setVisibility(View.GONE);
            if (status == 30 || status == 40) {
                tv_right2.setVisibility(View.GONE);
                btn.setVisibility(View.VISIBLE);
                btn.setText(status == 30 ? "发货" : "完成交易");
                btn.setOnClickListener(status == 30
                        ?
                        //发货
                        new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                TakeDeliveryOfGoodsFragmentDialog takeDialog = new TakeDeliveryOfGoodsFragmentDialog();
                                takeDialog.show(mManager, "takeDeliverOfGoods");
                            }
                        }
                        :
                        //完成交易
                        new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                ConfirmFragmentDialog confirmFragmentDialog = new ConfirmFragmentDialog();
                                confirmFragmentDialog.show(mManager, "confirmDialog");
                            }
                        });
            } else {
                tv_right2.setVisibility(View.VISIBLE);
                btn.setVisibility(View.GONE);
                tv_right2.setText(getStatusText(status));
            }
        }

        return view;
    }

    private String getStatusText(int status) {
        switch (status) {
            case 0:
                return "待确认";
            case 10:
                return "已预定";
            case 20:
                return "已发布";
            case 30:
                return "待企业交易";
            case 40:
                return "交易完成";
            case 50:
                return "失效";
        }
        return "";
    }

    @Override
    public void onClick(View view) {

    }
}
