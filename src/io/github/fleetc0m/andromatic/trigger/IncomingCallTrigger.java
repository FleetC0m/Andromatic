package io.github.fleetc0m.andromatic.trigger;

import io.github.fleetc0m.andromatic.R;
import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

public class IncomingCallTrigger extends Trigger {

	public static final String PHONE_NUM_FIELD = "phone number";
	
	private EditText phoneNumber;
	
	public IncomingCallTrigger(){
		super(null);
	}
	
	public IncomingCallTrigger(Context context){
		super(context);
	}
	
	
	@Override
	public View getConfigView(Bundle b) {
		LayoutInflater inflater = (LayoutInflater) 
				context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
		View view = inflater.inflate(R.layout.incoming_call_trigger, null);
		
		phoneNumber = (EditText) view.findViewById(R.id.incoming_call_trigger_number_edit);
		String phoneNum = b.getString(PHONE_NUM_FIELD);
		
		if(phoneNum != null){
			phoneNumber.setText(phoneNum);
		}
		
		return view;
	}

	@Override
	public View getConfigView() {

		return getConfigView(new Bundle());
	}

	@Override
	public boolean trig() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public String getConfigString() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getIntentAction() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getHumanReadableString() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getHumanReadableString(String rule) {
		// TODO Auto-generated method stub
		return null;
	}

}
