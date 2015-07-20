package com.witalk.widget;

import android.os.Handler;
import android.support.v4.view.ViewPager;
import android.util.Log;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * Created by Administrator on 2015/7/13 0013.
 */
public class VPAutoScrollManager {
    /***
     * 轮播图当前选中项
     */
    private int currPgae = 0;
    /***
     * 计时服务
     */
    private ScheduledExecutorService scheduledExecutorService;
    /***
     * 轮播图自动滚动线程
     */
    private ScrollTask scrollTask;
    private ViewPager mViewPager;

    public VPAutoScrollManager(ViewPager viewPager) {
        this.mViewPager = viewPager;
    }


    /***
     * 启动滚动线程
     */
    public void startScroll() {
        if (mViewPager.getAdapter().getCount() > 1) {
            if (scheduledExecutorService != null) {
                scheduledExecutorService = null;
            }
            if (scrollTask == null) {
                scrollTask = new ScrollTask();
            }
            scheduledExecutorService = Executors
                    .newSingleThreadScheduledExecutor();
            scheduledExecutorService.scheduleAtFixedRate(scrollTask, 1, 5,
                    TimeUnit.SECONDS);
//            Log.i("item", "item create service");
        }
    }

    /***
     * 停止滚动线程
     */
    public void stopScroll() {
        if (scheduledExecutorService != null) {
            scheduledExecutorService.shutdown();
            scheduledExecutorService = null;
        }
        if (scrollTask != null) {
            scrollTask = null;
        }
    }

    /***
     * 更新轮播图
     */
    private Handler handler = new Handler() {
        public void handleMessage(android.os.Message msg) {
            mViewPager.setCurrentItem(currPgae);
//            Log.i("item", "set item = " + currPgae);
        };
    };

    /***
     * 轮播图自动滚动线程
     */
    private class ScrollTask implements Runnable {
        public void run() {
//            Log.i("item", "item outside");
            currPgae = (mViewPager.getCurrentItem() + 1) % mViewPager.getAdapter().getCount();
//            Log.i("item", "get item = " + currPgae);
            handler.obtainMessage().sendToTarget();
        }
    }
}
