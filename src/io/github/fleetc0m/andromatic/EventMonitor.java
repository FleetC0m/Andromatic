package io.github.fleetc0m.andromatic;

import android.app.Service;
import android.content.Intent;
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
		// TODO Auto-generated method stub
		return null;
	}
	
	private static class Poller implements Runnable{

		private static final String TAG = "poller";
		@Override
		public void run() {
			while(true){
				Log.d(TAG, "polling");
				try {
					Thread.sleep(60 * 1 * 1000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}
		
	}

}
