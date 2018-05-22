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
import com.nylc.nylc.eventbus.ApproveEvent;
import com.nylc.nylc.model.ApproveSale;
import com.nylc.nylc.model.BaseResult;
import com.nylc.nylc.model.Member;
import com.nylc.nylc.model.ProductType;
import com.nylc.nylc.model.SellType;
import com.nylc.nylc.utils.CommonUtils;
import com.nylc.nylc.utils.Urls;

import org.greenrobot.eventbus.EventBus;
import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.util.List;

/**
 * Created by kasim on 2018/3/28.
 */

public class EditSaleOrderFragmentDialog extends BaseDialogFragment implements View.OnClickListener {
    private Spinner sp_names, sp_grains;
    private RadioGroup rg_sellTypes;
    private EditText et_price, et_water, et_count, et_earnest;
    private Button bt_confirm;

    private List<ProductType> productTypes;
    private List<Member> members;

    private ApproveSale approveSale;


    public static EditSaleOrderFragmentDialog getInstance(ApproveSale approveSale, int position) {
        EditSaleOrderFragmentDialog dialog = new EditSaleOrderFragmentDialog();
        Bundle args = new Bundle();
        args.putParcelable("sale", approveSale);
        args.putInt("position", position);
        dialog.setArguments(args);
        return dialog;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.dialog_sale_order, container);
        approveSale = getArguments().getParcelable("sale");
        sp_names = view.findViewById(R.id.sp_name);
        sp_grains = view.findViewById(R.id.sp_grains);
        rg_sellTypes = view.findViewById(R.id.rg_sellType);
        et_price = view.findViewById(R.id.et_price);
        et_water = view.findViewById(R.id.et_water);
        et_count = view.findViewById(R.id.et_count);
        et_earnest = view.findViewById(R.id.et_earnest);
        bt_confirm = view.findViewById(R.id.bt_confirm);
        bt_confirm.setOnClickListener(this);

