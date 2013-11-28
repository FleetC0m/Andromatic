package io.github.fleetc0m.andromatic.action;

import io.github.fleetc0m.andromatic.R;
import android.app.Activity;
import android.content.Context;
import android.media.AudioManager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.SeekBar;
import android.widget.Switch;

public class SetVibrateAction extends Action{
	private static final String CHECKED_FIELD= "checked";
	private Switch vibrateSwitch;
	
	@Override
	public void setArgs(String arg) {
	
	}

	@Override
	//http://stackoverflow.com/questions/7317974/android-mute-unmute-phone
	public boolean act() {
		AudioManager audioManager = (AudioManager)context.getSystemService(Context.AUDIO_SERVICE);
		
		if(vibrateSwitch.isChecked()){
			audioManager.setRingerMode(AudioManager.RINGER_MODE_VIBRATE);
		}else{
			audioManager.setRingerMode(AudioManager.RINGER_MODE_SILENT);
		}
		return true;
	}

	@Override
	public View getConfigView(Bundle b) {
		LayoutInflater i = (LayoutInflater) context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
		View view = i.inflate(R.layout.set_vibrate_action, null);
		vibrateSwitch = (Switch) view.findViewById(R.id.set_vibrate_action);
		boolean checked = b.getBoolean(CHECKED_FIELD);
		vibrateSwitch.setChecked(checked);
		return view;
	}

	@Override
	public View getConfigView() {
		return getConfigView(new Bundle());
	}

	@Override
	public String getConfigString() {
		// whether is checked
		return Boolean.toString(vibrateSwitch.isChecked());
	}

	@Override
	public String getHumanReadableString() {
		return getHumanReadableString(getConfigString());
	}

	@Override
	public String getHumanReadableString(String rule) {
		if(Boolean.parseBoolean(rule)){
			return "You set the vibrate mode, it will mute the volume and vibrate";
		}else{
			return "You unset the vibrate mode, it will mute the volume and not vibrate";
		}
	}

}
