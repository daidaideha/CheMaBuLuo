package com.cmbl.car.adapter;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.cmbl.car.R;
import com.cmbl.car.model.CarUnit;

/**
 * Created by Administrator on 2015/7/15 0015.
 */
public class ChangeCarlAdatper extends BaseAdapter {
    private LayoutInflater inflater;
    private List<CarUnit> listData;

    public ChangeCarlAdatper(Activity context) {
        this.inflater = context.getLayoutInflater();
        this.listData = new ArrayList<>();
    }
    
    public void clearList() {
    	listData.clear();
    }
    
    public void addList(List<CarUnit> listData) {
    	this.listData.addAll(listData);
    }

    @Override
    public int getCount() {
        return listData.size();
    }

    @Override
    public Object getItem(int position) {
        return listData.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        convertView = inflater.inflate(R.layout.adapter_change_car, null);
        TextView tv_number = (TextView) convertView.findViewById(R.id.tv_car_number);
        TextView tv_type = (TextView) convertView.findViewById(R.id.tv_car_type);
        ImageView iv_check = (ImageView) convertView.findViewById(R.id.iv_check);
        
        tv_number.setText(listData.get(position).getFrom() + listData.get(position).getNumber());
        tv_type.setText(listData.get(position).getType());
        if (listData.get(position).isChecked()) {
			iv_check.setBackgroundResource(R.mipmap.icon_change_checked);
		} else {
			iv_check.setBackgroundResource(R.mipmap.icon_change_uncheck);
		}
        return convertView;
    }
}
