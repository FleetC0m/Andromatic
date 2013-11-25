package io.github.fleetc0m.andromatic.action;

import io.github.fleetc0m.andromatic.R;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.SeekBar;

public class ChangeVolumeAction extends Action {
	private static String Volume = "volume";
	private SeekBar seekbar;
	
	@Override
	public void setArgs(Bundle arg) {

	}

	@Override
	public boolean act() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public View getConfigView(LayoutInflater inflator, Bundle b) {
		View view = inflator.inflate(R.layout.change_volume_action, null);
		seekbar = (SeekBar) view.findViewById(R.id.change_volume_action_seekbar);
		seekbar.setMax(10);
		int pos = b.getInt(Volume, -1);
		if(pos != -1){
			seekbar.setProgress(pos);
		}
		return view;
	}

	@Override
	public View getEmptyConfigView(LayoutInflater inflator) {
		return getConfigView(inflator, new Bundle());
	}

}
