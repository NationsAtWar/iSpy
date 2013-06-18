package org.nationsatwar.ispy.Events;

import java.util.List;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockRedstoneEvent;
import org.bukkit.event.player.PlayerInteractEvent;

import org.nationsatwar.ispy.ISpy;
import org.nationsatwar.ispy.Trigger;
import org.nationsatwar.ispy.Conditions.ConditionUtility;
import org.nationsatwar.ispy.SerializedObjects.ISpyDoor;
import org.nationsatwar.ispy.SerializedObjects.ISpyLocation;

public final class DoorEvents implements Listener {
	
	private Block doorBlock;
	private int blockPower;
	
	protected final ISpy plugin;
    
    public DoorEvents(ISpy plugin) {
    	
        this.plugin = plugin;
    }
    
    @EventHandler
    private void onPlayerInteract(PlayerInteractEvent event) { // Wooden door open event
    	
    	// Get world name
    	String worldName = event.getPlayer().getWorld().getName();
		
		Block clickedBlock = event.getClickedBlock();
    	
		// Cancels event unless the player has right clicked a wooden door
    	if (clickedBlock == null || !clickedBlock.getType().equals(Material.WOODEN_DOOR) ||
    			!event.getAction().equals(Action.RIGHT_CLICK_BLOCK))
    		return;

    	ISpyDoor door = new ISpyDoor(clickedBlock);
    	List<Trigger> triggers;
    	
    	// Get list of all triggers that contain event (Different list dependent on doorOpen or doorClose)
    	if (door.isDoorOpen())
    		triggers = EventUtility.getInitiatedTriggers(worldName, EventUtility.doorClose);
    	else
    		triggers = EventUtility.getInitiatedTriggers(worldName, EventUtility.doorOpen);
    	
    	// Add event properties to each trigger
    	for (Trigger trigger : triggers) {
    		
    		if (clickedBlock != null) {
    			
    			Location blockLocation = clickedBlock.getLocation();
        		
        		if (blockLocation != null)
        			trigger.setBlockLocation(new ISpyLocation(blockLocation));
    		}
    		
    		trigger.setBlockUser(event.getPlayer().getName());
    	}
    	
    	// Send triggers to check against conditions
    	ConditionUtility.checkConditions(triggers, plugin);
    	
    	// Cancel event if specified by action
    	if (plugin.eventUtil.isEventCancelled())
    		event.setCancelled(true);
    }
    
    @EventHandler
    private void onBlockRedstone(BlockRedstoneEvent event) { // Iron door open event
    	
    	// Get world name
    	String worldName = event.getBlock().getWorld().getName();
		
		Block eventBlock = event.getBlock();
    	
		// Cancels event unless the affected block is an iron door
    	if (!eventBlock.getType().equals(Material.IRON_DOOR_BLOCK))
    		return;
    	
    	// Redstone takes a second to become accustomed to it's new power level and thus a delay timer is in order
    	if (plugin.eventUtil.isEventDelayed() && event.getBlock().equals(doorBlock)) {
    		
    		event.setNewCurrent(blockPower);
    		return;
    	}
    	
    	ISpyDoor door = new ISpyDoor(eventBlock);
    	List<Trigger> triggers;
    	
    	// Get list of all triggers that contain event (Different list dependent on doorOpen or doorClose)
    	if (door.isDoorOpen())
    		triggers = EventUtility.getInitiatedTriggers(worldName, EventUtility.doorClose);
    	else
    		triggers = EventUtility.getInitiatedTriggers(worldName, EventUtility.doorOpen);
    	
    	// Add event properties to each trigger
    	for (Trigger trigger : triggers) {
    		
    		if (eventBlock != null) {
    			
    			Location blockLocation = eventBlock.getLocation();
        		
        		if (blockLocation != null)
        			trigger.setBlockLocation(new ISpyLocation(blockLocation));
    		}
    	}
    	
    	// Send triggers to check against conditions
    	ConditionUtility.checkConditions(triggers, plugin);
    	
    	if (plugin.eventUtil.isEventCancelled()) {
    		
    		if (door.isDoorOpen())
        		event.setNewCurrent(15);
    		else
        		event.setNewCurrent(0);
    	}
    	
    	doorBlock = eventBlock;
    	blockPower = event.getNewCurrent();

		plugin.eventUtil.eventDelay = true;
    	DelayTimer delay = new DelayTimer(plugin);
    	delay.runTaskLaterAsynchronously(plugin, 1);
    }
}