        et_count.setText(approveSale.getQUANTITY() + "");
        et_price.setText(approveSale.getPRICE());
        et_earnest.setText(approveSale.getSUBSCRIPTION() + "");
        et_water.setText(approveSale.getWARTER() + "");
        getProductsType();
        queryUserGroup();
        querySellTypes();
        return view;
    }

    private void querySellTypes() {
        RequestParams params = new RequestParams(Urls.querySellTypeAction);
        params.addBodyParameter("tokenKey", CommonUtils.getToken(getActivity()));
        x.http().post(params, new Callback.CommonCallback<BaseResult>() {
            @Override
            public void onSuccess(BaseResult result) {
                CommonUtils.judgeCode(getActivity(), result.getCode());
                String level = result.getLevel();
                if ("success".equals(level)) {
                    List<SellType> sellTypes = JSON.parseArray(result.getData(), SellType.class);
                    String sell_type = approveSale.getSELL_TYPE();
                    for (int i = 0; i < sellTypes.size(); i++) {
                        SellType type = sellTypes.get(i);
                        RadioButton button = new RadioButton(getActivity());
                        button.setText(type.getDISPLAY_NAME_ZH());
                        button.setTag(type);
                        rg_sellTypes.addView(button, LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                        if (type.getDISPLAY_NAME_ZH().equals(sell_type)) {
                            int id = button.getId();
                            rg_sellTypes.check(id);
                        }
                        if (!type.getDISPLAY_NAME_ZH().equals("预定")) {
                            et_earnest.setText("0");
                            et_earnest.setEnabled(false);
                        }
                    }
                    rg_sellTypes.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                        @Override
                        public void onCheckedChanged(RadioGroup radioGroup, int checkedId) {
                            RadioButton button = radioGroup.findViewById(checkedId);
                            if (!button.getText().toString().equals("预定")) {
                                et_earnest.setText("0");
                                et_earnest.setEnabled(false);
                            } else {
                                et_earnest.setText(approveSale.getSUBSCRIPTION() + "");
                                et_earnest.setEnabled(true);
                            }
                        }
                    });


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
                    productTypes = JSON.parseArray(result.getData(), ProductType.class);
                    sp_grains.setAdapter(new ArrayAdapter<ProductType>(getContext(), android.R.layout.simple_list_item_1, productTypes));
                    String product_type = approveSale.getPRODUCT_TYPE();
                    for (int i = 0; i < productTypes.size(); i++) {
                        ProductType productType = productTypes.get(i);
                        if (product_type.equals(productType.getDISPLAY_NAME_ZH())) {
                            sp_grains.setSelection(i);
                        }
                    }
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
                    String farmer_name = approveSale.getFARMER_NAME();
                    for (int i = 0; i < members.size(); i++) {
                        Member member = members.get(i);
                        if (farmer_name.equals(member.getEMPNAME())) {
                            sp_names.setSelection(i);
                        }
                    }
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
        if (TextUtils.isEmpty(et_price.getText().toString())) {
            Toast.makeText(getActivity(), "请输入单价", Toast.LENGTH_SHORT).show();
            return;
        }
        if (TextUtils.isEmpty(et_water.getText().toString())) {
            Toast.makeText(getActivity(), "请输入水分点", Toast.LENGTH_SHORT).show();
            return;
        }
        if (TextUtils.isEmpty(et_count.getText().toString())) {
            Toast.makeText(getActivity(), "请输入粮食数量", Toast.LENGTH_SHORT).show();
            return;
        }
        SellType type = null;
        String earnest = "";
        for (int i = 0; i < rg_sellTypes.getChildCount(); i++) {
            RadioButton radioButton = (RadioButton) rg_sellTypes.getChildAt(i);
            if (radioButton.isChecked()) {
                type = (SellType) radioButton.getTag();
//                int id = radioButton.getId();
                if (!type.getDISPLAY_NAME_ZH().equals("预定")) {
                    earnest = "0";
                } else {
                    if (TextUtils.isEmpty(et_earnest.getText().toString())) {
                        Toast.makeText(getActivity(), "请输入垫付定金", Toast.LENGTH_SHORT).show();
                        return;
                    } else {
                        earnest = et_earnest.getText().toString();
                    }
                }
            }
        }
        Member member = (Member) sp_names.getSelectedItem();
        String farmerId = member.getEMP_ID();
        ApproveSale approveSale = getArguments().getParcelable("sale");
        String orderId = approveSale.getID();
        String sellType = type.getDISPLAY_NAME_ZH();
        ProductType goodsType = (ProductType) sp_grains.getSelectedItem();
        String productType = goodsType.getDISPLAY_NAME_ZH();
        String price = et_price.getText().toString();
        String water = et_water.getText().toString();
        String count = et_count.getText().toString();
        RequestParams params = new RequestParams(Urls.updateProductOrder);
        params.addBodyParameter("tokenKey", CommonUtils.getToken(getActivity()));
        params.addBodyParameter("farmerId", farmerId);
        params.addBodyParameter("orderId", orderId);
        params.addBodyParameter("productType", productType);
        params.addBodyParameter("sellType", sellType);
        params.addBodyParameter("price", price);
        params.addBodyParameter("quantity", count);
        params.addBodyParameter("warter", water);
        params.addBodyParameter("subscription", earnest);
        x.http().post(params, new Callback.CommonCallback<BaseResult>() {
            @Override
            public void onSuccess(BaseResult result) {
                Log.i("", "");
                if (result.getLevel().equals("success")) {
                    ApproveEvent event = new ApproveEvent();
                    event.setRefreshType(ApproveEvent.STATE_PRODUCTS);
                    EventBus.getDefault().post(event);
                    Toast.makeText(getActivity(), result.getMsg(), Toast.LENGTH_SHORT).show();
                    dismiss();
                } else {
                    Toast.makeText(getActivity(), result.getMsg(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                Log.i("", "");
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
