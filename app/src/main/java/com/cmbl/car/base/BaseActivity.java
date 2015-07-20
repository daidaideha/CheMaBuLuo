package com.cmbl.car.base;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Handler.Callback;
import android.os.Message;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout.LayoutParams;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.cmbl.car.R;
import com.witalk.httpthread.HttpBodyBase;
import com.witalk.httpthread.HttpThread;
import com.witalk.httpthread.volley.data.RequestManager;

/**
 * <一句话功能简述> 模板Activity <功能详细描述> 1、统一定制头部控件的操作 2、设置数据加载过渡界面显示隐藏 3、设置数据加载失败界面显示隐藏
 * 4、设置数据加载成功后界面显示隐藏
 */
public abstract class BaseActivity extends FragmentActivity {
	/***
	 * 内容布局控件
	 */
	protected RelativeLayout rl_bodyer;
	/***
	 * 正在加载显示控件
	 */
	protected TextView tv_loading;
	/***
	 * 加载失败显示控件
	 */
	protected TextView tv_fialed;

	/***
	 * 请求等待提示框
	 */
	private ProgressDialog mDialog;
	
	/***
	 * http请求返回处理handler
	 */
	protected Handler handler;

	/*
	 * (non-Javadoc)
	 * 
	 * @see android.app.Activity#onCreate(android.os.Bundle)
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_base);
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

		rl_bodyer = (RelativeLayout) findViewById(R.id.rl_bodyer);
		tv_loading = (TextView) findViewById(R.id.tv_loading);
		tv_fialed = (TextView) findViewById(R.id.tv_failed);

		setOnHeaderLeftClickListener(null);
		
		handler = new Handler(new Callback() {
			
			@Override
			public boolean handleMessage(Message msg) {
				// TODO Auto-generated method stub
				CallBackListener(msg);
				return false;
			}
		});
	}
	/*
	 * (non-Javadoc)
	 * 
	 * @see android.app.Activity#onResume()
	 */
	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
        // onresume时，取消notification显示
	}

	@Override
	protected void onStop() {
		super.onStop();
		RequestManager.cancelAll(this);
	}
	
	/***
	 * 初始化头部控件
	 * @author Pro.Linyl
	 * @CreateTime 2015年6月24日
	 * @UpdateAuthor 
	 * @UpdateTime	
	 * @description
	 *
	 */
	protected abstract void initHeader();
	/***
	 * 初始化布局控件
	 * @author Pro.Linyl
	 * @CreateTime 2015年6月24日
	 * @UpdateAuthor 
	 * @UpdateTime	
	 * @description
	 *
	 */
	protected abstract void initBodyer();
	/***
	 * http请求返回监听
	 * @author Pro.Linyl
	 * @CreateTime 2015年6月24日
	 * @UpdateAuthor 
	 * @UpdateTime	
	 * @description
	 *
	 * @param msg
	 */
	protected abstract void CallBackListener(Message msg);

	/***
	 * 执行http请求操作
	 * @author Pro.Linyl
	 * @CreateTime 2015年6月8日
	 * @UpdateAuthor Pro.Linyl
	 * @UpdateTime	2015年6月8日
	 * @description
	 *
	 * @param bodyBase http请求消息类
	 * @param msg 请求对话框显示内容
	 */
	public void executeRequest(final HttpBodyBase bodyBase, String msg) {
		showDialog(msg);
		HttpThread thread = new HttpThread(this);
		thread.executeRequest(bodyBase, handler);
	}

	/***
	 * 获取头部标题控件
	 * 
	 * @return 头部标题控件
	 */
	private TextView getHeaderTitle() {
		return (TextView) findViewById(R.id.tv_header_title);
	}

	/***
	 * 获取头部左部布局控件
	 * 
	 * @return 头部左部布局控件
	 */
	private RelativeLayout getHeaderLeftRel() {
        return (RelativeLayout) findViewById(R.id.rl_header_left);
	}

	/***
	 * 获取头部右部布局控件
	 * 
	 * @return 头部右部布局控件
	 */
	private RelativeLayout getHeaderRightRel() {
        return (RelativeLayout) findViewById(R.id.rl_header_right);
	}

	/***
	 * 设置头部标题点击监听
	 * 
	 * @param onClickListener
	 *            	头部标题点击监听 
	 */
	public void setOnHeaderTitleClickListener(OnClickListener onClickListener) { 
		TextView tv_title = getHeaderTitle();
		tv_title.setOnClickListener(onClickListener);
	}

	/***
	 * 设置头部左部布局点击监听
	 * 
	 * @param onClickListener
	 *            非空 为自定义头部左部布局点击监听 null 为结束该界面
	 */
	public void setOnHeaderLeftClickListener(OnClickListener onClickListener) {
		RelativeLayout rl_left = getHeaderLeftRel();
		if (onClickListener != null) {
			rl_left.setOnClickListener(onClickListener);
		} else {
			rl_left.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View arg0) {
					// TODO Auto-generated method stub
					finish();
				}
			});
		}
	}

	/***
	 * 设置头部右部布局点击监听
	 * 
	 * @param onClickListener
	 *            头部右部布局点击监听
	 */
	public void setOnHeaderRightClickListener(OnClickListener onClickListener) {
		RelativeLayout rl_right = getHeaderRightRel();
		rl_right.setOnClickListener(onClickListener);
	}

	/***
	 * 设置头部标题内容
	 * 
	 * @param title
	 *            标题内容
	 */
	public void setHeaderTitle(String title) {
		TextView tv_title = getHeaderTitle();
		tv_title.setText(title);
	}

	/***
	 * 设置内容布局
	 * 
	 * @param layoutId
	 *            内容布局文件id
	 */
	public void setMyContextView(int layoutId) {
		LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		View view = inflater.inflate(layoutId, null);
		view.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT,
				LayoutParams.MATCH_PARENT));
		rl_bodyer.addView(view);
	}

	/***
	 * 设置内容布局
	 * 
	 * @param view
	 *            内容布局view
	 */
	public void setMyContextView(View view) {
		view.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT,
				LayoutParams.MATCH_PARENT));
		rl_bodyer.addView(view);
	}

	/***
	 * 设置头部左部显示内容或图标
	 * 
	 * @param title
	 *            头部左部显示内容
	 * @param imgRes
	 *            头部左部显示图标id 若显示文字，则imgRes设置为0 若显示图标，则title设置为null
	 *            若都不显示，则imgRes设置为0，title设置为null
	 */
