package com.cmbl.car.activity.maintenance;

import java.util.ArrayList;
import java.util.List;

import android.content.Intent;
import android.os.Bundle;
import android.os.Message;
import android.view.View;
import android.widget.AbsListView;
import android.widget.ListView;
import android.widget.TextView;

import com.cmbl.car.R;
import com.cmbl.car.adapter.MShopAdapter;
import com.cmbl.car.base.BaseActivity;
import com.cmbl.car.model.CarUnit;
import com.cmbl.car.model.ShopUnit;
import com.witalk.widget.CMBLTools;
import com.witalk.widget.PullToRefreshView;

/**
 * Created by Administrator on 2015/7/17 0017.
 */
public class ShopActivity extends BaseActivity implements View.OnClickListener {
    private PullToRefreshView pullToRefreshView;
    private ListView mListView;
    private TextView tv_carType;
    private List<ShopUnit> listShop;
    private CarUnit car;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setMyContextView(R.layout.activity_maintenance_shop);

        initData();
        initHeader();
        initBodyer();
    }

    private void initData() {
        listShop = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            ShopUnit shop = new ShopUnit();
            shop.setStart(i % 5 + 0.5f);
            shop.setName("杭州百得利 " + i);
            shop.setAddress("余杭区文一西路960号 ------" + i);
            shop.setDistance(i);
            listShop.add(shop);
        }
    }

    @Override
    protected void initHeader() {
        setHeaderTitle(getString(R.string.tv_homepage_appointment));
    }

    @Override
    protected void initBodyer() {
        pullToRefreshView = (PullToRefreshView) findViewById(R.id.pulltorefresh);
        mListView = (ListView) findViewById(R.id.listview);
        View headView = getLayoutInflater().inflate(R.layout.view_shop_header, null);
        headView.findViewById(R.id.tv_change).setOnClickListener(this);
        tv_carType = (TextView) headView.findViewById(R.id.tv_car_type);
        headView.setLayoutParams(new AbsListView.LayoutParams(AbsListView.LayoutParams.MATCH_PARENT,
                40));
        MShopAdapter adatper = new MShopAdapter(this);
        adatper.addList(listShop);
        adatper.notifyDataSetChanged();
        mListView.addHeaderView(headView);
        mListView.setAdapter(adatper);

        pullToRefreshView.setEnablePullTorefresh(false);
    }

    @Override
    protected void CallBackListener(Message msg) {

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_change:
            	Bundle bundle = new Bundle();
            	bundle.putInt("id", 1);
            	if (car != null) {
                	bundle.putInt("id", car.getId());
				}
                CMBLTools.IntentToOtherForResult(this, ChangeCarActivity.class, bundle, 1);
                break;
            default:
                break;
        }
    }
    
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
    	// TODO Auto-generated method stub
    	super.onActivityResult(requestCode, resultCode, data);
    	if (requestCode == 1 && resultCode == 1) {
    		car = (CarUnit) data.getSerializableExtra("car");
    		tv_carType.setText(car.getType());
		}
    }
}
