package com.cmbl.car.widget;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ListView;

import com.cmbl.car.adapter.CarTypeAdatper;

public class MySideBar extends View {
	private char[] l;
	private ListView list;
//	private TextView mDialogText;
	private int m_nItemHeight;
	private CarTypeAdatper adapter;

	public MySideBar(Context context) {
		super(context);
		init();
	}

	public MySideBar(Context context, AttributeSet attrs) {
		super(context, attrs);
		init();
	}

	private void init() {
		l = new char[] {'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J',
				'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V',
				'W', 'X', 'Y', 'Z' };
	}

	public MySideBar(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		init();
	}

	public void setListView(ListView _list) {
		list = _list;
	}

	public void setAdapter(CarTypeAdatper adapter) {
		this.adapter = adapter;
	}

//	public void setTextView(TextView mDialogText) {
//		this.mDialogText = mDialogText;
//	}

	public boolean onTouchEvent(MotionEvent event) {
		super.onTouchEvent(event);
		int i = (int) event.getY();
		int idx = i / m_nItemHeight;
		if (idx >= l.length) {
			idx = l.length - 1;
		} else if (idx < 0) {
			idx = 0;
		}
		if (event.getAction() == MotionEvent.ACTION_DOWN
				|| event.getAction() == MotionEvent.ACTION_MOVE) {
			getParent().requestDisallowInterceptTouchEvent(true);
			int position = 0;
//			mDialogText.setVisibility(View.VISIBLE);
//			mDialogText.setText("" + l[idx]);
			if (idx != 0) {
				position = adapter.getPositionForSection(l[idx]);
				if (position == -1) {
					return true;
				}
			}
			list.setSelection(position);
		} else {
			getParent().requestDisallowInterceptTouchEvent(false);
//			mDialogText.setVisibility(View.INVISIBLE);
		}
		return true;
	}

	@SuppressLint("DrawAllocation")
	protected void onDraw(Canvas canvas) {
		m_nItemHeight = (int) (getMeasuredHeight() / 26);
		Paint paint = new Paint();
		paint.setColor(0xff595c61);
		paint.setTextSize(14);
		paint.setTextAlign(Paint.Align.CENTER);
		float widthCenter = getMeasuredWidth() / 2;
		for (int i = 0; i < l.length; i++) {
			canvas.drawText(String.valueOf(l[i]), widthCenter,
					m_nItemHeight + (i * m_nItemHeight), paint);
		}
		super.onDraw(canvas);
	}

}
