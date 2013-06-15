package org.nationsatwar.ispy.Events;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.nationsatwar.ispy.ISpy;
import org.nationsatwar.ispy.Trigger;
import org.nationsatwar.ispy.Conditions.ConditionUtility;
import org.nationsatwar.ispy.SerializedObjects.ISpyLocation;
import org.nationsatwar.ispy.SerializedObjects.ISpyRegion;
import org.nationsatwar.ispy.Utility.ConfigParser;

public class RegionEvents extends BukkitRunnable {
	
	protected ISpy plugin;
	
	public RegionEvents(ISpy plugin) {
		
		this.plugin = plugin;
	}

	public void run() {
		
		enterRegionEvent();
		leaveRegionEvent();
		
		for (World world : Bukkit.getServer().getWorlds()) {
			
			String worldName = world.getName();
			
	    	List<Trigger> triggers = EventUtility.getInitiatedTriggers(worldName, EventUtility.regionEnter);
	    	
	    	// Removes triggers that don't match the region parameter
	    	for (Trigger trigger : triggers)
	    		changingRegion(trigger, true, true);
		}
	}
	
	private void enterRegionEvent() {
		
		for (World world : Bukkit.getServer().getWorlds()) {
			
			String worldName = world.getName();
			
	    	List<Trigger> triggers = EventUtility.getInitiatedTriggers(worldName, EventUtility.regionEnter);
	    	
	    	List<Trigger> editedTriggers = new ArrayList<Trigger>();
	    	
	    	// Removes triggers that don't match the region parameter
	    	for (Trigger trigger : triggers) {
	    		
	    		if (changingRegion(trigger, true, false))
	    			editedTriggers.add(trigger);
	    	}
	    	
	    	// Send triggers to check against conditions
	    	ConditionUtility.checkConditions(editedTriggers, plugin);
		}
	}
	
	private void leaveRegionEvent() {
		
		for (World world : Bukkit.getServer().getWorlds()) {
			
			String worldName = world.getName();
			
	    	List<Trigger> triggers = EventUtility.getInitiatedTriggers(worldName, EventUtility.regionLeave);
	    	
	    	List<Trigger> editedTriggers = new ArrayList<Trigger>();
	    	
	    	// Removes triggers that don't match the region parameter
	    	for (Trigger trigger : triggers) {
	    		
	    		if (changingRegion(trigger, false, false))
	    			editedTriggers.add(trigger);
	    	}
	    	
	    	// Send triggers to check against conditions
	    	ConditionUtility.checkConditions(editedTriggers, plugin);
		}
	}
	
	private boolean changingRegion(Trigger trigger, boolean entering, boolean updating) {
		
		// For every region parameter
		for (String regionVariable : trigger.getEventParameters()) {
			
			Object regionObject = ConfigParser.getLiteral(regionVariable, trigger);
			
			// For ever player in the trigger world
			for (Player player : Bukkit.getServer().getWorld(trigger.getWorldName()).getPlayers()) {
				
				String playerName = player.getName();
				Location playerLocation = Bukkit.getServer().getPlayer(playerName).getLocation();
				
				@SuppressWarnings("unchecked")
				Map<String, Object> region = (Map<String, Object>) regionObject;

				Location location1 = ISpyRegion.getLocation1(region);
				Location location2 = ISpyRegion.getLocation2(region);
				
				double originX = (location1.getX() + location2.getX()) / 2;
				double originY = (location1.getY() + location2.getY()) / 2;
				double originZ = (location1.getZ() + location2.getZ()) / 2;
				
				double radiusX = Math.abs(originX - location1.getX());
				double radiusY = Math.abs(originY - location1.getY());
				double radiusZ = Math.abs(originZ - location1.getZ());
				
				ISpyLocation ispyLocation1 = new ISpyLocation(location1);
				ISpyLocation ispyLocation2 = new ISpyLocation(location2);
				
				ISpyRegion iSpyRegion = new ISpyRegion(ispyLocation1, ispyLocation2);
				
				if (Math.abs(playerLocation.getX() - originX) < radiusX && 
						Math.abs(playerLocation.getY() - originY) < radiusY && 
						Math.abs(playerLocation.getZ() - originZ) < radiusZ) {
					
					if (updating || entering) {
						
						if (plugin.triggerManager.containsPlayerRegion(playerName, iSpyRegion.serialize()))
							return false;
						else {
							plugin.triggerManager.addPlayerRegion(playerName, iSpyRegion.serialize());
							trigger.setEnteringPlayer(playerName);
							return true;
						}
					}
				} else {
					
					if (updating || !entering) {
						
						if (plugin.triggerManager.containsPlayerRegion(playerName, iSpyRegion.serialize())) {
							
							plugin.triggerManager.removePlayerRegion(playerName);
							trigger.setLeavingPlayer(playerName);
							return true;
						} else
							return false;
					}
				}
			}
		}
		
		return false;
	}
}