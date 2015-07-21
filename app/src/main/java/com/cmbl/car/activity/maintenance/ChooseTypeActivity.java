package com.cmbl.car.activity.maintenance;

import java.util.ArrayList;
import java.util.List;

import android.content.Intent;
import android.os.Bundle;
import android.os.Message;
import android.support.v4.widget.DrawerLayout;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

import com.cmbl.car.R;
import com.cmbl.car.adapter.CarModelAdatper;
import com.cmbl.car.adapter.CarTypeAdatper;
import com.cmbl.car.adapter.CarTypeDrawerAdatper;
import com.cmbl.car.base.BaseActivity;
import com.cmbl.car.base.FinalValue;
import com.cmbl.car.model.CarTypeUnit;
import com.cmbl.car.widget.MySideBar;
import com.witalk.widget.CMBLTools;
import com.witalk.widget.PullToRefreshView;

public class ChooseTypeActivity extends BaseActivity implements OnItemClickListener, PullToRefreshView.OnHeaderRefreshListener {
	private MySideBar sideBar;
	private ListView mListView;
	private DrawerLayout mDrawerLayout;
	private PullToRefreshView mPullToRefreshView;
	private ListView mListViewDrawer;
	
	private List<CarTypeUnit> listData;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setMyContextView(R.layout.activity_choose_type);
		
		initData();
		initHeader();
		initBodyer();
		setResult(2);
	}
	
	private void initData() {
		listData = new ArrayList<>();
		for (int i = 0; i < 50; i++) {
			CarTypeUnit unit = new CarTypeUnit();
			if (i < 5) {
				unit.setPinyin("a");
			} else if (i < 10 && i >= 5) {
				unit.setPinyin("b");
			} else if (i < 15 && i >= 10) {
				unit.setPinyin("c");
			} else if (i < 25 && i >= 15) {
				unit.setPinyin("d");
			} else if (i < 40 && i >= 25) {
				unit.setPinyin("f");
			} else if (i < 50 && i >= 40) {
				unit.setPinyin("x");
			}
			unit.setName(unit.getPinyin() + i);
			unit.setCode(i);
			listData.add(unit);
		}
	}

	@Override
	protected void initHeader() {
		// TODO Auto-generated method stub
		setHeaderTitle(getString(R.string.tv_car_type));
//		setRightRelHide(false);
//		setRightTextOrBtn(FinalValue.RIGHT_MODE_BTN);
//		setHeaderRightBtn(R.mipmap.icon_search);
//		setOnHeaderRightClickListener(new OnClickListener() {
//
//			@Override
//			public void onClick(View view) {
//				// TODO Auto-generated method stub
//				CMBLTools.IntentToOther(ChooseTypeActivity.this, );
//			}
//		});
	}

	@Override
	protected void initBodyer() {
		// TODO Auto-generated method stub
		sideBar = (MySideBar) findViewById(R.id.sideBar);
		mListView = (ListView) findViewById(R.id.listview);
		CarTypeAdatper adatper = new CarTypeAdatper(this);
		adatper.clearList();
		adatper.addList(listData);
		sideBar.setListView(mListView);
		sideBar.setAdapter(adatper);
		
		mListView.setAdapter(adatper);
		mListView.setOnItemClickListener(this);

		initDrawer();
	}

	private void initDrawer() {
		mDrawerLayout = (DrawerLayout) findViewById(R.id.drawerlayout);
		mPullToRefreshView = (PullToRefreshView) findViewById(R.id.left_drawer);
        mPullToRefreshView.setEnablePullTorefresh(false);
        mPullToRefreshView.setOnHeaderRefreshListener(this);
		mListViewDrawer = (ListView) findViewById(R.id.listviewr_left);

		View header = getLayoutInflater().inflate(R.layout.view_drawer_header, null);
		mListViewDrawer.addHeaderView(header);
		mListViewDrawer.setAdapter(new CarTypeDrawerAdatper(this));
		mListViewDrawer.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				Intent intent = new Intent();
				intent.putExtra("type", "奥迪 A3");
                setResult(1, intent);
				finish();
			}
		});
	}

	@Override
	protected void CallBackListener(Message msg) {
		// TODO Auto-generated method stub
	}

	@Override
	public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
		// TODO Auto-generated method stub
		mDrawerLayout.openDrawer(mPullToRefreshView);
	}

    @Override
    public void onHeaderRefresh(PullToRefreshView view) {

    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (mDrawerLayout.isDrawerOpen(mPullToRefreshView)) {
            mDrawerLayout.closeDrawer(mPullToRefreshView);
            return  true;
        } else
            return super.onKeyDown(keyCode, event);
    }
}
