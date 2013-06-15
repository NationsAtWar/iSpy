package org.nationsatwar.ispy.Conditions;

import org.nationsatwar.ispy.Trigger;
import org.nationsatwar.ispy.Utility.ConfigParser;
import org.nationsatwar.ispy.Utility.Debugger;

public class RelationalConditions {
	
	public static boolean conditionEquals(Trigger trigger, String[] condition) {
		
		Object firstValue = ConfigParser.getLiteral(condition[0], trigger);
		Object secondValue = ConfigParser.getLiteral(condition[1], trigger);
		
		if (firstValue == null || secondValue == null)
			return false;
		
		if (firstValue.equals(secondValue))
			return true;
		
		return false;
	}
	
	public static boolean conditionNotEquals(Trigger trigger, String[] condition) {
		
		Object firstValue = ConfigParser.getLiteral(condition[0], trigger);
		Object secondValue = ConfigParser.getLiteral(condition[1], trigger);
		
		if (firstValue == null || secondValue == null)
			return false;
		
		if (!firstValue.equals(secondValue))
			return true;
		
		return false;
	}
	
	public static boolean conditionGreaterThan(Trigger trigger, String[] condition, boolean orEquals) {
		
		try {
		
			int firstValue = Integer.parseInt(ConfigParser.getLiteral(condition[0], trigger).toString());
			int secondValue = Integer.parseInt(ConfigParser.getLiteral(condition[1], trigger).toString());
			
			if (firstValue > secondValue)
				return true;
			else if (orEquals && firstValue == secondValue)
				return true;
		}
		catch (NumberFormatException e) { Debugger.notANumber(trigger, e); }
		
		return false;
	}
	
	public static boolean conditionLessThan(Trigger trigger, String[] condition, boolean orEquals) {
		
		try {
		
			int firstValue = Integer.parseInt(ConfigParser.getLiteral(condition[0], trigger).toString());
			int secondValue = Integer.parseInt(ConfigParser.getLiteral(condition[1], trigger).toString());
			
			if (firstValue < secondValue)
				return true;
			else if (orEquals && firstValue == secondValue)
				return true;
		}
		catch (NumberFormatException e) { Debugger.notANumber(trigger, e); }
		
		return false;
	}
}