package com.nylc.nylc.character.company;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.nylc.nylc.BaseDialogFragment;
import com.nylc.nylc.R;
import com.nylc.nylc.eventbus.RefreshEvent;
import com.nylc.nylc.model.BaseResult;
import com.nylc.nylc.utils.CommonUtils;
import com.nylc.nylc.utils.Urls;

import org.greenrobot.eventbus.EventBus;
import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

/**
 * 收货商报价
 * Created by kasim on 2018/4/2.
 */

public class ReceiveGrainDialog extends BaseDialogFragment {

    public static ReceiveGrainDialog getInstance(int maxQuote, String orderVillageId) {
        ReceiveGrainDialog dialog = new ReceiveGrainDialog();
        Bundle arguments = new Bundle();
        arguments.putInt("maxQuote", maxQuote);
        arguments.putString("orderVillageId", orderVillageId);
        dialog.setArguments(arguments);
        return dialog;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.dialog_receive_grain, container);
        int maxQuote = getArguments().getInt("maxQuote");
        final String orderVillageId = getArguments().getString("orderVillageId");
        TextView tv_maxQuote = view.findViewById(R.id.tv_maxQuote);
        final EditText et_myQuote = view.findViewById(R.id.et_myQuote);
        Button btn = view.findViewById(R.id.btn);
        tv_maxQuote.setText(maxQuote + "元/斤");
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String myQuote = et_myQuote.getText().toString();
                if (TextUtils.isEmpty(myQuote)) {
                    Toast.makeText(getActivity(), "请输入报价", Toast.LENGTH_SHORT).show();
                    return;
                }
                quote(orderVillageId, myQuote);
            }
        });
        return view;
    }

    private void quote(String orderVillageId, String quote) {
        RequestParams params = new RequestParams(Urls.addQuoteAction);
        params.addBodyParameter("orderVillageId", orderVillageId);
        params.addBodyParameter("tokenKey", CommonUtils.getToken(getActivity()));
        params.addBodyParameter("quote", quote);
        x.http().post(params, new Callback.CommonCallback<BaseResult>() {
            @Override
            public void onSuccess(BaseResult result) {
                CommonUtils.judgeCode(getActivity(), result.getCode());
                String level = result.getLevel();
                if ("success".equals(level)) {
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
}
