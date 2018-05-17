package com.nylc.nylc.character.leader;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.Editable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.nylc.nylc.BaseDialogFragment;
import com.nylc.nylc.R;
import com.nylc.nylc.eventbus.AddOrderEvent;
import com.nylc.nylc.eventbus.ApproveEvent;
import com.nylc.nylc.model.BaseResult;
import com.nylc.nylc.model.Member;
import com.nylc.nylc.model.ProductType;
import com.nylc.nylc.utils.CommonUtils;
import com.nylc.nylc.utils.Urls;

import org.greenrobot.eventbus.EventBus;
import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.util.List;

/**
 * 生产资料弹出框
 * Created by kasim on 2018/3/28.
 */

public class BuyOrderFragmentDialog extends BaseDialogFragment implements View.OnClickListener {
    //    private String[] names = {"张三", "李四"};
//    private String[] grains = {"小麦", "水稻", "玉米"};
    private List<ProductType> goodsTypes;
    private Spinner sp_names, sp_grains;
    private EditText et_count, et_earnest;
    private List<Member> members;
    private Button bt_confirm;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.dialog_buy_order, container);
        sp_names = view.findViewById(R.id.sp_name);
        sp_grains = view.findViewById(R.id.sp_grains);
        et_count = view.findViewById(R.id.et_count);
        et_earnest = view.findViewById(R.id.et_earnest);
        bt_confirm = view.findViewById(R.id.bt_confirm);
        bt_confirm.setOnClickListener(this);
        getProductsType();
        queryUserGroup();
        return view;
    }

    private void getProductsType() {
        RequestParams params = new RequestParams(Urls.queryProductTypeAction);
        params.addBodyParameter("tokenKey", CommonUtils.getToken(getActivity()));
        x.http().post(params, new Callback.CommonCallback<BaseResult>() {
            @Override
            public void onSuccess(BaseResult result) {
                String code = result.getCode();
                CommonUtils.judgeCode(getActivity(), code);
                String level = result.getLevel();
                if ("success".equals(level)) {
                    //请求成功
                    goodsTypes = JSON.parseArray(result.getData(), ProductType.class);
                    sp_grains.setAdapter(new ArrayAdapter<ProductType>(getContext(), android.R.layout.simple_list_item_1, goodsTypes));
                } else {
                    String msg = result.getMsg();
                    Toast.makeText(getActivity(), msg, Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                Log.e("error", ex.getMessage());
                Toast.makeText(getActivity(), "连接服务器失败", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onCancelled(CancelledException cex) {

            }

            @Override
            public void onFinished() {

            }
        });

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

    Callback.Cancelable cancelable;

    private void confirm() {
        if (cancelable != null && !cancelable.isCancelled()) {
            cancelable.cancel();
        }
        Editable countText = et_count.getText();
        if (countText == null||countText.toString().equals("")) {
            Toast.makeText(getActivity(), "请输入亩数", Toast.LENGTH_SHORT).show();
            return;
        }
        Editable earnestText = et_earnest.getText();
        if (earnestText == null||earnestText.toString().equals("")) {
            Toast.makeText(getActivity(), "请输入定金", Toast.LENGTH_SHORT).show();
            return;
        }
        Member member = (Member) sp_names.getSelectedItem();
        String emp_id = member.getEMP_ID();
        String type = sp_grains.getSelectedItem().toString();
        RequestParams params = new RequestParams(Urls.addGoodsOrder);
        params.addBodyParameter("tokenKey", CommonUtils.getToken(getActivity()));
        params.addBodyParameter("farmerId", emp_id);
        params.addBodyParameter("subscription", earnestText.toString());
        params.addBodyParameter("quantity", countText.toString());
        params.addBodyParameter("productType", type);
        cancelable = x.http().post(params, new Callback.CommonCallback<BaseResult>() {
            @Override
            public void onSuccess(BaseResult result) {
                CommonUtils.judgeCode(getActivity(), result.getCode());
                String level = result.getLevel();
                if ("success".equals(level)) {
                    ApproveEvent event = new ApproveEvent();
                    event.setRefreshType(ApproveEvent.STATE_GOODS);
                    EventBus.getDefault().post(event);

                    Toast.makeText(getActivity(), result.getMsg(), Toast.LENGTH_SHORT).show();
                    dismiss();
                } else {
                    Toast.makeText(getActivity(), result.getMsg(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                Log.e("error", ex.getMessage());
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
