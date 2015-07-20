package com.cmbl.car.adapter;

import android.app.Activity;
import android.content.Context;
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
 * Created by Administrator on 2015/7/17 0017.
 */
public class MShopAdapter extends BaseAdapter {
    private Context context;
    private LayoutInflater inflater;
    private List<ShopUnit> listData;

    public MShopAdapter(Activity context) {
        this.context = context;
        this.inflater = context.getLayoutInflater();
        listData = new ArrayList<>();
    }

    public void clearList() {
        listData.clear();
    }

    public void addList(List<ShopUnit> listData) {
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
        convertView = inflater.inflate(R.layout.adapter_m_shop, null);
        LinearLayout ll_start = (LinearLayout) convertView.findViewById(R.id.ll_start);
        initView(convertView, listData.get(position));
        initStart(ll_start, listData.get(position));
        return convertView;
    }

    private void initView(View convertView, ShopUnit shop) {
        ImageView iv_shop = (ImageView) convertView.findViewById(R.id.iv_photo);
        TextView tv_name = (TextView) convertView.findViewById(R.id.tv_shop_name);
        TextView tv_address = (TextView) convertView.findViewById(R.id.tv_shop_address);
        TextView tv_distance = (TextView) convertView.findViewById(R.id.tv_shop_distance);

        tv_name.setText(shop.getName());
        tv_address.setText(shop.getAddress());
        tv_distance.setText(shop.getDistance() + "km");
    }

    /***
     * 设置星星个数
     * @param ll_start
     */
    private void initStart(LinearLayout ll_start, ShopUnit shop) {
        boolean isHalf = false;
        int mInt = (int) shop.getStart();
        int mInt10 = (int) (shop.getStart() * 10);
        if (mInt * 10 < mInt10) {
            mInt += 1;
            isHalf = true;
        }
        for (int i = 0; i < mInt; i++) {
            ImageView iv = new ImageView(context);
            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            layoutParams.setMargins(5, 0, 0, 0);
            if (i == 0)
                layoutParams.setMargins(0, 0, 0, 0);
            iv.setLayoutParams(layoutParams);
            iv.setBackgroundResource(R.mipmap.icon_start_full);
            if (isHalf && i == mInt - 1)
                iv.setBackgroundResource(R.mipmap.icon_start_half);
            ll_start.addView(iv);
        }
        TextView tv = new TextView(context);
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        layoutParams.setMargins(5, 0, 0, 0);
        tv.setLayoutParams(layoutParams);
        tv.setText(shop.getStart() + "");
        tv.setTextColor(context.getResources().getColor(R.color.color_index_text));
        tv.setTextSize(12);
        ll_start.addView(tv);
    }
}
