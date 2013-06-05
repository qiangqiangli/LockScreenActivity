package com.cellshop.action;

import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;

public class ListViewDispatchListener extends GestureDetector.SimpleOnGestureListener{
	@Override
	public boolean onDown(MotionEvent e) {
		// TODO Auto-generated method stub
		return false;
	}
	@Override
	public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX,
			float distanceY) {
		// TODO Auto-generated method stub
		if ((Math.abs(e2.getX()-e1.getX()))<((Math.abs(e2.getY()-e1.getY())))) {
			Log.d("»¬¶¯", "ºáÏò»¬¶¯");
			return true;
		}
		return false;
	}
}
