package org.nationsatwar.ispy;

import org.nationsatwar.ispy.Events.EventUtility;

public final class ConfigParser  {
	
	public static Object getLiteral(String value, Trigger trigger) {
		
		// If the value is itself a literal, remove quotations and return
		if (value.charAt(0) == '"')
			for (int i = 1; i < value.length(); i++)
				if (value.charAt(i) == '"')
					return value.substring(1, i);
		
		Object eventObject = EventUtility.getEventVariable(value, trigger);
		
		return eventObject;
	}
}