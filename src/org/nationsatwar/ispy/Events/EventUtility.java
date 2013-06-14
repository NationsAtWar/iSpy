package org.nationsatwar.ispy.Events;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.nationsatwar.ispy.ISpy;
import org.nationsatwar.ispy.Trigger;

public final class EventUtility {
	
	// Event Names
	public static String blockPlace = "block.place";
	public static String blockBreak = "block.break";
	public static String blockUse = "block.use";
	
	// Event variables
	public static String eventBlockPlacer = "event.blockPlacer";
	public static String eventBlockBreaker = "event.blockBreaker";
	public static String eventBlockLocation = "event.blockLocation";
	
	public static List<Trigger> getInitiatedTriggers(String worldName, String eventName) {
		
		List<Trigger> triggers = new ArrayList<Trigger>();
		
		File triggerDirectory = new File(worldName + ISpy.triggerPath);
		
		if (!triggerDirectory.exists())
			return triggers;

		for (File triggerFile : triggerDirectory.listFiles()) {
			
			FileConfiguration triggerConfig = YamlConfiguration.loadConfiguration(triggerFile);
			
			boolean triggerActive = triggerConfig.getBoolean(ISpy.configActivePath); // Trigger must be active
			int triggerCounter = triggerConfig.getInt(ISpy.configCounterPath); // Counter must not be 0
			
			// If config file exists, is active, the counter is not at 0, 
			// and contains the event name, then add trigger to List
			if (triggerConfig != null && triggerActive && triggerCounter != 0 && 
					triggerConfig.getStringList(ISpy.configEventsPath).contains(eventName)) {
				
				Trigger trigger = new Trigger(triggerFile.getName().replaceFirst("[.][^.]+$", ""), worldName);
				triggers.add(trigger);
			}
		}
		
		return triggers;
	}
	
	public static Object getEventVariable(String property, Trigger trigger) {
		
		if (property.equals(eventBlockPlacer))
			return trigger.getBlockPlacer();
		if (property.equals(eventBlockBreaker))
			return trigger.getBlockBreaker();
		else if (property.equals(eventBlockLocation))
			return trigger.getBlockLocation();
		
		return property;
	}
}