package org.nationsatwar.ispy.Conditions;

import java.io.File;
import java.io.IOException;
import java.util.List;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.nationsatwar.ispy.ISpy;
import org.nationsatwar.ispy.Trigger;
import org.nationsatwar.ispy.Actions.ActionUtility;

public final class ConditionUtility {
	
	private static String equalsCondition = "==";
	private static String notEqualsCondition = "!=";
	private static String hasCondition = "has";
	
	public static void checkConditions(List<Trigger> triggers) {
		
		for (Trigger trigger : triggers) {
			
			// Load the configuration file for each trigger
			File triggerFile = new File(trigger.getTriggerFileName());
			FileConfiguration triggerConfig = YamlConfiguration.loadConfiguration(triggerFile);
			
			// Cycle through each condition
			for (String condition : triggerConfig.getStringList(ISpy.configConditionsPath))
				if (!isTrueStatement(condition, trigger))
					return;
			
			int triggerCounter = triggerConfig.getInt(ISpy.configCounterPath);
			
			// Tick counter down if eligible
			if (triggerCounter > 0) {
				
				triggerConfig.set(ISpy.configCounterPath, triggerCounter - 1);
				
				try { triggerConfig.save(triggerFile); }
			    catch (IOException e) { ISpy.log("Error saving config file: " + e.getMessage()); }
			}
			
			// Execute Action List if all conditions are true
			ActionUtility.executeActions(trigger, triggerConfig.getStringList(ISpy.configActionsPath));
		}
	}
	
	private static boolean isTrueStatement(String condition, Trigger trigger) {
		
		String[] parsedCondition = parseCondition(condition);
		
		String operator = parsedCondition[0].toLowerCase();
		
		if (operator.equals(equalsCondition))
			return EqualsCondition.conditionEquals(trigger, parseArguments(parsedCondition));
		
		else if (operator.equals(notEqualsCondition))
			return EqualsCondition.conditionNotEquals(trigger, parseArguments(parsedCondition));
		
		else if (operator.equals(hasCondition))
			return HasCondition.conditionHas(trigger, parseArguments(parsedCondition));
		
		return false;
		/*
		
		for (int i = 1; i < condition.length(); i++) {
			
			// Returns true if the first operand equals the second operand
			if (condition.charAt(i) == '=' && condition.charAt(i - 1) == '=') {
				
				Object firstValue = ConfigParser.getLiteral(condition.substring(0, i - 2).trim(), trigger);
				Object secondValue = ConfigParser.getLiteral(condition.substring(i + 1).trim(), trigger);
				
				if (firstValue == null || secondValue == null)
					return false;
				
				if (firstValue.equals(secondValue))
					return true;
			}

			// Returns true if the first operand does not equal the second operand
			else if (condition.charAt(i) == '=' && condition.charAt(i - 1) == '!') {
				
				Object firstValue = ConfigParser.getLiteral(condition.substring(0, i - 2).trim(), trigger);
				Object secondValue = ConfigParser.getLiteral(condition.substring(i + 1).trim(), trigger);
				
				if (firstValue == null || secondValue == null)
					return false;
				
				if (!firstValue.equals(secondValue))
					return true;
			}

			// Returns true if the first operand contains the second operand
			else if (condition.length() >= i + 3 && condition.substring(i, i + 3).toLowerCase().equals("has")) {
				
				Object firstValue = ConfigParser.getLiteral(condition.substring(0, i - 1).trim(), trigger);
				Object secondValue = ConfigParser.getLiteral(condition.substring(i + 3).trim(), trigger);
				
				if (firstValue == null || secondValue == null)
					return false;
				
				// This will assume that the script is trying to see if a player has an item
				// TODO: This needs to be much more versatile, re-work it as appropriate
				if (firstValue instanceof String && secondValue instanceof Map<?, ?>) {

					@SuppressWarnings("unchecked")
					Map<String, Object> map = (Map<String, Object>) secondValue;
					
					ItemStack itemstack = ISpyItemStack.getItemStack(map);
					
					Inventory playerInventory = Bukkit.getServer().getPlayer(firstValue.toString()).getInventory();
					
					if (playerInventory.contains(itemstack))
						return true;
				}
				
				if (firstValue.equals(secondValue))
					return true;
			}
		}
		
		return false;
		
		*/
	}
	
	private static String[] parseCondition(String condition) {
		
		int minLength;
		
		for (int i = 0; i < condition.length(); i++) {
			
			// minLength mainly protects against out of bounds errors, and keeps parsing numbers clean looking
			minLength = 0;

			// Returns arguments if condition contains the '==' operator
			minLength = i + equalsCondition.length();
			if (condition.length() >= minLength && condition.substring(i, minLength).equals(equalsCondition)) {
				
				String[] arguments = {
						condition.substring(i, minLength),		// Condition Operator
						condition.substring(0, i - 1).trim(),	// First Operand
						condition.substring(minLength).trim()	// Second Operand
				};
				
				return arguments;
			}

			// Returns arguments if condition contains the '!=' operator
			minLength = i + notEqualsCondition.length();
			if (condition.length() >= minLength && condition.substring(i, minLength).equals(notEqualsCondition)) {
				
				String[] arguments = {
						condition.substring(i, minLength),		// Condition Operator
						condition.substring(0, i - 1).trim(),	// First Operand
						condition.substring(minLength).trim()	// Second Operand
				};
				
				return arguments;
			}

			// Returns arguments if condition contains the 'has' operator
			minLength = i + hasCondition.length();
			if (condition.length() >= minLength && condition.substring(i, minLength).toLowerCase().equals(hasCondition)) {
				
				String[] arguments = {
						condition.substring(i, minLength),		// Condition Operator
						condition.substring(0, i - 1).trim(),	// First Operand
						condition.substring(minLength).trim()	// Second Operand
				};
				
				return arguments;
			}
		}
		
		return null;
	}
	
	private static String[] parseArguments(String[] parsedCondition) {
		
		String[] arguments = new String[parsedCondition.length - 1];
		
		for (int i = 1; i < parsedCondition.length; i++)
			arguments[i - 1] = parsedCondition[i];
		
		return arguments;
	}
}