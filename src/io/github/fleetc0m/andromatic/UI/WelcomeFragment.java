package io.github.fleetc0m.andromatic.UI;

import io.github.fleetc0m.andromatic.R;
import android.support.v4.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class WelcomeFragment extends Fragment{
	
	private static final String TAG = "WF";
	private Context context;
	private ViewPager viewPager;
	
	public View onCreateView(LayoutInflater inflater, final ViewGroup container, 
			Bundle savedInstanceState){
		super.onCreateView(inflater, container, savedInstanceState);
		context = this.getActivity();
		this.viewPager = (ViewPager)this.getActivity().findViewById(R.id.pager);
		View rootView = inflater.inflate(R.layout.welcome_fragment, null);
		return rootView;
	}
}
