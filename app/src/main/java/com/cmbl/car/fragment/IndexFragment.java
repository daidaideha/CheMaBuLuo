package com.cmbl.car.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.cmbl.car.R;
import com.cmbl.car.activity.MapActivity;
import com.cmbl.car.activity.SearchActivity;
import com.cmbl.car.activity.ShopListActivity;
import com.cmbl.car.activity.person.AddNewCar;
import com.cmbl.car.adapter.ShopAdatper;
import com.cmbl.car.model.ShopUnit;
import com.cmbl.car.widget.LocationManager;
import com.witalk.widget.CMBLTools;
import com.witalk.widget.FoucesViewPager;
import com.witalk.widget.PullToRefreshView;
import com.witalk.widget.VPAutoScrollManager;

import java.util.ArrayList;
import java.util.List;

public class IndexFragment extends Fragment implements ViewPager.OnPageChangeListener, View.OnTouchListener, PullToRefreshView.OnHeaderRefreshListener, View.OnClickListener, LocationManager.ReceiveLocationListener {
    private PullToRefreshView pullToRefreshView;
    private FoucesViewPager mViewPager;
    private ListView mListView;
    private LinearLayout layoutVPBottom;
    private TextView tvLocation;

    private List<ImageView> listBottomView;
    private List<ImageView> listVP;
    private List<ShopUnit> listShop;
    private int[] picRes = new int[] {R.mipmap.banner, R.mipmap.banner2, R.mipmap.banner3};
    /***
     * 当前页数
     */
    private int pageIndex = 0;
    /***
     * 触摸点x轴坐标
     */
    private float xDown;

    private VPAutoScrollManager vpAutoScrollManager;
    private LocationManager mLocationManager;

