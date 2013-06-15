package org.nationsatwar.ispy.Commands;

import java.io.File;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import org.nationsatwar.ispy.ISpy;

public final class LoadCommand {

	protected final ISpy plugin;
	
	public LoadCommand(ISpy plugin) {
		
		this.plugin = plugin;
	}

	/**
	 * Handles the 'Load' command.
	 * <p>
	 * Will load a trigger into memory for the user who issued the command. All future commands will reference this 
	 * trigger until a new trigger is created or loaded.
	 * 
	 * @param player  Player sending the command
	 * @param triggerName  The name of the trigger to load into memory
	 */
	public final void execute(Player player, String triggerName) {
		
		String worldName = player.getWorld().getName();
		String fullTriggerPath = worldName + ISpy.triggerPath + triggerName + ISpy.triggerExtension;
	    File dataFile = new File(fullTriggerPath);
	    
	    // Return if trigger already exists
	    if (!dataFile.exists()) {
	    	
	    	player.sendMessage(ChatColor.YELLOW + "Can't load: " + triggerName + " - Trigger doesn't exist for this world.");
	    	return;
	    }
	    
	    // Set the trigger as active for this user
    	plugin.triggerManager.addActiveTrigger(player.getName(), fullTriggerPath);
    	
    	player.sendMessage(ChatColor.YELLOW + triggerName + " has been loaded.");
	}
}