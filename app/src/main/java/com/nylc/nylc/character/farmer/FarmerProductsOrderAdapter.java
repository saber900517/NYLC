package com.nylc.nylc.character.farmer;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.nylc.nylc.R;
import com.nylc.nylc.model.BaseResult;
import com.nylc.nylc.model.ProductOrder;
import com.nylc.nylc.utils.CommonUtils;
import com.nylc.nylc.utils.Urls;
import com.nylc.nylc.utils.ViewHolder;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.util.List;

/**
 * Created by kasim on 2018/3/28.
 */

public class FarmerProductsOrderAdapter extends BaseAdapter {
    private Context mContext;
    private List<ProductOrder> mList;

    public FarmerProductsOrderAdapter(Context mContext, List<ProductOrder> mList) {
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
        view = LayoutInflater.from(mContext).inflate(R.layout.item_farmer_pruduct_order, null);
        TextView tv_name = ViewHolder.get(view, R.id.tv_name);
        TextView tv_products = ViewHolder.get(view, R.id.tv_products);
        TextView tv_count = ViewHolder.get(view, R.id.tv_count);
        TextView tv_state = ViewHolder.get(view, R.id.tv_state);
        TextView btn = ViewHolder.get(view, R.id.btn);
        final int position = i;
        final ProductOrder item = mList.get(i);
        tv_name.setText(item.getFARMER_NAME());
        tv_products.setText(item.getPRODUCT_TYPE());
        tv_count.setText(item.getQUANTITY() + "亩");
        //（0：待确认10：被选中20：已发布 30：待发货 40：已发货 50：交易完成）
        tv_state.setText(getStateText(item.getSTATUS()));
        btn.setVisibility(item.getSTATUS() == 30 ? View.VISIBLE : View.INVISIBLE);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cancelProductOrder(item, position);
            }
        });
        return view;
    }

    private void cancelProductOrder(final ProductOrder item, final int position) {
        RequestParams params = new RequestParams(Urls.invalidProductOrder);
        params.addBodyParameter("tokenKey", CommonUtils.getToken(mContext));
        params.addBodyParameter("orderId", item.getID());
        x.http().post(params, new Callback.CommonCallback<BaseResult>() {
            @Override
            public void onSuccess(BaseResult result) {
                CommonUtils.judgeCode(mContext, result.getCode());
                if ("success".equals(result.getLevel())) {
                    Toast.makeText(mContext, result.getMsg(), Toast.LENGTH_SHORT).show();
                    item.setSTATUS(50);
                    mList.set(position, item);
                    notifyDataSetChanged();
                } else {
                    Toast.makeText(mContext, result.getMsg(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {

            }

            @Override
            public void onCancelled(CancelledException cex) {

            }

            @Override
            public void onFinished() {

            }
        });
    }

    private String getStateText(int status) {
        if (status == 0) {
            return "待确认";
        } else if (status == 10) {
            return "已预订";
        } else if (status == 20) {
            return "已发布";
        } else if (status == 30) {
            return "待企业交易";
        } else if (status == 40) {
            return "交易完成";
        } else if (status == 50) {
            return "失效";
        } else {
            return "";
        }
    }
}
