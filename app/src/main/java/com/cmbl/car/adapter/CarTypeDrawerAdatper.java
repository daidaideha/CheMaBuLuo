package com.cmbl.car.adapter;

import android.app.Activity;
import android.content.Context;
import android.media.Image;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.cmbl.car.R;
import com.cmbl.car.model.ShopUnit;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2015/7/15 0015.
 */
public class CarTypeDrawerAdatper extends BaseAdapter {
    private Context context;
    private LayoutInflater inflater;
//    private List<ShopUnit> listData;

    public CarTypeDrawerAdatper(Activity context) {
        this.context = context;
        this.inflater = context.getLayoutInflater();
//        listData = new ArrayList<>();
    }

    public void clearList() {
//        listData.clear();
    }

    public void addList(List<ShopUnit> listData) {
//        this.listData.addAll(listData);
    }

    @Override
    public int getCount() {
        return 10;
    }

    @Override
    public Object getItem(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        convertView = inflater.inflate(R.layout.adapter_car_type_drawer, null);
        View iv_line = convertView.findViewById(R.id.line);
        if (position == 0)
            iv_line.setVisibility(View.GONE);
//        LinearLayout ll_start = (LinearLayout) convertView.findViewById(R.id.ll_start);
//        initView(convertView, listData.get(position));
//        initStart(ll_start, listData.get(position));
        return convertView;
    }
}
