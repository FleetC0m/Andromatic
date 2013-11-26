package io.github.fleetc0m.andromatic.action;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;

/**
 * Actions are instantiated by the triggers to perform 
 * a certain operation.
 * @author manifesto
 *
 */
public abstract class Action {
	
	/**
	 * The Action will be instantiated by class name
	 * through dynamic loading.
	 */
	protected static final String CLASS_NAME = "io.github.fleetc0m.andromatic.Action";
	
	protected Context context;
	public void setContext(Context context){
		this.context = context;
	}
	
	/**
	 * Set the argument of this action.
	 * @param arg
	 */
	public abstract void setArgs(String arg);
	
	/**
	 * Execute this action. 
	 * @return true on succeed. false on failure.
	 */
	public abstract boolean act();

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
	 * Get the string representation of current config in the 
	 * config view.
	 * @param view The view returned by getConfigView()
	 * @return The string representation of config info
	 */
	public abstract String saveConfig(View view);
	
}
