package io.github.fleetc0m.andromatic;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

/**
 * This class listens the system event (broadcast)
 * and dispatch trigger based on the intent type
 * and registered trigger instantiate triggers 
 * through dynamic loading.
 * @author manifesto
 *
 */
public class TriggerDispatcher extends BroadcastReceiver {

	@Override
	public void onReceive(Context arg0, Intent arg1) {
		// TODO Auto-generated method stub

	}

}
