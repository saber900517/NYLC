package com.nylc.nylc.character.company;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

import com.nylc.nylc.BaseActivity;
import com.nylc.nylc.R;
import com.nylc.nylc.model.MeansOfProduction;
import com.nylc.nylc.model.ReceiveGrain;

import java.util.ArrayList;
import java.util.List;

/**
 * 收粮食
 * Created by kasim on 2018/3/28.
 */

public class ReceiveGrainActivity extends BaseActivity implements View.OnClickListener {
    private ImageView iv_back;
    private Spinner sp_county, sp_town;
    private ListView list_type, list_grain;
    private TextView tv_curveGraph;

    private String[] counties = {"全部", "滨海县"};
    private String[] towns = {"全部", "滨海港镇"};
    private String[] types = {"水稻", "玉米", "小麦"};

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_receive_grain);
        init();
    }

    private void init() {
        iv_back = findViewById(R.id.iv_back);
        sp_county = findViewById(R.id.sp_county);
        sp_town = findViewById(R.id.sp_town);
        list_type = findViewById(R.id.list_type);
        list_grain = findViewById(R.id.list_grain);
        tv_curveGraph = findViewById(R.id.tv_curveGraph);

        tv_curveGraph.setOnClickListener(this);
        iv_back.setOnClickListener(this);
        defaultData();
    }

    private void defaultData() {
        sp_county.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, counties));
        sp_town.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, towns));
        list_type.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, types));
        List<ReceiveGrain> list = new ArrayList<>();
        for (int i = 0; i < 4; i++) {
            ReceiveGrain production = new ReceiveGrain();
            list.add(production);
        }
        list_grain.setAdapter(new ReceiverGrainAdapter(this, list));
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_back:
                finish();
                break;
            case R.id.tv_curveGraph:
                startActivity(new Intent(this, CurveGraphActivity.class));
                break;
        }
    }
}
