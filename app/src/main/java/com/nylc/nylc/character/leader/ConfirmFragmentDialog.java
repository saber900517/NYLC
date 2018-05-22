package com.nylc.nylc.character.leader;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatDialogFragment;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
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
import com.nylc.nylc.eventbus.ApproveEvent;
import com.nylc.nylc.model.ApproveSale;
import com.nylc.nylc.model.BaseResult;
import com.nylc.nylc.utils.CommonUtils;
import com.nylc.nylc.utils.Urls;

import org.greenrobot.eventbus.EventBus;
import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.math.BigDecimal;

/**
 * Created by kasim on 2018/3/28.
 */

public class ConfirmFragmentDialog extends BaseDialogFragment implements View.OnClickListener {

    public static ConfirmFragmentDialog getInstance(ApproveSale approveSale) {
        ConfirmFragmentDialog dialog = new ConfirmFragmentDialog();
        Bundle arguments = new Bundle();
        arguments.putParcelable("approveSale", approveSale);
        dialog.setArguments(arguments);
        return dialog;
    }

    EditText et_price;
    EditText et_amount;
    EditText et_water;
    TextView tv_amount;
    TextView tv_needPay;
    TextView tv_sellType;
    TextView tv_productType;
    ApproveSale approveSale;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.dialog_confirm, container);
        TextView tv_count = view.findViewById(R.id.tv_count);
        TextView tv_earnest = view.findViewById(R.id.tv_earnest);
        tv_amount = view.findViewById(R.id.tv_amount);
        tv_needPay = view.findViewById(R.id.tv_needPay);
        Button btn = view.findViewById(R.id.btn);
        btn.setOnClickListener(this);

        et_price = view.findViewById(R.id.et_price);
        et_water = view.findViewById(R.id.et_water);
        tv_sellType = view.findViewById(R.id.tv_sellType);
        tv_productType = view.findViewById(R.id.tv_productType);
        et_amount = view.findViewById(R.id.et_amount);

        et_price.addTextChangedListener(new MyTextWatcher());
        et_amount.addTextChangedListener(new MyTextWatcher());

        approveSale = getArguments().getParcelable("approveSale");
        tv_count.setText(approveSale.getQUANTITY() + "");
        tv_earnest.setText(approveSale.getSUBSCRIPTION() + "");
        tv_sellType.setText(approveSale.getSELL_TYPE());
        tv_productType.setText(approveSale.getPRODUCT_TYPE());
        return view;
    }

    @Override
    public void onClick(View view) {
        if (TextUtils.isEmpty(et_price.getText().toString())) {
            Toast.makeText(getActivity(), "请输入单价", Toast.LENGTH_SHORT).show();
            return;
        }
        if (TextUtils.isEmpty(et_water.getText().toString())) {
            Toast.makeText(getActivity(), "请输入水分点", Toast.LENGTH_SHORT).show();
            return;
        }
        if (TextUtils.isEmpty(et_amount.getText().toString())) {
            Toast.makeText(getActivity(), "请输入总斤数", Toast.LENGTH_SHORT).show();
            return;
        }
        RequestParams params = new RequestParams(Urls.commitProductOrder);
        params.addBodyParameter("tokenKey", CommonUtils.getToken(getActivity()));
        params.addBodyParameter("farmerId", approveSale.getFARMER_ID());
        params.addBodyParameter("orderId", approveSale.getID());
        params.addBodyParameter("subscription", approveSale.getSUBSCRIPTION() + "");
        params.addBodyParameter("sellType", approveSale.getSELL_TYPE());
        params.addBodyParameter("price", et_price.getText().toString());
        params.addBodyParameter("quantityJin", et_amount.getText().toString());
        params.addBodyParameter("amount", tv_amount.getText().toString());
        params.addBodyParameter("needPay", tv_needPay.getText().toString());
        params.addBodyParameter("warter", et_water.getText().toString());
        x.http().post(params, new Callback.CommonCallback<BaseResult>() {
            @Override
            public void onSuccess(BaseResult result) {
                CommonUtils.judgeCode(getActivity(), result.getCode());
                if ("success".equals(result.getLevel())) {
                    ApproveEvent event = new ApproveEvent();
                    event.setRefreshType(ApproveEvent.STATE_PRODUCTS);
                    EventBus.getDefault().post(event);
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

    class MyTextWatcher implements TextWatcher {

        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

        }

        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

        }

        @Override
        public void afterTextChanged(Editable editable) {
            String amount = et_amount.getText().toString();
            String price = et_price.getText().toString();
            if (!TextUtils.isEmpty(amount) && !TextUtils.isEmpty(price)) {
                double totalAmount = new BigDecimal(amount).multiply(new BigDecimal(price)).doubleValue();
                double needPay = new BigDecimal(String.valueOf(totalAmount)).subtract(new BigDecimal(approveSale.getSUBSCRIPTION())).doubleValue();
                tv_amount.setText(String.valueOf(totalAmount));
                tv_needPay.setText(String.valueOf(needPay));
            }
        }
    }
}
