package org.nationsatwar.ispy;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public final class TriggerManager {

	protected final ISpy plugin;
	
	// Key: Player Name | Value: Trigger Path
	private HashMap<String, String> activeTriggers;
	
	// Key: Player Name | Value: Region List
	private HashMap<String, List<Map<String, Object>>> playerRegions;

	public TriggerManager(ISpy plugin) {
		
		this.plugin = plugin;
		
		activeTriggers = new HashMap<String, String>();
		playerRegions = new HashMap<String, List<Map<String, Object>>>();
	}
	
	public String getActiveTrigger(String playerName) {
		
		return activeTriggers.get(playerName);
	}
	
	public void addActiveTrigger(String userName, String triggerName) {
		
		activeTriggers.put(userName, triggerName);
	}
	
	public List<Map<String, Object>> getPlayerRegions(String playerName) {
		
		return playerRegions.get(playerName);
	}
	
	public void addPlayerRegion(String playerName, Map<String, Object> playerRegion) {
		
		if (playerRegions.get(playerName) == null) {
			
			ISpy.log("Adding player region");
			
			List<Map<String, Object>> regionList = new ArrayList<Map<String,Object>>();
			regionList.add(playerRegion);
			
			playerRegions.put(playerName, regionList);
		} else
			playerRegions.get(playerName).add(playerRegion);
	}
	
	public boolean containsPlayerRegion(String playerName, Map<String, Object> playerRegion) {
		
		if (playerRegions.get(playerName) == null)
			return false;
		
		for (Map<String, Object> region : playerRegions.get(playerName)) {
			
			if (playerRegion.equals(region))
				return true;
		}
		
		return false;
	}
	
	public void removePlayerRegion(String playerName) {
		
		playerRegions.remove(playerName);
	}
}