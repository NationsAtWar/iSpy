package org.nationsatwar.ispy.Utility;

import org.bukkit.Bukkit;
import org.bukkit.World;

public class PropertyUtility {
	
	// World variables
	public static String worldTime = "world.time";
	public static String worldMoonPhase = "world.moonPhase";
	public static String worldWeather = "world.weather";
	public static String worldPlayerCount = "world.playerCount";
	
	public static Object getWorldVariable(String property, String worldName) {
		
		World world = Bukkit.getServer().getWorld(worldName);
		
		if (property.equals(worldTime))
			return world.getTime();
		
		if (property.equals(worldMoonPhase)) {
			
			int days = (int) (world.getFullTime() / 24000);
			int phase = days % 8;
			
			if (phase == 0)
				return "full moon";
			else if (phase == 1)
				return "waning gibbous";
			else if (phase == 2)
				return "waning half";
			else if (phase == 3)
				return "waning crescent";
			else if (phase == 4)
				return "new moon";
			else if (phase == 5)
				return "waxing crescent";
			else if (phase == 6)
				return "waxing half";
			else if (phase == 7)
				return "waxing gibbous";
		}
		
		if (property.equals(worldWeather)) {
			
			if (world.isThundering())
				return "storm";
			else if (world.hasStorm())
				return "rain";
			else
				return "clear";
		}
		
		if (property.equals(worldPlayerCount))
			return world.getPlayers().size();
		
		return property;
	}
}