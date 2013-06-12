package org.nationsatwar.ispy.Events;

import java.util.List;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;

import org.nationsatwar.ispy.ISpy;
import org.nationsatwar.ispy.Trigger;
import org.nationsatwar.ispy.Conditions.ConditionUtility;

public class BlockEvents implements Listener {
	
	protected final ISpy plugin;
    
    public BlockEvents(ISpy plugin) {
    	
        this.plugin = plugin;
    }
    
    @EventHandler
    public void onBlockPlace(BlockPlaceEvent event) {
    	
    	// Get world name
    	String worldName = event.getPlayer().getWorld().getName();
    	
    	// Get list of all triggers that contain event
    	List<Trigger> triggers = EventUtility.initiateTriggers(worldName, EventUtility.blockPlace);
    	
    	// Add event properties to each trigger
    	for (Trigger trigger : triggers) {
    		
    		trigger.setBlockLocation(event.getBlock().getLocation());
    		trigger.setPlayerName(event.getPlayer().getName());
    	}
    	
    	// Send triggers to check against conditions
    	for (Trigger trigger : triggers)
    		if (ConditionUtility.checkConditions(trigger))
    			plugin.log("Boo yeah baby");
    }
    
    @EventHandler
    private void onBlockBreak(BlockBreakEvent event) {
    	
    	plugin.log("LOL");
    }
}