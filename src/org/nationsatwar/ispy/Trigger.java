package org.nationsatwar.ispy;

import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

import org.nationsatwar.ispy.SerializedObjects.ISpyLocation;

public final class Trigger {
	
	Logger log = Logger.getLogger("Minecraft");
	
	private final String triggerName;
	private final String worldName;
	
	private String eventTrigger;
	private List<String> eventParameters;
	
	private String blockPlacer;
	private String blockBreaker;
	private String blockUser;
	
	private String enteringPlayer;
	private String leavingPlayer;
	
	private ISpyLocation blockLocation;
	
	public Trigger(String triggerName, String worldName) {
		
		this.triggerName = triggerName;
		this.worldName = worldName;
	}
	
	// Getters
	public String getTriggerFileName() {
		
		return worldName + ISpy.triggerPath + triggerName + ISpy.triggerExtension;
	}
	
	public String getTriggerName() {
		
		return triggerName;
	}
	
	public String getEventTrigger() {
		
		return eventTrigger;
	}
	
	public List<String> getEventParameters() {
		
		return eventParameters;
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
	
	public String getBlockUser() {
		
		return blockUser;
	}
	
	public String getEnteringPlayer() {
		
		return enteringPlayer;
	}
	
	public String getLeavingPlayer() {
		
		return leavingPlayer;
	}
	
	public Map<?, ?> getBlockLocation() {
		
		if (blockLocation != null)
			return blockLocation.serialize();
		else
			return null;
	}
	
	// Setters
	public void setEventTrigger(String eventTrigger) {
		
		this.eventTrigger = eventTrigger;
	}

	public void addEventParameters(List<String> eventParameters) {
		
		this.eventParameters = eventParameters;
	}
	
	public void setBlockPlacer(String blockPlacer) {
		
		this.blockPlacer = blockPlacer;
	}
	
	public void setBlockBreaker(String blockBreaker) {
		
		this.blockBreaker = blockBreaker;
	}
	
	public void setBlockUser(String blockUser) {
		
		this.blockUser = blockUser;
	}
	
	public void setEnteringPlayer(String enteringPlayer) {
		
		this.enteringPlayer = enteringPlayer;
	}
	
	public void setLeavingPlayer(String leavingPlayer) {
		
		this.leavingPlayer = leavingPlayer;
	}
	
	public void setBlockLocation(ISpyLocation blockLocation) {
		
		this.blockLocation = blockLocation;
	}
}