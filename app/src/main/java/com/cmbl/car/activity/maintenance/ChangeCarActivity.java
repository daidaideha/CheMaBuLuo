package com.cmbl.car.activity.maintenance;

import android.content.Intent;
import android.os.Bundle;
import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

import com.cmbl.car.R;
import com.cmbl.car.activity.person.AddNewCar;
import com.cmbl.car.adapter.ChangeCarlAdatper;
import com.cmbl.car.base.BaseActivity;
import com.cmbl.car.base.FinalValue;
import com.cmbl.car.model.CarUnit;
import com.witalk.widget.CMBLTools;

import java.util.ArrayList;
import java.util.List;

public class ChangeCarActivity extends BaseActivity implements OnItemClickListener {
	private ListView mListView;
	private List<CarUnit> listData;
	private int id;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setMyContextView(R.layout.view_listview);
		
		try {
			id = getIntent().getIntExtra("id", -1);
		} catch (Exception e) {
			// TODO: handle exception
		}

		initData();
		initHeader();
		initBodyer();
        setResult(2);
	}
	
	private void initData() {
		listData = new ArrayList<>();
		for (int i = 0; i < 3; i++) {
			CarUnit car = new CarUnit();
			car.setId(i);
			car.setFrom("浙A");
			car.setNumber("0000" + i);
			car.setType("奥迪A6L 2014款 TFSI 标准款 " + i);
			car.setChecked(false);
			if (id == i) {
				car.setChecked(true);
			}
			listData.add(car);
		}
	}

	@Override
	protected void initHeader() {
		// TODO Auto-generated method stub
		setHeaderTitle(getString(R.string.tv_header_change_car));
		setRightRelHide(false);
		setRightTextOrBtn(FinalValue.RIGHT_MODE_TEXT);
		setHeaderRightTV(getString(R.string.tv_header_add_newcar), 0);
		setOnHeaderRightClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View view) {
				// TODO Auto-generated method stub
				CMBLTools.IntentToOtherForResult(ChangeCarActivity.this, AddNewCar.class, null, 1);
			}
		});
	}

	@Override
	protected void initBodyer() {
		// TODO Auto-generated method stub
		mListView = (ListView) findViewById(R.id.listview);
		ChangeCarlAdatper adatper = new ChangeCarlAdatper(this);
		adatper.clearList();
		adatper.addList(listData);
		mListView.setAdapter(adatper);
		mListView.setOnItemClickListener(this);
	}

	@Override
	protected void CallBackListener(Message msg) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onItemClick(AdapterView<?> arg0, View arg1, int position, long arg3) {
		// TODO Auto-generated method stub
		Intent intent = new Intent();
		intent.putExtra("car", listData.get(position));
		setResult(1, intent);
		finish();
	}

}
