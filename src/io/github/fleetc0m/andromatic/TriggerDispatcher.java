package io.github.fleetc0m.andromatic;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

/**
 * This class listens the system event (broadcast)
 * and dispatch trigger based on the intent type
 * and registered trigger instantiate triggers 
 * through dynamic loading.
 * @author manifesto
 *
 */
public class TriggerDispatcher extends BroadcastReceiver {
	private static final String BOOT_COMPLETED = "android.intent.action.BOOT_COMPLETED";
	private static final String TAG = "TD";

	@Override
	public void onReceive(Context c, Intent i) {
		if(i.getAction().equals(BOOT_COMPLETED)){
			Intent serviceLauncher = new Intent(c, EventMonitor.class);
			c.startService(serviceLauncher);
		}else {
			
		}
	}

}
