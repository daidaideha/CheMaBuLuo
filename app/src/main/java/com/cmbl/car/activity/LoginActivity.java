package com.cmbl.car.activity;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.cmbl.car.R;
import com.cmbl.car.base.BaseActivity;
import com.witalk.widget.CMBLTools;

import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by Administrator on 2015/7/17 0017.
 */
public class LoginActivity extends BaseActivity implements View.OnClickListener, TextWatcher {
    private EditText edt_phone;
    private EditText edt_code;
    private Button btn_getcode;
    private Button btn_login;
    private TextView tv_agree;

    private boolean isAgree = true;
    private boolean isLoginClickAble = false;
    private int minute = 60;
    private Timer timer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        initHeader();
        initBodyer();
    }

    @Override
    protected void initHeader() {
        findViewById(R.id.rl_close).setOnClickListener(this);
    }

    @Override
    protected void initBodyer() {
        edt_phone = (EditText) findViewById(R.id.edt_login_name);
        edt_code = (EditText) findViewById(R.id.edt_code);
        btn_getcode = (Button) findViewById(R.id.btn_getcode);
        btn_login = (Button) findViewById(R.id.btn_login);
        tv_agree = (TextView) findViewById(R.id.tv_agree);

        btn_getcode.setOnClickListener(this);
        tv_agree.setOnClickListener(this);
        btn_login.setOnClickListener(this);
        findViewById(R.id.tv_agreement).setOnClickListener(this);

        edt_phone.addTextChangedListener(this);
        edt_code.addTextChangedListener(this);
        btn_login.setBackgroundResource(R.drawable.round_unblue);
        btn_login.setClickable(isLoginClickAble);
    }

    private void checkLoginAble() {
        if (!CMBLTools.isValueEmpty(edt_code.getText().toString().trim()) &&
                !CMBLTools.isValueEmpty(edt_phone.getText().toString().trim())) {
            btn_login.setBackgroundResource(R.drawable.round_blue);
            isLoginClickAble = true;
        } else {
            btn_login.setBackgroundResource(R.drawable.round_unblue);
            isLoginClickAble = false;
        }
        btn_login.setClickable(isLoginClickAble);
    }

    @Override
    protected void CallBackListener(Message msg) {
        initTimer();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (timer != null)
            timer.cancel();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.rl_close:
                finish();
                break;
            case R.id.btn_login:
                CMBLTools.toastMsg("lgoin", this);
                break;
            case R.id.btn_getcode:
                break;
            case R.id.tv_agree:
                isAgree = !isAgree;
                if (isAgree)
                    tv_agree.setCompoundDrawablesWithIntrinsicBounds(R.mipmap.icon_agree, 0, 0, 0);
                else
                    tv_agree.setCompoundDrawablesWithIntrinsicBounds(R.mipmap.icon_disagree, 0, 0, 0);
                break;
            case R.id.tv_agreement:
                CMBLTools.IntentToOther(this, WebViewActivity.class, null);
                break;
            default:
                break;
        }
    }

    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg.what == 1) {
                btn_getcode.setText(minute + "");
                btn_getcode.setClickable(false);
            } else {
                btn_getcode.setText(getString(R.string.btn_getcode));
                btn_getcode.setClickable(true);
                timer.cancel();
            }
        }
    };

    private void initTimer() {
        timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                Message msg = handler.obtainMessage();
                if (minute > 1) {
                    minute--;
                    msg.what = 1;
                } else {
                    msg.what = 0;
                }
                handler.sendMessage(msg);
            }
        }, 0, 1000);
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
        checkLoginAble();
    }

    @Override
    public void afterTextChanged(Editable s) {

    }
}
