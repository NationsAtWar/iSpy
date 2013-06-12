package org.nationsatwar.ispy;

import org.bukkit.Location;

public class Trigger {
	
	private String triggerName;
	private String worldName;

	private String playerName;
	private Location blockLocation;
	
	public Trigger(String triggerName, String worldName) {
		
		this.triggerName = triggerName;
		this.worldName = worldName;
	}
	
	public String getTriggerName() {
		
		return triggerName;
	}
	
	public String getWorldName() {
		
		return worldName;
	}
	
	public String getPlayerName() {
		
		return playerName;
	}
	
	public Location getBlockLocation() {
		
		return blockLocation;
	}
	
	public void setPlayerName(String playerName) {
		
		this.playerName = playerName;
	}
	
	public void setBlockLocation(Location blockLocation) {
		
		this.blockLocation = blockLocation;
	}
}