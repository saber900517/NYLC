package com.nylc.nylc.character.leader;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.nylc.nylc.BaseDialogFragment;
import com.nylc.nylc.R;
import com.nylc.nylc.model.BaseResult;
import com.nylc.nylc.model.Member;
import com.nylc.nylc.model.ProductType;
import com.nylc.nylc.model.SellType;
import com.nylc.nylc.utils.CommonUtils;
import com.nylc.nylc.utils.Urls;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.util.List;

/**
 * Created by kasim on 2018/3/28.
 */

public class SaleBatchOrderFragmentDialog extends BaseDialogFragment implements View.OnClickListener {
    private Spinner sp_names;
    private Button bt_confirm;

    private List<ProductType> goodsTypes;
    private List<Member> members;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.dialog_sale_batch_order, container);
        sp_names = view.findViewById(R.id.sp_name);
        bt_confirm = view.findViewById(R.id.bt_confirm);
        bt_confirm.setOnClickListener(this);

        queryUserGroup();
        return view;
    }


    private void queryUserGroup() {
        RequestParams params = new RequestParams(Urls.queryUserGroup);
        params.addBodyParameter("tokenKey", CommonUtils.getToken(getActivity()));
        x.http().post(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                BaseResult baseResult = JSON.parseObject(result, BaseResult.class);
                String code = baseResult.getCode();
                CommonUtils.judgeCode(getActivity(), code);
                String level = baseResult.getLevel();
                if ("success".equals(level)) {
                    //请求成功
                    members = JSON.parseArray(baseResult.getData(), Member.class);
                    sp_names.setAdapter(new ArrayAdapter<Member>(getContext(), android.R.layout.simple_list_item_1, members));
                } else {
                    String msg = baseResult.getMsg();
                    Toast.makeText(getActivity(), msg, Toast.LENGTH_SHORT).show();
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

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.bt_confirm:
                confirm();
                break;
        }
    }

    private void confirm() {
        Member member = (Member) sp_names.getSelectedItem();
        String farmerId = member.getEMP_ID();
        RequestParams params = new RequestParams(Urls.addProductOrders);
        params.addBodyParameter("tokenKey", CommonUtils.getToken(getActivity()));
        params.addBodyParameter("farmerId", farmerId);
        x.http().post(params, new Callback.CommonCallback<BaseResult>() {
            @Override
            public void onSuccess(BaseResult result) {
                Log.i("", "");
                if (result.getLevel().equals("success")) {
                    Toast.makeText(getActivity(), result.getMsg(), Toast.LENGTH_SHORT).show();
                    dismiss();
                } else {
                    Toast.makeText(getActivity(), result.getMsg(), Toast.LENGTH_SHORT).show();
                    dismiss();
                }
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                Log.i("", "");
                Toast.makeText(getActivity(), ex.getMessage(), Toast.LENGTH_SHORT).show();
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
