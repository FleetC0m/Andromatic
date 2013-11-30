package io.github.fleetc0m.andromatic;

import java.util.List;

import io.github.fleetc0m.andromatic.action.Action;
import io.github.fleetc0m.andromatic.trigger.Trigger;
import android.app.Service;
import android.content.Intent;
import android.os.Bundle;
import android.os.IBinder;
import android.util.Log;

/**
 * This is a background worker service created only
 * by the TriggerDispatcher at the boot complete
 * time to poll for event and dispatch trigger.
 * @author manifesto
 *
 */
public class EventMonitor extends Service {

	private static final String TAG = "EM";
	public EventMonitor(){
		
	}
	
	@Override
	public void onCreate(){
		super.onCreate();
	}

	@Override 
	public int onStartCommand(Intent intent, int flags, int startId){
		//Log.d(TAG, "on start Command.");
		super.onStartCommand(intent, flags, startId);
		new Thread(new Poller()).start();
		return Service.START_FLAG_REDELIVERY;
	}
	
	@Override
	public IBinder onBind(Intent arg0) {
		throw new RuntimeException("This service does not allow binding");
	}
	
	private class Poller implements Runnable{
		private List<Bundle> ruleList;
		private SQLHandler sql;
		public Poller(){
			sql = new SQLHandler(EventMonitor.this);
		}
		
		private static final String TAG = "poller";
		@Override
		public void run() {
			while(true){
				Log.d(TAG, "polling");
				ruleList.clear();
				ruleList.addAll(sql.getRulesByPollingType(true));
				for(final Bundle b : ruleList){
					new Executor(b).start();
				}
				try {
					Thread.sleep(60 * 1 * 1000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}
		
	}
	
	private class Executor extends Thread{
		private Bundle b;
		public Executor(Bundle b){
			this.b = b;
		}
		
		@Override
		public void run(){
			try {
				Trigger t = (Trigger) Class.forName(b.getString(SQLHandler.TRIGGER_CLASS_NAME)).newInstance();
				t.setArgs(null, b.getString(SQLHandler.TRIGGER_RULE));
				if(t.trig()){
					Action a = (Action)Class.forName(b.getString(SQLHandler.ACTION_CLASS_NAME)).newInstance();
					a.setArgs(EventMonitor.this, b.getString(SQLHandler.ACTION_RULE));
					a.act();
					Log.d(TAG, b.getString(SQLHandler.TRIGGER_CLASS_NAME) + " : " +
							b.getString(SQLHandler.ACTION_CLASS_NAME) +  "executed");
				}
			} catch (InstantiationException e) {
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			}
		}
	}

}
