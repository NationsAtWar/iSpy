package org.nationsatwar.ispy.Actions;

import java.io.File;
import java.io.IOException;
import java.util.List;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.configuration.serialization.ConfigurationSerializable;
import org.nationsatwar.ispy.ISpy;
import org.nationsatwar.ispy.Trigger;
import org.nationsatwar.ispy.Utility.ConfigParser;
import org.nationsatwar.ispy.Utility.Debugger;

public final class ActionUtility {
	
	private static String messageAction = "message";
	
	public static void executeActions(Trigger trigger, List<String> actionList) {
		
		// Cycle through each action
		for (String action : actionList) {
			
			String actionType = getActionType(action);

			// Check if message operation
			if (actionType.equals(messageAction)) {
				
				if (!MessageAction.execute(trigger, action.substring(messageAction.length() + 1)))
					Debugger.invalidAction(trigger, action);
				
				continue;
			}
			
			// Check if assignment operation
			else {
				
				boolean validAction = false;
				
				for (int i = 0; i < action.length(); i++) {
					
					// Assignment Operation
					if (action.charAt(i) == '=') {
						
						assignmentAction(action, trigger, i);
						validAction = true;
						break;
					}
				}
				
				if (validAction)
					continue;
				
				// Invalid operation
				Debugger.invalidAction(trigger, action);
			}
		}
	}
	
	private static void assignmentAction(String action, Trigger trigger, int assignmentOperatorPos) {
		
		String firstValue = action.substring(0, assignmentOperatorPos - 1).trim();
		String secondValue = action.substring(assignmentOperatorPos + 1).trim();
		
		String variableName = (String) ConfigParser.getLiteral(firstValue, trigger);
		Object variableValue = ConfigParser.getLiteral(secondValue, trigger);
		ConfigurationSerializable serializedValue = (variableValue instanceof ConfigurationSerializable) ? 
			(ConfigurationSerializable) variableValue : null;
		
		if (variableValue == null) {
			
			Debugger.invalidValue(trigger, secondValue);
			return;
		}
		
		// Load trigger config
		File dataFile = new File(trigger.getTriggerFileName());
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
	    catch (IOException e) { ISpy.log("Failure saving config: " + e.getMessage()); }
	}
	
	private static String getActionType(String action) {
		
		for (int i = 1; i < action.length(); i++)
			if (action.charAt(i) == ' ')
				return action.substring(0, i);;
			
		return "";
	}
}