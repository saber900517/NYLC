package com.nylc.nylc.character.farmer;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.nylc.nylc.R;
import com.nylc.nylc.model.Goods;
import com.nylc.nylc.utils.Urls;
import com.nylc.nylc.utils.ViewHolder;

import java.util.List;

/**
 * 管理商品列表适配器
 * Created by 吴曰阳 on 2018/3/3.
 */

public class FarmerGoodsAdapter extends BaseAdapter {

    private List<Goods> mList;
    private Context mContext;

    public FarmerGoodsAdapter(List<Goods> mList, Context mContext) {
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
            v = LayoutInflater.from(mContext).inflate(R.layout.item_farmer_products, null);
        }
        TextView tv_name = ViewHolder.get(v, R.id.tv_name);
        TextView tv_price = ViewHolder.get(v, R.id.tv_price);
        ImageView iv_img = ViewHolder.get(v, R.id.iv_img);
        final Goods product = mList.get(i);
        tv_name.setText(product.getGOODS_NAME());
        tv_price.setText("￥" + product.getGOODS_PRICE() + "元");
        ImageLoader.getInstance().displayImage(Urls.IMG + product.getGOODS_PICTURE(), iv_img);
        return v;
    }


}
