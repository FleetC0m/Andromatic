package io.github.fleetc0m.andromatic.action;

import io.github.fleetc0m.andromatic.R;
import android.app.Activity;
import android.content.Context;
import android.media.AudioManager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.SeekBar;

public class ChangeVolumeAction extends Action {
	
	public ChangeVolumeAction(Context context, String savedRule) {
		super(context, savedRule);
	}

	private SeekBar seekbar;

	@Override
	public boolean act() {
		//http://stackoverflow.com/questions/7317974/android-mute-unmute-phone
		AudioManager audioManager = (AudioManager)context.getSystemService(Context.AUDIO_SERVICE);
		int curr_progress = Integer.parseInt(savedRule);
		int maxVolume = audioManager.getStreamMaxVolume(AudioManager.STREAM_RING);
		audioManager.setStreamVolume(AudioManager.STREAM_RING,(int)maxVolume*curr_progress/10,
				 AudioManager.FLAG_SHOW_UI + AudioManager.FLAG_PLAY_SOUND);
		return true;
	}

	@Override
	public View getConfigView(String savedRule) {
		LayoutInflater i = (LayoutInflater) context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
		View view = i.inflate(R.layout.change_volume_action, null);
		seekbar = (SeekBar) view.findViewById(R.id.change_volume_action_seekbar);
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
		//seekbar.position
		return ""+seekbar.getProgress();
	}

	@Override
	public String getHumanReadableString() {
		return getHumanReadableString(savedRule);
	}

	@Override
	public String getHumanReadableString(String rule) {
		int curr_progress = Integer.parseInt(rule);
		int percent = (int) curr_progress*10;
		return "the volume will be set to be "+percent+"%";
	}

}
