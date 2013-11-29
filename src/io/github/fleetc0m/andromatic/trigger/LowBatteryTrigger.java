package io.github.fleetc0m.andromatic.trigger;

import io.github.fleetc0m.andromatic.R;
import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;

public class LowBatteryTrigger extends Trigger {

	public LowBatteryTrigger(){
		super(null);
	}
	
	public LowBatteryTrigger(Context context){
		super(context);
	}
	
	@Override
	public View getConfigView(String savedRule) {
		LayoutInflater inflater = (LayoutInflater) 
				context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
		View view = inflater.inflate(R.layout.low_battery_trigger, null);
		return view;
	}

	@Override
	public View getConfigView() {
		return getConfigView(null);
	}

	@Override
	public boolean trig() {
		return true;
	}

	@Override
	public String getConfigString() {
		return null;
	}

	@Override
	public String getIntentAction() {
		return "android.intent.action.BATTERY_LOW";
	}

	@Override
	public String getHumanReadableString() {
		return "When the power is low, the action will be performed.";
	}

	@Override
	public String getHumanReadableString(String rule) {
		return "When the power is low, the action will be performed.";
	}

	@Override
	public boolean needPolling() {
		return false;
	}

}
