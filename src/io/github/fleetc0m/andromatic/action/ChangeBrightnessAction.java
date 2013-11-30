package io.github.fleetc0m.andromatic.action;

import io.github.fleetc0m.andromatic.R;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.provider.Settings;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.SeekBar;

public class ChangeBrightnessAction extends Action{

	public ChangeBrightnessAction(Context context, String savedRule) {
		super(context, savedRule);
	}

	private SeekBar seekbar;
	
	public ChangeBrightnessAction(){
		//This constructor must be there even if it doesn't do anything.
		//Otherwise the classloader will grumble at you. 
	}
	@Override
	public boolean act() {
		//http://stackoverflow.com/questions/6708692/changing-the-screen-brightness-system-setting-android
		float brightness = Integer.parseInt(savedRule)/10;
		int brightnessInt = (int)(brightness*255);
		if(brightnessInt<1)brightnessInt=1;
		
		Settings.System.putInt(context.getContentResolver(),
				Settings.System.SCREEN_BRIGHTNESS_MODE,Settings.System.SCREEN_BRIGHTNESS_MODE_MANUAL);
		Settings.System.putInt(context.getContentResolver(),Settings.System.SCREEN_BRIGHTNESS,brightnessInt);
		
		//apply brightness by creating a dummy activity
		Intent intent = new Intent(context,DummyBrightnessActivity.class);
		intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		intent.putExtra("brightness value", brightness);
		context.startActivity(intent);
		return false;
	}

	@Override
	public View getConfigView(String savedRule) {
		LayoutInflater i = (LayoutInflater) context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
		View view = i.inflate(R.layout.change_brightness_action, null);
		seekbar = (SeekBar) view.findViewById(R.id.change_brightness_action_seekbar);
		seekbar.setMax(10);
		if(savedRule==null) return view;
		int pos = Integer.parseInt(savedRule);
		if(pos != -1){
			seekbar.setProgress(pos);
		}
		return view;
	}

	@Override
	public View getConfigView() {
		return getConfigView(null);
	}

	@Override
	public String getConfigString() {
		savedRule = ""+seekbar.getProgress();
		return savedRule;
	}

	@Override
	public String getHumanReadableString() {
		return getHumanReadableString(savedRule);
	}

	@Override
	public String getHumanReadableString(String rule) {
		int curr_progress = Integer.parseInt(rule);
		int percent = (int) curr_progress*10;
		return "the brightness will be set to be "+percent+"%";
	}

}
