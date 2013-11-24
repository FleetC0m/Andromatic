package io.github.fleetc0m.andromatic;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

/**
 * This is a background worker service created only
 * by the TriggerDispatcher at the boot complete
 * time to poll for event and dispatch trigger.
 * @author manifesto
 *
 */
public class EventMonitor extends Service {

	@Override
	public IBinder onBind(Intent arg0) {
		// TODO Auto-generated method stub
		return null;
	}

}
