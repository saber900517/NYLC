package com.nylc.nylc.character.company;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

import com.nylc.nylc.BaseActivity;
import com.nylc.nylc.R;
import com.nylc.nylc.model.ReceiveGrain;
import com.nylc.nylc.utils.ViewHolder;

import java.util.List;

/**
 * Created by kasim on 2018/3/29.
 */

public class ReceiverGrainAdapter extends BaseAdapter {
    private Context mContext;
    private List<ReceiveGrain> mList;

    public ReceiverGrainAdapter(Context mContext, List<ReceiveGrain> mList) {
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
            view = LayoutInflater.from(mContext).inflate(R.layout.item_receive_grain, null);
        }
        TextView tv_village = ViewHolder.get(view, R.id.tv_village);
        TextView tv_productType = ViewHolder.get(view, R.id.tv_name);
        TextView tv_count = ViewHolder.get(view, R.id.tv_count);
        TextView tv_price = ViewHolder.get(view, R.id.tv_price);
        final ReceiveGrain receiveGrain = mList.get(i);
        tv_village.setText(receiveGrain.getVILLAGE());
        tv_productType.setText(receiveGrain.getPRODUCT_TYPE());
        tv_count.setText(receiveGrain.getQUANTITY() + "亩");
        tv_price.setText(receiveGrain.getMostQuote() + "元/斤");
        TextView bt = ViewHolder.get(view, R.id.btn);
        bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ReceiveGrainDialog dialog = ReceiveGrainDialog.getInstance(receiveGrain.getMostQuote(), receiveGrain.getID());
                dialog.show(((BaseActivity) mContext).getSupportFragmentManager(), "ReceiveGrain");
            }
        });
        return view;
    }
}
