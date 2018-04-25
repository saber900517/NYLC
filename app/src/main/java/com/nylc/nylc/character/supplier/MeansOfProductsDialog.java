package com.nylc.nylc.character.supplier;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AppCompatDialogFragment;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.nylc.nylc.BaseDialogFragment;
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

/**
 * 供货中心列表点击报价窗口
 * Created by kasim on 2018/4/2.
 */

public class MeansOfProductsDialog extends BaseDialogFragment {

    public MeansOfProductsDialog() {
    }

    public static MeansOfProductsDialog getInstance(MeansOfProduction item) {
        MeansOfProductsDialog dialog = new MeansOfProductsDialog();
        Bundle args = new Bundle();
        args.putParcelable("item", item);
        dialog.setArguments(args);
        return dialog;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.dialog_means_of_products, container);
        final MeansOfProduction item = getArguments().getParcelable("item");
        TextView tv_minQuote = ViewHolder.get(view, R.id.tv_minQuote);
        tv_minQuote.setText(item.getMinQuote() + "元/亩");
        final EditText et_myQuote = ViewHolder.get(view, R.id.et_myQuote);
        Button btn_confirm = ViewHolder.get(view, R.id.btn_confirm);
        btn_confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String myQuote = et_myQuote.getText().toString();
                if (TextUtils.isEmpty(myQuote)) {
                    Toast.makeText(getActivity(), "请输入报价", Toast.LENGTH_SHORT).show();
                    return;
                }
                RequestParams params = new RequestParams(Urls.addUpdateQuoteOrderAction);
                params.addBodyParameter("tokenKey", CommonUtils.getToken(getActivity()));
                params.addBodyParameter("townId", item.getTOWN_ID());
                params.addBodyParameter("orderVillageId", item.getID());
                params.addBodyParameter("quote", myQuote);
                x.http().post(params, new Callback.CommonCallback<BaseResult>() {
                    @Override
                    public void onSuccess(BaseResult result) {
                        CommonUtils.judgeCode(getActivity(), result.getCode());
                        if ("success".equals(result.getLevel())) {
                            Toast.makeText(getActivity(), result.getMsg(), Toast.LENGTH_SHORT).show();
                            EventBus.getDefault().post(new RefreshEvent());
                            dismiss();
                        } else {
                            Toast.makeText(getActivity(), result.getMsg(), Toast.LENGTH_SHORT).show();
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
        });
        return view;
    }

}
