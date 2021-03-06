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

		// Checks to see if the value is a property
		for (int i = 0; i < value.length(); i++) {
			
			if (value.charAt(i) == '.') {
				
				String variable = value.substring(0, i);
				
				// Get Event Property if variable equals 'event'
				if (variable.equals("event")) {
					
					Object eventObject = EventUtility.getEventVariable(value, trigger);
					return eventObject;
				}
				
				// Get World Property if variable equals 'world'
				else if (variable.equals("world")) {
					
					Object eventObject = PropertyUtility.getWorldVariable(value, trigger.getWorldName());
					return eventObject;
				}
				
				// Get Trigger Property if variable equals 'trigger'
				else if (variable.equals("trigger")) {
					
					Object triggerVariableObject = getTriggerVariable(value.substring(i + 1), trigger);
					return triggerVariableObject;
				}
			}
		}
		
		// Checks to see if the value is a local or global variable
		Object variableObject = getVariable(value, trigger);
		
		return variableObject;
	}
	
	private static Object getVariable(String variableName, Trigger trigger) {
		
		// Load the configuration file that matches the trigger name
		File triggerFile = new File(trigger.getTriggerFileName());
		FileConfiguration triggerConfig = YamlConfiguration.loadConfiguration(triggerFile);
		
		String variablePath = ISpy.configVariablesLocalPath + "." + variableName;
		
		if (triggerConfig.isSet(variablePath)) {
			
			if (triggerConfig.isConfigurationSection(variablePath))
				return triggerConfig.getConfigurationSection(variablePath).getValues(true);
			else if (triggerConfig.isString(variablePath))
				return triggerConfig.getString(variablePath);
			else if (triggerConfig.isInt(variablePath))
				return triggerConfig.getInt(variablePath);
			else
				return triggerConfig.get(variablePath);
		}
		
		variablePath = ISpy.configVariablesGlobalPath + "." + variableName;

		if (triggerConfig.isSet(variablePath)) {
		
			if (triggerConfig.isConfigurationSection(variablePath))
				return triggerConfig.getConfigurationSection(variablePath).getValues(true);
			else if (triggerConfig.isString(variablePath))
				return triggerConfig.getString(variablePath);
			else if (triggerConfig.isInt(variablePath))
				return triggerConfig.getInt(variablePath);
			else
				return triggerConfig.get(variablePath);
		}
		
		return variableName;
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