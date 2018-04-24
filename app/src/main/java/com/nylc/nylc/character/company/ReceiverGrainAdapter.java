package com.nylc.nylc.character.company;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;

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
        Button bt = ViewHolder.get(view,R.id.btn);
        bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ReceiveGrainDialog dialog = new ReceiveGrainDialog();
                dialog.show(((BaseActivity)mContext).getSupportFragmentManager(),"ReceiveGrain");
            }
        });
        return view;
    }
}
