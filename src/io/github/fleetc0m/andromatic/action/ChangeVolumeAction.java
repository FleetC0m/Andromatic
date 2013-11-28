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
	private static final String VOLUME_FIELD = "volume";
	private SeekBar seekbar;
	
	@Override
	public void setArgs(String arg) {

	}

	@Override
	public boolean act() {
		//http://stackoverflow.com/questions/7317974/android-mute-unmute-phone
		AudioManager audioManager = (AudioManager)context.getSystemService(Context.AUDIO_SERVICE);
		int curr_progress = seekbar.getProgress();
		int maxVolume = audioManager.getStreamMaxVolume(AudioManager.STREAM_RING);
		audioManager.setStreamVolume(AudioManager.STREAM_RING,(int)maxVolume*curr_progress/10,
				 AudioManager.FLAG_SHOW_UI + AudioManager.FLAG_PLAY_SOUND);
		return false;
	}

	@Override
	public View getConfigView(Bundle b) {
		LayoutInflater i = (LayoutInflater) context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
		View view = i.inflate(R.layout.change_volume_action, null);
		seekbar = (SeekBar) view.findViewById(R.id.change_volume_action_seekbar);
		seekbar.setMax(10);
		int pos = b.getInt(VOLUME_FIELD, -1);
		if(pos != -1){
			seekbar.setProgress(pos);
		}
		return view;
	}

	@Override
	public View getConfigView() {
		return getConfigView(new Bundle());
	}

	@Override
	public String getConfigString() {
		//
		return ""+seekbar.getProgress();
	}

	@Override
	public String getHumanReadableString() {
		// TODO Auto-generated method stub
		return getHumanReadableString(getConfigString());
	}

	@Override
	public String getHumanReadableString(String rule) {
		// TODO Auto-generated method stub
		AudioManager audioManager = (AudioManager)context.getSystemService(Context.AUDIO_SERVICE);
		int curr_progress = Integer.parseInt(rule);
		int maxVolume = audioManager.getStreamMaxVolume(AudioManager.STREAM_RING);
		int volume = (int) seekbar.getProgress()/10*maxVolume;
		return "the volume will be set to be "+volume;
	}

}
