package org.nationsatwar.ispy;

import java.util.logging.Logger;

import org.nationsatwar.ispy.SerializedObjects.ISpyLocation;

public final class Trigger {
	
	Logger log = Logger.getLogger("Minecraft");
	
	private final String triggerName;
	private final String worldName;
	
	private String blockPlacer;
	private String blockBreaker;
	
	private ISpyLocation blockLocation;
	
	public Trigger(String triggerName, String worldName) {
		
		this.triggerName = triggerName;
		this.worldName = worldName;
	}
	
	// Getters
	public String getTriggerName() {
		
		return triggerName;
	}
	
	public String getWorldName() {
		
		return worldName;
	}
	
	public String getBlockPlacer() {
		
		return blockPlacer;
	}
	
	public String getBlockBreaker() {
		
		return blockBreaker;
	}
	
	public ISpyLocation getBlockLocation() {
		
		return blockLocation;
	}
	
	// Setters
	public void setBlockPlacer(String blockPlacer) {
		
		this.blockPlacer = blockPlacer;
	}
	
	public void setBlockBreaker(String blockBreaker) {
		
		this.blockBreaker = blockBreaker;
	}
	
	public void setBlockLocation(ISpyLocation blockLocation) {
		
		this.blockLocation = blockLocation;
	}
}