package io.github.fleetc0m.andromatic.trigger;

import java.util.Calendar;

import io.github.fleetc0m.andromatic.R;
import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TimePicker;

public class TimedEventTrigger extends Trigger {
	public static final String START_TIME_FIELD = "start time";
	public static final String END_TIME_FIELD = "end time";
	
	private TimePicker startTimeEdit;
	private TimePicker endTimeEdit;
	
	public TimedEventTrigger(){
		super(null);
	}
	
	public TimedEventTrigger(Context context){
		super(context);
	}
	@Override
	public View getConfigView(Bundle b) {
		// TODO Auto-generated method stub
		LayoutInflater inflater = (LayoutInflater) 
				context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
		View view = inflater.inflate(R.layout.timed_event_trigger, null);
		startTimeEdit = (TimePicker) view.findViewById(R.id.timed_event_trigger_prompt_start_time_pick);
		endTimeEdit = (TimePicker) view.findViewById(R.id.timed_event_trigger_prompt_end_time_pick);
		String start_time = b.getString(START_TIME_FIELD);
		if(start_time != null){			
			startTimeEdit.setCurrentHour(Integer.parseInt(start_time.split(":")[0]));
			startTimeEdit.setCurrentMinute(Integer.parseInt(start_time.split(":")[1]));
		}
		String end_time = b.getString(END_TIME_FIELD);
		if(end_time != null){
			endTimeEdit.setCurrentHour(Integer.parseInt(end_time.split(":")[0]));
			endTimeEdit.setCurrentMinute(Integer.parseInt(end_time.split(":")[1]));
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
		// TODO Auto-generated method stub
		Calendar c = Calendar.getInstance();
		int curr_hour = c.get(Calendar.HOUR_OF_DAY);
		int curr_minute = c.get(Calendar.MINUTE);
		int curr_time = curr_hour*60 + curr_minute;
		int start = startTimeEdit.getCurrentHour()*60+startTimeEdit.getCurrentMinute();
		int end = endTimeEdit.getCurrentHour()*60+endTimeEdit.getCurrentMinute();
		if(start<=end){
			if((start<=curr_time)&&(curr_time<=end)){
				return true;
			}else{
				return false;
			}
		}else{
			if((start>=curr_time)&&(curr_time>=end)){
				return true;
			}else{
				return false;
			}
		}
		
	}

	@Override
	public String getConfigString() {
		// TODO Auto-generated method stub
		//the_start_hour:the_start_minite the_end_hour:the_end_minute
		return startTimeEdit.getCurrentHour()+":"+startTimeEdit.getCurrentMinute()+
				" "+endTimeEdit.getCurrentHour()+":"+endTimeEdit.getCurrentMinute();
	}

	@Override
	public String getIntentAction() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getHumanReadableString() {
		// TODO Auto-generated method stub
		return getHumanReadableString(startTimeEdit.getCurrentHour()+":"+startTimeEdit.getCurrentMinute()+
				" "+endTimeEdit.getCurrentHour()+":"+endTimeEdit.getCurrentMinute());
	}

	@Override
	public String getHumanReadableString(String rule) {
		// TODO Auto-generated method stub
		return "from"+rule.split(" ")[0]+" to "+rule.split(" ")[1];
	}

	@Override
	public boolean needPolling() {
		return true;
	}

}
