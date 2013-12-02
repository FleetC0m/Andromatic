package io.github.fleetc0m.andromatic;

import io.github.fleetc0m.andromatic.UI.CreateNewRuleFragment;
import io.github.fleetc0m.andromatic.UI.ShowAllRulesFragment;
import io.github.fleetc0m.andromatic.UI.WelcomeFragment;

import java.util.Locale;

import android.app.ActivityManager;
import android.app.ActivityManager.RunningServiceInfo;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.Menu;
import android.view.MenuItem;

public class RootActivity extends FragmentActivity {

	/**
	 * The {@link android.support.v4.view.PagerAdapter} that will provide
	 * fragments for each of the sections. We use a
	 * {@link android.support.v4.app.FragmentPagerAdapter} derivative, which
	 * will keep every loaded fragment in memory. If this becomes too memory
	 * intensive, it may be best to switch to a
	 * {@link android.support.v4.app.FragmentStatePagerAdapter}.
	 */
	private SectionsPagerAdapter mSectionsPagerAdapter;

	/**
	 * The {@link ViewPager} that will host the section contents.
	 */
	private ViewPager mViewPager;
	
	private CreateNewRuleFragment createNewRuleFragment;
	private ShowAllRulesFragment showAllRulesFragment;
	private WelcomeFragment welcomeFragment;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_root);

		startServiceIfNotRunning();
		this.createNewRuleFragment = new CreateNewRuleFragment();
		this.showAllRulesFragment = new ShowAllRulesFragment();
		this.welcomeFragment = new WelcomeFragment();
		// Create the adapter that will return a fragment for each of the three
		// primary sections of the app.
		mSectionsPagerAdapter = new SectionsPagerAdapter(
				getSupportFragmentManager());

		// Set up the ViewPager with the sections adapter.
		mViewPager = (ViewPager) findViewById(R.id.pager);
		mViewPager.setAdapter(mSectionsPagerAdapter);
		
		createNewRuleFragment.setArgs(mViewPager, showAllRulesFragment);
	}

	private void startServiceIfNotRunning(){
	    if(!eventMonitorUp()){
			Intent serviceLauncher = new Intent(this, EventMonitor.class);
			this.startService(serviceLauncher);
	    }
	}
	
	private boolean eventMonitorUp(){
	    ActivityManager manager = (ActivityManager) getSystemService(Context.ACTIVITY_SERVICE);
	    for (RunningServiceInfo service : manager.getRunningServices(Integer.MAX_VALUE)) {
	        if (EventMonitor.class.getName().equals(service.service.getClassName())) {
				return true;
	        }
	    }
	    return false;
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.root, menu);
		return true;
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		
	    switch (item.getItemId()) {
	    	case R.id.action_settings:
	    		Intent settings = new Intent(this, SettingsActivity.class);
	    		startActivity(settings);
	    		return true;
	    		
	        default:
	            return super.onOptionsItemSelected(item);
	    }
	    
	    
	}

	/**
	 * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
	 * one of the sections/tabs/pages.
	 */
	public class SectionsPagerAdapter extends FragmentPagerAdapter {

		public SectionsPagerAdapter(FragmentManager fm) {
			super(fm);
		}

		@Override
		public Fragment getItem(int position) {
			switch(position){
			case 0:
				return welcomeFragment;
			case 1:
				return createNewRuleFragment;
			case 2:
				return showAllRulesFragment;
			default:
					throw new IllegalArgumentException("invalid position");
			}
//			Fragment fragment = new DummySectionFragment();
//			Bundle args = new Bundle();
//			args.putInt(DummySectionFragment.ARG_SECTION_NUMBER, position + 1);
//			fragment.setArguments(args);
//			return fragment;
		}

		@Override
		public int getCount() {
			return 3;
		}

		@Override
		public CharSequence getPageTitle(int position) {
			Locale l = Locale.getDefault();
			switch (position) {
			case 0:
				return getString(R.string.welcome_section_title).toUpperCase(l);
			case 1:
				return getString(R.string.new_rule_section_title).toUpperCase(l);
			case 2:
				return getString(R.string.all_rule_section_title).toUpperCase(l);
			}
			return null;
		}
	}
}
