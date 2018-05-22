package com.nylc.nylc.character.farmer;

import android.app.AlertDialog;
import android.content.Context;
import android.graphics.drawable.BitmapDrawable;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
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
        TextView tv_date = ViewHolder.get(view, R.id.tv_date);
        final int position = i;
        final ProductOrder item = mList.get(i);
        String created_date = item.getCREATED_DATE();
        tv_date.setText(created_date);
        tv_name.setText(item.getFARMER_NAME());
        tv_products.setText(item.getPRODUCT_TYPE());
        tv_count.setText(item.getQUANTITY() + "亩");
        //（0：待确认10：被选中20：已发布 30：待发货 40：已发货 50：交易完成）
        tv_state.setText(getStateText(item.getSTATUS()));
        btn.setVisibility(item.getSTATUS() == 30 ? View.VISIBLE : View.INVISIBLE);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showCancelDialog(item, position);
            }
        });
        return view;
    }

    AlertDialog cancelDialog;

    private void showCancelDialog(final ProductOrder item, final int position) {
        AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
        View v = LayoutInflater.from(mContext).inflate(R.layout.dialog_title_content_towbtn, null);
        Button btn_confirm = v.findViewById(R.id.btn_confirm);
        Button btn_cancel = v.findViewById(R.id.btn_cancel);
        TextView tv_title = v.findViewById(R.id.tv_title);
        TextView tv_content = v.findViewById(R.id.tv_content);
        tv_title.setText("取消订单");
        tv_content.setText("您确定要取消当前订单吗？");
        cancelDialog = builder.create();
        cancelDialog.getWindow().setBackgroundDrawable(new BitmapDrawable());
        cancelDialog.setCanceledOnTouchOutside(false);
        cancelDialog.show();
        cancelDialog.getWindow().setContentView(v);
        cancelDialog.getWindow().setGravity(Gravity.CENTER);
        cancelDialog.getWindow().setLayout(700, CommonUtils.dip2px(140, mContext));
        btn_confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cancelProductOrder(item, position);
                if (cancelDialog != null && cancelDialog.isShowing()) cancelDialog.dismiss();
            }
        });
        btn_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (cancelDialog != null && cancelDialog.isShowing()) cancelDialog.dismiss();
            }
        });
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
                Toast.makeText(mContext, "连接服务器失败", Toast.LENGTH_SHORT).show();
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
