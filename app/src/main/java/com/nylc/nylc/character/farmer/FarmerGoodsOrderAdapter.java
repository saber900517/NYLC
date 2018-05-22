package com.nylc.nylc.character.farmer;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.nylc.nylc.R;
import com.nylc.nylc.model.BaseResult;
import com.nylc.nylc.model.GoodsOrder;
import com.nylc.nylc.utils.CommonUtils;
import com.nylc.nylc.utils.SharedPreferencesUtil;
import com.nylc.nylc.utils.Urls;
import com.nylc.nylc.utils.ViewHolder;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.util.List;

/**
 * Created by kasim on 2018/3/28.
 */

public class FarmerGoodsOrderAdapter extends BaseAdapter {
    private Context mContext;
    private List<GoodsOrder> mList;

    public FarmerGoodsOrderAdapter(Context mContext, List<GoodsOrder> mList) {
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
        view = LayoutInflater.from(mContext).inflate(R.layout.item_farmer_goods_order, null);
        final int position = i;
        TextView tv_name = ViewHolder.get(view, R.id.tv_name);
        TextView tv_products = ViewHolder.get(view, R.id.tv_products);
        TextView tv_count = ViewHolder.get(view, R.id.tv_count);
        TextView tv_state = ViewHolder.get(view, R.id.tv_state);
        TextView btn = ViewHolder.get(view, R.id.btn);
        TextView tv_date = ViewHolder.get(view, R.id.tv_date);
        TextView tv_earnest = ViewHolder.get(view, R.id.tv_earnest);


        final GoodsOrder item = mList.get(i);

        String created_date = item.getCREATED_DATE();
        tv_date.setText(created_date);
        int subscription = item.getSUBSCRIPTION();
        tv_earnest.setText("定金" + subscription + "元");
        String farmer_name = item.getFARMER_NAME();
        String village = item.getVILLAGE();
        tv_name.setText(TextUtils.isEmpty(farmer_name) ? village : farmer_name);
        tv_products.setText(item.getPRODUCT_TYPE());
        tv_count.setText(item.getQUANTITY() + "亩");
        //（0：待确认 10：已预定 20：已发布 30：待企业交易 40：交易完成 50：失效）
        String empType = (String) SharedPreferencesUtil.getParam(mContext, "empType", "农民");
        boolean isSupplier = empType.equals("供应商");

        if (isSupplier) {
            btn.setVisibility(item.getSTATUS() == 30 || item.getSTATUS() == 40 ? View.VISIBLE : View.GONE);
            btn.setText(item.getSTATUS() == 30 ? "发货" : item.getSTATUS() == 40 ? "完成交易" : "");
            btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    operateQuoteOrderAction(item, position);
                }
            });
        } else {
//            btn.setVisibility(item.getSTATUS() == 0 ? View.VISIBLE : View.GONE);
//            btn.setText("删除");
//            btn.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View view) {
//                    delGoodsOrder(item);
//                }
//            });
            btn.setVisibility(View.GONE);
        }


        tv_state.setText(getStateText(item.getSTATUS()));
        return view;
    }

    private void delGoodsOrder(final GoodsOrder goodsOrder) {
        RequestParams params = new RequestParams(Urls.delGoodsOrder);
        params.addBodyParameter("tokenKey", CommonUtils.getToken(mContext));
        params.addBodyParameter("id", goodsOrder.getID());
        x.http().post(params, new Callback.CommonCallback<BaseResult>() {
            @Override
            public void onSuccess(BaseResult result) {
                CommonUtils.judgeCode(mContext, result.getCode());
                Toast.makeText(mContext, result.getMsg(), Toast.LENGTH_SHORT).show();
                if ("success".equals(result.getLevel())) {
                    mList.remove(goodsOrder);
                    notifyDataSetChanged();
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

    //发货、完成交易
    private void operateQuoteOrderAction(final GoodsOrder item, final int position) {
        final int status = item.getSTATUS();
        RequestParams params = new RequestParams(Urls.operateQuoteOrderAction);
        params.addBodyParameter("tokenKey", CommonUtils.getToken(mContext));
        params.addBodyParameter("townId", item.getTOWN_ID());
        params.addBodyParameter("orderVillageId", item.getID());
        params.addBodyParameter("type", status == 30 ? "1" : "2");//1:发货 2:交易完成
        x.http().post(params, new Callback.CommonCallback<BaseResult>() {
            @Override
            public void onSuccess(BaseResult result) {
                CommonUtils.judgeCode(mContext, result.getCode());

                if ("success".equals(result.getLevel())) {
                    item.setSTATUS(status == 30 ? 40 : 50);
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
            return "被选中";
        } else if (status == 20) {
            return "已发布";
        } else if (status == 30) {
            return "待发货";
        } else if (status == 40) {
            return "已发货";
        } else if (status == 50) {
            return "交易完成";
        } else {
            return "";
        }
    }
}
