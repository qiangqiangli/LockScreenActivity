package com.cellshop.action;

import android.app.Activity;
import android.content.Context;
import android.os.Handler;
import android.view.Display;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

public class MainPageGestureDetector extends GestureDetector{
	private static  MainPageScrolling mainPageScrolling=new MainPageScrolling();
	public float MAX_WIDTH;
	public RelativeLayout.LayoutParams layoutParams;
	public LinearLayout mainLayout;
	private WindowManager windowManager;
	private float speed=40;
	private boolean covered=true;
	private Handler handler;
	@SuppressWarnings("deprecation")
	public MainPageGestureDetector(Context context,LinearLayout mainLayout) {
		super(context,mainPageScrolling);
		// TODO Auto-generated constructor stub
		this.mainLayout=mainLayout;
		windowManager = ((Activity)context).getWindowManager(); 
		Display display = windowManager.getDefaultDisplay();
		MAX_WIDTH=(float) (display.getWidth()*0.7);
		mainPageScrolling.setLayoutInfo(mainLayout, MAX_WIDTH);
	}
	public boolean isCovered() {
		return covered;
	}
	@Override
	public boolean onTouchEvent(MotionEvent ev) {
		// TODO Auto-generated method stub
		
		if (!super.onTouchEvent(ev)) {
			if ((mainPageScrolling.getIsScrolling())&&(ev.getAction()==MotionEvent.ACTION_UP)) {
				endMove();
				return true;
			}
		}
		else {
			return true;
		}
		return false;
	}
	private void endMove() {
		Display display = windowManager.getDefaultDisplay();
		@SuppressWarnings("deprecation")
		float changeWidth=(float) (display.getWidth()*0.1);
		scrollingMove(changeWidth);
	}
	public void scrollingMove(float changeWidth) {
		layoutParams = (RelativeLayout.LayoutParams) mainLayout.getLayoutParams();	
		speed=Math.abs(speed);
		if (covered) {
			speed=layoutParams.leftMargin>=changeWidth?speed:-speed;
		}
		else {
			speed=layoutParams.leftMargin<=(MAX_WIDTH-changeWidth)?-speed:speed;

		}
		handler=new Handler();
		handler.post(new Runnable() {
			
			boolean fristEnd=true;
			@Override
			public void run() {
				// 拖拉结束后的自动效果
				float locat=layoutParams.leftMargin+speed;
				
				if (fristEnd) {
					
				}
				
				if ((locat<0)||(locat>MAX_WIDTH)) {
					locat=locat<0?0:MAX_WIDTH;
					layoutParams.leftMargin=(int)locat;
					layoutParams.rightMargin=(int)-locat;
					mainLayout.setLayoutParams(layoutParams);
					handler.removeCallbacks(this);
					mainPageScrolling.setIsScrolling(false);
					covered=locat==0;
				}
				else {
					layoutParams.leftMargin=(int)locat;
					layoutParams.rightMargin=(int)-locat;
					mainLayout.setLayoutParams(layoutParams);
					handler.postDelayed(this, 10);
				}
				
				
			}
		});	
	}

}

