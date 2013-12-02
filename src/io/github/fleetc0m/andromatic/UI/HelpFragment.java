package io.github.fleetc0m.andromatic.UI;

import io.github.fleetc0m.andromatic.HelpActivity;
import io.github.fleetc0m.andromatic.R;
import io.github.fleetc0m.andromatic.RootActivity;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.support.v4.app.Fragment;

public class HelpFragment extends Fragment{
	private WelcomeFragment welcomeFragment;
	private ViewPager viewPager;
	private Button backBtn;
	private Context context;
	private static final String TAG = "HF";
	private int type;
	public HelpFragment(){
		
	}
	
	public void setArgs(int i){
		type = i;
	}
	public View onCreateView(LayoutInflater inflater, final ViewGroup container, 
			Bundle savedInstanceState){
		super.onCreateView(inflater, container, savedInstanceState);
		context = this.getActivity();
		this.viewPager = (ViewPager)this.getActivity().findViewById(R.id.pager_help);
		View rootView ;
		switch(type){
		case 0:
			rootView = inflater.inflate(R.layout.help_fragment1, null);
			break;
		case 1:
			rootView = inflater.inflate(R.layout.help_fragment2, null);
			break;
		case 2:
			rootView = inflater.inflate(R.layout.help_fragment3, null);
			break;
		case 3:
			rootView = inflater.inflate(R.layout.help_fragment4, null);
			break;
		default:
			rootView = inflater.inflate(R.layout.help_fragment1, null);
		}
		backBtn = (Button)rootView.findViewById(R.id.backButton);
		backBtn.setOnClickListener(new BackToMainListener());
		//this.welcomeFragment = (WelcomeFragment) this.viewPager.getAdapter().instantiateItem(this.viewPager, 0);
		return rootView;
	}
	
	public class BackToMainListener implements OnClickListener{

		@Override
		public void onClick(View v) {
				Intent settings = new Intent((Activity)v.getContext(), RootActivity.class);
	    		startActivity(settings);
		}
		
	}
}
