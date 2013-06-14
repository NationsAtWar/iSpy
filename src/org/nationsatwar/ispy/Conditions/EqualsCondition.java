package org.nationsatwar.ispy.Conditions;

import org.nationsatwar.ispy.Trigger;
import org.nationsatwar.ispy.Utility.ConfigParser;

public class EqualsCondition {
	
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
}