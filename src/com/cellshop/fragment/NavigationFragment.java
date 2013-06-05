package com.cellshop.fragment;

import com.cellshop.main.FragmentChanger;
import com.cellshop.main.R;
import android.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class NavigationFragment extends Fragment{
	FragmentChanger fragmentChanger;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		fragmentChanger=(FragmentChanger)getActivity();
	}
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		return inflater.inflate(R.layout.navigation_fragment, container, false);
	}
	public void mainPageClick(View view) {
		Log.d("12334", "12334");
	}

}
