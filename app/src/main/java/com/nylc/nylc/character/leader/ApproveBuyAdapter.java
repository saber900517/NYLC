package com.nylc.nylc.character.leader;

import android.app.AlertDialog;
import android.content.Context;
import android.graphics.drawable.BitmapDrawable;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.nylc.nylc.R;
import com.nylc.nylc.model.ApproveBuy;
import com.nylc.nylc.model.BaseResult;
import com.nylc.nylc.model.Goods;
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
    public View getView(final int i, View view, ViewGroup viewGroup) {
        view = LayoutInflater.from(mContext).inflate(R.layout.item_approve_buy, null);
        TextView tv_name = ViewHolder.get(view, R.id.tv_name);
        TextView tv_products = ViewHolder.get(view, R.id.tv_products);
        TextView tv_count = ViewHolder.get(view, R.id.tv_count);
        TextView tv_state = ViewHolder.get(view, R.id.tv_state);
        TextView btn = ViewHolder.get(view, R.id.btn);
        TextView tv_earnest = ViewHolder.get(view, R.id.tv_earnest);
        TextView tv_date = ViewHolder.get(view, R.id.tv_date);

        final ApproveBuy approveBuy = mList.get(i);
        tv_name.setText(approveBuy.getFARMER_NAME());
        tv_products.setText(approveBuy.getPRODUCT_TYPE());
        tv_count.setText(approveBuy.getQUANTITY() + "亩");
        tv_earnest.setText("定金" + approveBuy.getSUBSCRIPTION() + "元");
        //（0：待确认10：被选中20：已发布 30：待发货 40：已发货 50：交易完成）
        tv_state.setText(getStateText(approveBuy.getSTATUS()));
        btn.setVisibility(approveBuy.getSTATUS() == 0 ? View.VISIBLE : View.GONE);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDeleteDialog(i);
            }
        });
        tv_date.setText(approveBuy.getCREATED_DATE());
        return view;
    }

    AlertDialog deleteDialog;

    private void showDeleteDialog(final int position) {
        AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
        View v = LayoutInflater.from(mContext).inflate(R.layout.dialog_title_content_towbtn, null);
        Button btn_confirm = v.findViewById(R.id.btn_confirm);
        Button btn_cancel = v.findViewById(R.id.btn_cancel);
        TextView tv_title = v.findViewById(R.id.tv_title);
        TextView tv_content = v.findViewById(R.id.tv_content);
        tv_title.setText("删除");
        tv_content.setText("您确定要删除当前订单吗？");
        deleteDialog = builder.create();
        deleteDialog.getWindow().setBackgroundDrawable(new BitmapDrawable());
        deleteDialog.setCanceledOnTouchOutside(false);
        deleteDialog.show();
        deleteDialog.getWindow().setContentView(v);
        deleteDialog.getWindow().setGravity(Gravity.CENTER);
        deleteDialog.getWindow().setLayout(700, CommonUtils.dip2px(140, mContext));
        btn_confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                deleteGoods(position);
                if (deleteDialog != null && deleteDialog.isShowing()) deleteDialog.dismiss();
            }
        });
        btn_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (deleteDialog != null && deleteDialog.isShowing()) deleteDialog.dismiss();
            }
        });

    }

    /**
     * 删除商品
     *
     * @param position
     */
    private void deleteGoods(final int position) {
        ApproveBuy approveBuy = mList.get(position);
        RequestParams params = new RequestParams(Urls.delGoodsOrder);
        params.addBodyParameter("tokenKey", CommonUtils.getToken(mContext));
        params.addBodyParameter("id", approveBuy.getID());
        x.http().post(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                Log.i("", "");
                BaseResult baseResult = JSON.parseObject(result, BaseResult.class);
                CommonUtils.judgeCode(mContext, baseResult.getCode());
                String level = baseResult.getLevel();
                if ("success".equals(level)) {
                    //请求成功
                    mList.remove(position);
                    notifyDataSetChanged();
                } else {
                    //请求失败
                    Toast.makeText(mContext, baseResult.getMsg(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                Log.e("error", ex.getMessage());
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
