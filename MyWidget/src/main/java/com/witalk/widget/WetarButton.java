/**
 * @author Pro.Linyl
 * @CreateTime 2015年6月11日
 * @UpdateAuthor 
 * @UpdateTime	 
 */
package com.witalk.widget;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.RelativeLayout;

/***
 * 点击位置出现扩散光圈控件
 * 
 * @author Pro.Linyl
 * @CreateTime 2015年6月11日
 * @UpdateAuthor
 * @UpdateTime
 * @description 点击的位置会出现一个扩散的光圈（color）
 *
 */
@SuppressLint({ "ClickableViewAccessibility", "HandlerLeak" })
public class WetarButton extends RelativeLayout {
	/***
	 * 最大透明度值
	 */
	private final int MAX_ALPHA = 100;
	/***
	 * 半径增长速度
	 */
	private final int RADOFFSET = 20;
	/***
	 * 透明度减少速度
	 */
	private final int ALPOFFSET = 20;
	/***
	 * 光圈的透明度
	 */
	private int alpha = 0;
	/***
	 * 光圈的半径
	 */
	private int radius = 0;
	/***
	 * 手指按下的X轴坐标
	 */
	private int xDown = 0;
	/***
	 * 手指按下的Y轴坐标
	 */
	private int yDown = 0;
	/***
	 * 画笔
	 */
	private Paint paint;
	/***
	 * 光圈颜色
	 */
	private int color;

	public WetarButton(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
		initPaint();
	}

	public WetarButton(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub
		initPaint();
	}

	public WetarButton(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
		// TODO Auto-generated constructor stub
		initPaint();
	}

	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		// TODO Auto-generated method stub
		super.onMeasure(widthMeasureSpec, heightMeasureSpec);
	}

	@Override
	protected void onDraw(Canvas canvas) {
		// TODO Auto-generated method stub
		super.onDraw(canvas);
		canvas.drawCircle(xDown, yDown, radius, paint);
	}

	/**
	 * 初始化paint
	 */
	private void initPaint() {
		color = Color.WHITE;

		/** 新建一个画笔 */
		paint = new Paint();
		paint.setAntiAlias(true);
		paint.setStrokeWidth(radius);
		// 设置是环形方式绘制
		paint.setStyle(Paint.Style.FILL);
		paint.setAlpha(alpha);
		paint.setColor(color);
	}

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		// TODO Auto-generated method stub
		super.onTouchEvent(event);
		switch (event.getAction()) {
		case MotionEvent.ACTION_DOWN:
			radius = 0;
			alpha = MAX_ALPHA;
			xDown = (int) event.getX();
			yDown = (int) event.getY();
			handler.sendEmptyMessage(0);
			break;
		case MotionEvent.ACTION_MOVE:

			break;
		case MotionEvent.ACTION_UP:

			break;

		default:
			break;
		}
		return true;
	}

	/***
	 * 刷新界面handler
	 */
	private Handler handler = new Handler() {

		@Override
		public void handleMessage(Message msg) {
			super.handleMessage(msg);
			switch (msg.what) {
			case 0:
				flushState();
				invalidate();
				if (alpha != 0) {
					// 如果透明度没有到0，则继续刷新，否則停止刷新
					handler.sendEmptyMessageDelayed(0, 50);
				}
				break;
			default:
				break;
			}
		}

		/**
		 * 刷新状态
		 */
		private void flushState() {
			radius += RADOFFSET;
			alpha -= ALPOFFSET;
			if (alpha < 0) {
				alpha = 0;
			}
			paint.setAlpha(alpha);
			paint.setStrokeWidth(radius);
		}

	};
}