//	public void setHeaderLeftTV(String title, int imgRes) {
//		ImageView tv_left = (ImageView) findViewById(R.id.tv_header_left);
//		if (tv_left != null) {
//			if (title != null) {
//				tv_left.setText(title);
//			}
//			if (imgRes > 0) {
//				tv_left.setBackgroundResource(imgRes);
//			}
//		}
//	}

	/***
	 * 设置头部左部显示控件内容和图标
	 * 
	 * @param title
	 *            显示内容
	 * @param imgRes
	 *            显示图标
	 * @param de
	 *            图标显示在文字方向 0：左边 1：底部 2：右边 3：底部
	 */
//	public void setHeaderLeftTVWithImg(String title, int imgRes, int de) {
//		ImageView tv_left = (ImageView) findViewById(R.id.tv_header_left);
//		if (tv_left != null) {
//			if (title != null) {
//				tv_left.setText(title);
//			}
//			if (imgRes > 0) {
//				switch (de) {
//				case 0:
//					tv_left.setCompoundDrawablesWithIntrinsicBounds(imgRes, 0,
//							0, 0);
//					break;
//				case 1:
//					tv_left.setCompoundDrawablesWithIntrinsicBounds(0, imgRes,
//							0, 0);
//					break;
//				case 2:
//					tv_left.setCompoundDrawablesWithIntrinsicBounds(0, 0,
//							imgRes, 0);
//					break;
//				case 3:
//					tv_left.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0,
//							imgRes);
//					break;
//				default:
//					break;
//				}
//			}
//		}
//	}

	/***
	 * 设置头部右部显示内容或图标
	 * 
	 * @param title
	 *            头部右部显示内容
	 * @param imgRes
	 *            头部右部显示图标id 若显示文字，则imgRes设置为0 若显示图标，则title设置为null
	 *            若都不显示，则imgRes设置为0，title设置为null
	 */
	public void setHeaderRightTV(String title, int imgRes) {
		TextView tv_right = (TextView) findViewById(R.id.tv_header_right);
		if (tv_right != null) {
			if (title != null) {
				tv_right.setText(title);
			}
			if (imgRes > 0) {
				tv_right.setBackgroundResource(imgRes);
			}
		}
	}

	/***
	 * 设置头部右部显示内容或图标
	 * 
	 * @param imgRes
	 *            头部右部显示图标id
	 */
	public void setHeaderRightBtn(int imgRes) {
		ImageView btn_right = (ImageView) findViewById(R.id.btn_header_right);
		if (btn_right != null) {
			if (imgRes > 0) {
				btn_right.setBackgroundResource(imgRes);
			}
		}
	}

	/***
	 * 设置头部右部显示控件内容和图标
	 * 
	 * @param title
	 *            显示内容
	 * @param imgRes
	 *            显示图标
	 * @param de
	 *            图标显示在文字方向 0：左边 1：底部 2：右边 3：底部
	 */
	public void setHeaderRightTVWithImg(String title, int imgRes, int de) {
		TextView tv_right = (TextView) findViewById(R.id.tv_header_right);
		if (tv_right != null) {
			if (title != null) {
				tv_right.setText(title);
			}
			if (imgRes > 0) {
				switch (de) {
				case 0:
					tv_right.setCompoundDrawablesWithIntrinsicBounds(imgRes, 0,
							0, 0);
					break;
				case 1:
					tv_right.setCompoundDrawablesWithIntrinsicBounds(0, imgRes,
							0, 0);
					break;
				case 2:
					tv_right.setCompoundDrawablesWithIntrinsicBounds(0, 0,
							imgRes, 0);
					break;
				case 3:
					tv_right.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0,
							imgRes);
					break;
				default:
					break;
				}
			}
		}
	}

	/***
	 * 设置头部左部控件显示隐藏
	 * 
	 * @param hide
	 *            true隐藏， false显示
	 */
	public void setLeftRelHide(boolean hide) {
		RelativeLayout rl_left = getHeaderLeftRel();
		if (rl_left != null) {
			if (hide) {
				rl_left.setVisibility(View.GONE);
			} else {
				rl_left.setVisibility(View.VISIBLE);
			}
		}
	}

	/***
	 * 设置头部右部控件显示隐藏
	 * 
	 * @param hide
	 *            true隐藏， false显示
	 */
	public void setRightRelHide(boolean hide) {
		RelativeLayout rl_right = getHeaderRightRel();
		if (rl_right != null) {
			if (hide) {
				rl_right.setVisibility(View.GONE);
			} else {
				rl_right.setVisibility(View.VISIBLE);
			}
		}
	}

	/**
	 * 设置头部右侧按钮显示类型
	 * 
	 * @param type
	 *            表示文字显示 RIGHT_MODE_BTN 表示按钮显示
	 */
	public void setRightTextOrBtn(int type) {
		if (type == FinalValue.RIGHT_MODE_TEXT) {
			findViewById(R.id.tv_header_right).setVisibility(View.VISIBLE);
			findViewById(R.id.btn_header_right).setVisibility(View.GONE);
		} else if (type == FinalValue.RIGHT_MODE_BTN) {
			findViewById(R.id.tv_header_right).setVisibility(View.GONE);
			findViewById(R.id.btn_header_right).setVisibility(View.VISIBLE);
		}
	}

	/***
	 * 显示等待加载框
	 * @author Pro.Linyl
	 * @CreateTime 2015年6月26日
	 * @UpdateAuthor 
	 * @UpdateTime	
	 * @description
	 *
	 * @param msg 加载框显示内容
	 */
	public void showDialog(String msg) {
		mDialog = new ProgressDialog(this);
		mDialog.setMessage(msg);
		mDialog.setCancelable(true);
		mDialog.show();
	}

	/***
	 * 隐藏等待加载框
	 * @author Pro.Linyl
	 * @CreateTime 2015年6月26日
	 * @UpdateAuthor 
	 * @UpdateTime	
	 * @description
	 *
	 */
	public void dismissDialog() {
		mDialog.dismiss();
	}

	/***
	 * 设置正在加载界面显示隐藏
	 * 
	 * @param hide
	 *            true为隐藏， false为显示
	 */
	public void setLoadingHide(boolean hide) {
		if (hide) {
			tv_loading.setVisibility(View.GONE);
		} else {
			tv_loading.setVisibility(View.VISIBLE);
		}
	}

	/***
	 * 设置加载失败界面显示隐藏
	 * 
	 * @param hide
	 *            true为隐藏，false为显示
	 */
	public void setFialedHide(boolean hide) {
		if (hide) {
			tv_fialed.setVisibility(View.GONE);
		} else {
			tv_fialed.setVisibility(View.VISIBLE);
		}
	}

	/***
	 * 设置主体内容界面显示隐藏
	 * 
	 * @param hide
	 *            true为隐藏，false为显示
	 */
	public void setBoyderHide(boolean hide) {
		if (hide) {
			rl_bodyer.setVisibility(View.GONE);
		} else {
			rl_bodyer.setVisibility(View.VISIBLE);
		}
	}
}
