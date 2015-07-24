package com.cmbl.car.activity;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.widget.TextView;

import com.cmbl.car.R;
import com.witalk.widget.CMBLTools;
import com.witalk.widget.RoundProgressBar;
import com.witalk.widget.tranforms.ZoomOutSlideTransformer;

public class SplashActivity extends Activity {
    private RoundProgressBar progressBar3;
    private TextView tvSec3;
    private RoundProgressBar progressBar2;
    private TextView tvSec2;
    private RoundProgressBar progressBar;
    private TextView tvSec;

    private int i = 0, j = 0, z = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        progressBar = (RoundProgressBar) findViewById(R.id.roundProgressBar3);
        tvSec = (TextView) findViewById(R.id.tv_sec);

        progressBar2 = (RoundProgressBar) findViewById(R.id.roundProgressBar2);
        tvSec2 = (TextView) findViewById(R.id.tv_min);

        progressBar3 = (RoundProgressBar) findViewById(R.id.roundProgressBar);
        tvSec3 = (TextView) findViewById(R.id.tv_hour);


        new Thread(new Runnable() {
            @Override
            public void run() {
                while (true) {
                    if (i < 60) {
                        i++;
                        progressBar.setProgress(i);
                        myHandler.sendEmptyMessage(i);
                        if (i == 60) {
                            j ++;
                            progressBar2.setProgress(j);
                            myHandler2.sendEmptyMessage(j);
                            i = 0;
                        }
                        if (j == 60) {
                            z ++;
                            progressBar3.setProgress(z);
                            myHandler3.sendEmptyMessage(z);
                            j = 0;
                        }
                        if (z == 24)
                            z = 0;
                        try {
                            Thread.sleep(100);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        }).start();

//        finish();
//        CMBLTools.IntentToOther(this, HomePageActivity.class, null);
    }

    private Handler myHandler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message msg) {
            int i = msg.what;
            if (i == 60)
                i = 0;
            String text;
            if (i < 10)
                text = "0" + i;
            else
                text = i + "";
            tvSec.setText(text);
            return false;
        }
    });

    private Handler myHandler2 = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message msg) {
            int i = msg.what;
            if (i == 60)
                i = 0;
            String text;
            if (i < 10)
                text = "0" + i;
            else
                text = i + "";
            tvSec2.setText(text);
            return false;
        }
    });

    private Handler myHandler3 = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message msg) {
            int i = msg.what;
            if (i == 24)
                i = 0;
            String text;
            if (i < 10)
                text = "0" + i;
            else
                text = i + "";
            tvSec3.setText(text);
            return false;
        }
    });


}
