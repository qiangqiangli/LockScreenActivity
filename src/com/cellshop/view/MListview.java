package com.cellshop.view;

import com.cellshop.action.ListViewDispatchListener;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.widget.ListView;

public class MListview extends ListView{
	
	boolean upScrolling=false;
	GestureDetector gestureDetector;

	public MListview(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		gestureDetector=new GestureDetector(context,new ListViewDispatchListener());
//		this.setLongClickable(true);
		// TODO Auto-generated constructor stub
		
	}
	public MListview(Context context, AttributeSet attrs) {
		super(context, attrs);
		gestureDetector=new GestureDetector(context,new ListViewDispatchListener());
//		this.setLongClickable(true);
		// TODO Auto-generated constructor stub
	}
	public MListview(Context context) {
		super(context);
		gestureDetector=new GestureDetector(context,new ListViewDispatchListener());
//		this.setLongClickable(true);
		// TODO Auto-generated constructor stub
	}

	@Override
	public boolean dispatchTouchEvent(MotionEvent ev) {
		// TODO Auto-generated method stub
		Log.d("滑动", ev.getAction()+"");
		if (gestureDetector.onTouchEvent(ev)) {
			Log.d("滑动", "左右滑动");
		}
		else {
			Log.d("滑动", "上下滑动");

		}
//		return super.dispatchTouchEvent(ev);
		return false;
	}

}
