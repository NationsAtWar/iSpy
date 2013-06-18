package org.nationsatwar.ispy.Events;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.nationsatwar.ispy.ISpy;
import org.nationsatwar.ispy.Trigger;

public final class EventUtility {
	
	ISpy plugin;
	
	// Event Names
	public static String blockPlace = "block.place";
	public static String blockBreak = "block.break";
	public static String blockUse = "block.use";
	
	public static String regionEnter = "region.enter";
	public static String regionLeave = "region.leave";
	
	public static String doorOpen = "door.open";
	public static String doorClose = "door.close";
	
	// Event Variables
	public static String eventBlockPlacer = "event.blockPlacer";
	public static String eventBlockBreaker = "event.blockBreaker";
	public static String eventBlockUser = "event.blockUser";
	public static String eventBlockLocation = "event.blockLocation";
	
	// Miscellaneous
	private boolean cancelEvent = false;
	public boolean eventDelay = false;
	
	public EventUtility(ISpy plugin) {
		
		this.plugin = plugin;
	}
	
	public void setEventCancellation() {
		
		cancelEvent = true;
	}
	
	public boolean isEventCancelled() {
		
		if (cancelEvent) {
			
			cancelEvent = false;
			return true;
		}
		
		return false;
	}
	
	public boolean isEventDelayed() {
		
		return eventDelay;
	}
	
	public static List<Trigger> getInitiatedTriggers(String worldName, String eventName) {
		
		List<Trigger> triggers = new ArrayList<Trigger>();
		
		File triggerDirectory = new File(worldName + ISpy.triggerPath);
		
		if (!triggerDirectory.exists())
			return triggers;

		for (File triggerFile : triggerDirectory.listFiles()) {
			
			FileConfiguration triggerConfig = YamlConfiguration.loadConfiguration(triggerFile);
			
			boolean triggerActive = triggerConfig.getBoolean(ISpy.configActivePath); // Trigger must be active
			int triggerCounter = triggerConfig.getInt(ISpy.configCounterPath); // Counter must not be 0
			
			// If config file exists, is active, and the counter is not at 0 - continue
			if (triggerConfig != null && triggerActive && triggerCounter != 0) {

				// If event list contains the event name, parse parameters and add trigger to list
				for (String event : triggerConfig.getStringList(ISpy.configEventsPath)) {
					
					if (event.length() < eventName.length())
						continue;
					
					if (event.substring(0, eventName.length()).equals(eventName)) {
						
						Trigger trigger = new Trigger(triggerFile.getName().replaceFirst("[.][^.]+$", ""), worldName);
						trigger.setEventTrigger(eventName);
						trigger.addEventParameters(parseParameters(event.substring(eventName.length())));
						triggers.add(trigger);
					}
				}
			}
		}
		
		return triggers;
	}
	
	public static Object getEventVariable(String property, Trigger trigger) {
		
		if (property.equals(eventBlockPlacer))
			return trigger.getBlockPlacer();
		if (property.equals(eventBlockBreaker))
			return trigger.getBlockBreaker();
		if (property.equals(eventBlockUser))
			return trigger.getBlockUser();
		else if (property.equals(eventBlockLocation))
			return trigger.getBlockLocation();
		
		return property;
	}
	
	private static List<String> parseParameters(String parameter) {
		
		List<String> parameters = new ArrayList<String>();
		
		for (int i = 0; i < parameter.length(); i++)
			if (parameter.charAt(i) == '(')
				for (int j = i + 1; j < parameter.length(); j++) {
					
					if (parameter.charAt(j) == ',') {
						
						parameters.add(parameter.substring(i + 1, j).trim());
						i = j;
					}
					if (parameter.charAt(j) == ')') {
						
						parameters.add(parameter.substring(i + 1, j).trim());
						return parameters;
					}
				}
		
		return parameters;
	}
}