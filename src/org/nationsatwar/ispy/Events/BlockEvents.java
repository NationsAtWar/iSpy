package org.nationsatwar.ispy.Events;

import java.util.List;

import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.player.PlayerInteractEvent;

import org.nationsatwar.ispy.ISpy;
import org.nationsatwar.ispy.Trigger;
import org.nationsatwar.ispy.Conditions.ConditionUtility;
import org.nationsatwar.ispy.SerializedObjects.ISpyLocation;

public final class BlockEvents implements Listener {
	
	protected final ISpy plugin;
    
    public BlockEvents(ISpy plugin) {
    	
        this.plugin = plugin;
    }
    
    @EventHandler
    public void onBlockPlace(BlockPlaceEvent event) {
    	
    	// Get world name
    	String worldName = event.getPlayer().getWorld().getName();
    	
    	// Get list of all triggers that contain event
    	List<Trigger> triggers = EventUtility.getInitiatedTriggers(worldName, EventUtility.blockPlace);
    	
    	// Add event properties to each trigger
    	for (Trigger trigger : triggers) {
    		
    		trigger.setBlockLocation(new ISpyLocation(event.getBlock().getLocation()));
    		trigger.setBlockPlacer(event.getPlayer().getName());
    	}
    	
    	// Send triggers to check against conditions
    	ConditionUtility.checkConditions(triggers, plugin);
    }
    
    @EventHandler
    private void onBlockBreak(BlockBreakEvent event) {
    	
    	// Get world name
    	String worldName = event.getPlayer().getWorld().getName();
    	
    	// Get list of all triggers that contain event
    	List<Trigger> triggers = EventUtility.getInitiatedTriggers(worldName, EventUtility.blockBreak);
    	
    	// Add event properties to each trigger
    	for (Trigger trigger : triggers) {
    		
    		trigger.setBlockLocation(new ISpyLocation(event.getBlock().getLocation()));
    		trigger.setBlockBreaker(event.getPlayer().getName());
    	}
    	
    	// Send triggers to check against conditions
    	ConditionUtility.checkConditions(triggers, plugin);
    }
    
    @EventHandler
    private void onPlayerInteract(PlayerInteractEvent event) {
    	
    	// Get world name
    	String worldName = event.getPlayer().getWorld().getName();
    	
    	// Get list of all triggers that contain event
    	List<Trigger> triggers = EventUtility.getInitiatedTriggers(worldName, EventUtility.blockUse);
    	
    	// Add event properties to each trigger
    	for (Trigger trigger : triggers) {
    		
    		Block clickedBlock = event.getClickedBlock();
    		
    		if (clickedBlock != null) {
    			
    			Location blockLocation = clickedBlock.getLocation();
        		
        		if (blockLocation != null)
        			trigger.setBlockLocation(new ISpyLocation(blockLocation));
    		}
    		
    		trigger.setBlockUser(event.getPlayer().getName());
    	}
    	
    	// Send triggers to check against conditions
    	ConditionUtility.checkConditions(triggers, plugin);
    }
}