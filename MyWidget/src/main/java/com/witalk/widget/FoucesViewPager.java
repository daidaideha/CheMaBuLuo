package com.witalk.widget;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;

/**
 * Created by Administrator on 2015/7/16 0016.
 */
public class FoucesViewPager extends ViewPager {
    public FoucesViewPager(Context context) {
        super(context);
    }

    public FoucesViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    float x, y;
    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
//        switch (ev.getAction()) {
//            case MotionEvent.ACTION_DOWN:
//                x = ev.getX();
//                y = ev.getY();
//                // onInterceptTouchEvent已经记录
//                // mLastMotionY = y;
//                break;
//            case MotionEvent.ACTION_MOVE:
//                break;
//            case MotionEvent.ACTION_UP:
//            case MotionEvent.ACTION_CANCEL:
//                Log.i("item", "y = " + y + " move y = " + ev.getY() + " diff y = " + Math.abs(y - ev.getY()));
//                if (Math.abs(x - ev.getX()) > 40)
                    getParent().requestDisallowInterceptTouchEvent(true);
//                else if (Math.abs(y - ev.getY()) < 400)
//                    getParent().requestDisallowInterceptTouchEvent(true);
//                else
//                    getParent().requestDisallowInterceptTouchEvent(false);
//                break;
//        }
        return super.dispatchTouchEvent(ev);
    }
}
