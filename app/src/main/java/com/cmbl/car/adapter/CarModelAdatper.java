package com.cmbl.car.adapter;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.cmbl.car.R;

/**
 * Created by Administrator on 2015/7/15 0015.
 */
public class CarModelAdatper extends BaseAdapter {
    private LayoutInflater inflater;

    public CarModelAdatper(Activity context) {
        this.inflater = context.getLayoutInflater();
    }

    @Override
    public int getCount() {
        return 10;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        convertView = inflater.inflate(R.layout.adapter_carmodel, null);
        return convertView;
    }
}
