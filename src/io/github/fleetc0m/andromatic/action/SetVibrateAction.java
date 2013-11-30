package io.github.fleetc0m.andromatic.action;

import io.github.fleetc0m.andromatic.R;
import android.app.Activity;
import android.content.Context;
import android.media.AudioManager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.Switch;

public class SetVibrateAction extends Action{
	private Switch vibrateSwitch;

	public SetVibrateAction(Context context, String savedRule) {
		super(context, savedRule);
	}
	public SetVibrateAction(){
		
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
	public View getConfigView(String savedRule) {
		LayoutInflater i = (LayoutInflater) context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
		View view = i.inflate(R.layout.set_vibrate_action, null);
		vibrateSwitch = (Switch) view.findViewById(R.id.set_vibrate_action);
		if(savedRule==null) return view;
		boolean checked = Boolean.parseBoolean(savedRule);
		vibrateSwitch.setChecked(checked);
		return view;
	}

	@Override
	public View getConfigView() {
		return getConfigView(null);
	}

	@Override
	public String getConfigString() {
		// whether is checked
		savedRule = Boolean.toString(vibrateSwitch.isChecked());
		return savedRule;
	}

	@Override
	public String getHumanReadableString() {
		return getHumanReadableString(savedRule);
	}

	@Override
	public String getHumanReadableString(String rule) {
		if(Boolean.parseBoolean(rule)){
			return "vibrate mode will be set and it will mute the volume and vibrate";
		}else{
			return "vibrate mode will be unset and it will mute the volume and not vibrate";
		}
	}

}
