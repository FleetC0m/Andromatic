package io.github.fleetc0m.andromatic.action;

import io.github.fleetc0m.andromatic.R;
import android.app.Activity;
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
		// TODO Auto-generated method stub
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
	public String getConfigString(View view) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getHumanReadableString(View view) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getHumanReadableString(String rule) {
		// TODO Auto-generated method stub
		return null;
	}

}
