package com.cmbl.car.widget;

import android.app.Activity;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.MapStatusUpdate;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.MyLocationData;
import com.baidu.mapapi.model.LatLng;

/**
 * 百度地图定位管理类
 * Created by Administrator on 2015/7/23 0023.
 */
public class LocationManager {
    private Activity activity;
    private MapView mMapView;
    private BaiduMap mBaiduMap;
    private ReceiveLocationListener mReceiveLocationListener;

    // 定位相关
    private LocationClient mLocClient;
    public MyLocationListenner myListener = new MyLocationListenner();
    private boolean isFirstLoc = true;// 是否首次定位

    /***
     * 位置：省
     */
    private String province;
    /***
     * 位置：市
     */
    private String city;
    /***
     * 位置：县
     */
    private String district;
    /***
     * 位置：街道
     */
    private String street;
    /***
     * 位置：门牌号码
     */
    private String streetNumber;
    /***
     * 位置：楼层
     */
    private String floor;
    /***
     * 位置：地址
     */
    private String address;
    /***
     * 纬度
     */
    private double latitude;
    /***
     * 经度
     */
    private double longitude;

    public LocationManager(MapView mMapView, Activity activity) {
        this.activity = activity;
        this.mMapView = mMapView;
        this.mBaiduMap = mMapView.getMap();
        this.mBaiduMap.setMyLocationEnabled(true);
    }

    public LocationManager(Activity activity) {
        this.activity = activity;
    }

    public void setReceiveLocationListener(ReceiveLocationListener mReceiveLocationListener) {
        this.mReceiveLocationListener = mReceiveLocationListener;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getDistrict() {
        return district;
    }

    public void setDistrict(String district) {
        this.district = district;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getStreetNumber() {
        return streetNumber;
    }

    public void setStreetNumber(String streetNumber) {
        this.streetNumber = streetNumber;
    }

    public String getFloor() {
        return floor;
    }

    public void setFloor(String floor) {
        this.floor = floor;
    }

    /***
     * 初始化定位
     * @param isReplay 是否循环定位
     */
    public void initLocation(boolean isReplay) {
        // 定位初始化
        mLocClient = new LocationClient(activity);
        mLocClient.registerLocationListener(myListener);
        LocationClientOption option = new LocationClientOption();
        option.setOpenGps(true);// 打开gps
        option.setCoorType("bd09ll"); // 设置坐标类型
        option.setIsNeedAddress(true);
        if (isReplay)
            option.setScanSpan(1000);
        mLocClient.setLocOption(option);
        mLocClient.start();
    }

    /**
     * 定位SDK监听函数
     */
    public class MyLocationListenner implements BDLocationListener {

        @Override
        public void onReceiveLocation(BDLocation location) {
            // map view 销毁后不在处理新接收的位置
            if (location == null)
                return;
            setProvince(location.getProvince());
            setCity(location.getCity());
            setDistrict(location.getDistrict());
            setStreet(location.getStreet());
            setStreetNumber(location.getStreetNumber());
            setFloor(location.getFloor());
            setAddress(location.getAddrStr());
            setLatitude(location.getLatitude());
            setLongitude(location.getLongitude());
            if (mMapView != null) {
                MyLocationData locData = new MyLocationData.Builder()
                        .accuracy(location.getRadius())
                                // 此处设置开发者获取到的方向信息，顺时针0-360
                        .direction(100).latitude(location.getLatitude())
                        .longitude(location.getLongitude()).build();
                mBaiduMap.setMyLocationData(locData);
                if (isFirstLoc) {
                    isFirstLoc = false;
                    LatLng ll = new LatLng(location.getLatitude(), location.getLongitude());
                    MapStatusUpdate u = MapStatusUpdateFactory.newLatLng(ll);
                    mBaiduMap.animateMapStatus(u);
                }
            }
            if (mReceiveLocationListener != null)
                mReceiveLocationListener.onReceiveLocation();
        }

        public void onReceivePoi(BDLocation poiLocation) {
        }
    }

    public void onPause() {
        if (mMapView != null)
            mMapView.onPause();
    }

    public void onResume() {
        if (mMapView != null)
            mMapView.onResume();
    }

    public void onDestroy() {
        if (mMapView != null) {
            // 关闭定位图层
            mBaiduMap.setMyLocationEnabled(false);
            mMapView.onDestroy();
            mMapView = null;
        }
        if (mLocClient != null) {
            // 退出时销毁定位
            mLocClient.stop();
        }
    }

    /***
     * 定位返回监听
     */
    public interface ReceiveLocationListener {
        /***
         * 定位返回操作
         */
        void onReceiveLocation();
    }
}
