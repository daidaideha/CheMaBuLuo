package com.cmbl.car.activity;

import android.os.Bundle;
import android.os.Message;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.RelativeLayout;

import com.cmbl.car.R;
import com.cmbl.car.base.BaseActivity;
import com.witalk.widget.CMBLTools;

/**
 * Created by Administrator on 2015/7/20 0020.
 */
public class SearchActivity extends BaseActivity implements View.OnClickListener, TextWatcher {
    private EditText mEditText;
    private RelativeLayout mDelLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        initHeader();
        initBodyer();
    }

    @Override
    protected void initHeader() {
        mEditText = (EditText) findViewById(R.id.edt_search);
        mDelLayout = (RelativeLayout) findViewById(R.id.rl_del);

        mEditText.addTextChangedListener(this);
        mDelLayout.setOnClickListener(this);
        findViewById(R.id.tv_cancel).setOnClickListener(this);
    }

    @Override
    protected void initBodyer() {

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
                break;
        }
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
        if (CMBLTools.isValueEmpty(mEditText.getText().toString().trim()))
            mDelLayout.setVisibility(View.GONE);
        else
            mDelLayout.setVisibility(View.VISIBLE);
    }

    @Override
    public void afterTextChanged(Editable s) {

    }
}
