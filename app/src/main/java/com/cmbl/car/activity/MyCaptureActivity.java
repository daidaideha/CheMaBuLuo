package com.cmbl.car.activity;

import android.os.Bundle;

import com.zbar.lib.CaptureActivity;
import com.witalk.widget.CMBLTools;

public class MyCaptureActivity extends CaptureActivity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void handleDecode(String result) {
        super.handleDecode(result);
        CMBLTools.toastMsg(result, this);
    }
}
