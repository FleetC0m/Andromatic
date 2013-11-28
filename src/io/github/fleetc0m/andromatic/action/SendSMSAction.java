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
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class SendSMSAction extends Action{
	
	public SendSMSAction(Context context, String savedRule) {
		super(context, savedRule);
	}

	private static final String PHONE_NUM_FIELD = "phone number";
	private static final String MSG_FIELD = "message";
	private EditText phoneNumEdit;
	private EditText msgEdit;

	
	@Override
	public boolean act() {
		String phoneNo = phoneNumEdit.getText().toString();
		String msg = msgEdit.getText().toString();
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
 
        PendingIntent sentPI = PendingIntent.getBroadcast(this.context, 0,
            new Intent(SENT), 0);
 
        PendingIntent deliveredPI = PendingIntent.getBroadcast(this.context, 0,
            new Intent(DELIVERED), 0);
 
        //---when the SMS has been sent---
        context.registerReceiver(new BroadcastReceiver(){
            @Override
            public void onReceive(Context arg0, Intent arg1) {
                switch (getResultCode())
                {
                    case Activity.RESULT_OK:
                        Toast.makeText(context, "SMS sent", 
                                Toast.LENGTH_SHORT).show();
                        break;
                    case SmsManager.RESULT_ERROR_GENERIC_FAILURE:
                        Toast.makeText(context, "Generic failure", 
                                Toast.LENGTH_SHORT).show();
                        break;
                    case SmsManager.RESULT_ERROR_NO_SERVICE:
                        Toast.makeText(context, "No service", 
                                Toast.LENGTH_SHORT).show();
                        break;
                    case SmsManager.RESULT_ERROR_NULL_PDU:
                        Toast.makeText(context, "Null PDU", 
                                Toast.LENGTH_SHORT).show();
                        break;
                    case SmsManager.RESULT_ERROR_RADIO_OFF:
                        Toast.makeText(context, "Radio off", 
                                Toast.LENGTH_SHORT).show();
                        break;
                }
            }
        }, new IntentFilter(SENT));
 
        //---when the SMS has been delivered---
        context.registerReceiver(new BroadcastReceiver(){
            @Override
            public void onReceive(Context arg0, Intent arg1) {
                switch (getResultCode())
                {
                    case Activity.RESULT_OK:
                        Toast.makeText(context, "SMS delivered", 
                                Toast.LENGTH_SHORT).show();
                        break;
                    case Activity.RESULT_CANCELED:
                        Toast.makeText(context, "SMS not delivered", 
                                Toast.LENGTH_SHORT).show();
                        break;                        
                }
            }
        }, new IntentFilter(DELIVERED));        
 
        SmsManager sms = SmsManager.getDefault();
        sms.sendTextMessage(phoneNumber, null, message, sentPI, deliveredPI);        
    }	
	@Override
	public View getConfigView(String savedRule) {
		// TODO Auto-generated method stub
		LayoutInflater i = (LayoutInflater) context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
		View view = i.inflate(R.layout.sms_send_action, null);
		phoneNumEdit = (EditText) view.findViewById(R.id.sms_send_action_prompt_phone_no);
		msgEdit = (EditText) view.findViewById(R.id.sms_send_action_prompt_msg);
		String phoneNum = b.getString(PHONE_NUM_FIELD);
		if(phoneNum != null){
			phoneNumEdit.setText(phoneNum);
		}
		String msg = b.getString(MSG_FIELD);
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
		return phoneNumEdit.getText().toString()+" "+msgEdit.getText().toString();
	}

	@Override
	public String getHumanReadableString(){
		return getHumanReadableString(getConfigString());
	}

	@Override
	public String getHumanReadableString(String rule) {
		// TODO Auto-generated method stub
		String num = rule.split(" ",2)[0];
		String msg = rule.split(" ",2)[1];
		return "you will send the following msg to "+num+":\n"+msg;
	}
	
}
