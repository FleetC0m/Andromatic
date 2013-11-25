package io.github.fleetc0m.andromatic.action;

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
	
	/**
	 * Set the argument of this action.
	 * @param arg
	 */
	public abstract void setArgs(Bundle arg);
	
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
	public abstract View getConfigView(LayoutInflater inflator, Bundle b);
	
	/**
	 * Get an unfilled view at add new rule fragment.
	 * @param inflator
	 * @return
	 */
	public abstract View getEmptyConfigView(LayoutInflater inflator);
	
}
