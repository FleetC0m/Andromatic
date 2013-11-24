package io.github.fleetc0m.andromatic.trigger;

import java.util.ArrayList;
import java.util.HashMap;

import android.content.Intent;
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
	
	/**
	 * Get the view at add new rule fragment based on savedVars provided 
	 * @param inflator
	 * @param savedVars
	 * @return
	 */
	public abstract View getConfigView(LayoutInflater inflator, HashMap<String, String> savedVars);
	
	/**
	 * Get an unfilled view at add new rule fragment.
	 * @param inflator
	 * @return
	 */
	public abstract View getEmptyConfigView(LayoutInflater inflator);
	
	/**
	 * Let the action happen
	 * @return true on success. false on fail.
	 */
	public abstract boolean trig();
	
	/**
	 * Get the string representation of the info 
	 * in the configView which can be used to write into
	 * the database. 
	 * @param configView This should be the same view
	 * get from getConfigView or getEmptyConfigView
	 * @return the 
	 */
	public abstract String saveConfig(View configView);
}
