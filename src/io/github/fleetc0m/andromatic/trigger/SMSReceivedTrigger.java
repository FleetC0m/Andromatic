package io.github.fleetc0m.andromatic.trigger;


import io.github.fleetc0m.andromatic.R;
import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.telephony.PhoneNumberUtils;
import android.telephony.SmsMessage;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

public class SMSReceivedTrigger extends Trigger {
	private EditText phoneNumEdit;
	private EditText keywordEdit;
	private static final String TAG = "SRT";
	
	
	public SMSReceivedTrigger(){
		super(null);
	}
	
	public SMSReceivedTrigger(Context context){
		super(context);
	}
	
	@Override
	public View getConfigView(String savedRule) {
		LayoutInflater inflater = (LayoutInflater) 
				context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
		View view = inflater.inflate(R.layout.sms_received_trigger, null);
		phoneNumEdit = (EditText) view.findViewById(R.id.sms_received_trigger_phone_num_edit);
		keywordEdit = (EditText) view.findViewById(R.id.sms_received_trigger_keyword_edit);		
		if(savedRule==null) return view;
		String phoneNum = savedRule.split(" ",2)[0];
		if(phoneNum != null){
			phoneNumEdit.setText(phoneNum);
		}
		String keyword = savedRule.split(" ",2)[1];
		if(keyword != null){
			keywordEdit.setText(keyword);
		}
		return view;
	}

	@Override
	public View getConfigView() {
		return getConfigView(null);
	}

	@Override
	public boolean trig() {
		//Toast.makeText(null, "received", Toast.LENGTH_LONG).show();
		Bundle b = incomingIntent.getExtras();
		
		SmsMessage[] msgs = null;
		String msg_from="";
		String phone_no = savedRule.split(" ",2)[0];
		String msg = savedRule.split(" ",2)[1];
		if(b!=null){
			Object[] pdus = (Object[])b.get("pdus");
			msgs = new SmsMessage[pdus.length];
			for(int i= 0; i < msgs.length; i ++){
				msgs[i] = SmsMessage.createFromPdu((byte[])pdus[i]);
				msg_from = msgs[i].getOriginatingAddress();
				Log.d(TAG, "from " + msg_from + " phone_no " + phone_no);
				String msgBody = msgs[i].getMessageBody();
				
				if(PhoneNumberUtils.compare(phone_no, msg_from)){
					if(msgBody.indexOf(msg)!=-1){
						return true;
					}
				}
			}
		}
		return false;
	}


	@Override
	public String getConfigString() {
		// TODO Auto-generated method stub
		//phone_num keyword
		savedRule = phoneNumEdit.getText().toString()+" "+keywordEdit.getText().toString();
		return savedRule;
	}

	@Override
	public String getHumanReadableString() {
		// TODO Auto-generated method stub
		return getHumanReadableString(savedRule);
	}

	@Override
	public String getHumanReadableString(String rule) {
		String response =  "When a message from ";
		if(rule.split(" ", 2)[0].equals("")){
			response += "anyone";
		}else{
			response += rule.split(" ", 2)[0];
		}
		
		if(rule.split(" ", 2)[1].equals("")){
			
		}else{
			response += " containing keyword " + rule.split(" ", 2)[1];
		}
		response += " is received";
		return response;
	}

	@Override
	public String getIntentAction() {
		return "android.provider.Telephony.SMS_RECEIVED";
	}

	@Override
	public boolean needPolling() {
		return false;
	}
}
