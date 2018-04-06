package com.nylc.nylc.character.company;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.nylc.nylc.BaseDialogFragment;
import com.nylc.nylc.R;

/**
 * 收货商报价
 * Created by kasim on 2018/4/2.
 */

public class ReceiveGrainDialog extends BaseDialogFragment {
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.dialog_receive_grain,container);
        return view;
    }
}
