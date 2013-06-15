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
	private static String lessThanCondition = "<";
	private static String lessThanOrEqualCondition = "<=";
	private static String greaterThanCondition = ">";
	private static String greaterThanOrEqualCondition = ">=";
	private static String hasCondition = "has";
	private static String inCondition = "in";
	
	public static void checkConditions(List<Trigger> triggers, ISpy plugin) {
		
		for (Trigger trigger : triggers) {
			
			// Load the configuration file for each trigger
			File triggerFile = new File(trigger.getTriggerFileName());
			FileConfiguration triggerConfig = YamlConfiguration.loadConfiguration(triggerFile);
			
			boolean failedCondition = false;
			
			// Cycle through each condition
			for (String condition : triggerConfig.getStringList(ISpy.configConditionsPath))
				if (!isTrueStatement(condition, trigger, plugin)) {
					
					failedCondition = true;
					break;
				}
			
			if (failedCondition)
				continue;
			
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
	
	private static boolean isTrueStatement(String condition, Trigger trigger, ISpy plugin) {
		
		String[] parsedCondition = parseCondition(condition);
		
		String operator = parsedCondition[0].toLowerCase();
		
		if (operator.equals(equalsCondition))
			return RelationalConditions.conditionEquals(trigger, parseArguments(parsedCondition));
		
		else if (operator.equals(notEqualsCondition))
			return RelationalConditions.conditionNotEquals(trigger, parseArguments(parsedCondition));
		
		else if (operator.equals(lessThanCondition))
			return RelationalConditions.conditionLessThan(trigger, parseArguments(parsedCondition), false);
		
		else if (operator.equals(lessThanOrEqualCondition))
			return RelationalConditions.conditionLessThan(trigger, parseArguments(parsedCondition), true);
		
		else if (operator.equals(greaterThanCondition))
			return RelationalConditions.conditionGreaterThan(trigger, parseArguments(parsedCondition), false);
		
		else if (operator.equals(greaterThanOrEqualCondition))
			return RelationalConditions.conditionGreaterThan(trigger, parseArguments(parsedCondition), true);
		
		else if (operator.equals(hasCondition))
			return HasCondition.conditionHas(trigger, parseArguments(parsedCondition));
		
		else if (operator.equals(inCondition))
			return InCondition.conditionIn(trigger, parseArguments(parsedCondition), plugin, true);
		
		return false;
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

			// Returns arguments if condition contains the '<' operator
			minLength = i + lessThanCondition.length();
			if (condition.length() >= minLength && condition.substring(i, minLength).equals(lessThanCondition)) {
				
				String[] arguments = {
						condition.substring(i, minLength),		// Condition Operator
						condition.substring(0, i - 1).trim(),	// First Operand
						condition.substring(minLength).trim()	// Second Operand
				};
				
				return arguments;
			}

			// Returns arguments if condition contains the '<=' operator
			minLength = i + lessThanOrEqualCondition.length();
			if (condition.length() >= minLength && condition.substring(i, minLength).equals(lessThanOrEqualCondition)) {
				
				String[] arguments = {
						condition.substring(i, minLength),		// Condition Operator
						condition.substring(0, i - 1).trim(),	// First Operand
						condition.substring(minLength).trim()	// Second Operand
				};
				
				return arguments;
			}

			// Returns arguments if condition contains the '>' operator
			minLength = i + greaterThanCondition.length();
			if (condition.length() >= minLength && condition.substring(i, minLength).equals(greaterThanCondition)) {
				
				String[] arguments = {
						condition.substring(i, minLength),		// Condition Operator
						condition.substring(0, i - 1).trim(),	// First Operand
						condition.substring(minLength).trim()	// Second Operand
				};
				
				return arguments;
			}

			// Returns arguments if condition contains the '>=' operator
			minLength = i + greaterThanOrEqualCondition.length();
			if (condition.length() >= minLength && condition.substring(i, minLength).equals(greaterThanOrEqualCondition)) {
				
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

			// Returns arguments if condition contains the 'in' operator
			minLength = i + inCondition.length();
			if (condition.length() >= minLength && condition.substring(i, minLength).toLowerCase().equals(inCondition)) {
				
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