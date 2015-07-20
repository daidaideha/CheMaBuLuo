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
import android.widget.SectionIndexer;
import android.widget.TextView;

import com.cmbl.car.R;
import com.cmbl.car.model.CarTypeUnit;
import com.cmbl.car.model.ShopUnit;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2015/7/15 0015.
 */
public class CarTypeAdatper extends BaseAdapter implements SectionIndexer {
    private Context context;
    private LayoutInflater inflater;
    private List<CarTypeUnit> listData;

    public CarTypeAdatper(Activity context) {
        this.context = context;
        this.inflater = context.getLayoutInflater();
        listData = new ArrayList<>();
    }

    public void clearList() {
        listData.clear();
    }

    public void addList(List<CarTypeUnit> listData) {
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
		String nickName_abc = listData.get(position).getPinyin().toUpperCase();
        convertView = inflater.inflate(R.layout.adapter_car_type, null);

        View line = convertView.findViewById(R.id.line);
        ImageView iv_car = (ImageView) convertView.findViewById(R.id.iv_car);
        TextView tv_name = (TextView) convertView.findViewById(R.id.tv_car_type);
        TextView tv_catalog = (TextView) convertView.findViewById(R.id.tv_catalog);
        TextView tv_code = (TextView) convertView.findViewById(R.id.tv_code);

        tv_name.setText(listData.get(position).getName());
        tv_catalog.setText(nickName_abc);
        tv_code.setText(listData.get(position).getCode() + "");
		if (position == 0) {
			tv_catalog.setVisibility(View.VISIBLE);
			line.setVisibility(View.GONE);
			tv_catalog.setText(nickName_abc);
		} else {
			String catalog = listData.get(position - 1).getPinyin().toUpperCase();
			if (nickName_abc.equals(catalog)) {
				tv_catalog.setVisibility(View.GONE);
				line.setVisibility(View.VISIBLE);
			} else {
				tv_catalog.setVisibility(View.VISIBLE);
				line.setVisibility(View.GONE);
				tv_catalog.setText(nickName_abc);
			}
		}
        return convertView;
    }

	@Override
	public int getPositionForSection(int section) {
		// TODO Auto-generated method stub
		for (int i = 0; i < listData.size(); i++) {
			String l = listData.get(i).getPinyin().toUpperCase();
			char firstChar = l.charAt(0);
			if (firstChar == section) {
				return i;
			}
		}
		return -1;
	}

	@Override
	public int getSectionForPosition(int arg0) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public Object[] getSections() {
		// TODO Auto-generated method stub
		return null;
	}
}
