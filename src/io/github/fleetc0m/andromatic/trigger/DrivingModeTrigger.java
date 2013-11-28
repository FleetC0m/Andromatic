package io.github.fleetc0m.andromatic.trigger;

import io.github.fleetc0m.andromatic.R;
import android.app.Activity;
import android.content.Context;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

public class DrivingModeTrigger extends Trigger{
	
	public static final String SPEED_FIELD = "speed";
	private EditText speedEdit;
	private String speed;
	private float curr_speed;
	public DrivingModeTrigger(){
		super(null);
	}
	
	public DrivingModeTrigger(Context context){
		super(context);
		LocationManager locationManager = (LocationManager)context.getSystemService(Context.LOCATION_SERVICE);
		LocationListener locationListener = new LocationListener(){
			@Override
			public void onLocationChanged(Location location) {
				// TODO Auto-generated method stub
				curr_speed = (float) (location.getSpeed()*2.2369);
			}

			@Override
			public void onProviderDisabled(String provider) {
			}

			@Override
			public void onProviderEnabled(String provider) {
			}

			@Override
			public void onStatusChanged(String provider, int status,
					Bundle extras) {
			}
		};
	}
	@Override
	public View getConfigView(Bundle b) {
		// TODO Auto-generated method stub
		LayoutInflater inflater = (LayoutInflater) 
				context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
		View view = inflater.inflate(R.layout.driving_mode_trigger, null);
		speedEdit = (EditText) view.findViewById(R.id.driving_mode_trigger_prompt_speed_edit);
		String speed = b.getString(SPEED_FIELD);
		if(speed != null){
			speedEdit.setText(speed);
			this.speed = speed;
		}
		return view;
	}

	@Override
	public View getConfigView() {
		// TODO Auto-generated method stub
		return getConfigView(new Bundle());
	}

	@Override
	public boolean trig() {
		return curr_speed>=Float.parseFloat(speed);
	}

	@Override
	public String getConfigString() {
		// TODO Auto-generated method stub
		//speed
		return speed;
	}

	@Override
	public String getIntentAction() {
		return null;
	}

	@Override
	public String getHumanReadableString() {
		// TODO Auto-generated method stub
		return getHumanReadableString(speed);
	}

	@Override
	public String getHumanReadableString(String rule) {
		// TODO Auto-generated method stub
		return "You set "+rule+" as the speed. When your speed exceeds "+rule;
	}

	@Override
	public boolean needPolling() {
		return true;
	}

}
