package org.nationsatwar.ispy.Commands;

import java.io.File;
import java.io.IOException;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import org.nationsatwar.ispy.ISpy;
import org.nationsatwar.ispy.SerializedObjects.ISpyBlock;
import org.nationsatwar.ispy.SerializedObjects.ISpyDoor;
import org.nationsatwar.ispy.SerializedObjects.ISpyEntity;
import org.nationsatwar.ispy.SerializedObjects.ISpyItemStack;
import org.nationsatwar.ispy.SerializedObjects.ISpyLocation;
import org.nationsatwar.ispy.SerializedObjects.ISpyRegion;
import org.nationsatwar.ispy.Utility.Debugger;

public final class RecordCommand {

	protected final ISpy plugin;
	
	public RecordCommand(ISpy plugin) {
		
		this.plugin = plugin;
	}

	/**
	 * Handles the 'Record' command.
	 * <p>
	 * This will record information dependent on the command data entered that related to the 
	 * player sending the command.
	 * <p>
	 * <u>Command Data: Location, Item, Block</u><br>
	 * <b>Location</b>: The current location of the player's targeted block<br>
	 * <b>Item</b>: The current selected item of the player<br>
	 * <b>Block</b>: The block data of the player's targeted block<br>
	 * <b>Entity</b>: The entity data of the player's targeted entity<br>
	 * 
	 * @param player  Player sending the command
	 * @param commandData  The data of the record command to respond to
	 */
	public final void execute(Player player, String commandData) {
		
		String fullTriggerPath = plugin.triggerManager.getActiveTrigger(player.getName());
		
		if (fullTriggerPath == null) {
			
			player.sendMessage(ChatColor.YELLOW + "There is currently no active trigger.");
			return;
		}
		
	    File triggerFile = new File(fullTriggerPath);
	    
	    // Return if active trigger doesn't exists
	    if (!triggerFile.exists()) {
	    	
	    	Debugger.triggerDoesntExist(triggerFile.getName());
	    	return;
	    }
	    
	    commandData = commandData.toLowerCase();
	    FileConfiguration config = YamlConfiguration.loadConfiguration(triggerFile);
	    
	    if (commandData.equals("location")) {
	    	
	    	ISpyLocation location = new ISpyLocation(player.getTargetBlock(null, 10).getLocation());
	    	config.set(ISpy.configRecordPath, location.serialize());
	    	
	    	player.sendMessage(ChatColor.YELLOW + "The targeted block's location has been recorded.");
	    }
	    
	    else if (commandData.equals("item")) {
	    	
	    	ISpyItemStack itemstack = new ISpyItemStack(player.getItemInHand());
	    	config.set(ISpy.configRecordPath, itemstack.serialize());
	    	
	    	player.sendMessage(ChatColor.YELLOW + "The active item has been recorded.");
	    }
	    
	    else if (commandData.equals("block")) {
	    	
	    	ISpyBlock block = new ISpyBlock(player.getTargetBlock(null, 10));
	    	config.set(ISpy.configRecordPath, block.serialize());
	    	
	    	player.sendMessage(ChatColor.YELLOW + "The targeted block has been recorded.");
	    }
	    
	    else if (commandData.equals("entity")) {
	    	
	    	ISpyEntity entity = new ISpyEntity(player);
	    	config.set(ISpy.configRecordPath, entity.serialize());
	    	
	    	player.sendMessage(ChatColor.YELLOW + "The targeted entity has been recorded.");
	    }
	    
	    else if (commandData.equals("door")) {
	    	
	    	Block block = player.getTargetBlock(null, 10);
	    	
	    	if (block.getType().equals(Material.WOODEN_DOOR) || block.getType().equals(Material.IRON_DOOR_BLOCK)) {
    			
    			ISpyDoor door = new ISpyDoor(block);
    	    	config.set(ISpy.configRecordPath, door.serialize());
    	    	
    	    	player.sendMessage(ChatColor.YELLOW + "The targeted door has been recorded.");
    	    	
	    	} else
		    	player.sendMessage(ChatColor.YELLOW + "You need to target a door to record this value.");
	    }
	    
	    else if (commandData.equals("debug")) {

	    	ISpyLocation location1 = new ISpyLocation(player.getTargetBlock(null, 10).getLocation());
	    	ISpyLocation location2 = new ISpyLocation(new Location(plugin.getServer().getWorld("world"), 10, 70, 10));
	    	
	    	ISpyRegion region = new ISpyRegion(location1, location2);
	    	
	    	config.set(ISpy.configRecordPath, region.serialize());
	    	
	    	player.sendMessage(ChatColor.YELLOW + "The targeted entity has been recorded.");
	    }
	    
	    else
	    	player.sendMessage(ChatColor.YELLOW + commandData + " is not a valid argument.");
    	
    	try { config.save(triggerFile); }
	    catch (IOException e) { ISpy.log("Error saving config file: " + e.getMessage()); }
	}
}