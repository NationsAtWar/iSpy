package org.nationsatwar.ispy.Conditions;

import org.nationsatwar.ispy.ISpy;
import org.nationsatwar.ispy.Trigger;

public class LogicalConditions {
	
	public static boolean conditionAnd(Trigger trigger, String[] condition, ISpy plugin) {
		
		if (ConditionUtility.isTrueStatement(condition[1], trigger, plugin) &&
				ConditionUtility.isTrueStatement(condition[2], trigger, plugin))
			return true;
		else
			return false;
	}
	
	public static boolean conditionOr(Trigger trigger, String[] condition, ISpy plugin) {
		
		if (ConditionUtility.isTrueStatement(condition[1], trigger, plugin) ||
				ConditionUtility.isTrueStatement(condition[2], trigger, plugin))
			return true;
		else
			return false;
	}
	
	public static boolean conditionXor(Trigger trigger, String[] condition, ISpy plugin) {
		
		if ((ConditionUtility.isTrueStatement(condition[1], trigger, plugin) &&
				!ConditionUtility.isTrueStatement(condition[2], trigger, plugin)) ||
				(!ConditionUtility.isTrueStatement(condition[1], trigger, plugin) &&
						ConditionUtility.isTrueStatement(condition[2], trigger, plugin)))
			return true;
		else
			return false;
	}
}