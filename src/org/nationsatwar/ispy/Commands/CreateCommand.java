package org.nationsatwar.ispy.Commands;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.FileConfigurationOptions;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import org.nationsatwar.ispy.ISpy;

public final class CreateCommand {

	protected final ISpy plugin;
	
	private static String lineBreak = "\r\n";
	
	public CreateCommand(ISpy plugin) {
		
		this.plugin = plugin;
	}

	/**
	 * Handles the 'Create' command.
	 * <p>
	 * Will create a new trigger for the developer to manipulate. This will create a config file
	 * in the '(worldname)/triggers/' directory.
	 * 
	 * @param player  Person sending the command
	 * @param worldName  The world of the player sending the command
	 * @param triggerName  The name to call the new trigger
	 */
	public final void execute(Player player, String triggerName) {
		
		String worldName = player.getWorld().getName();
	    File dataFile = new File(worldName + ISpy.triggerPath + triggerName + ISpy.triggerExtension);
	    
	    // Return if trigger already exists
	    if (dataFile.exists()) {
	    	
	    	player.sendMessage(ChatColor.YELLOW + "Trigger name: " + triggerName + " already exists for this world.");
	    	return;
	    }
		
	    FileConfiguration config = YamlConfiguration.loadConfiguration(dataFile);
	    FileConfigurationOptions configOptions = config.options();

	    // Creates default config parameters on creation
	    
	    List<String> eventPlaceholder = new ArrayList<String>();
	    eventPlaceholder.add("New Event Here");
	    List<String> conditionPlaceholder = new ArrayList<String>();
	    conditionPlaceholder.add("New Condition Here");
	    List<String> actionPlaceholder = new ArrayList<String>();
	    actionPlaceholder.add("New Action Here");
	    
	    config.addDefault(ISpy.configNamePath, triggerName);
	    config.addDefault(ISpy.configVariablesGlobalPath + ".someGlobal", "null");
	    config.addDefault(ISpy.configVariablesLocalPath + ".someLocal", "null");
	    config.addDefault(ISpy.configEventsPath, eventPlaceholder);
	    config.addDefault(ISpy.configConditionsPath, conditionPlaceholder);
	    config.addDefault(ISpy.configActionsPath, actionPlaceholder);
	    
	    configOptions.copyDefaults(true);
	    
	    // Add header to config file
	    String header = "iSpy Trigger Config File" + lineBreak;
	    configOptions.header(header);
	    configOptions.copyHeader(true);
	    
	    // Save the file
	    try { config.save(dataFile); }
	    catch (IOException e) { ISpy.log("Error saving config file: " + e.getMessage()); }
	    
	    // Set the trigger to active for this user
    	plugin.triggerManager.setActiveTrigger(player.getName(), triggerName);
    	
    	player.sendMessage(ChatColor.YELLOW + "Trigger name: '" + triggerName + "' created.");
    	player.sendMessage(ChatColor.YELLOW + "Edit '" + dataFile.getPath() + "' to start scripting your trigger.");
	}
}