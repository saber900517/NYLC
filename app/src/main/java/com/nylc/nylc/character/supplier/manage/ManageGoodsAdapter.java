package com.nylc.nylc.character.supplier.manage;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nylc.nylc.R;
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
 * 管理商品列表适配器
 * Created by 吴曰阳 on 2018/3/3.
 */

public class ManageGoodsAdapter extends BaseAdapter {

    private List<Goods> mList;
    private Context mContext;

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
        ImageView iv_img = ViewHolder.get(v,R.id.iv_img);
        final Goods product = mList.get(i);
        tv_name.setText(product.getGOODS_NAME());
        tv_price.setText("￥" + product.getGOODS_PRICE() + "元");
        ImageLoader.getInstance().displayImage(Urls.IMG+product.getGOODS_PICTURE(),iv_img);
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
                deleteGoods(i);
            }
        });
        return v;
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
