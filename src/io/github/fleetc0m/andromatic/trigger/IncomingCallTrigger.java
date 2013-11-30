package io.github.fleetc0m.andromatic.trigger;

import io.github.fleetc0m.andromatic.R;
import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

public class IncomingCallTrigger extends Trigger {

	public static final String PHONE_NUM_FIELD = "phone number";
	public static final String INCOMINGCALL_INTENT = "android.intent.action.PHONE_STATE";

	private EditText phoneEdit;
	
	public IncomingCallTrigger(){
		super(null);
	}
	
	public IncomingCallTrigger(Context context){
		super(context);

	}
	
	
	@Override
	public View getConfigView(String configStr) {
		LayoutInflater inflater = (LayoutInflater) 
				context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
		View view = inflater.inflate(R.layout.incoming_call_trigger, null);
		
		phoneEdit = (EditText) view.findViewById(R.id.incoming_call_trigger_number_edit);
		String phoneNum = configStr;
		
		if(phoneNum != null){
			phoneEdit.setText(phoneNum);
		}
		
		return view;
	}

	@Override
	public View getConfigView() {

		return getConfigView(null);
	}

	@Override
	public boolean trig() {
			if(incomingIntent == null){
				return false;
			}
			
			Bundle bundle = incomingIntent.getExtras();
			String incomingNumber = bundle.getString(TelephonyManager.EXTRA_INCOMING_NUMBER);
			if(incomingNumber != null && savedRule != null && savedRule.equals(incomingNumber)){
				return true;
			}else{
				return false;
			}
		
	}

	@Override
	public String getConfigString() {
		if(phoneEdit == null){
			savedRule = null;
			return null;
		}
		savedRule=phoneEdit.getText().toString();
		return savedRule;
	}

	@Override
	public String getIntentAction() {
		
		return INCOMINGCALL_INTENT;
	}

	@Override
	public String getHumanReadableString() {
		return getHumanReadableString(savedRule);
	}

	@Override
	public String getHumanReadableString(String rule) {
		
		return "When an incoming call phone number: " + rule +  " is received";
	}

	@Override
	public boolean needPolling() {
		return false;
	}
	


}
