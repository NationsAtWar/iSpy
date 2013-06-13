package org.nationsatwar.ispy.Utility;

import org.nationsatwar.ispy.ISpy;
import org.nationsatwar.ispy.Trigger;

public final class Debugger  {
	
	public static void invalidValue(Trigger trigger, String value) {
		
		ISpy.log(trigger.getTriggerFileName() + ": " + value + " is not a valid value.");
	}
	
	public static void invalidAction(Trigger trigger, String value) {
		
		ISpy.log(trigger.getTriggerFileName() + ": " + value + " is not a valid action.");
	}
}