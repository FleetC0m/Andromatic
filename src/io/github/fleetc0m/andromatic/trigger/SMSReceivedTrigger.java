package io.github.fleetc0m.andromatic.trigger;

import io.github.fleetc0m.andromatic.R;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

public class SMSReceivedTrigger extends Trigger {
	public static final String PHONE_NUM_FIELD = "phone number";
	public static final String KEYWORD_FIELD = "keyword";
	
	private EditText phoneNumEdit;
	private EditText keywordEdit;
	public SMSReceivedTrigger(Context context){
		super(context);
	}
	
	@Override
	public View getConfigView(LayoutInflater inflator,
			Bundle b) {
		View view = inflator.inflate(R.layout.sms_received_trigger, null);
		phoneNumEdit = (EditText) view.findViewById(R.id.sms_received_trigger_phone_num_edit);
		keywordEdit = (EditText) view.findViewById(R.id.sms_received_trigger_keyword_edit);
		String phoneNum = b.getString(PHONE_NUM_FIELD);
		if(phoneNum != null){
			phoneNumEdit.setText(phoneNum);
		}
		String keyword = b.getString(KEYWORD_FIELD);
		if(keyword != null){
			keywordEdit.setText(keyword);
		}
		return view;
	}

	@Override
	public View getEmptyConfigView(LayoutInflater inflator) {
		return getConfigView(inflator, new Bundle());
	}

	@Override
	public boolean trig() {
		
		return false;
	}

	@Override
	public String saveConfig(View configView) {
		// TODO Auto-generated method stub
		return null;
	}
}
