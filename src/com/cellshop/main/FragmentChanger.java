package com.cellshop.main;

import android.app.Fragment;

public interface FragmentChanger {
	public void fragmentChange(Fragment fragment);
	public void fragmentBackChange();
	public void fragmentForwardChange(Fragment fragment);
}
