package io.github.fleetc0m.andromatic.trigger;

import java.util.ArrayList;
import java.util.HashMap;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;

/** This is the base class trigger for all triggers.
 *  
 * @author manifesto
 *
 */
public abstract class Trigger {
	protected Intent incomingIntent;
	protected String savedRule;
	protected Context context;
	/**
	 * The class name of the trigger is used by Trigger 
	 * Dispatcher to create an instance of trigger
	 * through dynamic loading.
	 */
	private static final String CLASS_NAME = "io.github.fleetc0m.andromatic.trigger";

	public Trigger(){
		this(null, null);
	}
	public Trigger(Intent incomingIntent, String savedRule){
		this.incomingIntent = incomingIntent;
		this.savedRule = savedRule;
	}
	
	public void setContext(Context context){
		this.context = context;
	}
	
	public Trigger(Context context){
		this.context = context;
	}
	
	/**
	 * Get the view at add new rule fragment based on savedVars provided 
	 * @param inflator
	 * @param savedVars
	 * @return
	 */
	public abstract View getConfigView(Bundle b);
	
	/**
	 * Get an unfilled view at add new rule fragment.
	 * @param inflator
	 * @return
	 */
	public abstract View getConfigView();
	
	/**
	 * Query whether to fire this trigger.
	 * @return
	 */
	public abstract boolean trig();
	/**
	 * Get the string representation of current config in the 
	 * config view. This string is stored in the database as
	 * is, and will be provided to the action class through setArgs.
	 * @param view The view returned by getConfigView()
	 * @return The string representation of config info
	 */
	public abstract String getConfigString();
	
	
	/**
	 * Get the intent action this trigger wants to listen to.
	 * This string will be compared against intent.getAction() 
	 * to decide whether to instantiate this trigger so they must
	 * be exactly the same.
	 * @return
	 */
	public abstract String getIntentAction();
	
	/**
	 * Get the human readable representation of the defined rule.
	 * This string is displayed in the user interface.
	 * @param view
	 * @return
	 */
	public abstract String getHumanReadableString();
	
	/**
	 * Translate the defined rule get from getConfigString to a human
	 * readable string.
	 * @param rule The rule String
	 * @return a human readable string.
	 */
	public abstract String getHumanReadableString(String rule);
}
