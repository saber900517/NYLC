package com.nylc.nylc.character.leader;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;

import com.nylc.nylc.BaseDialogFragment;
import com.nylc.nylc.R;

/**
 * 提货
 * Created by kasim on 2018/3/28.
 */

public class TakeDeliveryOfGoodsFragmentDialog extends BaseDialogFragment {
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.dialog_take_deliver_of_goods, container);
        return view;
    }


}