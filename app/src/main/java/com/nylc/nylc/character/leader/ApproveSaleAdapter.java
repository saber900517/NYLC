package com.nylc.nylc.character.leader;

import android.content.Context;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.nylc.nylc.R;
import com.nylc.nylc.model.ApproveSale;
import com.nylc.nylc.model.BaseResult;
import com.nylc.nylc.utils.CommonUtils;
import com.nylc.nylc.utils.Urls;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.util.List;

/**
 * Created by kasim on 2018/3/28.
 */

public class ApproveSaleAdapter extends BaseAdapter {
    private Context mContext;
    private List<ApproveSale> mList;
    private FragmentManager mManager;

    public ApproveSaleAdapter(Context mContext, List<ApproveSale> mList, FragmentManager mManager) {
        this.mContext = mContext;
        this.mList = mList;
        this.mManager = mManager;
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
        view = LayoutInflater.from(mContext).inflate(R.layout.item_approve_sale, null);
        final int position = i;
        TextView tv_earnest = view.findViewById(R.id.tv_earnest);
        TextView tv_state = view.findViewById(R.id.tv_state);
        TextView tv_name = view.findViewById(R.id.tv_name);
        TextView tv_productType = view.findViewById(R.id.tv_products);
        TextView tv_count = view.findViewById(R.id.tv_count);
        TextView tv_price = view.findViewById(R.id.tv_price);
        TextView tv_realWeight = view.findViewById(R.id.tv_real_weight);
        TextView tv_water = view.findViewById(R.id.tv_water);
        TextView btn = view.findViewById(R.id.btn);
        TextView btn_edit = view.findViewById(R.id.btn_edit);
        TextView btn_delete = view.findViewById(R.id.btn_delete);
        TextView tv_date = view.findViewById(R.id.tv_date);
        final ApproveSale approveSale = mList.get(i);
        int subscription = approveSale.getSUBSCRIPTION();//定金
        tv_earnest.setVisibility(subscription > 0 ? View.VISIBLE : View.GONE);
        tv_earnest.setText("定金" + subscription);
        tv_name.setText(approveSale.getFARMER_NAME());
        tv_productType.setText(approveSale.getPRODUCT_TYPE());
        tv_count.setText(approveSale.getQUANTITY() + "亩");
        tv_date.setText(approveSale.getCREATED_DATE());
        tv_price.setText(approveSale.getPRICE() + "元/斤");
        int status = approveSale.getSTATUS();

        if (status == 0) {
            btn.setVisibility(View.GONE);
            tv_state.setText(getStatusText(status));
            btn_edit.setVisibility(View.VISIBLE);
            btn_delete.setVisibility(View.VISIBLE);
            btn_edit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    EditSaleOrderFragmentDialog dialog = EditSaleOrderFragmentDialog.getInstance(approveSale, position);
                    dialog.show(mManager, "editOrder");
                }
            });
            btn_delete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    delSaleOrder(approveSale);
                }
            });
        } else {
            btn_edit.setVisibility(View.GONE);
            btn_delete.setVisibility(View.GONE);
            if (status == 30 || status == 10) {
//                tv_state.setVisibility(View.GONE);
                btn.setVisibility(View.VISIBLE);
                btn.setText("完成交易");
                btn.setOnClickListener(
                        //完成交易
                        new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                ConfirmFragmentDialog confirmFragmentDialog = ConfirmFragmentDialog.getInstance(approveSale);
                                confirmFragmentDialog.show(mManager, "confirmDialog");
                            }
                        });
            } else {
//                tv_state.setVisibility(View.VISIBLE);
                btn.setVisibility(View.GONE);
                tv_state.setText(getStatusText(status));
            }
        }

        return view;
    }

    private void delSaleOrder(final ApproveSale approveSale) {
        RequestParams params = new RequestParams(Urls.delProductOrder);
        params.addBodyParameter("tokenKey", CommonUtils.getToken(mContext));
        params.addBodyParameter("farmerId", approveSale.getFARMER_ID());
        params.addBodyParameter("orderId", approveSale.getID());
        x.http().post(params, new Callback.CommonCallback<BaseResult>() {
            @Override
            public void onSuccess(BaseResult result) {
                CommonUtils.judgeCode(mContext, result.getCode());
                if ("success".equals(result.getLevel())) {
                    mList.remove(approveSale);
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

    private String getStatusText(int status) {
        switch (status) {
            case 0:
                return "待确认";
            case 10:
                return "已预定";
            case 20:
                return "已发布";
            case 30:
                return "待企业交易";
            case 40:
                return "交易完成";
            case 50:
                return "失效";
        }
        return "";
    }


}