    // TODO: Rename and change types and number of parameters
    public static IndexFragment newInstance() {
        IndexFragment fragment = new IndexFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mLocationManager = new LocationManager(getActivity());
        mLocationManager.setReceiveLocationListener(this);
        mLocationManager.initLocation(false);
        listBottomView = new ArrayList<>();
        listVP = new ArrayList<>();
        for (int i = 0; i < picRes.length; i++) {
            ImageView iv = new ImageView(getActivity());
            iv.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.MATCH_PARENT));
            iv.setScaleType(ImageView.ScaleType.FIT_XY);
            iv.setBackgroundResource(picRes[i]);
            listVP.add(iv);
        }
        initData();
    }

    private void initData() {
        listShop = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            ShopUnit shop = new ShopUnit();
            shop.setStart(i + 0.5f);
            shop.setName("杭州百得利 " + i);
            shop.setAddress("余杭区文一西路960号 ------" + i);
            shop.setDistance(i);
            listShop.add(shop);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_index, container, false);

        initPullToRefresh(view);
        initListView(view);
        initModel(view);
        return view;
    }

    private void initModel(View view) {
        view.findViewById(R.id.rl_appointment).setOnClickListener(this);
        view.findViewById(R.id.rl_drive).setOnClickListener(this);
        view.findViewById(R.id.rl_buy).setOnClickListener(this);
    }

    private void initPullToRefresh(View view) {
        pullToRefreshView = (PullToRefreshView) view.findViewById(R.id.pulltorefresh);
        pullToRefreshView.setOnHeaderRefreshListener(this);
        pullToRefreshView.setEnablePullLoadMoreDataStatus(false);
    }

    private void initViewPager(View view) {
        mViewPager = (FoucesViewPager) view.findViewById(R.id.viewpager);
        mViewPager.setAdapter(new PagerAdapter() {
            @Override
            public int getCount() {
                return picRes.length;
            }

            @Override
            public boolean isViewFromObject(View view, Object object) {
                return view == object;
            }

            @Override
            public Object instantiateItem(ViewGroup container, int position) {
                ((ViewPager) container).addView(listVP.get(position));
                return listVP.get(position);
            }

            @Override
            public void destroyItem(ViewGroup container, int position, Object object) {
                ((ViewPager) container).removeView((View) object);
            }
        });
        mViewPager.setOnTouchListener(this);
        mViewPager.addOnPageChangeListener(this);
        mViewPager.setCurrentItem(0);
        if (vpAutoScrollManager == null)
            vpAutoScrollManager = new VPAutoScrollManager(mViewPager);
    }

    private void initVPBottom(View view) {
        layoutVPBottom = (LinearLayout) view.findViewById(R.id.layout_vp_bottom);
        for (int i = 0; i < mViewPager.getAdapter().getCount(); i++) {
            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT);
            layoutParams.setMargins(5, 0, 0, 0);
            ImageView iv = new ImageView(getActivity());
            iv.setBackgroundResource(R.mipmap.icon_banner_narmol);
            if (i == 0) {
                iv.setBackgroundResource(R.mipmap.icon_banner_fouces);
                layoutParams.setMargins(0, 0, 0, 0);
            }
            iv.setLayoutParams(layoutParams);
            layoutVPBottom.addView(iv);
            listBottomView.add(iv);
        }
    }

    private void initListView(View view) {
        mListView = (ListView) view.findViewById(R.id.listview);
        View headerView = getActivity().getLayoutInflater().inflate(R.layout.view_index_header, null);
        initViewPager(headerView);
        initVPBottom(headerView);
        tvLocation = (TextView) headerView.findViewById(R.id.tv_location);
        headerView.findViewById(R.id.rl_search).setOnClickListener(this);
        headerView.findViewById(R.id.tv_more).setOnClickListener(this);
        headerView.findViewById(R.id.rl_header_location).setOnClickListener(this);
        mListView.addHeaderView(headerView);
        ShopAdatper adatper = new ShopAdatper(getActivity());
        adatper.addList(listShop);
        adatper.notifyDataSetChanged();
        mListView.setAdapter(adatper);
    }

    @Override
    public void onPause() {
        if (mLocationManager != null)
            mLocationManager.onPause();
        super.onPause();
    }

    @Override
    public void onResume() {
        if (mLocationManager != null)
            mLocationManager.onResume();
        super.onResume();
    }

    @Override
    public void onStart() {
        super.onStart();
        vpAutoScrollManager.startScroll();
    }

    @Override
    public void onStop() {
        super.onStop();
        vpAutoScrollManager.stopScroll();
    }

    @Override
    public void onDestroy() {
        if (mLocationManager != null)
            mLocationManager.onDestroy();
        super.onDestroy();
        if (vpAutoScrollManager != null)
            vpAutoScrollManager = null;
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
    }

    @Override
    public void onPageSelected(int position) {
        pageIndex = position;
        for (int i = 0; i < listBottomView.size(); i++) {
            if (i == position)
                listBottomView.get(i).setBackgroundResource(R.mipmap.icon_banner_fouces);
            else
                listBottomView.get(i).setBackgroundResource(R.mipmap.icon_banner_narmol);
        }
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                vpAutoScrollManager.stopScroll();
                xDown = event.getX();
                // onInterceptTouchEvent已经记录
                // mLastMotionY = y;
                break;
            case MotionEvent.ACTION_MOVE:
                break;
            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_CANCEL:
                if (xDown - event.getX() > 40 && pageIndex == mViewPager.getAdapter().getCount() - 1) {
                    mViewPager.setCurrentItem(0);
                } else if (event.getX() - xDown > 40 && pageIndex == 0) {
                    mViewPager.setCurrentItem(mViewPager.getAdapter().getCount() - 1);
                } else if (xDown - event.getX() > 0) {
                    mViewPager.setCurrentItem(mViewPager.getCurrentItem() + 1);
                } else if (xDown - event.getX() < 0) {
                    mViewPager.setCurrentItem(mViewPager.getCurrentItem() - 1);
                }
                vpAutoScrollManager.startScroll();
                break;
        }
        return true;
    }

    @Override
    public void onHeaderRefresh(PullToRefreshView view) {

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.rl_appointment:
                Bundle bundle = new Bundle();
                bundle.putInt("type", 1);
                CMBLTools.IntentToOther(getActivity(), AddNewCar.class, bundle);
                break;
            case R.id.rl_drive:
                break;
            case R.id.rl_buy:
                break;
            case R.id.rl_search:
                CMBLTools.IntentToOther(getActivity(), SearchActivity.class, null);
                break;
            case R.id.tv_more:
                CMBLTools.IntentToOther(getActivity(), ShopListActivity.class, null);
                break;
            case R.id.rl_header_location:
                CMBLTools.IntentToOther(getActivity(), MapActivity.class, null);
                break;
        }
    }

    @Override
    public void onReceiveLocation() {
        String country = mLocationManager.getDistrict();
        if (!CMBLTools.isValueEmpty(country))
            tvLocation.setText(country);
        mLocationManager.onDestroy();
    }
}
