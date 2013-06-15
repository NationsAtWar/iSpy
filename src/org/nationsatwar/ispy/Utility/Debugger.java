package org.nationsatwar.ispy.Utility;

import org.nationsatwar.ispy.ISpy;
import org.nationsatwar.ispy.Trigger;

public final class Debugger  {
	
	public static void triggerDoesntExist(String fileName) {
		
		ISpy.log(fileName + " does not exist.");
	}
	
	public static void invalidValue(Trigger trigger, String value) {
		
		ISpy.log(trigger.getTriggerFileName() + ": " + value + " is not a valid value.");
	}
	
	public static void invalidAction(Trigger trigger, String value) {
		
		ISpy.log(trigger.getTriggerFileName() + ": " + value + " is not a valid action.");
	}
	
	public static void notANumber(Trigger trigger, NumberFormatException message) {
		
		ISpy.log(trigger.getTriggerFileName() + ": Tried to parse an invalid number: " + message.getMessage());
	}
}