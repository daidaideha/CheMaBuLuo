package com.cmbl.car.activity.person;

import android.content.Intent;
import android.os.Bundle;
import android.os.Message;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.cmbl.car.R;
import com.cmbl.car.activity.maintenance.ChooseModelActivity;
import com.cmbl.car.activity.maintenance.ChooseTypeActivity;
import com.cmbl.car.activity.maintenance.ShopActivity;
import com.cmbl.car.base.BaseActivity;
import com.witalk.widget.CMBLTools;

/**
 * Created by Administrator on 2015/7/17 0017.
 */
public class AddNewCar extends BaseActivity implements View.OnClickListener {
    private EditText edt_number;
    private EditText edt_distance;
    private TextView tv_from;
    private TextView tv_type;
    private TextView tv_model;
    
    private int type = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setMyContextView(R.layout.activity_add_newcar);
        
        try {
			type = getIntent().getIntExtra("type", -1);
		} catch (Exception e) {
			// TODO: handle exception
		}

        initHeader();
        initBodyer();
    }

    @Override
    protected void initHeader() {
        setHeaderTitle(getString(R.string.tv_header_addcar));
    }

    @Override
    protected void initBodyer() {
        tv_from = (TextView) findViewById(R.id.tv_car_from);
        tv_type = (TextView) findViewById(R.id.tv_car_type);
        tv_model = (TextView) findViewById(R.id.tv_car_model);
        edt_number = (EditText) findViewById(R.id.edt_car_number);
        edt_distance = (EditText) findViewById(R.id.edt_car_distance);

        tv_from.setOnClickListener(this);
        findViewById(R.id.rl_car_type).setOnClickListener(this);
        findViewById(R.id.rl_car_model).setOnClickListener(this);
        findViewById(R.id.btn_save).setOnClickListener(this);
    }

    @Override
    protected void CallBackListener(Message msg) {

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.rl_car_type:
                CMBLTools.IntentToOtherForResult(this, ChooseTypeActivity.class, null, 2);
                break;
            case R.id.rl_car_model:
                CMBLTools.IntentToOtherForResult(this, ChooseModelActivity.class, null, 1);
                break;
            case R.id.tv_car_from:
                break;
            case R.id.btn_save:
                if (check()) {
                    finish();
                    if (type == 1) {
                        CMBLTools.IntentToOther(this, ShopActivity.class, null);
                    }
                }
                break;
            default:
                break;
        }
    }

    private boolean check() {
        if (CMBLTools.isValueEmpty(tv_type.getText().toString().trim())
                || tv_type.getText().toString().trim().equals(getString(R.string.hint_car_type))) {
            CMBLTools.toastMsg(getString(R.string.toast_choose_car_type), this);
            return false;
        }
        if (CMBLTools.isValueEmpty(tv_model.getText().toString().trim())
                || tv_model.getText().toString().trim().equals(getString(R.string.hint_car_model))) {
            CMBLTools.toastMsg(getString(R.string.toast_choose_car_model), this);
            return false;
        }
        if (CMBLTools.isValueEmpty(edt_number.getText().toString().trim())) {
            CMBLTools.toastMsg(getString(R.string.toast_input_car_number), this);
            return false;
        }
        if (CMBLTools.isValueEmpty(edt_distance.getText().toString().trim())) {
            CMBLTools.toastMsg(getString(R.string.toast_input_car_distance), this);
            return false;
        }
        return true;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1 && resultCode == 1) {
            tv_model.setTextColor(getResources().getColor(R.color.color_index_near_shop));
            tv_model.setText(data.getStringExtra("model"));
        } else if (requestCode == 1 && resultCode == 2) {
            if (CMBLTools.isValueEmpty(tv_model.getText().toString()))
                tv_model.setTextColor(getResources().getColor(R.color.color_index_more));
        } else if (requestCode == 2 && resultCode == 1) {
            tv_type.setTextColor(getResources().getColor(R.color.color_index_near_shop));
            tv_type.setText(data.getStringExtra("type"));
        } else if (requestCode == 2 && resultCode == 2) {
            if (CMBLTools.isValueEmpty(tv_type.getText().toString()))
                tv_type.setTextColor(getResources().getColor(R.color.color_index_more));
        }
    }
}
