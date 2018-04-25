package com.nylc.nylc.character.supplier;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.nylc.nylc.BaseActivity;
import com.nylc.nylc.R;
import com.nylc.nylc.eventbus.RefreshEvent;
import com.nylc.nylc.model.BaseResult;
import com.nylc.nylc.model.MeansOfProduction;
import com.nylc.nylc.utils.CommonUtils;
import com.nylc.nylc.utils.Urls;
import com.nylc.nylc.utils.ViewHolder;

import org.greenrobot.eventbus.EventBus;
import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.util.List;

/**
 * Created by kasim on 2018/3/29.
 */

public class MeansOfProductionAdapter extends BaseAdapter {
    private Context mContext;
    private List<MeansOfProduction> mList;

    public MeansOfProductionAdapter(Context mContext, List<MeansOfProduction> mList) {
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
            view = LayoutInflater.from(mContext).inflate(R.layout.item_means_of_production, null);
        }
        final MeansOfProduction meansOfProduction = mList.get(i);
        int minQuote = meansOfProduction.getMinQuote();
        int myQuote = meansOfProduction.getMyQuote();
        boolean showDeleteButton = myQuote > 0 ? true : false;
        Button bt = ViewHolder.get(view, R.id.btn_quote);
        TextView tv_myPrice = ViewHolder.get(view, R.id.tv_my_price);
        TextView tv_minPrice = ViewHolder.get(view, R.id.tv_min_price);
        TextView tv_name = ViewHolder.get(view, R.id.tv_name);
        tv_name.setText(meansOfProduction.getPRODUCT_TYPE());
        tv_minPrice.setVisibility(minQuote > 0 ? View.VISIBLE : View.GONE);
        tv_myPrice.setVisibility(myQuote > 0 ? View.VISIBLE : View.GONE);
        tv_minPrice.setText("最低报价：" + minQuote + "元/亩");
        tv_myPrice.setText("我的报价：" + myQuote + "元/亩");
        bt.setText(showDeleteButton ? "修改" : "报价");
        bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MeansOfProductsDialog dialog = MeansOfProductsDialog.getInstance(meansOfProduction);
                dialog.show(((BaseActivity) mContext).getSupportFragmentManager(), "MeansOFProducts");
            }
        });
        Button btn_delete = ViewHolder.get(view, R.id.btn_delete);
        btn_delete.setVisibility(showDeleteButton ? View.VISIBLE : View.INVISIBLE);
        btn_delete.setOnClickListener(showDeleteButton ? new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                deleteQuote(meansOfProduction);
            }
        } : null);
        return view;
    }

    private void deleteQuote(MeansOfProduction meansOfProduction) {
        RequestParams params = new RequestParams(Urls.delQuoteOrderAction);
        params.addBodyParameter("tokenKey", CommonUtils.getToken(mContext));
        params.addBodyParameter("townId", meansOfProduction.getTOWN_ID());
        params.addBodyParameter("orderVillageId", meansOfProduction.getID());//供应商订单ID
        x.http().post(params, new Callback.CommonCallback<BaseResult>() {
            @Override
            public void onSuccess(BaseResult result) {
                CommonUtils.judgeCode(mContext, result.getCode());
                if ("success".equals(result.getLevel())) {
                    Toast.makeText(mContext, result.getMsg(), Toast.LENGTH_SHORT).show();
                    EventBus.getDefault().post(new RefreshEvent());
                } else {
                    Toast.makeText(mContext, result.getMsg(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                Toast.makeText(mContext, ex.getMessage(), Toast.LENGTH_SHORT).show();
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
