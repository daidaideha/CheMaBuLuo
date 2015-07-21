package com.cmbl.car.activity;

import android.os.Bundle;
import android.os.Message;
import android.view.View;
import android.widget.ListView;

import com.cmbl.car.R;
import com.cmbl.car.adapter.ShopAdatper;
import com.cmbl.car.base.BaseActivity;
import com.cmbl.car.base.FinalValue;
import com.cmbl.car.model.ShopUnit;
import com.witalk.widget.CMBLTools;
import com.witalk.widget.PullToRefreshView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2015/7/21 0021.
 */
public class ShopListActivity extends BaseActivity implements PullToRefreshView.OnFooterRefreshListener {
    private PullToRefreshView mPullToRefreshView;
    private ListView mListView;

    private List<ShopUnit> listShop;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setMyContextView(R.layout.view_refreshwithlist);

        initData();
        initHeader();
        initBodyer();
    }

    private void initData() {
        listShop = new ArrayList<>();
        for (int i = 0; i < 15; i++) {
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
        setHeaderTitle(getString(R.string.tv_header_shop));
        setRightTextOrBtn(FinalValue.RIGHT_MODE_BTN);
        setRightRelHide(false);
        setOnHeaderRightClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CMBLTools.IntentToOther(ShopListActivity.this, SearchActivity.class, null);
            }
        });
    }

    @Override
    protected void initBodyer() {
        mPullToRefreshView = (PullToRefreshView) findViewById(R.id.pulltorefresh);
        mListView = (ListView) findViewById(R.id.listview);

        mPullToRefreshView.setEnablePullTorefresh(false);
        mPullToRefreshView.setEnablePullLoadMoreDataStatus(true);
        mPullToRefreshView.setOnFooterRefreshListener(this);

        ShopAdatper adatper = new ShopAdatper(this);
        adatper.addList(listShop);
        adatper.notifyDataSetChanged();
        mListView.setAdapter(adatper);
    }

    @Override
    protected void CallBackListener(Message msg) {

    }

    @Override
    public void onFooterRefresh(PullToRefreshView view) {

    }
}
