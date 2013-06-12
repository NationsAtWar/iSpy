package org.nationsatwar.ispy;

import java.util.logging.Logger;

import org.bukkit.plugin.java.JavaPlugin;
import org.nationsatwar.ispy.Events.BlockEvents;

/**
 * The iSpy parent class.
 * <p>
 * Custom scripting plugin for Minecraft
 * 
 * @author Aculem
 */
public class ISpy extends JavaPlugin {
	
	private final Logger log = Logger.getLogger("Minecraft");
	
	protected CommandParser commandParser;
	
	// iSpy Config Defaults
	public static String triggerNamePath = "trigger.name";
	public static String triggerEventsPath = "trigger.events";
	public static String triggerConditionsPath = "trigger.conditions";
	public static String triggerActionsPath = "trigger.actions";

	/**
	 * Initializes the plugin on server startup.
	 */
	public void onEnable() {
		
		getServer().getPluginManager().registerEvents(new BlockEvents(this), this);
		
		commandParser = new CommandParser(this);
		
    	getCommand("ispy").setExecutor(commandParser);
    	
    	log("iSpy has been enabled.");
	}

	/**
	 * Handles the plugin on server stop.
	 */
	public void onDisable() {
		
		log("iSpy has been disabled.");
	}

	/**
	 * Plugin logger handler. Useful for debugging.
	 * 
	 * @param logMessage  Message to send to the console.
	 */
	public void log(String logMessage) {
		
		log.info("iSpy: " + logMessage);
	}
}