package com.cmbl.car.activity;

import android.os.Build;
import android.os.Bundle;
import android.os.Message;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.cmbl.car.R;
import com.cmbl.car.base.BaseActivity;
import com.cmbl.car.base.FinalValue;
import com.cmbl.car.fragment.IndexFragment;
import com.cmbl.car.fragment.MyFragment;
import com.cmbl.car.fragment.OrderFragment;
import com.witalk.widget.CMBLTools;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

/***
 * 程序主页面
 */
public class HomePageActivity extends FragmentActivity implements View.OnClickListener {
    private TextView tvTitle;
    private RelativeLayout rl_header;
    private RelativeLayout layout_main;
    /***
     * 首页页面
     */
    private IndexFragment indexFragment;
    /***
     * 订单页面
     */
    private OrderFragment orderFragment;
    /***
     * 我的页面
     */
    private MyFragment myFragment;
    /***
     * Fragment管理器
     */
    private FragmentManager fragmentManager;
    /***
     * 底部按钮列表
     */
    private List<TextView> listBottom;
    /***
     * 第二次按退出的时间
     */
    private long time = 0;
    private int[] narmolPic = {R.mipmap.icon_homepage_home_narmol, R.mipmap.icon_homepage_order_narmol, R.mipmap.icon_homepage_my_narmol};
    private int[] foucesPic = {R.mipmap.icon_homepage_home_fouces, R.mipmap.icon_homepage_order_fouces, R.mipmap.icon_homepage_my_fouces};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            Window window = getWindow();
            // Translucent status bar
            window.setFlags(
                    WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS,
                    WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
//			// Translucent navigation bar
//			window.setFlags(
//					WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION,
//					WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
        }

        layout_main = (RelativeLayout) findViewById(R.id.layout_main);

        fragmentManager = getSupportFragmentManager();
        listBottom = new ArrayList<>();

        initHeader();
        initBodyer();
    }

    protected void initHeader() {
        rl_header = (RelativeLayout) findViewById(R.id.rl_header);
        tvTitle = (TextView) findViewById(R.id.tv_header_title);
    }

    /***
     * 显示不同类型的头部标题栏
     */
    private void setHeaderStyle(int HeaderStyle) {
        switch (HeaderStyle) {
            case FinalValue.HEADER_STYLE_TITLE:
                rl_header.setVisibility(View.VISIBLE);
                break;
            case FinalValue.HEADER_STYLE_SEARCH:
                rl_header.setVisibility(View.GONE);
                break;
            default:
                break;
        }
    }

    protected void initBodyer() {
        listBottom.add((TextView) findViewById(R.id.tv_homepage_index));
        listBottom.add((TextView) findViewById(R.id.tv_homepage_order));
        listBottom.add((TextView) findViewById(R.id.tv_homepage_my));

        for(TextView tv: listBottom) {
            tv.setOnClickListener(this);
        }

        listBottom.get(0).performClick();
    }

    @Override
    public void onClick(View v) {
        seTitle(v.getId());
        setTabSelection(v.getId());
        for (int i = 0; i < narmolPic.length; i ++) {
            TextView tv = listBottom.get(i);
            if (v.getId() == tv.getId()) {
                tv.setCompoundDrawablesWithIntrinsicBounds(0, foucesPic[i], 0, 0);
                tv.setTextColor(getResources().getColor(R.color.color_bottom_fouces));
            } else {
                tv.setCompoundDrawablesWithIntrinsicBounds(0, narmolPic[i], 0, 0);
                tv.setTextColor(getResources().getColor(R.color.color_bottom_normal));
            }
        }
    }

    /***
     * 底部菜单选中后操作
     * @author Pro.Linyl
     * @CreateTime 2015.07.13
     * @UpdateAuthor
     * @UpdateTime
     * @description
     *
     * @param id 控件id
     */
    private void setTabSelection(int id) {
        // TODO Auto-generated method stub
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        switch (id) {
            case R.id.tv_homepage_index:// 首页
                layout_main.setBackgroundResource(R.mipmap.bg_index_homepage);
                if (indexFragment == null)
                    indexFragment = IndexFragment.newInstance();
                transaction.replace(R.id.layout_bodyer, indexFragment);
                setHeaderStyle(FinalValue.HEADER_STYLE_SEARCH);
                break;
            case R.id.tv_homepage_order:// 订单
                layout_main.setBackgroundColor(getResources().getColor(R.color.blue));
                if (orderFragment == null)
                    orderFragment = OrderFragment.newInstance();
                transaction.replace(R.id.layout_bodyer, orderFragment);
                setHeaderStyle(FinalValue.HEADER_STYLE_TITLE);
                break;
            case R.id.tv_homepage_my:// 我
                layout_main.setBackgroundColor(getResources().getColor(R.color.blue));
                if (myFragment == null)
                    myFragment = MyFragment.newInstance();
                transaction.replace(R.id.layout_bodyer, myFragment);
                setHeaderStyle(FinalValue.HEADER_STYLE_TITLE);
                break;
            default:
                break;
        }
        transaction.commit();
    }

    /***
     * 标题改变操作
     * @author Pro.Linyl
     * @CreateTime 2015.07.13
     * @UpdateAuthor
     * @UpdateTime
     * @description
     *
     * @param id 控件id
     */
    private void seTitle(int id) {
        // TODO Auto-generated method stub
        String title = "";
        switch (id) {
            case R.id.tv_homepage_index:
                title = getString(R.string.tv_homepage_bottom_index);
                break;
            case R.id.tv_homepage_order:
                title = getString(R.string.tv_homepage_bottom_order);
                break;
            case R.id.tv_homepage_my:
                title = getString(R.string.tv_homepage_bottom_my);
                break;
            default:
                break;
        }
        tvTitle.setText(title);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0
                && event.getAction() == KeyEvent.ACTION_DOWN) {
            int LENG_EXIT = 1500;
            if (System.currentTimeMillis() - time > LENG_EXIT) {
                Toast.makeText(this, getResources().getString(R.string.toast_double_exit),
                        Toast.LENGTH_LONG).show();
                time = System.currentTimeMillis();
            } else {
                System.exit(0);
            }
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
}
