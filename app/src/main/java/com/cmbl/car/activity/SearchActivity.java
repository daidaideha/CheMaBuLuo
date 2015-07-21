package com.cmbl.car.activity;

import android.os.Bundle;
import android.os.Message;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.cmbl.car.R;
import com.cmbl.car.adapter.ShopAdatper;
import com.cmbl.car.base.BaseActivity;
import com.cmbl.car.base.FinalValue;
import com.cmbl.car.model.ShopUnit;
import com.witalk.widget.CMBLTools;
import com.witalk.widget.PullToRefreshView;
import com.witalk.widget.SPreferences;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2015/7/20 0020.
 */
public class SearchActivity extends BaseActivity implements View.OnClickListener, TextWatcher, PullToRefreshView.OnFooterRefreshListener, TextView.OnEditorActionListener {
    private EditText mEditText;
    private RelativeLayout mDelLayout;
    private PullToRefreshView mPullToRefreshView;
    private ListView mListView;

    private List<ShopUnit> listShop;
    private String strHistroy;
    private String[] searchHistroy;
    private ShopAdatper adatper;
    private SPreferences mSPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        mSPreferences = new SPreferences(this);
        listShop = new ArrayList<>();
        adatper = new ShopAdatper(this);

        initSearch();
        initHeader();
        initBodyer();
    }

    private void initSearch() {
        strHistroy = mSPreferences.getSp().getString("histroy", "");
        if (!CMBLTools.isValueEmpty(strHistroy) && strHistroy.contains(","))
            searchHistroy = strHistroy.split(",");
        System.out.println("histroy: " + strHistroy);
    }

    private void initData(int size) {
        listShop.clear();
        for (int i = 0; i < size; i++) {
            ShopUnit shop = new ShopUnit();
            shop.setStart(i % 5 + 0.5f);
            shop.setName("杭州百得利 " + i);
            shop.setAddress("余杭区文一西路960号 ------" + i);
            shop.setDistance(i);
            listShop.add(shop);
        }
        adatper.clearList();
        adatper.addList(listShop);
        adatper.notifyDataSetChanged();
    }

    @Override
    protected void initHeader() {
        mEditText = (EditText) findViewById(R.id.edt_search);
        mDelLayout = (RelativeLayout) findViewById(R.id.rl_del);

        mEditText.addTextChangedListener(this);
        mDelLayout.setOnClickListener(this);
        findViewById(R.id.tv_cancel).setOnClickListener(this);
        mEditText.setOnEditorActionListener(this);
    }

    @Override
    protected void initBodyer() {
        mPullToRefreshView = (PullToRefreshView) findViewById(R.id.pulltorefresh);
        mListView = (ListView) findViewById(R.id.listview);

        mListView.setAdapter(adatper);

        mPullToRefreshView.setEnablePullTorefresh(false);
        mPullToRefreshView.setOnFooterRefreshListener(this);
    }

    @Override
    protected void CallBackListener(Message msg) {

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_cancel:
                finish();
                break;
            case R.id.rl_del:
                mEditText.setText("");
                initData(0);
                break;
        }
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
        if (CMBLTools.isValueEmpty(mEditText.getText().toString().trim())) {
            mDelLayout.setVisibility(View.GONE);
        }  else {
            mDelLayout.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void afterTextChanged(Editable s) {

    }

    @Override
    public void onFooterRefresh(PullToRefreshView view) {

    }

    @Override
    public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
        if(actionId == EditorInfo.IME_ACTION_SEARCH){
            initData(mEditText.getText().toString().trim().length());
            if (searchHistroy != null) {
                strHistroy = "";
                int max = searchHistroy.length >= FinalValue.SEARCH_HISTROY_SIZE?FinalValue.SEARCH_HISTROY_SIZE:searchHistroy.length;
                System.out.println("max: " + max);
                for (int i = 0; i < max; i++)
                    strHistroy += (searchHistroy[i] + ",");
                strHistroy = mEditText.getText().toString().trim() + "," + strHistroy;
            } else {
                strHistroy = mEditText.getText().toString().trim() + ",";
            }

            mSPreferences.updateSp("histroy", strHistroy);
            initSearch();
            return true;
        }
        return false;
    }
}
