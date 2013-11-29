package io.github.fleetc0m.andromatic.trigger;

import io.github.fleetc0m.andromatic.R;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.BatteryManager;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.SeekBar;

public class LowBatteryTrigger extends Trigger {

	public LowBatteryTrigger(){
		super(null);
	}
	
	public LowBatteryTrigger(Context context){
		super(context);
	}
	
	private SeekBar seekbar;
	
	@Override
	public View getConfigView(String savedRule) {
		LayoutInflater i = (LayoutInflater) context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
		View view = i.inflate(R.layout.low_battery_trigger, null);
		seekbar = (SeekBar) view.findViewById(R.id.low_battery_trigger_seekbar);
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
	public boolean trig() {
		IntentFilter ifilter = new IntentFilter(Intent.ACTION_BATTERY_CHANGED);
		Intent batteryStatus = context.registerReceiver(null, ifilter);
		int level = batteryStatus.getIntExtra(BatteryManager.EXTRA_LEVEL,-1);
		int scale = batteryStatus.getIntExtra(BatteryManager.EXTRA_SCALE,-1);
		int batteryPct = (int) (level/(float)scale*100);
		int percent = Integer.parseInt(savedRule)*10;
		return batteryPct<=percent;
	}

	@Override
	public String getConfigString() {
		savedRule = ""+seekbar.getProgress();
		return savedRule;
	}

	@Override
	public String getIntentAction() {
		return null;
	}

	@Override
	public String getHumanReadableString() {
		return getHumanReadableString(savedRule);
	}

	@Override
	public String getHumanReadableString(String rule) {
		int percent = Integer.parseInt(rule)*10;
		return "When the power is below " + percent +"%, the action will be performed.";
	}

	@Override
	public boolean needPolling() {
		return true;
	}

}
