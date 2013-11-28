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
	protected String savedRule;
	
	public Action(){
		this(null,null);
	}
	
	public Action(Context context, String savedRule){
		this.context = context;
		this.savedRule = savedRule;
	}
	
	public void setContext(Context context){
		this.context = context;
	}
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
	public abstract View getConfigView(String savedRule);
	
	/**
	 * Get an unfilled view at add new rule fragment.
	 * @param inflator
	 * @return
	 */
	public abstract View getConfigView();
	
	/**
	 * Get the string representation of current config in the 
	 * config view. This string is stored in the database as
	 * is, and will be provided to the action class through setArgs.
	 * @param view The view returned by getConfigView()
	 * @return The string representation of config info
	 */
	public abstract String getConfigString();
	
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
