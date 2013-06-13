package org.nationsatwar.ispy;

import java.util.HashMap;

public final class TriggerManager {

	protected final ISpy plugin;
	
	// Key: user name | Value: Trigger Name
	private HashMap<String, String> activeTriggers;

	public TriggerManager(ISpy plugin) {
		
		this.plugin = plugin;
		
		activeTriggers = new HashMap<String, String>();
	}
	
	public String getActiveTrigger(String userName) {
		
		return activeTriggers.get(userName);
	}
	
	public void setActiveTrigger(String userName, String triggerName) {
		
		activeTriggers.put(userName, triggerName);
	}
}