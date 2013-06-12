package org.nationsatwar.ispy.Events;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.nationsatwar.ispy.ISpy;
import org.nationsatwar.ispy.Trigger;

public class EventUtility {
	
	public static String blockPlace = "block place";
	
	public static List<Trigger> initiateTriggers(String worldName, String eventName) {
		
		List<Trigger> triggers = new ArrayList<Trigger>();
		
		File triggerDirectory = new File(worldName + "/triggers/");

		for (File triggerFile : triggerDirectory.listFiles()) {
			
			FileConfiguration triggerConfig = YamlConfiguration.loadConfiguration(triggerFile);
			
			if (triggerConfig.getBoolean(ISpy.triggerEventsPath + "." + eventName)) {
				
				Trigger trigger = new Trigger(triggerFile.getName(), worldName);
				triggers.add(trigger);
			}
		}
		
		return triggers;
	}
}