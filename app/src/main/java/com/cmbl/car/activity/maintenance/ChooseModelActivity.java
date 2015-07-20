package com.cmbl.car.activity.maintenance;

import android.content.Intent;
import android.os.Bundle;
import android.os.Message;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.cmbl.car.R;
import com.cmbl.car.adapter.CarModelAdatper;
import com.cmbl.car.base.BaseActivity;

/**
 * Created by Administrator on 2015/7/17 0017.
 */
public class ChooseModelActivity extends BaseActivity implements AdapterView.OnItemClickListener {
    private ListView mListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setMyContextView(R.layout.view_listview);

        initHeader();
        initBodyer();
        setResult(2);
    }

    @Override
    protected void initHeader() {
        setHeaderTitle(getString(R.string.hint_car_model));
    }

    @Override
    protected void initBodyer() {
        mListView = (ListView) findViewById(R.id.listview);
        mListView.setAdapter(new CarModelAdatper(this));
        mListView.setOnItemClickListener(this);
    }

    @Override
    protected void CallBackListener(Message msg) {

    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Intent intent = new Intent();
        intent.putExtra("model", "2014款 TFSI 舒适型");
        setResult(1, intent);
        finish();
    }
}
