package com.cmbl.car.activity;

import android.os.Bundle;
import android.os.Message;

import com.baidu.mapapi.map.MapView;
import com.cmbl.car.R;
import com.cmbl.car.base.BaseActivity;
import com.cmbl.car.widget.LocationManager;


/**
 * Created by Administrator on 2015/7/22 0022.
 */
public class MapActivity extends BaseActivity implements LocationManager.ReceiveLocationListener {
    private MapView mMapView;
    private LocationManager locationManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setMyContextView(R.layout.activity_map);

        initBodyer();
    }

    @Override
    protected void onPause() {
        if (locationManager != null)
            locationManager.onPause();
        super.onPause();
    }

    @Override
    protected void onResume() {
        if (locationManager != null)
            locationManager.onResume();
        super.onResume();
    }

    @Override
    protected void onDestroy() {
        if (locationManager != null)
            locationManager.onDestroy();
        super.onDestroy();
    }

    @Override
    protected void initHeader() {

    }

    @Override
    protected void initBodyer() {
        mMapView = (MapView) findViewById(R.id.mapview);

        locationManager = new LocationManager(mMapView, this);
        locationManager.initLocation(false);
        locationManager.setReceiveLocationListener(this);
    }

    @Override
    protected void CallBackListener(Message msg) {

    }

    @Override
    public void onReceiveLocation() {

    }
}
