package com.nylc.nylc.character.supplier.manage;

import android.app.AlertDialog;
import android.content.Context;
import android.graphics.drawable.BitmapDrawable;
import android.util.Log;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nylc.nylc.R;
import com.nylc.nylc.model.BaseResult;
import com.nylc.nylc.model.Goods;
import com.nylc.nylc.model.MeansOfProduction;
import com.nylc.nylc.utils.CommonUtils;
import com.nylc.nylc.utils.Urls;
import com.nylc.nylc.utils.ViewHolder;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.util.List;

/**
 * 管理商品列表适配器
 * Created by 吴曰阳 on 2018/3/3.
 */

public class ManageGoodsAdapter extends BaseAdapter {

    private List<Goods> mList;
    private Context mContext;
    private AlertDialog deleteDialog;

    public ManageGoodsAdapter(List<Goods> mList, Context mContext) {
        this.mList = mList;
        this.mContext = mContext;
    }

    @Override
    public int getCount() {
        return mList != null ? mList.size() : 0;
    }

    @Override
    public Object getItem(int i) {
        return mList != null && mList.size() > i ? mList.get(i) : null;
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    public List<Goods> getList() {
        return mList;
    }

    @Override
    public View getView(final int i, View v, ViewGroup viewGroup) {

        if (v == null) {
            v = LayoutInflater.from(mContext).inflate(R.layout.item_manage_goods, null);
        }
        TextView tv_name = ViewHolder.get(v, R.id.tv_name);
        TextView tv_price = ViewHolder.get(v, R.id.tv_price);
        TextView tv_saleState = ViewHolder.get(v, R.id.tv_saleState);
        TextView tv_delete = ViewHolder.get(v, R.id.tv_delete);
        ImageView iv_img = ViewHolder.get(v, R.id.iv_img);
        final Goods product = mList.get(i);
        tv_name.setText(product.getGOODS_NAME());
        tv_price.setText("￥" + product.getGOODS_PRICE() + "元");
        ImageLoader.getInstance().displayImage(Urls.IMG + product.getGOODS_PICTURE(), iv_img);
        int status = product.getSTATUS();
        if (status == 1) {//1有效；0无效
            tv_delete.setVisibility(View.INVISIBLE);
            tv_saleState.setText("下架");
        } else {
            tv_delete.setVisibility(View.VISIBLE);
            tv_saleState.setText("上架");
        }
        tv_saleState.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                updateGoodsState(i);
            }
        });
        tv_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                deleteGoods(i);
                showDeleteDialog(i);
            }
        });
        return v;
    }

    private void showDeleteDialog(final int position) {
        AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
        View v = LayoutInflater.from(mContext).inflate(R.layout.dialog_title_content_towbtn, null);
        Button btn_confirm = v.findViewById(R.id.btn_confirm);
        Button btn_cancel = v.findViewById(R.id.btn_cancel);
        TextView tv_title = v.findViewById(R.id.tv_title);
        TextView tv_content = v.findViewById(R.id.tv_content);
        tv_title.setText("删除");
        tv_content.setText("您确定要删除当前商品吗？");
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
     * 更新商品上下架状态
     *
     * @param position
     */
    private void updateGoodsState(final int position) {
        final Goods product = mList.get(position);
        RequestParams params = new RequestParams(Urls.updateStatusAction);
        params.addBodyParameter("tokenKey", CommonUtils.getToken(mContext));
        params.addBodyParameter("goodsId", product.getGOODS_ID());
        params.addBodyParameter("status", String.valueOf(product.getSTATUS() == 0 ? 1 : 0));
        x.http().post(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                Log.i("", "");
                BaseResult baseResult = JSON.parseObject(result, BaseResult.class);
                CommonUtils.judgeCode(mContext, baseResult.getCode());
                String level = baseResult.getLevel();
                if ("success".equals(level)) {
                    //请求成功
                    int status = product.getSTATUS();
                    product.setSTATUS(status == 0 ? 1 : 0);
                    mList.set(position, product);
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

    /**
     * 删除商品
     *
     * @param position
     */
    private void deleteGoods(final int position) {
        Goods product = mList.get(position);
        RequestParams params = new RequestParams(Urls.delGoodsAction);
        params.addBodyParameter("tokenKey", CommonUtils.getToken(mContext));
        params.addBodyParameter("goodsId", product.getGOODS_ID());
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

}
