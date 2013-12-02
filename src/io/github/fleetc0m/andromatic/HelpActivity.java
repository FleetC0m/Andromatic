package io.github.fleetc0m.andromatic;

import io.github.fleetc0m.andromatic.UI.HelpFragment;

import java.util.Locale;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;

public class HelpActivity extends FragmentActivity{
	private SectionsPagerAdapter mSectionsPagerAdapter;
	private ViewPager mViewPager;
	
	private HelpFragment[] helpFragments;
	//private Button backButton;
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.help_root);
		helpFragments = new HelpFragment[4];
		for(int i=0;i<4;i++){
			helpFragments[i] = new HelpFragment();
			helpFragments[i].setArgs(i);
		}
		mSectionsPagerAdapter = new SectionsPagerAdapter(
				getSupportFragmentManager());
		mViewPager = (ViewPager) findViewById(R.id.pager_help);
		mViewPager.setAdapter(mSectionsPagerAdapter);
	}
	
    
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
			
			return helpFragments[position];
		}

		@Override
		public int getCount() {
			return 4;
		}

		@Override
		public CharSequence getPageTitle(int position) {
			Locale l = Locale.getDefault();
			switch (position) {
			case 0:
				return getString(R.string.help_section_title).toUpperCase(l);
			case 1:
				return getString(R.string.trigger_section_title).toUpperCase(l);
			case 2:
				return getString(R.string.action_section_title).toUpperCase(l);
			case 3:
				return getString(R.string.manage_section_title).toUpperCase(l);
			}
			return null;
		}
	}
}
