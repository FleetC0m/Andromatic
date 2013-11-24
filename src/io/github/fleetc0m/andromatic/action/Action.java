package io.github.fleetc0m.andromatic.action;

import android.os.Bundle;

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
}
