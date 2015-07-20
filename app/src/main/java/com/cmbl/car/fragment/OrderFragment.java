package com.cmbl.car.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.cmbl.car.R;
import com.cmbl.car.adapter.OrderAdatper;
import com.cmbl.car.model.OrderUnit;
import com.witalk.widget.PullToRefreshView;

import java.util.ArrayList;
import java.util.List;

public class OrderFragment extends Fragment implements AdapterView.OnItemClickListener, PullToRefreshView.OnFooterRefreshListener, PullToRefreshView.OnHeaderRefreshListener {
    private PullToRefreshView pullToRefreshView;
    private ListView mListView;
    private List<OrderUnit> listData;
    private OrderAdatper adatper;

    // TODO: Rename and change types and number of parameters
    public static OrderFragment newInstance() {
        OrderFragment fragment = new OrderFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        listData = new ArrayList<>();
        adatper = new OrderAdatper(getActivity());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_order, container, false);
        pullToRefreshView = (PullToRefreshView) view.findViewById(R.id.pulltorefresh);
        mListView = (ListView) view.findViewById(R.id.listview);
        mListView.setOnItemClickListener(this);
        mListView.setAdapter(adatper);
        pullToRefreshView.setOnHeaderRefreshListener(this);
        pullToRefreshView.setOnFooterRefreshListener(this);
        return view;
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

    }

    @Override
    public void onHeaderRefresh(PullToRefreshView view) {
        pullToRefreshView.onHeaderRefreshComplete();
    }

    @Override
    public void onFooterRefresh(PullToRefreshView view) {
        pullToRefreshView.onFooterRefreshComplete();
    }
}
