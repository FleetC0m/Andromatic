package io.github.fleetc0m.andromatic.UI;

import io.github.fleetc0m.andromatic.HelpActivity;
import io.github.fleetc0m.andromatic.R;
import io.github.fleetc0m.andromatic.SettingsActivity;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;

public class WelcomeFragment extends Fragment{
	
	private static final String TAG = "WF";
	private Context context;
	private ViewPager viewPager;
	private Button addBtn;
	private Button helpBtn;
	private Button editBtn;
	private CreateNewRuleFragment createNewRuleFragment;
	
	//private AllRuleListAdapter adapter;
	public View onCreateView(LayoutInflater inflater, final ViewGroup container, 
			Bundle savedInstanceState){
		super.onCreateView(inflater, container, savedInstanceState);
		context = this.getActivity();
		this.viewPager = (ViewPager)this.getActivity().findViewById(R.id.pager);
		View rootView = inflater.inflate(R.layout.welcome_fragment, null);
		addBtn = (Button)rootView.findViewById(R.id.addButton);
		addBtn.setOnClickListener(new AddUserListener());
		helpBtn = (Button) rootView.findViewById(R.id.helpButton);
		helpBtn.setOnClickListener(new HelpMsgListener());
		editBtn = (Button) rootView.findViewById(R.id.editButton);
		editBtn.setOnClickListener(new EditRuleListener());
		this.createNewRuleFragment = (CreateNewRuleFragment) this.viewPager.getAdapter().instantiateItem(this.viewPager, 1);
		return rootView;
	}
	
	public class AddUserListener implements OnClickListener{

		@Override
		public void onClick(View v) {
			Intent settings = new Intent((Activity)v.getContext(), SettingsActivity.class);
    		startActivity(settings);
		}
		
	}
	
	public class HelpMsgListener implements OnClickListener{

		@Override
		public void onClick(View v) {
			Intent settings = new Intent((Activity)v.getContext(), HelpActivity.class);
    		startActivity(settings);
		}
		
	}
	
	public class EditRuleListener implements OnClickListener{

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			if(createNewRuleFragment == null){
				Log.d(TAG, "createNewRule is null");
			}
			if(createNewRuleFragment.getAdapter() == null){
				Log.d(TAG, "adapter is null");
			}
			//createNewRuleFragment.getAdapter().setSavedState(b);
			viewPager.setCurrentItem(1);
		}
		
	}
}
