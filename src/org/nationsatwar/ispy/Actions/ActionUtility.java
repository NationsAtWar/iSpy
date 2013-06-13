package org.nationsatwar.ispy.Actions;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.logging.Logger;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.configuration.serialization.ConfigurationSerializable;
import org.nationsatwar.ispy.ConfigParser;
import org.nationsatwar.ispy.ISpy;
import org.nationsatwar.ispy.Trigger;

public final class ActionUtility {
	
	private static Logger log = Logger.getLogger("Minecraft");
	
	public static void executeActions(Trigger trigger, List<String> actionList) {
		
		// Cycle through each action
		for (String action : actionList) {
			
			for (int i = 0; i < action.length(); i++) {
				
				// Assignment Operation
				if (action.charAt(i) == '=') {
					
					assignmentAction(action, trigger, i);
					return;
				}
			}
		}
	}
	
	private static void assignmentAction(String action, Trigger trigger, int assignmentOperatorPos) {
		
		String variableName = (String) ConfigParser.getLiteral(action.substring(0, assignmentOperatorPos - 1).trim(), trigger);
		Object variableValue = ConfigParser.getLiteral(action.substring(assignmentOperatorPos + 1).trim(), trigger);
		ConfigurationSerializable serializedValue = (variableValue instanceof ConfigurationSerializable) ? 
			(ConfigurationSerializable) variableValue : null;
		
		// Load trigger config
		File dataFile = new File(trigger.getWorldName() + ISpy.triggerPath + trigger.getTriggerName());
		FileConfiguration triggerConfig = YamlConfiguration.loadConfiguration(dataFile);

		String globalPath = ISpy.configVariablesGlobalPath + "." + variableName;
		String localPath = ISpy.configVariablesLocalPath + "." + variableName;
		
		// Copy to global variable if it exists
		if (triggerConfig.contains(globalPath)) {
			
			if (serializedValue != null)
				triggerConfig.set(globalPath, serializedValue.serialize());
			else
				triggerConfig.set(globalPath, variableValue);
		}
		
		// Copy to local variable if it exists
		if (triggerConfig.contains(localPath)) {
			
			if (serializedValue != null)
				triggerConfig.set(localPath, serializedValue.serialize());
			else
				triggerConfig.set(localPath, variableValue);
		}
		
	    try { triggerConfig.save(dataFile); }
	    catch (IOException e) { log.info("Failure saving config: " + e.getMessage()); }
	}
}