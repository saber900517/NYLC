package com.nylc.nylc.character.leader;


import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatDialogFragment;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.nylc.nylc.BaseDialogFragment;
import com.nylc.nylc.R;

/**
 * Created by kasim on 2018/3/28.
 */

public class BuyOrderFragmentDialog extends BaseDialogFragment {
    private String[] names = {"张三", "李四"};
    private String[] grains = {"小麦", "水稻", "玉米"};


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.dialog_buy_order, container);
        Spinner sp_names = view.findViewById(R.id.sp_name);
        sp_names.setAdapter(new ArrayAdapter<String>(getContext(), android.R.layout.simple_list_item_1, names));
        Spinner sp_grains = view.findViewById(R.id.sp_grains);
        sp_grains.setAdapter(new ArrayAdapter<String>(getContext(), android.R.layout.simple_list_item_1, grains));
        return view;
    }

}
