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
	private static String andCondition = "&&";
	private static String orCondition = "||";
	private static String xorCondition = "^";
	private static String hasCondition = "has";
	private static String notHasCondition = "!has";
	private static String inCondition = "in";
	private static String notInCondition = "!in";

	/**
	 * The master function for checking to see if all conditions in each trigger are true or not.
	 * <p>
	 * For each trigger that passes every condition, this function will immediately pass the trigger 
	 * to the ActionUtility class to go through the action list
	 * 
	 * @param triggers  The list of triggers that match the event type
	 * @param plugin  The master plugin. Some conditions/actions depend on the manager inside of it
	 */
	public static void checkConditions(List<Trigger> triggers, ISpy plugin) {
		
		for (Trigger trigger : triggers) {
			
			// Load the configuration file for each trigger
			File triggerFile = new File(trigger.getTriggerFileName());
			FileConfiguration triggerConfig = YamlConfiguration.loadConfiguration(triggerFile);
			
			boolean failedCondition = false;
			
			// Cycle through each condition
			for (String condition : triggerConfig.getStringList(ISpy.configConditionsPath)) {

				// Separate Conditions (Those in parenthesis, or those implied by logical operators)
				if (!separateConditions(condition, trigger, plugin)) {
					
					failedCondition = true;
					break;
				}
			}
			
			// If a condition fails, then move onto the next trigger
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
			ActionUtility.executeActions(trigger, triggerConfig.getStringList(ISpy.configActionsPath), plugin);
		}
	}
	
	/**
	 * Once a condition is fully parsed, each expression will ultimately visit this function to see if 
	 * it's true or not
	 * 
	 * @param expression  The simplified expression
	 * @param trigger  The trigger class that holds important information for the function to check against
	 * @param plugin  The master plugin
	 * 
	 * @return  true if expression is true, false otherwise
	 */
	public static boolean isTrueStatement(String expression, Trigger trigger, ISpy plugin) {
		
		// 'true' and 'false' are themselves literals that mean what you think they mean
		if (expression.toLowerCase().equals("true"))
			return true;
		else if (expression.toLowerCase().equals("false"))
			return false;
		
		// Gets the operator in the [0] array, and additional operands as well
		String[] parsedExpression = parseExpression(expression);
		
		// stores the operator for easy access
		String operator = parsedExpression[0].toLowerCase();
		
		if (operator.equals(equalsCondition)) // if '=='
			return RelationalConditions.conditionEquals(trigger, parseArguments(parsedExpression));
		
		else if (operator.equals(notEqualsCondition)) // if '!='
			return RelationalConditions.conditionNotEquals(trigger, parseArguments(parsedExpression));
		
		else if (operator.equals(lessThanCondition)) // if '<'
			return RelationalConditions.conditionLessThan(trigger, parseArguments(parsedExpression), false);
		
		else if (operator.equals(lessThanOrEqualCondition)) // if '<='
			return RelationalConditions.conditionLessThan(trigger, parseArguments(parsedExpression), true);
		
		else if (operator.equals(greaterThanCondition)) // if '>'
			return RelationalConditions.conditionGreaterThan(trigger, parseArguments(parsedExpression), false);
		
		else if (operator.equals(greaterThanOrEqualCondition)) // if '>='
			return RelationalConditions.conditionGreaterThan(trigger, parseArguments(parsedExpression), true);
		
		else if (operator.equals(hasCondition)) // if 'has'
			return HasCondition.conditionHas(trigger, parseArguments(parsedExpression), false);
		
		else if (operator.equals(notHasCondition)) // if '!has'
			return HasCondition.conditionHas(trigger, parseArguments(parsedExpression), true);
		
		else if (operator.equals(inCondition)) // if 'in'
			return InCondition.conditionIn(trigger, parseArguments(parsedExpression), plugin, true, false);
		
		else if (operator.equals(notInCondition)) // if '!in'
			return InCondition.conditionIn(trigger, parseArguments(parsedExpression), plugin, true, true);
		
		// Return false if function can't recognize the operator
		return false;
	}
	
	/**
	 * Some conditions have multiple expressions denoted by parenthesis or simply by having a chain 
	 * of expressions in the same line.
	 * <p>
	 * This function will parse them into individual expressions and see if each one is true,
	 * returning the result
	 * 
	 * @param condition  The condition to separate
	 * @param trigger  The trigger class
	 * @param plugin  The master plugin
	 * 
	 * @return  true if all expressions are true, false otherwise
	 */
	private static boolean separateConditions(String condition, Trigger trigger, ISpy plugin) {
		
		// Determines where the separated expression begins
		int openingParenthesisPosition = 0;
		
		for (int i = 0; i < condition.length(); i++) {
			
			if (condition.charAt(i) == '(') {
				
				openingParenthesisPosition = i;
				continue;
			}

			if (condition.charAt(i) == ')') {
				
				// Stores the result as either 'true' or 'false'
				String separatedExpression = condition.substring(openingParenthesisPosition + 1, i);
				String result = calculateExpression(separatedExpression, trigger, plugin);
				
				condition = condition.substring(0, openingParenthesisPosition) + result + condition.substring(i + 1);
				
				// Starts the loop over
				openingParenthesisPosition = 0;
				i = 0;
				continue;
			}
		}
		
		// If condition is down to only one expression, then it will calculate here and return
		if (calculateExpression(condition, trigger, plugin).equals("true"))
			return true;
		else
			return false;
	}

	/**
	 * The parsed expression will eventually make its way here to see if it's a logical expression or not.
	 * All expressions, logical or otherwise, will be determined to be true or false and then sent back
	 * as a 'true' or 'false' string value for additional parsing.
	 * 
	 * @param expression  The expression to calculate
	 * @param trigger  The trigger class
	 * @param plugin  The master plugin
	 * 
	 * @return  Returns "true" if the expression is true, "false" otherwise
	 */
	private static String calculateExpression(String expression, Trigger trigger, ISpy plugin) {
		
		String logicalOperator = "";
		String firstOperand = "";
		int operatorPosition = 0;
		
		boolean logicalOperatorFound = false;
		
		// Will cycle through the string to location any logical expression operators if they exist
		for (int i = 0; i < expression.length(); i++) {
			
			int minLength = i + andCondition.length();
			if (expression.length() >= minLength && expression.substring(i, minLength).equals(andCondition)) {
				
				firstOperand = expression.substring(0, i - 1);
				logicalOperator = andCondition;
				operatorPosition = i;
				logicalOperatorFound = true;
				break;
			}
			
			minLength = i + orCondition.length();
			if (expression.length() >= minLength && expression.substring(i, minLength).equals(orCondition)) {
				
				firstOperand = expression.substring(0, i - 1);
				logicalOperator = orCondition;
				operatorPosition = i;
				logicalOperatorFound = true;
				break;
			}
			
			minLength = i + xorCondition.length();
			if (expression.length() >= minLength && expression.substring(i, minLength).equals(xorCondition)) {
				
				firstOperand = expression.substring(0, i - 1);
				logicalOperator = xorCondition;
				operatorPosition = i;
				logicalOperatorFound = true;
				break;
			}
		}
		
		// If a logical operator is found, then they are sent to their respective functions
		if (logicalOperatorFound) {
			
			String[] arguments = {
					logicalOperator, firstOperand,
					expression.substring(operatorPosition + logicalOperator.length() + 1)	// Second Operand
			};
			
			if (logicalOperator.equals(andCondition)) {
			
				if (LogicalConditions.conditionAnd(trigger, arguments, plugin))
					return "true";
				else
					return "false";
			}
			
			else if (logicalOperator.equals(orCondition)) {
			
				if (LogicalConditions.conditionOr(trigger, arguments, plugin))
					return "true";
				else
					return "false";
			}
			
			else if (logicalOperator.equals(xorCondition)) {
			
				if (LogicalConditions.conditionXor(trigger, arguments, plugin))
					return "true";
				else
					return "false";
			}
		}
		
		// Otherwise the expression is a simple statement and will be calculated and returned
		else {
			
			if (isTrueStatement(expression, trigger, plugin))
				return "true";
			else
				return "false";
		}
		
		return "false";
	}

	/**
	 * A simplified expression will typically contain an operator and two operands, they will be returned
	 * as a String Array with this function
	 * 
	 * @param expression  The expression to parse
	 * 
	 * @return  String array with operator and operands
	 */
	private static String[] parseExpression(String expression) {
		
		int minLength;
		
		for (int i = 0; i < expression.length(); i++) {
			
			// minLength mainly protects against out of bounds errors, and keeps parsing numbers clean looking
			minLength = 0;

			// Returns arguments if condition contains the '==' operator
			minLength = i + equalsCondition.length();
			if (expression.length() >= minLength && expression.substring(i, minLength).equals(equalsCondition)) {
				
				String[] arguments = {
						expression.substring(i, minLength),		// Condition Operator
						expression.substring(0, i - 1).trim(),	// First Operand
						expression.substring(minLength).trim()	// Second Operand
				};
				
				return arguments;
			}

			// Returns arguments if condition contains the '!=' operator
			minLength = i + notEqualsCondition.length();
			if (expression.length() >= minLength && expression.substring(i, minLength).equals(notEqualsCondition)) {
				
				String[] arguments = {
						expression.substring(i, minLength),		// Condition Operator
						expression.substring(0, i - 1).trim(),	// First Operand
						expression.substring(minLength).trim()	// Second Operand
				};
				
				return arguments;
			}

			// Returns arguments if condition contains the '<' operator
			minLength = i + lessThanCondition.length();
			if (expression.length() >= minLength && expression.substring(i, minLength).equals(lessThanCondition)) {
				
				String[] arguments = {
						expression.substring(i, minLength),		// Condition Operator
						expression.substring(0, i - 1).trim(),	// First Operand
						expression.substring(minLength).trim()	// Second Operand
				};
				
				return arguments;
			}

			// Returns arguments if condition contains the '<=' operator
			minLength = i + lessThanOrEqualCondition.length();
			if (expression.length() >= minLength && expression.substring(i, minLength).equals(lessThanOrEqualCondition)) {
				
				String[] arguments = {
						expression.substring(i, minLength),		// Condition Operator
						expression.substring(0, i - 1).trim(),	// First Operand
						expression.substring(minLength).trim()	// Second Operand
				};
				
				return arguments;
			}

			// Returns arguments if condition contains the '>' operator
			minLength = i + greaterThanCondition.length();
			if (expression.length() >= minLength && expression.substring(i, minLength).equals(greaterThanCondition)) {
				
				String[] arguments = {
						expression.substring(i, minLength),		// Condition Operator
						expression.substring(0, i - 1).trim(),	// First Operand
						expression.substring(minLength).trim()	// Second Operand
				};
				
				return arguments;
			}

			// Returns arguments if condition contains the '>=' operator
			minLength = i + greaterThanOrEqualCondition.length();
			if (expression.length() >= minLength && expression.substring(i, minLength).equals(greaterThanOrEqualCondition)) {
				
				String[] arguments = {
						expression.substring(i, minLength),		// Condition Operator
						expression.substring(0, i - 1).trim(),	// First Operand
						expression.substring(minLength).trim()	// Second Operand
				};
				
				return arguments;
			}

			// Returns arguments if condition contains the 'has' operator
			minLength = i + hasCondition.length();
			if (expression.length() >= minLength && expression.substring(i, minLength).toLowerCase().equals(hasCondition)) {
				
				String[] arguments = {
						expression.substring(i, minLength),		// Condition Operator
						expression.substring(0, i - 1).trim(),	// First Operand
						expression.substring(minLength).trim()	// Second Operand
				};
				
				return arguments;
			}

			// Returns arguments if condition contains the 'not has' operator
			minLength = i + notHasCondition.length();
			if (expression.length() >= minLength && expression.substring(i, minLength).toLowerCase().equals(notHasCondition)) {
				
				String[] arguments = {
						expression.substring(i, minLength),		// Condition Operator
						expression.substring(0, i - 1).trim(),	// First Operand
						expression.substring(minLength).trim()	// Second Operand
				};
				
				return arguments;
			}

			// Returns arguments if condition contains the 'in' operator
			minLength = i + inCondition.length();
			if (expression.length() >= minLength && expression.substring(i, minLength).toLowerCase().equals(inCondition)) {
				
				String[] arguments = {
						expression.substring(i, minLength),		// Condition Operator
						expression.substring(0, i - 1).trim(),	// First Operand
						expression.substring(minLength).trim()	// Second Operand
				};
				
				return arguments;
			}

			// Returns arguments if condition contains the 'not in' operator
			minLength = i + notInCondition.length();
			if (expression.length() >= minLength && expression.substring(i, minLength).toLowerCase().equals(notInCondition)) {
				
				String[] arguments = {
						expression.substring(i, minLength),		// Condition Operator
						expression.substring(0, i - 1).trim(),	// First Operand
						expression.substring(minLength).trim()	// Second Operand
				};
				
				return arguments;
			}
		}
		
		return null;
	}

	/**
	 * This is a simple function that simply removes the operator from an argument list
	 * Operations are implied by the function name that these arguments are passed to
	 * 
	 * @param parsedCondition  The argument list to parse
	 * 
	 * @return  String Array with operands only
	 */
	private static String[] parseArguments(String[] parsedCondition) {
		
		String[] arguments = new String[parsedCondition.length - 1];
		
		for (int i = 1; i < parsedCondition.length; i++)
			arguments[i - 1] = parsedCondition[i];
		
		return arguments;
	}
}