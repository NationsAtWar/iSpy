package org.nationsatwar.ispy.Utility;

import org.bukkit.Bukkit;
import org.bukkit.World;

public class PropertyUtility {
	
	// World variables
	public static String worldTime = "world.time";
	public static String worldWeather = "world.weather";
	public static String worldPlayerCount = "world.playerCount";
	
	public static Object getWorldVariable(String property, String worldName) {
		
		World world = Bukkit.getServer().getWorld(worldName);
		
		if (property.equals(worldTime))
			return world.getTime();
		
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