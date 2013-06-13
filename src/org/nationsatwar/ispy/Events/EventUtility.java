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
	
	// Event variables
	public static String eventBlockPlacer = "event.blockPlacer";
	public static String eventBlockBreaker = "event.blockBreaker";
	public static String eventBlockLocation = "event.blockLocation";
	
	public static List<Trigger> getInitiatedTriggers(String worldName, String eventName) {
		
		List<Trigger> triggers = new ArrayList<Trigger>();
		
		File triggerDirectory = new File(worldName + ISpy.triggerPath);

		for (File triggerFile : triggerDirectory.listFiles()) {
			
			FileConfiguration triggerConfig = YamlConfiguration.loadConfiguration(triggerFile);
			
			if (triggerConfig != null && triggerConfig.getStringList(ISpy.configEventsPath).contains(eventName)) {
				
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