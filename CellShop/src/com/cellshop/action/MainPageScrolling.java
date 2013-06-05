package com.cellshop.action;

import android.view.MotionEvent;
import android.view.GestureDetector.SimpleOnGestureListener;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

public class MainPageScrolling extends SimpleOnGestureListener{
	private int scrollingX;
	private boolean isScrolling;
	public RelativeLayout.LayoutParams layoutParams;
	public LinearLayout mainLayout;
	public float MAX_WIDTH;
	public void setLayoutInfo(LinearLayout layout,float width) {
		mainLayout=layout;
		MAX_WIDTH=width;
	}
	public boolean getIsScrolling() {
		return isScrolling;
	}
	public void setIsScrolling(boolean b) {
		isScrolling=b;
	}
	public boolean onScroll(MotionEvent event1, MotionEvent event2, float distanceX,
			float distanceY) {
		// »¬¶¯
		if ((Math.abs(event2.getX() - event1.getX())) > (Math
				.abs(event2.getY() - event1.getY()))) {
			isScrolling = true;
		}
		if (isScrolling) {
			scrollingX+=distanceX;
			layoutParams = (RelativeLayout.LayoutParams) mainLayout
					.getLayoutParams();
			float i = layoutParams.rightMargin + scrollingX;//
			float j = layoutParams.leftMargin - scrollingX;//
			if (j > MAX_WIDTH) {
				j=MAX_WIDTH;
				i=-j;
			} else if (j < 0) {
				i=j=0;
			} 
			layoutParams.rightMargin = (int) i;
			layoutParams.leftMargin = (int) j;
			mainLayout.setLayoutParams(layoutParams);
			return true;
		}
		return false;
	}
	@Override
	public boolean onDown(MotionEvent e) {
		// °´ÏÂ
		scrollingX=0;
		return true;
	}
}

