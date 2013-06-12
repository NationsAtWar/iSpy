package org.nationsatwar.ispy.Commands;

import java.io.File;
import java.io.IOException;
import java.util.logging.Logger;

import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.FileConfigurationOptions;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import org.nationsatwar.ispy.ISpy;

public class CreateCommand {

	protected final ISpy plugin;
	
	private static Logger log = Logger.getLogger("Minecraft");
	private static String lineBreak = "\r\n";
	
	public CreateCommand(ISpy plugin) {
		
		this.plugin = plugin;
	}

	/**
	 * Handles the 'Create' command.
	 * <p>
	 * Will create a new trigger for the developer to manipulate. This will create a config file
	 * in the '(worldname)/triggers' directory.
	 * 
	 * @param sender  Person sending the command
	 * @param args  String of arguments associated with the command
	 */
	public void execute(Player player, String worldName, String triggerName) {
		
	    File dataFile = new File(worldName + "/triggers/" + triggerName + ".yml");
	    
	    // Return if trigger already exists
	    if (dataFile.exists()) {
	    	
	    	player.sendMessage(ChatColor.YELLOW + "Trigger name: " + triggerName + " already exists for this world.");
	    	return;
	    }
		
	    FileConfiguration config = YamlConfiguration.loadConfiguration(dataFile);
	    FileConfigurationOptions configOptions = config.options();

	    // Creates default config parameters on creation
	    config.addDefault(ISpy.triggerNamePath, triggerName);
	    config.addDefault(ISpy.triggerEventsPath + ".placeholder", "Event Name");
	    config.addDefault(ISpy.triggerConditionsPath + ".placeholder", "Condition Name");
	    config.addDefault(ISpy.triggerActionsPath + ".placeholder", "Action Name");
	    configOptions.copyDefaults(true);
	    
	    // Add header to config file
	    String header = "iSpy Trigger Config File" + lineBreak;
	    configOptions.header(header);
	    configOptions.copyHeader(true);
	    
	    try {
	    	
	    	// Save Config file and end command
	    	config.save(dataFile);
	    	player.sendMessage(ChatColor.YELLOW + "Trigger name: '" + triggerName + "' created.");
	    	player.sendMessage(ChatColor.YELLOW + "Edit '" + dataFile.getPath() + "' to start scripting your trigger.");
	    	
	    } catch (IOException e) { log.info("Error saving config file: " + e.getMessage()); }
	}
}