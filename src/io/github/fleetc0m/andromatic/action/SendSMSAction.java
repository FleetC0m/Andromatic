package io.github.fleetc0m.andromatic.action;

import io.github.fleetc0m.andromatic.R;
import android.app.Activity;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class SendSMSAction extends Action{

	private static final String TAG = "SSA";
	public SendSMSAction(Context context, String savedRule) {
		super(context, savedRule);
	}

	private EditText phoneNumEdit;
	private EditText msgEdit;

	public SendSMSAction(){
		
	}
	
	@Override
	public boolean act() {
		String phoneNo = savedRule.split(" ")[0];
		String msg = savedRule.split(" ")[1];
		if(phoneNo.length()>0&&msg.length()>0){
			sendSMS(phoneNo,msg);
			return true;
		}else{
			return false;
		}
	}

	private void sendSMS(String phoneNumber, String message)
    {   
		//http://learnandroideasily.blogspot.in/2012/11/how-to-send-sms.html
        String SENT = "SMS_SENT";
        String DELIVERED = "SMS_DELIVERED";
 
        //PendingIntent sentPI = PendingIntent.getBroadcast(this.context, 0,
          //  new Intent(SENT), 0);
 
       // PendingIntent deliveredPI = PendingIntent.getBroadcast(this.context, 0,
         //   new Intent(DELIVERED), 0);

        SmsManager sms = SmsManager.getDefault();
        sms.sendTextMessage(phoneNumber, null, message, null, null);        
        Log.d(TAG, phoneNumber + ": " + message + " sent.");
    }	
	
	@Override
	public View getConfigView(String savedRule) {
		// TODO Auto-generated method stub
		LayoutInflater i = (LayoutInflater) context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
		View view = i.inflate(R.layout.sms_send_action, null);
		phoneNumEdit = (EditText) view.findViewById(R.id.sms_send_action_prompt_phone_no);
		msgEdit = (EditText) view.findViewById(R.id.sms_send_action_prompt_msg);
		if(savedRule==null) return view;
		String phoneNum = savedRule.split(" ",2)[0];
		if(phoneNum != null){
			phoneNumEdit.setText(phoneNum);
		}
		String msg = savedRule.split(" ",2)[1];
		if(msg != null){
			msgEdit.setText(msg);
		}
		return view;
	}

	@Override
	public View getConfigView(){
		return getConfigView(null);
	}

	@Override
	public String getConfigString(){
		// phone_num msg
		savedRule= phoneNumEdit.getText().toString()+" "+msgEdit.getText().toString();
		return savedRule;
	}

	@Override
	public String getHumanReadableString(){
		String savedRule = getConfigString();
		return getHumanReadableString(savedRule);
	}

	@Override
	public String getHumanReadableString(String rule) {
		// TODO Auto-generated method stub
		String num = rule.split(" ",2)[0];
		String msg = rule.split(" ",2)[1];
		return "you will send the following msg to "+num+":\n"+msg;
	}
	
}
