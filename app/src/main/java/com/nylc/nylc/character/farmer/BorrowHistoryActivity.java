package com.nylc.nylc.character.farmer;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;

import com.nylc.nylc.BaseActivity;
import com.nylc.nylc.R;
import com.nylc.nylc.model.BorrowHistory;

import java.util.ArrayList;
import java.util.List;

/**
 * 借钱历史
 * Created by 吴曰阳 on 2018/3/4.
 */

public class BorrowHistoryActivity extends BaseActivity implements View.OnClickListener {
    private ImageView iv_back;
    private ListView mListView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_borrow_history);
        init();

    }

    private void init() {
        iv_back = findViewById(R.id.iv_back);
        mListView = findViewById(R.id.list);

        iv_back.setOnClickListener(this);
        defaultData();
    }

    private void defaultData() {
        List<BorrowHistory> list = new ArrayList<>();
        BorrowHistory history = new BorrowHistory();
        history.setDate("2018年12月31日");
        history.setState(0);
        history.setMoney("3000");
        list.add(history);

        BorrowHistory history2 = new BorrowHistory();
        history2.setDate("2018年12月31日");
        history2.setState(1);
        history2.setMoney("1000");
        list.add(history2);

        BorrowHistory history3 = new BorrowHistory();
        history3.setDate("2018年12月31日");
        history3.setState(2);
        history3.setMoney("2000");
        list.add(history3);
        mListView.setAdapter(new BorrowHistoryAdapter(this, list));
    }

    @Override
    public void onClick(View view) {
        finish();
    }
}
