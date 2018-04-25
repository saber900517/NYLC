package com.nylc.nylc.character.supplier;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;

import com.nylc.nylc.BaseActivity;
import com.nylc.nylc.R;
import com.nylc.nylc.model.BaseResult;
import com.nylc.nylc.model.MeansOfProduction;
import com.nylc.nylc.utils.CommonUtils;
import com.nylc.nylc.utils.Urls;
import com.nylc.nylc.utils.ViewHolder;

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
        boolean showDeleteButton = i % 2 == 0 ? true : false;
        Button bt = ViewHolder.get(view, R.id.btn_quote);
        bt.setText(showDeleteButton ? "修改" : "报价");
        bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MeansOfProductsDialog dialog = new MeansOfProductsDialog();
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
        params.addBodyParameter("townId", "");
        params.addBodyParameter("orderVillageId", "");//供应商订单ID
        x.http().post(params, new Callback.CommonCallback<BaseResult>() {
            @Override
            public void onSuccess(BaseResult result) {
                CommonUtils.judgeCode(mContext, result.getCode());
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
}
