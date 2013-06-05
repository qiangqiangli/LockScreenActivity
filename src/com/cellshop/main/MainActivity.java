package com.cellshop.main;

import java.util.ArrayList;

import com.cellshop.action.MainPageGestureDetector;
import com.cellshop.fragment.MainPageFragment;

import android.os.Bundle;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.util.Log;
import android.view.Display;
import android.view.GestureDetector;
import android.view.Menu;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageButton;
import android.widget.LinearLayout;

public class MainActivity extends Activity implements FragmentChanger,MainPageScrollingGesture{
	/*
	 * 入口Activty
	 * 实现FragmentChanger来实现碎片切换
	 * 王汉肖
	 * 2013.4.10
	 */

	private FragmentTransaction transaction;
	private MainPageGestureDetector mainPageGestureDetector;
	private ArrayList<Fragment> fragments;
	private LinearLayout mainLayout;
	protected boolean isScrolling=false;
	protected boolean covered=true;
	protected float scrollingX,MAX_WIDTH,speed=30;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_main);
		init_fragment();
		setListener();
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// 创建menu
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
	
	public void mainPageClick(View view) {
		Log.d("12334", "12334");
	}
	
	@SuppressWarnings("deprecation")
	private void setListener() {
		// 创建监听
		WindowManager windowManager = getWindowManager(); 
		Display display = windowManager.getDefaultDisplay();
		MAX_WIDTH=(float) (display.getWidth()*0.7);
		mainLayout=(LinearLayout)findViewById(R.id.main_layout);
		ImageButton backButton=(ImageButton)findViewById(R.id.backButton);
		mainPageGestureDetector=new MainPageGestureDetector(this, mainLayout);
		mainLayout.setOnTouchListener(new View.OnTouchListener() {
			@Override
			public boolean onTouch(View v, MotionEvent event) {

				return mainPageGestureDetector.onTouchEvent(event);
			}
		});
		backButton.setOnTouchListener(new View.OnTouchListener() {
			
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				// TODO Auto-generated method stub
				if (event.getAction()==MotionEvent.ACTION_UP) {
					fragmentBackChange();
				}
				return true;
			}
		});
	}

	@SuppressLint("CommitTransaction")
	private void init_fragment() {
		// 设置主页
		transaction = getFragmentManager().beginTransaction();
		MainPageFragment mainPageFragment=new MainPageFragment();
		transaction.add(R.id.main_fragment_layout, mainPageFragment);
		transaction.commit();
		fragments=new ArrayList<Fragment>();
		fragments.add(mainPageFragment);
	}

	@Override
	public void fragmentChange(Fragment fragment) {
		// 更换主碎片
		transaction = getFragmentManager().beginTransaction();
		transaction.replace(R.id.main_fragment_layout, fragment);
		transaction.commit();
	}

	@Override
	public void fragmentBackChange() {
		// TODO Auto-generated method stub
		if (!mainPageGestureDetector.isCovered()) {
			mainPageGestureDetector.scrollingMove(0);
		}
		else if (fragments.size()>1) {
			fragments.remove(fragments.size()-1);
			fragmentChange(fragments.get(fragments.size()-1));
		}
		else {
			mainPageGestureDetector.scrollingMove(0);
		}
		
	}

	@Override
	public void fragmentForwardChange(Fragment fragment) {
		// TODO Auto-generated method stub
		fragments.add(fragment);
		fragmentChange(fragment);
	}

	@Override
	public GestureDetector getGestureDetector() {
		// TODO Auto-generated method stub
		return mainPageGestureDetector;
	}

}
