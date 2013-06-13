package org.nationsatwar.ispy.Utility;

import java.io.File;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.nationsatwar.ispy.ISpy;
import org.nationsatwar.ispy.Trigger;
import org.nationsatwar.ispy.Events.EventUtility;

public final class ConfigParser  {
	
	public static Object getLiteral(String value, Trigger trigger) {
		
		// If the value is itself a literal, remove quotations and return
		if (value.charAt(0) == '"')
			for (int i = 1; i < value.length(); i++)
				if (value.charAt(i) == '"')
					return value.substring(1, i);
		
		for (int i = 0; i < value.length(); i++) {
			
			if (value.charAt(i) == '.') {
				
				String property = value.substring(0, i);
				
				// Get Event Object if property contains 'event'
				if (property.equals("event")) {
					
					Object eventObject = EventUtility.getEventVariable(value, trigger);
					return eventObject;
				}
				
				// Get Trigger Variable Object if property contains 'trigger'
				if (property.equals("trigger")) {
					
					Object triggerVariableObject = getTriggerVariable(value.substring(i + 1), trigger);
					return triggerVariableObject;
				}
			}
		}
		
		return value;
	}
	
	private static Object getTriggerVariable(String property, Trigger trigger) {
		
		for (int i = 0; i < property.length(); i++) {
			
			if (property.charAt(i) == '.') {
				
				String triggerName = property.substring(0, i);
				String variableName = property.substring(i + 1);
				
				// Load the configuration file that matches the trigger name
				File triggerFile = new File(trigger.getWorldName() + ISpy.triggerPath + triggerName + ISpy.triggerExtension);
				FileConfiguration triggerConfig = YamlConfiguration.loadConfiguration(triggerFile);
				
				String variablePath = ISpy.configVariablesGlobalPath + "." + variableName;
				
				if (triggerConfig.isString(variablePath))
					return triggerConfig.getString(variablePath);
				
				if (triggerConfig.getConfigurationSection(variablePath) == null)
					return property;
				
				return triggerConfig.getConfigurationSection(variablePath).getValues(true);
			}
		}
		
		return property;
	}
}