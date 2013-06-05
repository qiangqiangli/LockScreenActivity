package com.cellshop.fragment;

import android.app.Fragment;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.util.Log;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import com.cellshop.main.FragmentChanger;
import com.cellshop.main.MainPageScrollingGesture;
import com.cellshop.main.R;

public class MainPageFragment extends Fragment{

	FragmentChanger fragmentChanger;
	GestureDetector gestureDetector;
	ListView listView;
	String[] strings={"1","2","3","4","5","6","7","8","9","0","1","2","3"};
	Button button;
	@Override
	public void onStart() {
		// TODO Auto-generated method stub
		super.onStart();
		fragmentChanger=(FragmentChanger)getActivity();
		button=(Button)getActivity().findViewById(R.id.fragment_change_button); 
		gestureDetector=new GestureDetector(getActivity(), new GestureDetector.SimpleOnGestureListener() {
			@Override
			public boolean onDown(MotionEvent e) {
				// TODO Auto-generated method stub
				return false;
			}
			@Override
			public boolean onSingleTapUp(MotionEvent e) {
				// TODO Auto-generated method stub
				NavigationFragment navigationFragment=new NavigationFragment();
            	fragmentChanger.fragmentForwardChange(navigationFragment);
				return true;
			}
		});
		button.setOnTouchListener(new View.OnTouchListener() {
			
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				// TODO Auto-generated method stub
				Log.d("MagePage","this is button");
				return 	gestureDetector.onTouchEvent(event);
			}
		});
		FragmentTransaction transaction = getFragmentManager().beginTransaction();
		NavigationFragment mainPageFragment=new NavigationFragment();
		transaction.add(R.id.testfragment, mainPageFragment);
		transaction.commit();
		listView=(ListView)getActivity().findViewById(R.id.listView1);
		listView.setOnTouchListener(new View.OnTouchListener() {
			
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				if (((MainPageScrollingGesture)getActivity()).getGestureDetector().onTouchEvent(event)) {
					Log.d("滑动", "底层滑动");
					return true;
				}
				Log.d("滑动", "上层滑动");
				return false;
			}
		});
		listView.setAdapter(new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, strings));
	}
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		return inflater.inflate(R.layout.main_page_fragment_layout, container, false);
	}

}
