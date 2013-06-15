package org.nationsatwar.ispy;

import java.util.logging.Logger;

import org.bukkit.plugin.java.JavaPlugin;
import org.nationsatwar.ispy.Events.BlockEvents;
import org.nationsatwar.ispy.Events.RegionEvents;
import org.nationsatwar.ispy.Utility.CommandParser;

/**
 * The iSpy parent class.
 * <p>
 * Custom scripting plugin for Minecraft
 * 
 * @author Aculem
 */
public final class ISpy extends JavaPlugin {
	
	public final CommandParser commandParser = new CommandParser(this);;
	public final TriggerManager triggerManager = new TriggerManager(this);;
	
	// iSpy Path Details
	public static final String triggerPath = "/triggers/";
	public static final String triggerExtension = ".yml";
	public static final String configNamePath = "trigger.name";
	public static final String configActivePath = "trigger.active";
	public static final String configCounterPath = "trigger.counter";
	public static final String configVariablesGlobalPath = "trigger.variables.global";
	public static final String configVariablesLocalPath = "trigger.variables.local";
	public static final String configEventsPath = "trigger.events";
	public static final String configConditionsPath = "trigger.conditions";
	public static final String configActionsPath = "trigger.actions";
	public static final String configRecordPath = "trigger.record";
	
	private static final Logger log = Logger.getLogger("Minecraft");

	/**
	 * Initializes the plugin on server startup.
	 */
	public void onEnable() {
    	
		new RegionEvents(this).runTaskTimer(this, 0, 20);
		
		// Set Command Executor
    	getCommand("ispy").setExecutor(commandParser);
		
    	// Register Events
		getServer().getPluginManager().registerEvents(new BlockEvents(this), this);
    	
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
	public static void log(String logMessage) {
		
		log.info("iSpy: " + logMessage);
	}
}