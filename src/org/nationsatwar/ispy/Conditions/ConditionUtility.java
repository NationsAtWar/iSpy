package org.nationsatwar.ispy.Conditions;

import java.io.File;
import java.util.List;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.nationsatwar.ispy.ISpy;
import org.nationsatwar.ispy.Trigger;
import org.nationsatwar.ispy.Actions.ActionUtility;
import org.nationsatwar.ispy.Utility.ConfigParser;


public final class ConditionUtility {
	
	public static void checkConditions(List<Trigger> triggers) {
		
		for (Trigger trigger : triggers) {
			
			// Load the configuration file for each trigger
			File triggerFile = new File(trigger.getTriggerFileName());
			FileConfiguration triggerConfig = YamlConfiguration.loadConfiguration(triggerFile);
			
			// Cycle through each condition
			for (String condition : triggerConfig.getStringList(ISpy.configConditionsPath))
				if (!isTrueStatement(condition, trigger))
					return;
			
			// Execute Action List if all conditions are true
			ActionUtility.executeActions(trigger, triggerConfig.getStringList(ISpy.configActionsPath));
		}
	}
	
	private static boolean isTrueStatement(String condition, Trigger trigger) {
		
		for (int i = 1; i < condition.length(); i++) {
			
			if (condition.charAt(i) == '=' && condition.charAt(i - 1) == '=') {
				
				Object firstValue = ConfigParser.getLiteral(condition.substring(0, i - 2).trim(), trigger);
				Object secondValue = ConfigParser.getLiteral(condition.substring(i + 1).trim(), trigger);
				
				ISpy.log(firstValue.toString());
				ISpy.log(secondValue.toString());
				
				if (firstValue == null || secondValue == null)
					return false;
				
				if (firstValue.equals(secondValue))
					return true;
			}
		}
		
		return false;
	}
}