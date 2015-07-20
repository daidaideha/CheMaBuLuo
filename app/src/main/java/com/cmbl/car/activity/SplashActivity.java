package com.cmbl.car.activity;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;

import com.cmbl.car.R;
import com.witalk.widget.CMBLTools;

public class SplashActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        finish();
        CMBLTools.IntentToOther(this, HomePageActivity.class, null);
    }
}
