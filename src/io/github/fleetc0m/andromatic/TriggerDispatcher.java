package io.github.fleetc0m.andromatic;

import io.github.fleetc0m.andromatic.action.Action;
import io.github.fleetc0m.andromatic.trigger.Trigger;

import java.util.List;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
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
		Log.d(TAG, "received broadcast " + i.getAction());
		if(i.getAction().equals(BOOT_COMPLETED)){
			Intent serviceLauncher = new Intent(c, EventMonitor.class);
			c.startService(serviceLauncher);
		}else {
			SQLHandler sql = new SQLHandler(c);
			List<Bundle> rules = sql.getRulesByIntent(i.getAction());
			for(Bundle b : rules){
				try{
					Trigger t = (Trigger) Class.forName(b.getString(SQLHandler.TRIGGER_CLASS_NAME)).newInstance();
					t.setArgs(i, b.getString(SQLHandler.TRIGGER_RULE));
					//Log.d(TAG, "checking trigger: " + b.getString(SQLHandler.TRIGGER_CLASS_NAME) + " result: " + t.trig());
					if(t.trig()){
						Log.d(TAG, b.getString(SQLHandler.TRIGGER_CLASS_NAME) + " : "
								+ b.getString(SQLHandler.ACTION_CLASS_NAME) + " executed");
						Action a = (Action) Class.forName(b.getString(SQLHandler.ACTION_CLASS_NAME)).newInstance();
						a.setArgs(c, b.getString(SQLHandler.ACTION_RULE));
						a.act();

					}
				}catch(ClassNotFoundException ex){
					ex.printStackTrace();
				}catch(LinkageError ex){
					ex.printStackTrace();
				} catch (InstantiationException e) {
					e.printStackTrace();
				} catch (IllegalAccessException e) {
					e.printStackTrace();
				}
			}
		}
	}

}
