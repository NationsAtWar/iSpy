package org.nationsatwar.ispy.Conditions;

import java.util.Map;

import org.bukkit.Location;
import org.nationsatwar.ispy.ISpy;
import org.nationsatwar.ispy.Trigger;
import org.nationsatwar.ispy.SerializedObjects.ISpyLocation;
import org.nationsatwar.ispy.SerializedObjects.ISpyRegion;
import org.nationsatwar.ispy.Utility.ConfigParser;

public class InCondition {
	
	public static boolean conditionIn(Trigger trigger, String[] condition, ISpy plugin, boolean entering) {
		
		Object firstValue = ConfigParser.getLiteral(condition[0], trigger);
		Object secondValue = ConfigParser.getLiteral(condition[1], trigger);
		
		if (firstValue == null || secondValue == null)
			return false;
		
		// This will assume that the script is trying to see if a player is inside a region
		// TODO: This needs to be streamlined and worked almost entirely through ISpyLocations
		if (firstValue instanceof String && secondValue instanceof Map<?, ?>) {
			
			String playerName = (String) firstValue;
			
			@SuppressWarnings("unchecked")
			Map<String, Object> region = (Map<String, Object>) secondValue;

			Location location1 = ISpyRegion.getLocation1(region);
			Location location2 = ISpyRegion.getLocation2(region);
			
			ISpyLocation ispyLocation1 = new ISpyLocation(location1);
			ISpyLocation ispyLocation2 = new ISpyLocation(location2);
			
			ISpyRegion iSpyRegion = new ISpyRegion(ispyLocation1, ispyLocation2);
			
			if (plugin.triggerManager.containsPlayerRegion(playerName, iSpyRegion.serialize()))
				return true;
			else
				return false;
		}
		
		return false;
	}
}